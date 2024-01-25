package br.com.jdevtreinamentos.tf.controller.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ErroController
 */
@WebServlet("/erro")
public class ExceptionHandlerController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final int ERRO_PADRAO = 500; 

	public ExceptionHandlerController() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processError(request, response);

	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processError(request, response);
	}

	private void processError(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");

		if (statusCode == null) {
			statusCode = ERRO_PADRAO;
		}

		String requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");
		if (requestUri == null) {
			requestUri = "Unknown";
		}

		String mensagem = "";

		if (statusCode.equals(HttpServletResponse.SC_FORBIDDEN)) {
			mensagem = "Acesso negado! Você não tem permissão para acessar este recurso.";
			statusCode = HttpServletResponse.SC_FORBIDDEN;

		} else if (statusCode.equals(HttpServletResponse.SC_NOT_FOUND)) {
			mensagem = "Recurso não encontrado! Você está solicitando um recurso que não existe.";
			statusCode = HttpServletResponse.SC_NOT_FOUND;
		} else {
			mensagem = "Ocorreu um erro inesperado!";
			statusCode = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
		}

		request.setAttribute("mensagemErro", mensagem);
		request.setAttribute("statusCode", statusCode);
		request.getRequestDispatcher("view/erro.jsp").forward(request, response);
	}
}
