package br.com.jdevtreinamentos.tf.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Classe responsável por receber e processar as requisições para os recursos
 * relacionados aos serviços da entidade <strong>Funcionário<strong>.
 * 
 * @author Erik Vasconcelos
 * @since 2024-01-13
 * @version 0.1 2024-01-13
 */

@MultipartConfig
@WebServlet(name = "inicioAdminController", urlPatterns = { "/inicio" })
public class InicioAdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public InicioAdminController() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			RequestDispatcher encaminhador = request.getRequestDispatcher("/view/admin/inicio.jsp");

			encaminhador.forward(request, response);

		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
