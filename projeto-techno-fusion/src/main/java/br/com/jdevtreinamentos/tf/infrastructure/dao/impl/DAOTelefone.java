package br.com.jdevtreinamentos.tf.infrastructure.dao.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import br.com.jdevtreinamentos.tf.infrastructure.connection.FabricaConexao;
import br.com.jdevtreinamentos.tf.infrastructure.dao.service.EntidadeGenericaDAO;
import br.com.jdevtreinamentos.tf.model.Funcionario;
import br.com.jdevtreinamentos.tf.model.Telefone;
import br.com.jdevtreinamentos.tf.util.Calculador;
import br.com.jdevtreinamentos.tf.util.Pageable;
import br.com.jdevtreinamentos.tf.util.Pagination;

/**
 * Classe que implementa o padrão de projeto Data Acess Object - DAO que separa
 * as regras de negócio do código de infraestrutura, neste caso, dos cógigos de
 * acesso ao banco de dados sobre a tabela de <strong>Telefone<strong>.
 * 
 * @author Erik Vasconcelos
 * @since 2024-01-08
 * @version 0.1 2024-01-08
 */

public class DAOTelefone implements Serializable, EntidadeGenericaDAO<Telefone> {

	private static final long serialVersionUID = 1L;

	private Connection conexao;
	private PreparedStatement stmt;
	private DAOFuncionario daoFuncionario;

	public DAOTelefone() {
		conexao = FabricaConexao.getConnection();
		daoFuncionario = new DAOFuncionario();
	}

	@Override
	public Telefone salvar(Telefone entidade) {
		Optional<Telefone> optional = Optional.empty();
		try {

			String sql = "";
			if (entidade.isNovo()) {
				sql = "INSERT INTO telefone(numero, funcionario_id) VALUES(?, ?)";
				stmt = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
				stmt.setString(1, entidade.getNumero());
				stmt.setLong(2, entidade.getFuncionario().getId());
				stmt.execute();
				FabricaConexao.connectionCommit();

				ResultSet resultado = stmt.getGeneratedKeys();

				boolean temProximo = resultado.next();

				if (temProximo) {
					optional = buscarPorId(resultado.getLong("id"));
					entidade.setId(optional.get().getId());
				}

			} else {
				sql = "UPDATE TELEFONE SET numero=?, funcionario_id=? WHERE id=?";
				stmt = conexao.prepareStatement(sql);
				stmt.setString(1, entidade.getNumero());
				stmt.setLong(2, entidade.getFuncionario().getId());
				stmt.setLong(3, entidade.getId());

				stmt.executeUpdate();
				FabricaConexao.connectionCommit();
				optional = buscarPorId(entidade.getId());
			}

		} catch (SQLException e) {
			FabricaConexao.connectionRollback();
			e.printStackTrace();
		}

		return optional.orElse(new Telefone());
	}

	public Set<Telefone> salvar(Set<Telefone> telefones) {
		Set<Telefone> telefonesSalvos = new HashSet<>();

		for (Telefone telefone : telefones) {
			Telefone telefoneSalvo = salvar(telefone);
			telefonesSalvos.add(telefoneSalvo);
		}

		return telefonesSalvos;
	}

	@Override
	public Optional<Telefone> buscarPorId(Long id) {
		Optional<Telefone> optional = Optional.empty();
		try {
			String sql = "SELECT t.id, t.numero, t.funcionario_id FROM Telefone t WHERE id = ?";
			stmt = conexao.prepareStatement(sql);
			stmt.setLong(1, id);

			ResultSet resultado = stmt.executeQuery();
			FabricaConexao.connectionCommit();

			if (resultado.next()) {
				Telefone telefone = new Telefone();
				telefone.setId(resultado.getLong("id"));
				telefone.setNumero(resultado.getString("numero"));

				// Recuperar o funcionário associado ao telefone
				Long funcionarioId = resultado.getLong("funcionario_id");
				Funcionario funcionario = daoFuncionario.buscarNomePorId(funcionarioId).get();

				telefone.setFuncionario(funcionario);

				optional = Optional.of(telefone);
			}

		} catch (SQLException e) {
			FabricaConexao.connectionRollback();
			e.printStackTrace();
		}

		return optional;
	}

	@Override
	public List<Telefone> obterTodos() {
		List<Telefone> telefones = new ArrayList<>();
		try {
			String sql = "SELECT t.id, t.numero, t.funcionario_id FROM telefone t ORDER BY id DESC";
			stmt = conexao.prepareStatement(sql);

			ResultSet resultado = stmt.executeQuery();
			FabricaConexao.connectionCommit();

			while (resultado.next()) {
				Telefone telefone = new Telefone();
				telefone.setId(resultado.getLong("id"));
				telefone.setNumero(resultado.getString("numero"));

				// Recuperar o funcionário associado ao telefone
				Long funcionarioId = resultado.getLong("funcionario_id");
				Funcionario funcionario = new Funcionario();
				funcionario.setId(funcionarioId);
				telefone.setFuncionario(funcionario);

				telefones.add(telefone);
			}

		} catch (SQLException e) {
			FabricaConexao.connectionRollback();
			e.printStackTrace();
		}

		return telefones;
	}

	@Override
	public boolean excluirPorId(Long id) {
		try {
			String sql = "DELETE FROM telefone WHERE id = ?";
			stmt = conexao.prepareStatement(sql);
			stmt.setLong(1, id);

			stmt.execute();
			FabricaConexao.connectionCommit();

			return true;
		} catch (SQLException e) {
			FabricaConexao.connectionRollback();
			e.printStackTrace();
			return false;
		}
	}

