package br.com.jdevtreinamentos.tf.controller;

import java.io.IOException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.codec.binary.Base64;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

import br.com.jdevtreinamentos.tf.controller.infra.OperacaoErro;
import br.com.jdevtreinamentos.tf.controller.infra.ResponseEntity;
import br.com.jdevtreinamentos.tf.controller.infra.StatusResposta;
import br.com.jdevtreinamentos.tf.exception.DadosInvalidosException;
import br.com.jdevtreinamentos.tf.exception.RecursoNaoEncontradoException;
import br.com.jdevtreinamentos.tf.infrastructure.dao.impl.DAOFuncionario;
import br.com.jdevtreinamentos.tf.model.Funcionario;
import br.com.jdevtreinamentos.tf.model.enumeration.EnumSexo;
import br.com.jdevtreinamentos.tf.model.enumeration.PerfilFuncionario;
import br.com.jdevtreinamentos.tf.util.Calculador;
import br.com.jdevtreinamentos.tf.util.GeradorSenha;
import br.com.jdevtreinamentos.tf.util.Pagination;
import br.com.jdevtreinamentos.tf.util.SessaoUtil;

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
