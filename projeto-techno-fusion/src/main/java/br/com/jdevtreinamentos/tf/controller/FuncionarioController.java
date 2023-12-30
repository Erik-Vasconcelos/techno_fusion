package br.com.jdevtreinamentos.tf.controller;

import java.io.IOException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.jdevtreinamentos.tf.controller.infra.ResponseEntity;
import br.com.jdevtreinamentos.tf.controller.infra.StatusResposta;
import br.com.jdevtreinamentos.tf.exception.DadosInvalidosException;
import br.com.jdevtreinamentos.tf.infrastructure.dao.impl.DAOFuncionario;
import br.com.jdevtreinamentos.tf.model.Funcionario;
import br.com.jdevtreinamentos.tf.model.enumeration.EnumSexo;
import br.com.jdevtreinamentos.tf.model.enumeration.PerfilFuncionario;
import br.com.jdevtreinamentos.tf.util.GeradorSenha;

/**
 * Classe responsável por receber e processar as requisições para os recursos
 * relacionados aos serviços da entidade <strong>Funcionário<strong>.
 * 
 * @author Erik Vasconcelos
 * @since 2023-12-19
 * @version 0.2 2023-12-30
 */

@WebServlet("/funcionario")
public class FuncionarioController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final Double SALARIO_MAXIMO = 100000.0;
	private static final Integer QTD_MIN_CARACTERES_LOGIN = 5;
	private static final Integer QTD_MAX_CARACTERES_LOGIN = 8;

	private DAOFuncionario daoFuncionario;

	public FuncionarioController() {
		daoFuncionario = new DAOFuncionario();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		encaminharParaPaginaFuncionarios(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Funcionario funcionario = null;
		try {

			String idTexto = request.getParameter("id");
			String nome = request.getParameter("nome");
			EnumSexo sexo = EnumSexo.toEnum(request.getParameter("sexo"));
			LocalDate dataNascimento = LocalDate.parse(request.getParameter("dataNascimento"),
					DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			String email = request.getParameter("email");
			Double salario = Double.parseDouble(
					request.getParameter("salario").replace("R$ ", "").replaceAll("\\.", "").replace(",", "."));
			String imagem = request.getParameter("imagem");
			PerfilFuncionario perfil = PerfilFuncionario.toEnum(request.getParameter("perfil"));
			String login = request.getParameter("login");

			funcionario = new Funcionario();
			if (idTexto != null && !idTexto.trim().isEmpty()) {
				funcionario.setId(Long.parseLong(idTexto));
			}

			funcionario.setNome(nome);
			funcionario.setSexo(sexo);
			funcionario.setDataNascimento(dataNascimento);
			funcionario.setEmail(email);
			funcionario.setSalario(salario);
			funcionario.setImagem(imagem);
			funcionario.setPerfil(perfil);
			funcionario.setLogin(login);

			validarDados(funcionario);

			if (funcionario.isNovo()) {
				String senhaGerada = GeradorSenha.gerar(funcionario);
				funcionario.setSenha(senhaGerada);

				daoFuncionario.salvar(funcionario);

				ResponseEntity<Funcionario> resposta = gerarReposta(Funcionario.class, StatusResposta.SUCCESS, null,
						"Funcionário inserido com sucesso!");
				request.getSession().setAttribute("resposta", resposta);
			} else {

			}

			response.sendRedirect("funcionario/display");

		} catch (Exception e) {
			ResponseEntity<Funcionario> resposta = gerarReposta(Funcionario.class, StatusResposta.ERROR, funcionario,
					"Erro no processamento: " + e.getMessage());
			request.setAttribute("resposta", resposta);

			encaminharParaPaginaFuncionarios(request, response);
		}
	}

	private void encaminhar(HttpServletRequest request, HttpServletResponse response, String caminho) {
		try {
			RequestDispatcher encaminhador = request.getRequestDispatcher(caminho);
			encaminhador.forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}

	private void encaminharParaPaginaFuncionarios(HttpServletRequest request, HttpServletResponse response) {
		try {
			RequestDispatcher encaminhador = request.getRequestDispatcher("/view/admin/funcionarios.jsp");
			request.setAttribute("tipoSexo", EnumSexo.values());
			request.setAttribute("perfilFuncionario", PerfilFuncionario.values());

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

		boolean salarioValido = funcionario.getSalario() != null && funcionario.getSalario() < SALARIO_MAXIMO;
		boolean loginValido = funcionario.getLogin() != null && !funcionario.getLogin().trim().isEmpty()
				&& funcionario.getLogin().length() >= QTD_MIN_CARACTERES_LOGIN
				&& funcionario.getLogin().length() <= QTD_MAX_CARACTERES_LOGIN;

		if (!salarioValido) {
			throw new DadosInvalidosException("O salário é obrigatório e deve ser menor do que "
					+ NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format(SALARIO_MAXIMO));
		}

		if (!loginValido) {
			throw new DadosInvalidosException("O login é obrigatório e deve conter no mínimo "
					+ QTD_MIN_CARACTERES_LOGIN + " e no máximo " + QTD_MAX_CARACTERES_LOGIN + " caracteres");
		}

		if (daoFuncionario.loginExiste(funcionario.getLogin())) {
			throw new DadosInvalidosException("O login já existe no banco, digite outro!");
		}

		return true;
	}

}
