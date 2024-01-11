package br.com.jdevtreinamentos.tf.controller.display;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.jdevtreinamentos.tf.controller.infra.ResponseEntity;
import br.com.jdevtreinamentos.tf.model.Funcionario;

/**
 * Padrão de projeto POST - REDIRECT - GET Servlet para onde o usuario é
 * redirecionado após realizar o post de salvar/atualizar as credencias e
 * realizar o tratamento das mensagens e retornar para visualização do perfil
 * 
 * @author Erik Vasconcelos
 * @since 2024-01-11
 * @version 0.1 2024-01-11
 */

@WebServlet("/funcionario/perfil/display")
public class PerfilControllerDisplay extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public PerfilControllerDisplay() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Object resposta = request.getSession().getAttribute("resposta");

		if (resposta != null) {
			@SuppressWarnings("unchecked")
			ResponseEntity<Funcionario> responseEntity = (ResponseEntity<Funcionario>) resposta;
			request.setAttribute("resposta", responseEntity);

			request.getSession().removeAttribute("resposta");
		}

		request.getRequestDispatcher("/funcionario/perfil").forward(request, response);
	}

}
