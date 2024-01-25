package br.com.jdevtreinamentos.tf.controller.admin;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.jdevtreinamentos.tf.controller.infra.ResponseEntity;
import br.com.jdevtreinamentos.tf.controller.infra.StatusResposta;
import br.com.jdevtreinamentos.tf.controller.infra.TipoDownload;
import br.com.jdevtreinamentos.tf.exception.RecursoNaoEncontradoException;
import br.com.jdevtreinamentos.tf.infrastructure.dao.impl.DAOMarca;
import br.com.jdevtreinamentos.tf.model.Funcionario;
import br.com.jdevtreinamentos.tf.model.Marca;
import br.com.jdevtreinamentos.tf.util.JasperUtil;

/**
 * Classe responsável por receber e processar as requisições para os recursos
 * relacionados aos serviços de relatório.
 * 
 * @author Erik Vasconcelos
 * @since 2024-01-14
 * @version 0.1 2024-01-14
 */

@WebServlet(name = "relatorioController", urlPatterns = { "/relatorio/marca-produtos" })
public class RelatorioController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DAOMarca daoMarca;

	public RelatorioController() {
		daoMarca = new DAOMarca();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			if (request.getServletPath().endsWith("marca-produtos")) {
				relatorioMarcaProduto(request, response);

			} else {
				request.setAttribute("marcas", daoMarca.obterTodos());
				encaminharParaPaginaRelatorio(request, response);
				
			}

		} catch (Exception e) {
			ResponseEntity<Funcionario> resposta = gerarReposta(Funcionario.class, StatusResposta.ERROR, null,
					"Erro no processamento: " + e.getMessage());
			request.setAttribute("resposta", resposta);

			encaminharParaPaginaRelatorio(request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	private void relatorioMarcaProduto(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setAttribute("marcas", daoMarca.obterTodos());

		String idMarcaString = request.getParameter("marca");
		String acao = request.getParameter("acao");

		if (acao != null) {
			Map<String, Object> params = new HashMap<>();
			params.put("PATH_SUB_REPORT", "/relatorios/");

			List<Marca> dados = carregarDados(idMarcaString);
			
			if(idMarcaString != null && idMarcaString.matches("^[1-9]\\d*$")) {
				request.setAttribute("marcaSelecionada", daoMarca.buscarPorId(Long.parseLong(idMarcaString)).get());
			}

			if (acao.equals("visualizar")) {

				request.setAttribute("relatorio", dados);
				encaminharParaPaginaRelatorio(request, response);

			} else if (acao.equals("download-pdf")) {
				downloadRelatorio(request, response, dados, params, TipoDownload.PDF);

			}else if (acao.equals("download-xls")) {
				downloadRelatorio(request, response, dados, params, TipoDownload.XLS);

			} else {
				ResponseEntity<Funcionario> resposta = gerarReposta(Funcionario.class, StatusResposta.ERROR, null,
						"Erro no processamento: A acao '" + acao + "' não está disponível");

				request.setAttribute("resposta", resposta);
				encaminharParaPaginaRelatorio(request, response);
			}

		} else {
			encaminharParaPaginaRelatorio(request, response);
		}

	}

	private List<Marca> carregarDados(String idMarcaString) {
		if (idMarcaString != null && idMarcaString.matches("^[1-9]\\d*$")) {
			Long id = Long.parseLong(idMarcaString);

			Optional<Marca> optional = daoMarca.buscarPorIdComProdutos(id);

			if (optional.isPresent()) {
				return Arrays.asList(optional.get());
			} else {
				throw new RecursoNaoEncontradoException("Não foi possível encontrar a marca de id " + id);
			}

		} else {
			return daoMarca.obterTodosComProdutos();
		}
	}

	private void downloadRelatorio(HttpServletRequest request, HttpServletResponse response, List<Marca> dados,
			Map<String, Object> params, TipoDownload tipo) {
		try {
			byte[] relatorio = null;
			
			String extensao = "";
			if(tipo.equals(TipoDownload.PDF)) {
				relatorio = new JasperUtil<Marca>().gerarRelatorioPdf(dados, "marca-produto.jasper", params,
						getServletContext());
				
				extensao = ".pdf";
			}else {
				relatorio = new JasperUtil<Marca>().gerarRelatorioXls(dados, "marca-produto.jasper", params,
						getServletContext());
				
				extensao = ".xls";
			}

			String nomeArquivo = "relatorio-marca-produtos"
					+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("_dd_MM_yyyy")) + extensao;

			response.setHeader("Content-Disposition", "attachment;filename=" + nomeArquivo);

			response.getOutputStream().write(relatorio);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void encaminharParaPaginaRelatorio(HttpServletRequest request, HttpServletResponse response) {
		try {
			RequestDispatcher encaminhador = request.getRequestDispatcher("/view/admin/relatorio-marca-produtos.jsp");
			request.setAttribute("marcas", daoMarca.obterTodos());

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

}
