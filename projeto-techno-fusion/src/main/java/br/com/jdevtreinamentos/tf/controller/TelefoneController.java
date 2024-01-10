package br.com.jdevtreinamentos.tf.controller;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

import br.com.jdevtreinamentos.tf.controller.infra.ResponseEntity;
import br.com.jdevtreinamentos.tf.controller.infra.StatusResposta;
import br.com.jdevtreinamentos.tf.exception.DadosInvalidosException;
import br.com.jdevtreinamentos.tf.exception.RecursoNaoEncontradoException;
import br.com.jdevtreinamentos.tf.infrastructure.dao.impl.DAOFuncionario;
import br.com.jdevtreinamentos.tf.infrastructure.dao.impl.DAOTelefone;
import br.com.jdevtreinamentos.tf.model.Funcionario;
import br.com.jdevtreinamentos.tf.model.Telefone;
import br.com.jdevtreinamentos.tf.util.Pagination;

/**
 * Classe responsável por receber e processar as requisições para os recursos
 * relacionados aos serviços da entidade <strong>Telefone<strong>.
 * 
 * @author Erik Vasconcelos
 * @since 2024-01-08
 * @version 0.1 2024-01-08
 */

@WebServlet(name = "telefoneController", urlPatterns = { "/funcionario/telefone", "/funcionario/telefone/editar", "/funcionario/telefone/excluir" })
public class TelefoneController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Integer PAGINA_INICIAL = 1;
	private static final Integer REGISTROS_POR_PAGINA = 10;

	private DAOTelefone daoTelefone;

	public TelefoneController() {
		daoTelefone = new DAOTelefone();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			if (request.getServletPath().endsWith("editar")) {
				editar(request, response);

			} else if (request.getServletPath().endsWith("excluir")) {
				excluir(request, response);

			} else {
				encaminharParaPaginaTelefones(request, response);
			}

		} catch (RecursoNaoEncontradoException re) {
			ResponseEntity<Telefone> resposta = gerarResposta(Telefone.class, StatusResposta.ERROR, null,
					"Erro no processamento: " + re.getMessage());
			request.setAttribute("resposta", resposta);
			try {
				RequestDispatcher encaminhador = request.getRequestDispatcher("/view/admin/telefones.jsp");
				request.setAttribute("pagination", null);

				encaminhador.forward(request, response);

			} catch (ServletException | IOException e1) {
				e1.printStackTrace();
			}

		} catch (Exception e) {
			ResponseEntity<Telefone> resposta = gerarResposta(Telefone.class, StatusResposta.ERROR, null,
					"Erro no processamento: " + e.getMessage());
			request.setAttribute("resposta", resposta);

			encaminharParaPaginaTelefones(request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Telefone telefone = null;
		try {
			String idTexto = request.getParameter("idTelefone");
			String numero = request.getParameter("numero");
			String idFuncionarioTexto = request.getParameter("idFuncionario");

			telefone = new Telefone();
			telefone.setNumero(numero);

			if (idTexto != null && !idTexto.trim().isEmpty()) {
				telefone.setId(Long.parseLong(idTexto));
			}

			if (idFuncionarioTexto != null && !idFuncionarioTexto.trim().isEmpty()) {
				Long idFuncionario = Long.parseLong(idFuncionarioTexto);

				Funcionario funcionario = new Funcionario();
				funcionario.setId(idFuncionario);

				telefone.setFuncionario(funcionario);
			}

			validarDados(telefone);

			ResponseEntity<Telefone> resposta = gerarResposta(Telefone.class, StatusResposta.SUCCESS, null, "");

			daoTelefone.salvar(telefone);
			resposta.setMensagem("Telefone salvo com sucesso!");

			request.getSession().setAttribute("resposta", resposta);

			response.sendRedirect("telefone/display?idFuncionario=" + idFuncionarioTexto);

		} catch (Exception e) {
			ResponseEntity<Telefone> resposta = gerarResposta(Telefone.class, StatusResposta.ERROR, telefone,
					"Erro no processamento: " + e.getMessage());
			request.setAttribute("resposta", resposta);

			encaminharParaPaginaTelefones(request, response);
		}
	}

	private void editar(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String idString = request.getParameter("id");

		if (idString != null && !idString.trim().isEmpty()) {
			Long id = Long.parseLong(idString);

			Optional<Telefone> optional = daoTelefone.buscarPorId(id);

			if (optional.isPresent()) {
				ObjectMapper mapper = JsonMapper.builder().findAndAddModules().build();

				String json = mapper.writeValueAsString(optional.get());

				response.setContentType("application/json");
				response.getWriter().write(json);
			} else {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				response.getWriter().write("Erro: não foi possível encontrar o telefone #" + id);
				response.setContentType("text/plain");
			}
		}
	}

	private void excluir(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String idString = request.getParameter("id");

		if (idString != null && !idString.trim().isEmpty()) {
			Long id = Long.parseLong(idString);

			Optional<Telefone> optional = daoTelefone.buscarPorId(id);

			if (optional.isPresent()) {
				daoTelefone.excluirPorId(id);

				ResponseEntity<Telefone> resposta = gerarResposta(Telefone.class, StatusResposta.SUCCESS, null,
						"Telefone #" + id + " excluído com sucesso");

				request.getSession().setAttribute("resposta", resposta);

				Long idFuncionario = optional.get().getFuncionario().getId();

				response.sendRedirect(
						request.getContextPath() + "/funcionario/telefone/display?idFuncionario=" + idFuncionario);
			} else {
				throw new RuntimeException("Id inválido! O Telefone #" + id + " não existe");
			}
		} else {
			throw new IllegalArgumentException("Informe o id do telefone a ser excluído!");
		}
	}

	private void encaminharParaPaginaTelefones(HttpServletRequest request, HttpServletResponse response) {
		encaminharParaPaginaTelefones(request, response, PAGINA_INICIAL);
	}

	private void encaminharParaPaginaTelefones(HttpServletRequest request, HttpServletResponse response, int page) {
		String idFuncionario = request.getParameter("idFuncionario");

		if (idFuncionario != null && !idFuncionario.trim().isEmpty()) {
			Long id = Long.parseLong(idFuncionario);

			Optional<Funcionario> funcionarioDaPaginaTelefone = new DAOFuncionario().buscarNomePorId(id);

			if (funcionarioDaPaginaTelefone.isPresent()) {

				Funcionario funcionario = funcionarioDaPaginaTelefone.get();
				Pagination<Telefone> pagination = daoTelefone.obterRegistrosPaginadosPreviewFuncionario(page,
						REGISTROS_POR_PAGINA, funcionario.getId());
				request.setAttribute("funcionarioTelefone", funcionario);

				encaminharParaPaginaTelefones(request, response, pagination);

			} else {
				throw new RecursoNaoEncontradoException("Id inválido! O funcionario #" + id + " não existe");
			}
		}
	}

	private void encaminharParaPaginaTelefones(HttpServletRequest request, HttpServletResponse response,
			Pagination<Telefone> pagination) {
		try {
			RequestDispatcher encaminhador = request.getRequestDispatcher("/view/admin/telefones.jsp");
			request.setAttribute("pagination", pagination);

			encaminhador.forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}

	private <T> ResponseEntity<T> gerarResposta(Class<T> tipoRetorno, StatusResposta status, T artefato,
			String mensagem) {
		ResponseEntity<T> resposta = new ResponseEntity<>(status, artefato, mensagem);
		return resposta;
	}

	private boolean validarDados(Telefone telefone) {
		if (telefone == null) {
			throw new NullPointerException("O telefone é nullo");
		}

		boolean numeroValido = telefone.getNumero() != null && !telefone.getNumero().trim().isEmpty();
		boolean idFuncionarioValido = telefone.getFuncionario() != null && telefone.getFuncionario().getId() != null;

		if (!numeroValido) {
			throw new DadosInvalidosException("O número do telefone é obrigatório.");
		}

		if (!idFuncionarioValido) {
			throw new DadosInvalidosException("O ID do funcionário é obrigatório para o telefone.");
		}

		return true;
	}

}
