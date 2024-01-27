package br.com.jdevtreinamentos.tf.controller.admin.display;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.jdevtreinamentos.tf.controller.infra.ResponseEntity;
import br.com.jdevtreinamentos.tf.model.Telefone;

/**
 * Padrão de projeto POST - REDIRECT - GET
 * Servlet para onde o usuario é redirecionado após realizar o post de salvar/atualizar
 * o telefone e realizar o tratamento das mensagens e retornar para visualização do usuário
 *  
 * @author Erik Vasconcelos
 * @since 2024-01-08
 * @version 0.2 2024-01-08
 */

@WebServlet("/funcionario/telefone/display")
public class TelefoneControllerDisplay extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public TelefoneControllerDisplay() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Object resposta = request.getSession().getAttribute("resposta");
	
		if (resposta != null) {
			@SuppressWarnings("unchecked")
			ResponseEntity<Telefone> responseEntity = (ResponseEntity<Telefone>) resposta;
			request.setAttribute("resposta", responseEntity);
			
			request.getSession().removeAttribute("resposta");
		}
		
		String id = request.getParameter("idFuncionario");

		request.getRequestDispatcher("/funcionario/telefone?idFuncionario=" + id).forward(request, response);
	}

}
