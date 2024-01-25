package br.com.jdevtreinamentos.tf.controller.admin;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

import br.com.jdevtreinamentos.tf.controller.infra.ResponseEntity;
import br.com.jdevtreinamentos.tf.controller.infra.StatusResposta;
import br.com.jdevtreinamentos.tf.infrastructure.dao.impl.DAOMarca;
import br.com.jdevtreinamentos.tf.model.Funcionario;
import br.com.jdevtreinamentos.tf.util.ModeloGrafico;

/**
 * Classe responsável por receber e processar as requisições para os recursos
 * relacionados aos serviços de gráficos.
 * 
 * @author Erik Vasconcelos
 * @since 2024-01-20
 * @version 0.1 2024-01-20
 */

@WebServlet(name = "graficoController", urlPatterns = { "/grafico/valor-medio-marca",
		"/grafico/valor-medio-marca/gerar" })
public class GraficoController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DAOMarca daoMarca;

	public GraficoController() {
		daoMarca = new DAOMarca();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			if (request.getServletPath().endsWith("valor-medio-marca/gerar")) {
				graficoValorMedioMarca(request, response);

			} else {
				encaminharParaPaginaGrafico(request, response);
			}

		} catch (Exception e) {
			ResponseEntity<Funcionario> resposta = gerarReposta(Funcionario.class, StatusResposta.ERROR, null,
					"Erro no processamento: " + e.getMessage());
			request.setAttribute("resposta", resposta);

			encaminharParaPaginaGrafico(request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	private void graficoValorMedioMarca(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ModeloGrafico<String, Double> modelo = null;
		String filtro = request.getParameter("filtro");

		if (filtro != null && !filtro.trim().equals("")) {
			Double valor = Double.parseDouble(
					request.getParameter("valor").replace("R$ ", "").replaceAll("\\.", "").replace(",", "."));

			if (filtro.equals("MAIOR_IGUAL_QUE")) {
				modelo = daoMarca.obterValorMedioProdutosMarcaValorMaiorIgualQue(valor);
			} else if (filtro.equals("MENOR_IGUAL_QUE")) {
				modelo = daoMarca.obterValorMedioProdutosMarcaValorMenorIgualQue(valor);
			}

		} else {
			modelo = daoMarca.obterValorMedioProdutosMarca();
		}

		ObjectMapper mapper = JsonMapper.builder().findAndAddModules().build();

		String json = mapper.writeValueAsString(modelo);

		response.setContentType("application/json");
		response.getWriter().write(json);

	}

	private void encaminharParaPaginaGrafico(HttpServletRequest request, HttpServletResponse response) {
		try {
			RequestDispatcher encaminhador = request.getRequestDispatcher("/view/admin/grafico-val-medio-marca.jsp");

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
