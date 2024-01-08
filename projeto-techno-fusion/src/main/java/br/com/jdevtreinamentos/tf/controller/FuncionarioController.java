package br.com.jdevtreinamentos.tf.controller;

import java.io.IOException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.codec.binary.Base64;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

import br.com.jdevtreinamentos.tf.controller.infra.OperacaoErro;
import br.com.jdevtreinamentos.tf.controller.infra.ResponseEntity;
import br.com.jdevtreinamentos.tf.controller.infra.StatusResposta;
import br.com.jdevtreinamentos.tf.exception.DadosInvalidosException;
import br.com.jdevtreinamentos.tf.infrastructure.dao.impl.DAOFuncionario;
import br.com.jdevtreinamentos.tf.model.Funcionario;
import br.com.jdevtreinamentos.tf.model.Telefone;
import br.com.jdevtreinamentos.tf.model.enumeration.EnumSexo;
import br.com.jdevtreinamentos.tf.model.enumeration.PerfilFuncionario;
import br.com.jdevtreinamentos.tf.util.Calculador;
import br.com.jdevtreinamentos.tf.util.GeradorSenha;
import br.com.jdevtreinamentos.tf.util.Pagination;

/**
 * Classe responsável por receber e processar as requisições para os recursos
 * relacionados aos serviços da entidade <strong>Funcionário<strong>.
 * 
 * @author Erik Vasconcelos
 * @since 2023-12-19
 * @version 0.2 2023-12-30
 */

