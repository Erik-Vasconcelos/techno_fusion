package br.com.jdevtreinamentos.tf.controller.admin;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.jdevtreinamentos.tf.controller.infra.ResponseEntity;
import br.com.jdevtreinamentos.tf.controller.infra.StatusResposta;
import br.com.jdevtreinamentos.tf.exception.DadosInvalidosException;
import br.com.jdevtreinamentos.tf.infrastructure.dao.impl.DAOFuncionario;
import br.com.jdevtreinamentos.tf.model.Funcionario;
import br.com.jdevtreinamentos.tf.util.GeradorSenha;

/**
 * Classe responsável por receber e processar as requisições para os recursos
 * relacionados aos serviços de <strong>Login<strong>.
 * 
 * @author Erik Vasconcelos
 * @since 2024-01-10
 * @version 0.1 2024-01-10
 */

@WebServlet(name = "loginController", urlPatterns = { "/login", "/logout" })
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DAOFuncionario daoFuncionario;

	public LoginController() {
		daoFuncionario = new DAOFuncionario();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			if (request.getServletPath().endsWith("logout")) {
				request.getSession().invalidate();
			}

			encaminharParaPaginaLogin(request, response);

		} catch (Exception e) {
			ResponseEntity<Funcionario> resposta = gerarReposta(Funcionario.class, StatusResposta.ERROR, null,
					"Erro no processamento: " + e.getMessage());
			request.setAttribute("resposta", resposta);

			encaminharParaPaginaLogin(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Funcionario funcionario = null;
		try {
			String login = request.getParameter("login");
			String senha = request.getParameter("senha");

			validarDados(funcionario);

			funcionario = new Funcionario();
			funcionario.setLogin(login);
			funcionario.setSenha(GeradorSenha.encriptarSenha(senha));

			Optional<Funcionario> optional = daoFuncionario.autenticarFuncionario(funcionario);
			
			if (optional.isPresent()) {
				request.getSession().setAttribute("usuario", optional.get());

				String urlParaRedirecional = (String) request.getSession().getAttribute("url");
				if (temUrlParaRedirecionar(urlParaRedirecional)) {

					response.sendRedirect(urlParaRedirecional.replaceFirst("\\/", ""));
					request.getSession().removeAttribute("url");
				} else {
					response.sendRedirect("inicio");
				}

			} else {
				ResponseEntity<Funcionario> resposta = gerarReposta(Funcionario.class, StatusResposta.ERROR, null,
						"Login e senha incorretos");
				request.setAttribute("resposta", resposta);
				encaminharParaPaginaLogin(request, response);
			}

		} catch (Exception e) {
			ResponseEntity<Funcionario> resposta = gerarReposta(Funcionario.class, StatusResposta.ERROR, funcionario,
					"Erro no processamento: " + e.getMessage());
			request.setAttribute("resposta", resposta);

			encaminharParaPaginaLogin(request, response);
		}
	}

	private boolean temUrlParaRedirecionar(String url) {
		if (url != null && !url.trim().isEmpty() && !url.equals("null")) {
			return true;
		}

		return false;
	}

	private void encaminharParaPaginaLogin(HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpSession session = request.getSession(false);

			if (session != null) {
				@SuppressWarnings("unchecked")
				ResponseEntity<Funcionario> resposta = ((ResponseEntity<Funcionario>) session.getAttribute("resposta"));

				if(resposta != null) {
					request.setAttribute("resposta", resposta);
					session.removeAttribute("resposta");
				}
			}

			RequestDispatcher encaminhador = request.getRequestDispatcher("/view/admin/login.jsp");
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
			return false;
		}

		boolean loginValido = funcionario.getLogin() != null && !funcionario.getLogin().trim().isEmpty();
		boolean senhaValida = funcionario.getSenha() != null && !funcionario.getSenha().trim().isEmpty();

		if (!senhaValida) {
			throw new DadosInvalidosException("A senha é obrigatória!");
		}

		if (!loginValido) {
			throw new DadosInvalidosException("O login é obrigatório!");
		}

		return true;
	}

}
