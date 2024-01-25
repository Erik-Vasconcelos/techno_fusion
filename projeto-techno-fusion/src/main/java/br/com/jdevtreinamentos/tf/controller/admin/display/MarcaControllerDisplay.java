package br.com.jdevtreinamentos.tf.controller.admin.display;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.jdevtreinamentos.tf.controller.infra.ResponseEntity;
import br.com.jdevtreinamentos.tf.model.Marca;

/**
 * Padrão de projeto POST - REDIRECT - GET
 * Servlet para onde o usuario é redirecionado após realizar o post de salvar/atualizar
 * o funcionario e rezlizar o tratamento das mensagens e retornat para visualizacão da marca
 *  
 * @author Erik Vasconcelos
 * @since 2024-01-12
 * @version 0.1 2024-01-12
 */

@WebServlet("/marca/display")
public class MarcaControllerDisplay extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public MarcaControllerDisplay() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Object resposta = request.getSession().getAttribute("resposta");
	
		if (resposta != null) {
			@SuppressWarnings("unchecked")
			ResponseEntity<Marca> responseEntity = (ResponseEntity<Marca>) resposta;
			request.setAttribute("resposta", responseEntity);
			
			request.getSession().removeAttribute("resposta");
		}

		request.getRequestDispatcher("/marca").forward(request, response);
	}

}
