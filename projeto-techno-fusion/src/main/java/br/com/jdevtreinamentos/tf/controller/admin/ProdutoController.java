package br.com.jdevtreinamentos.tf.controller.admin;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.codec.binary.Base64;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

import br.com.jdevtreinamentos.tf.controller.infra.OperacaoErro;
import br.com.jdevtreinamentos.tf.controller.infra.ResponseEntity;
import br.com.jdevtreinamentos.tf.controller.infra.StatusResposta;
import br.com.jdevtreinamentos.tf.exception.DadosInvalidosException;
import br.com.jdevtreinamentos.tf.exception.RecursoNaoEncontradoException;
import br.com.jdevtreinamentos.tf.infrastructure.dao.impl.DAOMarca;
import br.com.jdevtreinamentos.tf.infrastructure.dao.impl.DAOProduto;
import br.com.jdevtreinamentos.tf.model.Funcionario;
import br.com.jdevtreinamentos.tf.model.Marca;
import br.com.jdevtreinamentos.tf.model.Produto;
import br.com.jdevtreinamentos.tf.util.Calculador;
import br.com.jdevtreinamentos.tf.util.Pagination;
import br.com.jdevtreinamentos.tf.util.SessaoUtil;

@MultipartConfig
@WebServlet(name = "produtoController", urlPatterns = { "/produto", "/produto/editar", "/produto/excluir",
		"/produto/pesquisar", "/produto/downloadImagem" })