@MultipartConfig
@WebServlet(urlPatterns = { "/funcionario", "/funcionario/editar", "/funcionario/pesquisar", "/funcionario/excluir"})
public class FuncionarioController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final Integer PAGINA_INICIAL = 1;
	private static final Integer REGISTROS_POR_PAGINA = 5;

	private static final Double SALARIO_MAXIMO = 100000.0;
	private static final Integer QTD_MIN_CARACTERES_LOGIN = 5;
	private static final Integer QTD_MAX_CARACTERES_LOGIN = 8;

	private DAOFuncionario daoFuncionario;

	public FuncionarioController() {
		daoFuncionario = new DAOFuncionario();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//TODO - Implementar o download de imagem
		
		try {
			String pageString = request.getParameter("page");

			if (request.getServletPath().endsWith("editar")) {
				String idString = request.getParameter("id");

				if (idString != null && !idString.trim().isEmpty()) {
					Long id = Long.parseLong(idString);

					Optional<Funcionario> optional = daoFuncionario.buscarPorId(id);

					if (optional.isPresent()) {
						ObjectMapper mapper = JsonMapper.builder().findAndAddModules().build();

						String json = mapper.writeValueAsString(optional.get());

						response.getWriter().write(json);
						response.setContentType("application/json");
					} else {
						response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
						response.getWriter().write("Erro: não foi possível encontrar o funcionário #" + id);
						response.setContentType("text/plain");
					}
				}

			} else if (request.getServletPath().endsWith("excluir")) {
				
				String idString =request.getParameter("id");
				
				if(idString != null && !idString.trim().isEmpty()) {
					Long id = Long.parseLong(idString);
					
					boolean funcionarioExiste = daoFuncionario.registroExiste(id);
					
					if(funcionarioExiste) {
						daoFuncionario.excluirPorId(id);
						
						ResponseEntity<Funcionario> resposta = gerarReposta(Funcionario.class, StatusResposta.SUCCESS, null,
								"Funcionario #" + id + " excluído com sucesso");
						
						request.getSession().setAttribute("resposta", resposta);

						response.sendRedirect(request.getContextPath() + "/funcionario/display");
					}else {
						throw new RuntimeException("Id inválido! O Funcionario #" + id + " não existe" );
					}
				}else {
					throw new IllegalArgumentException("Informe o id do funcionario a ser excluído!");
				}
				
			} 
			else if (request.getServletPath().endsWith("pesquisar")) {
				String valor = request.getParameter("valor");

				if (valor != null && !valor.trim().isEmpty()) {

					Pagination<Funcionario> pagination = null;

					if (pageString != null && !pageString.trim().isEmpty()) {
						int pagina = Integer.parseInt(pageString);

						int totalPaginas = Calculador.obterTotalPaginas(
								daoFuncionario.obterTotalRegistrosPorNome(valor), REGISTROS_POR_PAGINA);

						boolean paginaValida = pagina > 0 && pagina <= totalPaginas;

						if (paginaValida) {
							pagination = daoFuncionario.obterRegistrosPaginadosPreviewPorNome(valor, pagina,
									REGISTROS_POR_PAGINA);

						} else {
							throw new IllegalArgumentException("A página #" + pagina + " não existe!");
						}

					} else {
						pagination = daoFuncionario.obterRegistrosPaginadosPreviewPorNome(valor, PAGINA_INICIAL,
								REGISTROS_POR_PAGINA);
					}

					request.setAttribute("valorPesquisa", valor);
					encaminharParaPaginaFuncionarios(request, response, pagination);
				} else {
					throw new IllegalArgumentException("Informe um valor para realizar a pesquisa!");
				}
			} else {
				if (pageString != null && !pageString.trim().isEmpty()) {
					int pagina = Integer.parseInt(pageString);

					int totalPaginas = Calculador.obterTotalPaginas(daoFuncionario.obterTotalRegistros(),
							REGISTROS_POR_PAGINA);

					boolean paginaValida = pagina > 0 && pagina <= totalPaginas;

					if (paginaValida) {
						encaminharParaPaginaFuncionarios(request, response, pagina);

					} else {
						throw new IllegalArgumentException("A página #" + pagina + " não existe!");
					}

				} else {
					encaminharParaPaginaFuncionarios(request, response);
				}
			}

		} catch (Exception e) {
			ResponseEntity<Funcionario> resposta = gerarReposta(Funcionario.class, StatusResposta.ERROR, null,
					"Erro no processamento: " + e.getMessage());
			request.setAttribute("resposta", resposta);

			encaminharParaPaginaFuncionarios(request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Funcionario funcionario = null;
		try {

			String idTexto = request.getParameter("id");
			String nome = request.getParameter("nome");
			EnumSexo sexo = EnumSexo.toEnum(request.getParameter("sexo"));
			LocalDate dataNascimento = LocalDate.parse(request.getParameter("dataNascimento"),
					DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			String email = request.getParameter("email");
			Double salario = Double.parseDouble(
					request.getParameter("salario").replace("R$ ", "").replaceAll("\\.", "").replace(",", "."));
			PerfilFuncionario perfil = PerfilFuncionario.toEnum(request.getParameter("perfil"));
			String login = request.getParameter("login");

			String imgArmazenadaNaPagina = request.getParameter("imagemArmazenada");

			String jsonTelefones = request.getParameter("telefones");
			
			funcionario = new Funcionario();
			funcionario.setNome(nome);
			funcionario.setSexo(sexo);
			funcionario.setDataNascimento(dataNascimento);
			funcionario.setEmail(email);
			funcionario.setSalario(salario);
			funcionario.setPerfil(perfil);
			funcionario.setLogin(login);
			
			if (idTexto != null && !idTexto.trim().isEmpty()) {
				funcionario.setId(Long.parseLong(idTexto));
			}
			
			ObjectMapper objectMapper = new ObjectMapper();
			Set<Telefone> telefones = objectMapper.readValue(jsonTelefones, new TypeReference<Set<Telefone>>() {});
			
			Funcionario f = new Funcionario();
			f.setId(funcionario.getId());
			
			//settando o id do fun nos telefones
			telefones.stream().forEach(t -> t.setFuncionario(f));
			
			funcionario.setTelefones(telefones);

			Part imagemPart = request.getPart("imagem");
			if (imagemPart != null && imagemPart.getSize() > 0) {
				byte[] imagemByte = IOUtils.toByteArray(imagemPart.getInputStream());
				String extensao = imagemPart.getContentType().split("/")[1];

				String imagemBase64 = new Base64().encodeAsString(imagemByte);

				String imagem = "data:image/" + extensao + ";base64," + imagemBase64;
				funcionario.setImagem(imagem);

			} else if (imgArmazenadaNaPagina != null && !imgArmazenadaNaPagina.trim().isEmpty()) {
				funcionario.setImagem(imgArmazenadaNaPagina);
			}

			validarDados(funcionario);

			ResponseEntity<Funcionario> resposta = gerarReposta(Funcionario.class, StatusResposta.SUCCESS, null, "");

			if (funcionario.isNovo()) {
				String senhaGerada = GeradorSenha.gerar(funcionario);
				funcionario.setSenha(senhaGerada);

				daoFuncionario.salvar(funcionario);
				resposta.setMensagem("Funcionário inserido com sucesso!");
			} else {
				daoFuncionario.salvar(funcionario);
				resposta.setMensagem("Funcionário atualizado com sucesso!");
			}

			request.getSession().setAttribute("resposta", resposta);

			response.sendRedirect("funcionario/display");

		} catch (Exception e) {
			ResponseEntity<Funcionario> resposta = gerarReposta(Funcionario.class, StatusResposta.ERROR, funcionario,
					"Erro no processamento: " + e.getMessage());
			request.setAttribute("resposta", resposta);
			request.setAttribute("operacaoErro", OperacaoErro.SAVE);

			encaminharParaPaginaFuncionarios(request, response);
		}
	}

	private void encaminharParaPaginaFuncionarios(HttpServletRequest request, HttpServletResponse response) {
		encaminharParaPaginaFuncionarios(request, response, PAGINA_INICIAL);
	}

	private void encaminharParaPaginaFuncionarios(HttpServletRequest request, HttpServletResponse response, int page) {
		Pagination<Funcionario> pagination = daoFuncionario.obterRegistrosPaginadosPreview(page, REGISTROS_POR_PAGINA);
		encaminharParaPaginaFuncionarios(request, response, pagination);
	}

	private void encaminharParaPaginaFuncionarios(HttpServletRequest request, HttpServletResponse response,
			Pagination<Funcionario> pagination) {
		try {
			RequestDispatcher encaminhador = request.getRequestDispatcher("/view/admin/funcionarios.jsp");
			request.setAttribute("tipoSexo", EnumSexo.values());
			request.setAttribute("perfilFuncionario", PerfilFuncionario.values());
			request.setAttribute("pagination", pagination);

			encaminhador.forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}

	private <T> ResponseEntity<T> gerarReposta(Class<T> tipoRetorno, StatusResposta status, T artefato,
			String mensagem) {
		ResponseEntity<T> resposta = new ResponseEntity<>(status, artefato, mensagem);
		return resposta;
	}

	private boolean validarDados(Funcionario funcionario) {
		if (funcionario == null) {
			return false;
		}

		boolean salarioValido = funcionario.getSalario() != null && funcionario.getSalario() < SALARIO_MAXIMO;
		boolean loginValido = funcionario.getLogin() != null && !funcionario.getLogin().trim().isEmpty()
				&& funcionario.getLogin().length() >= QTD_MIN_CARACTERES_LOGIN
				&& funcionario.getLogin().length() <= QTD_MAX_CARACTERES_LOGIN;

		if (!salarioValido) {
			throw new DadosInvalidosException("O salário é obrigatório e deve ser menor do que "
					+ NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format(SALARIO_MAXIMO));
		}

		if (!loginValido) {
			throw new DadosInvalidosException("O login é obrigatório e deve conter no mínimo "
					+ QTD_MIN_CARACTERES_LOGIN + " e no máximo " + QTD_MAX_CARACTERES_LOGIN + " caracteres");
		}

		if (funcionario.isNovo()) {
			if (daoFuncionario.loginExiste(funcionario.getLogin())) {
				throw new DadosInvalidosException("O login já existe no banco, digite outro!");
			}
		} else {
			if (daoFuncionario.loginExisteUpdate(funcionario.getId(), funcionario.getLogin())) {
				throw new DadosInvalidosException("O login já existe no banco, digite outro!");
			}
		}

		return true;
	}

}
