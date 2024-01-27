package br.com.jdevtreinamentos.tf.controller.admin;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

import br.com.jdevtreinamentos.tf.controller.infra.OperacaoErro;
import br.com.jdevtreinamentos.tf.controller.infra.ResponseEntity;
import br.com.jdevtreinamentos.tf.controller.infra.StatusResposta;
import br.com.jdevtreinamentos.tf.exception.DadosInvalidosException;
import br.com.jdevtreinamentos.tf.infrastructure.dao.impl.DAOMarca;
import br.com.jdevtreinamentos.tf.model.Marca;
import br.com.jdevtreinamentos.tf.util.Calculador;
import br.com.jdevtreinamentos.tf.util.Pagination;
import br.com.jdevtreinamentos.tf.util.SessaoUtil;

/**
 * Classe responsável por receber e processar as requisições para os recursos
 * relacionados aos serviços da <strong>Marca<strong>.
 * 
 * @author Erik Vasconcelos
 * @since 2024-01-12
 * @version 0.1 2024-01-12
 */

@WebServlet(name = "marcaController", urlPatterns = { "/marca", "/marca/editar", "/marca/excluir", "/marca/pesquisar" })
public class MarcaController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final Integer PAGINA_INICIAL = 1;
	private static final Integer REGISTROS_POR_PAGINA = 10;

	private DAOMarca daoMarca;

	public MarcaController() {
		daoMarca = new DAOMarca();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			if (request.getServletPath().endsWith("editar")) {
				editar(request, response);
			} else if (request.getServletPath().endsWith("excluir")) {
				excluir(request, response);
			} else if (request.getServletPath().endsWith("pesquisar")) {
				pesquisar(request, response);
			} else {
				obterPagina(request, response);
			}
		} catch (Exception e) {
			ResponseEntity<Marca> resposta = gerarReposta(Marca.class, StatusResposta.ERROR, null,
					"Erro no processamento: " + e.getMessage());
			request.setAttribute("resposta", resposta);

			encaminharParaPaginaMarcas(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Marca marca = null;
		try {
			String idTexto = request.getParameter("id");
			String nome = request.getParameter("nome");

			marca = new Marca();
			marca.setNome(nome);

			if (idTexto != null && !idTexto.trim().isEmpty()) {
				marca.setId(Long.parseLong(idTexto));
			}

			validarDados(marca);

			ResponseEntity<Marca> resposta = gerarReposta(Marca.class, StatusResposta.SUCCESS, null, "");

			if (marca.isNovo()) {
				daoMarca.salvar(marca);
				resposta.setMensagem("Marca inserida com sucesso!");
			} else {
				daoMarca.salvar(marca);
				resposta.setMensagem("Marca atualizada com sucesso!");
			}

			request.getSession().setAttribute("resposta", resposta);

			response.sendRedirect("marca/display");

		} catch (Exception e) {
			ResponseEntity<Marca> resposta = gerarReposta(Marca.class, StatusResposta.ERROR, marca,
					"Erro no processamento: " + e.getMessage());
			request.setAttribute("resposta", resposta);
			request.setAttribute("operacaoErro", OperacaoErro.SAVE);

			encaminharParaPaginaMarcas(request, response);
		}
	}

	private void editar(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String idString = request.getParameter("id");

		if (idString != null && !idString.trim().isEmpty()) {
			Long id = Long.parseLong(idString);

			Optional<Marca> optional = daoMarca.buscarPorId(id);

			if (optional.isPresent()) {
				ObjectMapper mapper = JsonMapper.builder().findAndAddModules().build();

				String json = mapper.writeValueAsString(optional.get());

				response.setContentType("application/json");
				response.getWriter().write(json);
			} else {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				response.getWriter().write("Erro: não foi possível encontrar a marca #" + id);
				response.setContentType("text/plain");
			}
		}
	}

	private void excluir(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String idString = request.getParameter("id");

		if (idString != null && !idString.trim().isEmpty()) {
			Long id = Long.parseLong(idString);

			boolean marcaExiste = daoMarca.registroExiste(id);

			if (marcaExiste) {
				daoMarca.excluirPorId(id);

				ResponseEntity<Marca> resposta = gerarReposta(Marca.class, StatusResposta.SUCCESS, null,
						"Marca #" + id + " excluída com sucesso");

				request.getSession().setAttribute("resposta", resposta);

				response.sendRedirect(request.getContextPath() + "/marca/display");
			} else {
				throw new RuntimeException("Id inválido! A Marca #" + id + " não existe");
			}
		} else {
			throw new IllegalArgumentException("Informe o id da marca a ser excluída!");
		}
	}

	private void pesquisar(HttpServletRequest request, HttpServletResponse response) {
		String pageString = request.getParameter("page");
		String valor = request.getParameter("valor");

		if (valor != null && !valor.trim().isEmpty()) {
			Pagination<Marca> pagination = null;

			if (pageString != null && !pageString.trim().isEmpty()) {
				int pagina = Integer.parseInt(pageString);

				int totalPaginas = Calculador.obterTotalPaginas(daoMarca.obterTotalRegistrosPorNome(valor),
						REGISTROS_POR_PAGINA);

				boolean paginaValida = pagina > 0 && pagina <= totalPaginas;

				if (paginaValida) {
					pagination = daoMarca.obterRegistrosPaginadosPreviewPorNome(valor, pagina, REGISTROS_POR_PAGINA);

				} else {
					throw new IllegalArgumentException("A página #" + pagina + " não existe!");
				}

			} else {
				pagination = daoMarca.obterRegistrosPaginadosPreviewPorNome(valor, PAGINA_INICIAL,
						REGISTROS_POR_PAGINA);
			}

			request.setAttribute("valorPesquisa", valor);
			encaminharParaPaginaMarcas(request, response, pagination);
		} else {
			throw new IllegalArgumentException("Informe um valor para realizar a pesquisa!");
		}
	}

	private void obterPagina(HttpServletRequest request, HttpServletResponse response) {
		String pageString = request.getParameter("page");
		if (pageString != null && !pageString.trim().isEmpty()) {
			int pagina = Integer.parseInt(pageString);

			int totalPaginas = Calculador.obterTotalPaginas(daoMarca.obterTotalRegistros(), REGISTROS_POR_PAGINA);

			boolean paginaValida = pagina > 0 && pagina <= totalPaginas;

			if (paginaValida) {
				encaminharParaPaginaMarcas(request, response, pagina);

			} else {
				throw new IllegalArgumentException("A página #" + pagina + " não existe!");
			}

		} else {
			encaminharParaPaginaMarcas(request, response);
		}
	}

	private void encaminharParaPaginaMarcas(HttpServletRequest request, HttpServletResponse response) {
		encaminharParaPaginaMarcas(request, response, PAGINA_INICIAL);
	}

	private void encaminharParaPaginaMarcas(HttpServletRequest request, HttpServletResponse response, int page) {
		Long idUsuarioLogado = SessaoUtil.getUsuarioLogado(request).getId();

		Pagination<Marca> pagination = daoMarca.obterRegistrosPaginadosPreview(page, REGISTROS_POR_PAGINA,
				idUsuarioLogado);
		encaminharParaPaginaMarcas(request, response, pagination);
	}

	private void encaminharParaPaginaMarcas(HttpServletRequest request, HttpServletResponse response,
			Pagination<Marca> pagination) {
		try {
			RequestDispatcher encaminhador = request.getRequestDispatcher("/view/admin/marca.jsp");
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

	private boolean validarDados(Marca marca) {
		if (marca == null) {
			return false;
		}

		String nome = marca.getNome();

		if (nome == null || nome.trim().length() < 3) {
			throw new DadosInvalidosException("O nome da marca é obrigatório e deve ter pelo menos 3 caracteres.");
		}

		return true;
	}

}
