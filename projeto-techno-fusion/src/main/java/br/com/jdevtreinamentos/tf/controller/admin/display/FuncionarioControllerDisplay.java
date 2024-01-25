package br.com.jdevtreinamentos.tf.controller.admin.display;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.jdevtreinamentos.tf.controller.infra.ResponseEntity;
import br.com.jdevtreinamentos.tf.model.Funcionario;

/**
 * Padrão de projeto POST - REDIRECT - GET
 * Servlet para onde o usuario é redirecionado após realizar o post de salvar/atualizar
 * o funcionario e rezlizar o tratamento das mensagens e retornat para visualiza,ão do usuário
 *  
 * @author Erik Vasconcelos
 * @since 2023-12-19
 * @version 0.2 2023-12-30
 */

@WebServlet("/funcionario/display")
public class FuncionarioControllerDisplay extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public FuncionarioControllerDisplay() {
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

		request.getRequestDispatcher("/funcionario").forward(request, response);
	}

}
