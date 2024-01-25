package br.com.jdevtreinamentos.tf.controller.usuarios;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.jdevtreinamentos.tf.infrastructure.dao.impl.DAOProduto;

/**
 * Classe responsável por receber e processar as requisições para os recursos
 * relacionados aos serviços da página inicial.
 * 
 * @author Erik Vasconcelos
 * @since 2024-01-22
 * @version 0.1 2024-01-22
 */

@WebServlet("")
public class IndexController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private DAOProduto daoProduto;
	
    public IndexController() {
    	daoProduto = new DAOProduto();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher encaminhador = request.getRequestDispatcher("/view/usuarios/index.jsp");
		request.setAttribute("produtosRecentes", daoProduto.obterDezProdutosMaisRecentes());
		request.setAttribute("produtosTopOfertas", daoProduto.obterDezProdutosMaioresDescontos());
		
		encaminhador.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
