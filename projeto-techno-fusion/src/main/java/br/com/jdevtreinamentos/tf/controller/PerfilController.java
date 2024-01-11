package br.com.jdevtreinamentos.tf.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.jdevtreinamentos.tf.controller.infra.ResponseEntity;
import br.com.jdevtreinamentos.tf.controller.infra.StatusResposta;
import br.com.jdevtreinamentos.tf.exception.DadosInvalidosException;
import br.com.jdevtreinamentos.tf.infrastructure.dao.impl.DAOFuncionario;
import br.com.jdevtreinamentos.tf.infrastructure.dao.impl.DAOTelefone;
import br.com.jdevtreinamentos.tf.model.Funcionario;
import br.com.jdevtreinamentos.tf.model.Telefone;
import br.com.jdevtreinamentos.tf.util.GeradorSenha;

/**
 * Classe responsável por receber e processar as requisições para os recursos
 * relacionados aos serviços da entidade <strong>Funcionário<strong>.
 * 
 * @author Erik Vasconcelos
 * @since 2024-01-11
 * @version 0.1 2024-01-11
 */

@MultipartConfig
@WebServlet(name = "perfilController", urlPatterns = { "/funcionario/perfil" })
public class PerfilController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Integer QTD_MIN_CARACTERES_SENHA = 8;

	private DAOFuncionario daoFuncionario;
	private DAOTelefone daoTelefone;

	public PerfilController() {
		daoFuncionario = new DAOFuncionario();
		daoTelefone = new DAOTelefone();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		encaminharParaPaginaPerfil(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			Funcionario usuarioLogado = (Funcionario) request.getSession().getAttribute("usuario");
			String novaSenha = request.getParameter("senha");
			usuarioLogado.setSenha(novaSenha);

			validarDados(usuarioLogado);

			usuarioLogado.setSenha(GeradorSenha.encriptarSenha(novaSenha));
			daoFuncionario.atualizarSenha(usuarioLogado);

			ResponseEntity<Funcionario> resposta = gerarReposta(Funcionario.class, StatusResposta.SUCCESS, null,
					"Credenciais atualizadas com sucesso!");

			request.getSession().setAttribute("resposta", resposta);

			response.sendRedirect(request.getContextPath() + "/funcionario/perfil/display");

		} catch (Exception e) {
			ResponseEntity<Funcionario> resposta = gerarReposta(Funcionario.class, StatusResposta.ERROR, null,
					"Erro no processamento: " + e.getMessage());
			request.setAttribute("resposta", resposta);

			encaminharParaPaginaPerfil(request, response);
		}
	}

	private void encaminharParaPaginaPerfil(HttpServletRequest request, HttpServletResponse response) {
		try {
			RequestDispatcher encaminhador = request.getRequestDispatcher("/view/admin/perfil.jsp");
			Funcionario usuarioLogado = (Funcionario) request.getSession().getAttribute("usuario");
			
			List<Telefone> telefones = daoTelefone.buscarTelefonesPorFuncionario(usuarioLogado.getId());
			request.setAttribute("telefones", telefones);
			
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

	private boolean validarDados(Funcionario funcionario) {
		if (funcionario == null) {
			throw new NullPointerException("O usuário logado está nullo");
		}

		boolean senhaValida = funcionario.getSenha() != null && !funcionario.getSenha().trim().isEmpty()
				&& funcionario.getSenha().length() >= QTD_MIN_CARACTERES_SENHA;

		if (!senhaValida) {
			throw new DadosInvalidosException(
					"A senha é obrigatória e deve conter no mínimo " + QTD_MIN_CARACTERES_SENHA + " caracteres");
		}

		return true;
	}

}