	public boolean excluirTodosTelefonesDoFuncionario(Long idFuncionario) {
		try {
			String sql = "DELETE FROM telefone WHERE funcionario_id = ?";
			stmt = conexao.prepareStatement(sql);
			stmt.setLong(1, idFuncionario);

			stmt.execute();
			FabricaConexao.connectionCommit();

			return true;
		} catch (SQLException e) {
			FabricaConexao.connectionRollback();
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public int obterTotalRegistros() {
		int totalTelefones = 0;

		try {
			String sql = "SELECT COUNT(*) FROM telefone";
			stmt = conexao.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				totalTelefones = rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return totalTelefones;
	}

	// Adicione outros métodos específicos se necessário

	// Exemplo de método específico para buscar telefones por funcionário
	public List<Telefone> buscarTelefonesPorFuncionario(Long funcionarioId) {
		List<Telefone> telefones = new ArrayList<>();
		try {
			String sql = "SELECT t.id, t.numero, t.funcionario_id FROM telefone t WHERE t.funcionario_id = ?";
			stmt = conexao.prepareStatement(sql);
			stmt.setLong(1, funcionarioId);

			ResultSet resultado = stmt.executeQuery();
			FabricaConexao.connectionCommit();

			while (resultado.next()) {
				Telefone telefone = new Telefone();
				telefone.setId(resultado.getLong("id"));
				telefone.setNumero(resultado.getString("numero"));

				// Recuperar o funcionário associado ao telefone
				Funcionario funcionario = new Funcionario();
				funcionario.setId(funcionarioId);
				telefone.setFuncionario(funcionario);

				telefones.add(telefone);
			}

		} catch (SQLException e) {
			FabricaConexao.connectionRollback();
			e.printStackTrace();
		}

		return telefones;
	}

	@Override
	public Pagination<Telefone> obterRegistrosPaginadosPreview(Integer numeroPagina, Integer registrosPorPagina) {
		Pagination<Telefone> pagination = new Pagination<>();

		try {
			int offset = (numeroPagina - 1) * registrosPorPagina;

			String sql = "SELECT t.id, t.numero, t.funcionario_id FROM telefone t ORDER BY id DESC LIMIT ? OFFSET ?";
			stmt = conexao.prepareStatement(sql);
			stmt.setInt(1, registrosPorPagina);
			stmt.setInt(2, offset);

			ResultSet resultado = stmt.executeQuery();
			FabricaConexao.connectionCommit();

			List<Telefone> telefones = new ArrayList<>();
			while (resultado.next()) {
				Telefone telefone = new Telefone();
				telefone.setId(resultado.getLong("id"));
				telefone.setNumero(resultado.getString("numero"));

				// Recuperar o funcionário associado ao telefone
				Long funcionarioId = resultado.getLong("funcionario_id");
				Funcionario funcionario = new Funcionario();
				funcionario.setId(funcionarioId);
				telefone.setFuncionario(funcionario);

				telefones.add(telefone);
			}

			int qtdRegistros = obterTotalRegistros();
			int totalPaginas = Calculador.obterTotalPaginas(qtdRegistros, registrosPorPagina);

			pagination.setContent(telefones);
			pagination.setTotalPages(totalPaginas);
			pagination.setTotalElements(qtdRegistros);
			pagination.setPageable(new Pageable(offset, registrosPorPagina, numeroPagina));

		} catch (SQLException e) {
			FabricaConexao.connectionRollback();
			e.printStackTrace();
		}

		return pagination;
	}

	public Pagination<Telefone> obterRegistrosPaginadosPreviewPorFuncionario(Integer numeroPagina,
			Integer registrosPorPagina, Long idFucionario) {
		Pagination<Telefone> pagination = new Pagination<>();

		try {
			int offset = (numeroPagina - 1) * registrosPorPagina;

			String sql = "SELECT t.id, t.numero, t.funcionario_id FROM telefone t WHERE funcionario_id = ? ORDER BY id DESC LIMIT ? OFFSET ?";
			stmt = conexao.prepareStatement(sql);

			stmt.setLong(1, idFucionario);
			stmt.setInt(2, registrosPorPagina);
			stmt.setInt(3, offset);

			ResultSet resultado = stmt.executeQuery();
			FabricaConexao.connectionCommit();

			List<Telefone> telefones = new ArrayList<>();
			while (resultado.next()) {
				Telefone telefone = new Telefone();
				telefone.setId(resultado.getLong("id"));
				telefone.setNumero(resultado.getString("numero"));

				Funcionario funcionario = daoFuncionario.buscarNomePorId(idFucionario).get();
				telefone.setFuncionario(funcionario);

				telefones.add(telefone);
			}

			int qtdRegistros = obterTotalRegistros();
			int totalPaginas = Calculador.obterTotalPaginas(qtdRegistros, registrosPorPagina);

			pagination.setContent(telefones);
			pagination.setTotalPages(totalPaginas);
			pagination.setTotalElements(qtdRegistros);
			pagination.setPageable(new Pageable(offset, registrosPorPagina, numeroPagina));

		} catch (SQLException e) {
			FabricaConexao.connectionRollback();
			e.printStackTrace();
		}

		return pagination;
	}

	@Override
	public boolean registroExiste(Long id) {
		boolean existe = false;

		try {
			String sql = "SELECT COUNT(*) FROM telefone WHERE id = ?";
			PreparedStatement stmtVerificar = conexao.prepareStatement(sql);
			stmtVerificar.setLong(1, id);

			ResultSet resultadoVerificar = stmtVerificar.executeQuery();
			resultadoVerificar.next();

			existe = resultadoVerificar.getInt(1) > 0;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return existe;
	}

}
