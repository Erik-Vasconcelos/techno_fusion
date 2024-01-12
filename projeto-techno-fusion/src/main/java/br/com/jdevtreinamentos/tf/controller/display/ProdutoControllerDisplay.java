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
 * redirecionado após realizar o post de salvar/atualizar o funcionario e
 * realizar o tratamento das mensagens e retorna para visualização do produtos
 * 
 * @author Erik Vasconcelos
 * @since 2023-01-12
 * @version 0.1 2023-01-12
 */

@WebServlet("/produto/display")
public class ProdutoControllerDisplay extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ProdutoControllerDisplay() {
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

		request.getRequestDispatcher("/produto").forward(request, response);
	}

}