public class ProdutoController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final Integer PAGINA_INICIAL = 1;
	private static final Integer REGISTROS_POR_PAGINA = 10;
	private static final Double VALOR_MAXIMO_PRODUTO = 300000.0;

	private DAOProduto daoProduto;

	public ProdutoController() {
		daoProduto = new DAOProduto();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			if (request.getServletPath().endsWith("editar")) {
				HttpSession session = request.getSession(false);

				if (session == null || session.isNew()) {
					response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				} else {
					editar(request, response);
				}

			} else if (request.getServletPath().endsWith("excluir")) {
				excluir(request, response);

			} else if (request.getServletPath().endsWith("pesquisar")) {
				pesquisar(request, response);

			} else if (request.getServletPath().endsWith("downloadImagem")) {
				downloadImagem(request, response);

			} else {
				obterPagina(request, response);
			}

		} catch (Exception e) {
			ResponseEntity<Produto> resposta = gerarResposta(Produto.class, StatusResposta.ERROR, null,
					"Erro no processamento: " + e.getMessage());
			request.setAttribute("resposta", resposta);

			encaminharParaPaginaProdutos(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Produto produto = null;
		try {
			String idTexto = request.getParameter("id");
			String descricao = request.getParameter("descricao");
			String modelo = request.getParameter("modelo");
			String caracteristicas = request.getParameter("caracteristicas");
			Double valor = Double.parseDouble(
					request.getParameter("valorProduto").replace("R$ ", "").replaceAll("\\.", "").replace(",", "."));
			Double desconto = Double.parseDouble(request.getParameter("desconto"));
			Long marcaId = Long.parseLong(request.getParameter("marca"));

			produto = new Produto();
			produto.setDescricao(descricao);
			produto.setModelo(modelo);
			produto.setCaracteristicas(caracteristicas);
			produto.setValor(valor);
			produto.setDesconto(desconto);

			Marca marca = new Marca();
			marca.setId(marcaId);
			produto.setMarca(marca);

			Funcionario cadastrador = SessaoUtil.getUsuarioLogado(request);
			produto.setCadastrador(cadastrador);

			if (idTexto != null && !idTexto.trim().isEmpty()) {
				produto.setId(Long.parseLong(idTexto));
			}

			Part imagemPart = request.getPart("imagem");
			if (imagemPart != null && imagemPart.getSize() > 0) {
				byte[] imagemByte = IOUtils.toByteArray(imagemPart.getInputStream());
				String extensao = imagemPart.getContentType().split("/")[1];

				String imagemBase64 = new Base64().encodeAsString(imagemByte);

				String imagem = "data:image/" + extensao + ";base64," + imagemBase64;
				produto.setImagem(imagem);
			}

			validarDados(produto);

			ResponseEntity<Produto> resposta = gerarResposta(Produto.class, StatusResposta.SUCCESS, null, "");

			if (produto.isNovo()) {
				daoProduto.salvar(produto);
				resposta.setMensagem("Produto inserido com sucesso!");
			} else {
				daoProduto.salvar(produto);
				resposta.setMensagem("Produto atualizado com sucesso!");
			}

			request.getSession().setAttribute("resposta", resposta);
			response.sendRedirect("produto/display");

		} catch (Exception e) {
			ResponseEntity<Produto> resposta = gerarResposta(Produto.class, StatusResposta.ERROR, produto,
					"Erro no processamento: " + e.getMessage());
			request.setAttribute("resposta", resposta);
			request.setAttribute("operacaoErro", OperacaoErro.SAVE);

			encaminharParaPaginaProdutos(request, response);
		}
	}

	private void editar(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String idString = request.getParameter("id");

		if (idString != null && !idString.trim().isEmpty()) {
			Long id = Long.parseLong(idString);

			Optional<Produto> optional = daoProduto.buscarPorId(id);
			if (optional.isPresent()) {
				ObjectMapper mapper = JsonMapper.builder().findAndAddModules().build();

				String json = mapper.writeValueAsString(optional.get());

				response.setContentType("application/json");
				response.getWriter().write(json);
			} else {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				response.getWriter().write("Erro: não foi possível encontrar o produto #" + id);
				response.setContentType("text/plain");
			}
		} else {
			throw new IllegalArgumentException("Informe o id do produto a ser editado!");
		}
	}

	private void excluir(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String idString = request.getParameter("id");

		if (idString != null && !idString.trim().isEmpty()) {
			Long id = Long.parseLong(idString);

			boolean produtoExiste = daoProduto.registroExiste(id);

			if (produtoExiste) {
				daoProduto.excluirPorId(id);

				ResponseEntity<Produto> resposta = gerarResposta(Produto.class, StatusResposta.SUCCESS, null,
						"Produto #" + id + " excluído com sucesso");

				request.getSession().setAttribute("resposta", resposta);

				response.sendRedirect(request.getContextPath() + "/produto/display");
			} else {
				throw new RuntimeException("Id inválido! O Produto #" + id + " não existe");
			}
		} else {
			throw new IllegalArgumentException("Informe o id do produto a ser excluído!");
		}
	}

	private void pesquisar(HttpServletRequest request, HttpServletResponse response) {
		String pageString = request.getParameter("page");
		String valor = request.getParameter("valor");

		if (valor != null && !valor.trim().isEmpty()) {
			Pagination<Produto> pagination = null;

			if (pageString != null && !pageString.trim().isEmpty()) {
				int pagina = Integer.parseInt(pageString);

				int totalPaginas = Calculador.obterTotalPaginas(daoProduto.obterTotalRegistrosPorDescricao(valor),
						REGISTROS_POR_PAGINA);

				boolean paginaValida = pagina > 0 && pagina <= totalPaginas;

				if (paginaValida) {
					pagination = daoProduto.obterRegistrosPaginadosPreviewPorDescricao(valor, pagina,
							REGISTROS_POR_PAGINA);
				} else {
					throw new IllegalArgumentException("A página #" + pagina + " não existe!");
				}

			} else {
				pagination = daoProduto.obterRegistrosPaginadosPreviewPorDescricao(valor, PAGINA_INICIAL,
						REGISTROS_POR_PAGINA);
			}

			request.setAttribute("valorPesquisa", valor);
			encaminharParaPaginaProdutos(request, response, pagination);
		} else {
			throw new IllegalArgumentException("Informe um valor para realizar a pesquisa!");
		}
	}

	private void downloadImagem(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String idString = request.getParameter("idProduto");

		if (idString != null && !idString.trim().isEmpty()) {
			Long id = Long.parseLong(idString);

			Optional<Produto> optional = daoProduto.buscarPorId(id);

			if (optional.isPresent()) {

				Produto produto = optional.get();

				String primeiroNome = produto.getDescricao().split("\\s+")[0];
				if (produto.getImagem() != null) {
					String extensaoImagem = produto.getImagem().split("\\/")[1].split("\\;")[0];
					String imagemBase64 = produto.getImagem().split("\\,")[1];

					response.setContentType("image/" + extensaoImagem);

					response.setHeader("Content-Disposition",
							"attachment;filename=" + primeiroNome + "." + extensaoImagem);

					response.getOutputStream().write(new Base64().decode(imagemBase64));
				} else {
					throw new RecursoNaoEncontradoException("O produto " + primeiroNome + " não possui imagem!");
				}

			} else {
				throw new RuntimeException("Id inválido! O produto #" + id + " não existe");
			}
		} else {
			throw new IllegalArgumentException("Informe o id do produto a realizar o download da imagem!");
		}

	}

	private void obterPagina(HttpServletRequest request, HttpServletResponse response) {
		String pageString = request.getParameter("page");
		if (pageString != null && !pageString.trim().isEmpty()) {
			int pagina = Integer.parseInt(pageString);

			int totalPaginas = Calculador.obterTotalPaginas(daoProduto.obterTotalRegistros(), REGISTROS_POR_PAGINA);

			boolean paginaValida = pagina > 0 && pagina <= totalPaginas;

			if (paginaValida) {
				encaminharParaPaginaProdutos(request, response, pagina);

			} else {
				throw new IllegalArgumentException("A página #" + pagina + " não existe!");
			}

		} else {
			encaminharParaPaginaProdutos(request, response);
		}
	}

	private void encaminharParaPaginaProdutos(HttpServletRequest request, HttpServletResponse response) {
		encaminharParaPaginaProdutos(request, response, PAGINA_INICIAL);
	}

	private void encaminharParaPaginaProdutos(HttpServletRequest request, HttpServletResponse response, int page) {
		Long idUsuarioLogado = SessaoUtil.getUsuarioLogado(request).getId();
		
		Pagination<Produto> pagination = daoProduto.obterRegistrosPaginadosPreview(page, REGISTROS_POR_PAGINA, idUsuarioLogado);
		encaminharParaPaginaProdutos(request, response, pagination);
	}

	private void encaminharParaPaginaProdutos(HttpServletRequest request, HttpServletResponse response,
			Pagination<Produto> pagination) {
		try {
			RequestDispatcher encaminhador = request.getRequestDispatcher("/view/admin/produtos.jsp");

			List<Marca> marcas = new DAOMarca().obterTodos();
			request.setAttribute("marcas", marcas);

			request.setAttribute("pagination", pagination);

			encaminhador.forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}

	private <T> ResponseEntity<T> gerarResposta(Class<T> tipoRetorno, StatusResposta status, T artefato,
			String mensagem) {
		ResponseEntity<T> resposta = new ResponseEntity<>(status, artefato, mensagem);
		return resposta;
	}

	private boolean validarDados(Produto produto) {
		if (produto == null) {
			return false;
		}

		boolean descricaoValida = produto.getDescricao() != null && !produto.getDescricao().trim().isEmpty();
		boolean modeloValido = produto.getModelo() != null && !produto.getModelo().trim().isEmpty();
		boolean caracteristicasValidas = produto.getCaracteristicas() != null
				&& !produto.getCaracteristicas().trim().isEmpty();
		boolean valorValido = produto.getValor() != null && produto.getValor() > 0
				&& produto.getValor() <= VALOR_MAXIMO_PRODUTO;
		boolean descontoValido = produto.getDesconto() != null && produto.getDesconto() >= 0
				&& produto.getDesconto() <= 100;

		if (!descricaoValida) {
			throw new DadosInvalidosException("A descrição é obrigatória e não pode estar vazia.");
		}

		if (!modeloValido) {
			throw new DadosInvalidosException("O modelo é obrigatório e não pode estar vazio.");
		}

		if (!caracteristicasValidas) {
			throw new DadosInvalidosException("As características são obrigatórias e não podem estar vazias.");
		}

		if (!valorValido) {
			throw new DadosInvalidosException("O valor é obrigatório e deve ser maior que zero e menor que "
					+ NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format(VALOR_MAXIMO_PRODUTO));
		}

		if (!descontoValido) {
			throw new DadosInvalidosException("O desconto deve estar no intervalo de 0 a 100.");
		}

		return true;
	}

}
