package br.com.jdevtreinamentos.tf.filter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.jdevtreinamentos.tf.controller.infra.ResponseEntity;
import br.com.jdevtreinamentos.tf.controller.infra.StatusResposta;
import br.com.jdevtreinamentos.tf.infrastructure.connection.FabricaConexao;
import br.com.jdevtreinamentos.tf.model.Funcionario;

/**
 * Filtro para realizar a seleção apenas dos usuário que estão logados para
 * acessarem os recursos descritos.
 * 
 * @author Erik Vasconcelos
 * @since 2024-01-10
 * @version 0.1 2024-01-10
 */

@WebFilter(filterName = "filtroLogin", urlPatterns = { "/funcionario/*" })
public class LoginFilter extends HttpFilter implements Filter {

	private static final long serialVersionUID = 1L;
	private Connection connection;

	public LoginFilter() {
	}

	public void destroy() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
		HttpServletRequest requestHttp = (HttpServletRequest) request;
		HttpServletResponse responseHttp = (HttpServletResponse) response;
		HttpSession sessao = requestHttp.getSession(false);
		try {
			if(sessao == null) {
				sessao = requestHttp.getSession();
			}
			
			Funcionario funcionario = (Funcionario) sessao.getAttribute("usuario");

			String recursoSolicitado = requestHttp.getServletPath();

			boolean redirecionarParaLogin = (funcionario == null || funcionario.getLogin() == null
					|| funcionario.getLogin().isEmpty()) && !recursoSolicitado.equals("/login");

			if (redirecionarParaLogin) {
				sessao = requestHttp.getSession();
				sessao.setAttribute("url", requestHttp.getServletPath());

				ResponseEntity<Funcionario> resposta = new ResponseEntity<>(StatusResposta.INFORMATION, null,
						"Realize o login!");
				sessao.setAttribute("resposta", resposta);

				responseHttp.sendRedirect(requestHttp.getContextPath() + "/login");
			} else {
				chain.doFilter(request, response);
			}
		} catch (Exception e) {
			ResponseEntity<Funcionario> resposta = new ResponseEntity<>(StatusResposta.ERROR, null,
					"Erro no servidor: " + e.getMessage());

			e.printStackTrace();
			try {
				if(sessao == null) {
					sessao = requestHttp.getSession();
				}
				sessao.setAttribute("resposta", resposta);
				responseHttp.sendRedirect(requestHttp.getContextPath() + "/erro");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
		connection = FabricaConexao.getConnection();
	}

}
