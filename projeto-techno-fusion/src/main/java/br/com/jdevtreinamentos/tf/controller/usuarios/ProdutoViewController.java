package br.com.jdevtreinamentos.tf.controller.usuarios;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.jdevtreinamentos.tf.controller.infra.ResponseEntity;
import br.com.jdevtreinamentos.tf.controller.infra.StatusResposta;
import br.com.jdevtreinamentos.tf.infrastructure.dao.impl.DAOProduto;
import br.com.jdevtreinamentos.tf.model.Produto;
import br.com.jdevtreinamentos.tf.util.Calculador;
import br.com.jdevtreinamentos.tf.util.Pagination;

/**
 * Classe responsável por receber e processar as requisições para os recursos
 * relacionados aos serviços de produtos da área dos usuários comuns.
 * 
 * @author Erik Vasconcelos
 * @since 2024-01-23
 * @version 0.1 2024-01-23
 */

@WebServlet(name = "produtoViewController", urlPatterns = { "/viewProduto", "/pesquisar" })
public class ProdutoViewController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final Integer PAGINA_INICIAL = 1;
	private static final Integer REGISTROS_POR_PAGINA = 10;

	private DAOProduto daoProduto;

	public ProdutoViewController() {
		daoProduto = new DAOProduto();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			if (request.getServletPath().endsWith("viewProduto")) {
				viewProduto(request, response);

			} else if (request.getServletPath().endsWith("pesquisar")) {
				pesquisar(request, response);
			}
			
		} catch (Exception e) {
			ResponseEntity<Produto> resposta = gerarResposta(Produto.class, StatusResposta.ERROR, null,
					"Erro no processamento: " + e.getMessage());
			request.setAttribute("resposta", resposta);

			encaminharParaPaginaViewPesquisa(request, response, null);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	private void viewProduto(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idString = request.getParameter("id");

		if (idString != null && idString.matches("^[1-9]\\d*$")) {
			Long id = Long.parseLong(idString);

			Optional<Produto> optional = daoProduto.buscarPorId(id);

			if (optional.isPresent()) {
				request.setAttribute("produto", optional.get());
			}
		}

		RequestDispatcher encaminhador = request.getRequestDispatcher("/view/usuarios/view-produto.jsp");
		request.setAttribute("produtosTopOfertas", daoProduto.obterDezProdutosMaioresDescontos());
		encaminhador.forward(request, response);
	}

	private void pesquisar(HttpServletRequest request, HttpServletResponse response) {
		String pageString = request.getParameter("page");
		String valor = request.getParameter("valor");

		if (valor != null && !valor.trim().isEmpty()) {
			Pagination<Produto> pagination = null;
			valor = valor.trim();
			
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
			encaminharParaPaginaViewPesquisa(request, response, pagination);
		} else {
			throw new IllegalArgumentException("Informe um valor para realizar a pesquisa!");
		}
	}

	private void encaminharParaPaginaViewPesquisa(HttpServletRequest request, HttpServletResponse response,
			Pagination<Produto> pagination) {
		try {
			RequestDispatcher encaminhador = request.getRequestDispatcher("/view/usuarios/pesquisa.jsp");

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

}
