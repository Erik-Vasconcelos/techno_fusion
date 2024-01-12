package br.com.jdevtreinamentos.tf.infrastructure.dao.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import br.com.jdevtreinamentos.tf.infrastructure.connection.FabricaConexao;
import br.com.jdevtreinamentos.tf.infrastructure.dao.service.EntidadeGenericaDAO;
import br.com.jdevtreinamentos.tf.model.Funcionario;
import br.com.jdevtreinamentos.tf.model.enumeration.EnumSexo;
import br.com.jdevtreinamentos.tf.model.enumeration.PerfilFuncionario;
import br.com.jdevtreinamentos.tf.util.Calculador;
import br.com.jdevtreinamentos.tf.util.Pageable;
import br.com.jdevtreinamentos.tf.util.Pagination;

/**
 * Classe que implementa o padrão de projeto Data Acess Object - DAO que separa
 * as regras de negócio do código de infraestrutura, neste caso, dos cógigos de
 * acesso ao banco de dados sobre a tabela de <strong>Funcionário<strong>.
 * 
 * @author Erik Vasconcelos
 * @since 2023-12-18
 * @version 0.2 2024-01-08
 */

public class DAOFuncionario implements Serializable, EntidadeGenericaDAO<Funcionario> {

	private static final long serialVersionUID = 1L;

	private Connection conexao;
	private PreparedStatement stmt;

	public DAOFuncionario() {
		conexao = FabricaConexao.getConnection();
	}

	@Override
	public Funcionario salvar(Funcionario entidade) {
		Optional<Funcionario> optional = Optional.empty();
		try {
			String sql = "";
			if (entidade.isNovo()) {

				sql = "INSERT INTO funcionario(nome, sexo, data_nascimento, email, salario, perfil, login, senha)"
						+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
				stmt = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
				stmt.setString(1, entidade.getNome());
				stmt.setString(2, entidade.getSexo().getSigla());
				stmt.setDate(3, Date.valueOf(entidade.getDataNascimento()));
				stmt.setString(4, entidade.getEmail());
				stmt.setDouble(5, entidade.getSalario());
				stmt.setString(6, entidade.getPerfil().name());
				stmt.setString(7, entidade.getLogin());
				stmt.setString(8, entidade.getSenha());

				stmt.execute();
				FabricaConexao.connectionCommit();

				ResultSet resultado = stmt.getGeneratedKeys();

				boolean temProximo = resultado.next();

				if (temProximo) {

					optional = buscarPorId(resultado.getLong("id"));

					entidade.setId(optional.get().getId());

					if (entidade.getImagem() != null && !entidade.getImagem().trim().isEmpty()) {
						salvarImagem(entidade);
					}
				}

			} else {
				sql = "UPDATE FUNCIONARIO SET nome=?, sexo=?, data_nascimento=?, email=?, salario=?, perfil=?, login=? WHERE id=?";
				stmt = conexao.prepareStatement(sql);
				stmt.setString(1, entidade.getNome());
				stmt.setString(2, entidade.getSexo().getSigla());
				stmt.setDate(3, Date.valueOf(entidade.getDataNascimento()));
				stmt.setString(4, entidade.getEmail());
				stmt.setDouble(5, entidade.getSalario());
				stmt.setString(6, entidade.getPerfil().name());
				stmt.setString(7, entidade.getLogin());
				stmt.setLong(8, entidade.getId());

				stmt.executeUpdate();
				FabricaConexao.connectionCommit();

				if (entidade.getImagem() != null && !entidade.getImagem().trim().isEmpty()) {
					salvarImagem(entidade);
				}

				optional = buscarPorId(entidade.getId());
			}

		} catch (SQLException e) {
			FabricaConexao.connectionRollback();
			e.printStackTrace();
		}

		return optional.orElse(new Funcionario());
	}

	public void salvarImagem(Funcionario funcionario) {
		try {
			String sql = "UPDATE funcionario SET imagem = ? WHERE id = ?";
			stmt = conexao.prepareStatement(sql);
			stmt.setString(1, funcionario.getImagem());
			stmt.setLong(2, funcionario.getId());

			stmt.execute();
			FabricaConexao.connectionCommit();

		} catch (SQLException e) {
			FabricaConexao.connectionRollback();
			e.printStackTrace();
		}
	}

	public void atualizarSenha(Funcionario entidade) {
		try {
			String sql = "UPDATE funcionario SET senha=? WHERE id=?";
			stmt = conexao.prepareStatement(sql);
			stmt.setString(1, entidade.getSenha());
			stmt.setLong(2, entidade.getId());

			stmt.executeUpdate();
			FabricaConexao.connectionCommit();
			
		} catch (SQLException e) {
			FabricaConexao.connectionRollback();
			e.printStackTrace();
		}
	}

	@Override
	public Optional<Funcionario> buscarPorId(Long id) {
		// TODO - criar sistema de encapsulamento do objeto e das informacoes
		// TODO - criar sistema de geracao de atributos automatico

		Optional<Funcionario> optional = Optional.empty();
		try {
			String sql = "SELECT f.id, f.nome, f.sexo, f.data_nascimento, f.perfil, f.email, f.salario, f.imagem, f.login FROM Funcionario f WHERE id = ?";
			stmt = conexao.prepareStatement(sql);
			stmt.setLong(1, id);

			ResultSet resultado = stmt.executeQuery();
			FabricaConexao.connectionCommit();

			if (resultado.next()) {
				Funcionario funcionario = new Funcionario();
				funcionario.setId(resultado.getLong("id"));
				funcionario.setNome(resultado.getString("nome"));
				funcionario.setSexo(EnumSexo.toEnumBySigla(resultado.getString("sexo")));
				funcionario.setDataNascimento(resultado.getDate("data_nascimento").toLocalDate());
				funcionario.setPerfil(PerfilFuncionario.toEnum(resultado.getString("perfil")));
				funcionario.setEmail(resultado.getString("email"));
				funcionario.setSalario(resultado.getDouble("salario"));
				funcionario.setImagem(resultado.getString("imagem"));
				funcionario.setLogin(resultado.getString("login"));

				optional = Optional.of(funcionario);
			}

		} catch (SQLException e) {
			FabricaConexao.connectionRollback();
			e.printStackTrace();
		}

		return optional;
	}

	public Optional<Funcionario> buscarNomePorId(Long id) {

		Optional<Funcionario> optional = Optional.empty();
		try {
			String sql = "SELECT f.id, f.nome FROM Funcionario f WHERE id = ?";
			stmt = conexao.prepareStatement(sql);
			stmt.setLong(1, id);

			ResultSet resultado = stmt.executeQuery();
			FabricaConexao.connectionCommit();

			if (resultado.next()) {
				Funcionario funcionario = new Funcionario();
				funcionario.setId(resultado.getLong("id"));
				funcionario.setNome(resultado.getString("nome"));

				optional = Optional.of(funcionario);
			}

		} catch (SQLException e) {
			FabricaConexao.connectionRollback();
			e.printStackTrace();
		}

		return optional;
	}

	public Optional<Funcionario> autenticarFuncionario(Funcionario funcionario) {
		Optional<Funcionario> optional = Optional.empty();

		try {
			String sql = "SELECT f.id, f.nome, f.sexo, f.data_nascimento, f.perfil, f.email, f.salario, f.imagem, f.login, f.senha FROM funcionario f WHERE login = ? AND senha = ?";
			stmt = conexao.prepareStatement(sql);
			stmt.setString(1, funcionario.getLogin());
			stmt.setString(2, funcionario.getSenha());

			ResultSet resultado = stmt.executeQuery();
			FabricaConexao.connectionCommit();

			if (resultado.next()) {
				Funcionario funcionarioAutenticado = new Funcionario();
				funcionarioAutenticado.setId(resultado.getLong("id"));
				funcionarioAutenticado.setNome(resultado.getString("nome"));
				funcionarioAutenticado.setSexo(EnumSexo.toEnumBySigla(resultado.getString("sexo")));
				funcionarioAutenticado.setDataNascimento(resultado.getDate("data_nascimento").toLocalDate());
				funcionarioAutenticado.setPerfil(PerfilFuncionario.toEnum(resultado.getString("perfil")));
				funcionarioAutenticado.setEmail(resultado.getString("email"));
				funcionarioAutenticado.setSalario(resultado.getDouble("salario"));
				funcionarioAutenticado.setImagem(resultado.getString("imagem"));
				funcionarioAutenticado.setLogin(resultado.getString("login"));

				optional = Optional.of(funcionarioAutenticado);
			}

		} catch (SQLException e) {
			FabricaConexao.connectionRollback();
			e.printStackTrace();
		}

		return optional;
	}

	@Override
	public List<Funcionario> obterTodos() {
		List<Funcionario> funcionarios = new ArrayList<>();
		try {
			String sql = "SELECT f.id, f.nome, f.sexo, f.data_nascimento, f.email, f.perfil, f.salario, f.imagem, f.login FROM funcionario f ORDER BY id DESC";
			stmt = conexao.prepareStatement(sql);

			ResultSet resultado = stmt.executeQuery();
			FabricaConexao.connectionCommit();

			while (resultado.next()) {
				Funcionario funcionario = new Funcionario();
				funcionario.setId(resultado.getLong("id"));
				funcionario.setNome(resultado.getString("nome"));
				funcionario.setSexo(EnumSexo.toEnumBySigla(resultado.getString("sexo")));
				funcionario.setDataNascimento(resultado.getDate("data_nascimento").toLocalDate());
				funcionario.setEmail(resultado.getString("email"));
				funcionario.setSalario(resultado.getDouble("salario"));
				funcionario.setPerfil(PerfilFuncionario.toEnum(resultado.getString("perfil")));
				funcionario.setImagem(resultado.getString("imagem"));
				funcionario.setLogin(resultado.getString("login"));

				funcionarios.add(funcionario);
			}

		} catch (SQLException e) {
			FabricaConexao.connectionRollback();
			e.printStackTrace();
		}

		return funcionarios;
	}

	@Override
	public Pagination<Funcionario> obterRegistrosPaginadosPreview(Integer numeroPagina, Integer registrosPorPagina) {
		Pagination<Funcionario> pagination = new Pagination<>();

		ResultSet resultado = null;
		try {
			int offset = (numeroPagina - 1) * registrosPorPagina;

			String sql = "SELECT f.id, f.nome, f.sexo, f.email, f.perfil, f.salario FROM funcionario f ORDER BY id DESC LIMIT ? OFFSET ?";
			stmt = conexao.prepareStatement(sql);
			stmt.setInt(1, registrosPorPagina);
			stmt.setInt(2, offset);

			resultado = stmt.executeQuery();
			FabricaConexao.connectionCommit();

			List<Funcionario> funcionarios = new ArrayList<>();
			while (resultado.next()) {
				Funcionario funcionario = new Funcionario();
				funcionario.setId(resultado.getLong("id"));
				funcionario.setNome(resultado.getString("nome"));
				funcionario.setSexo(EnumSexo.toEnumBySigla(resultado.getString("sexo")));
				funcionario.setEmail(resultado.getString("email"));
				funcionario.setSalario(resultado.getDouble("salario"));
				funcionario.setPerfil(PerfilFuncionario.toEnum(resultado.getString("perfil")));

				funcionarios.add(funcionario);
			}

			int qtdRegistros = obterTotalRegistros();
			int totalPaginas = Calculador.obterTotalPaginas(qtdRegistros, registrosPorPagina);

			pagination.setContent(funcionarios);
			pagination.setTotalPages(totalPaginas);
			pagination.setTotalElements(qtdRegistros);
			pagination.setPageable(new Pageable(offset, registrosPorPagina, numeroPagina));

		} catch (SQLException e) {
			FabricaConexao.connectionRollback();
			e.printStackTrace();

		} finally {
			try {
				if (resultado != null) {
					resultado.close();
				}

				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return pagination;
	}

	public Pagination<Funcionario> obterRegistrosPaginadosPreviewPorNome(String parteNome, Integer numeroPagina,
			Integer registrosPorPagina) {
		Pagination<Funcionario> pagination = new Pagination<>();

		try {
			int offset = (numeroPagina - 1) * registrosPorPagina;

			String sql = "SELECT f.id, f.nome, f.sexo, f.email, f.perfil, f.salario FROM funcionario f "
					+ "WHERE LOWER(f.nome) LIKE LOWER(?) " + "ORDER BY id DESC LIMIT ? OFFSET ?";

			stmt = conexao.prepareStatement(sql);
			stmt.setString(1, "%" + parteNome + "%");
			stmt.setInt(2, registrosPorPagina);
			stmt.setInt(3, offset);

			ResultSet resultado = stmt.executeQuery();
			FabricaConexao.connectionCommit();

			List<Funcionario> funcionarios = new ArrayList<>();
			while (resultado.next()) {
				Funcionario funcionario = new Funcionario();
				funcionario.setId(resultado.getLong("id"));
				funcionario.setNome(resultado.getString("nome"));
				funcionario.setSexo(EnumSexo.toEnumBySigla(resultado.getString("sexo")));
				funcionario.setEmail(resultado.getString("email"));
				funcionario.setSalario(resultado.getDouble("salario"));
				funcionario.setPerfil(PerfilFuncionario.toEnum(resultado.getString("perfil")));

				funcionarios.add(funcionario);
			}

			int qtdRegistros = obterTotalRegistrosPorNome(parteNome);
			int totalPaginas = Calculador.obterTotalPaginas(qtdRegistros, registrosPorPagina);

			pagination.setContent(funcionarios);
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
	public boolean excluirPorId(Long id) {
		try {
			String sql = "DELETE FROM funcionario WHERE id = ?";
			stmt = conexao.prepareStatement(sql);
			stmt.setLong(1, id);

			new DAOTelefone().excluirTodosTelefonesDoFuncionario(id);

			stmt.execute();
			FabricaConexao.connectionCommit();

		} catch (SQLException e) {
			FabricaConexao.connectionRollback();
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public int obterTotalRegistros() {
		int totalFuncionarios = 0;

		try {
			String sql = "SELECT COUNT(*) FROM funcionario";
			stmt = conexao.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				totalFuncionarios = rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return totalFuncionarios;
	}

	public int obterTotalRegistrosPorNome(String parteNome) {
		int total = 0;
		try {
			String sql = "SELECT COUNT(*) FROM funcionario WHERE LOWER(nome) LIKE LOWER(?)";
			PreparedStatement stmtCount = conexao.prepareStatement(sql);
			stmtCount.setString(1, "%" + parteNome + "%");
			ResultSet resultadoCount = stmtCount.executeQuery();
			resultadoCount.next();
			total = resultadoCount.getInt(1);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return total;
	}

	@Override
	public boolean registroExiste(Long id) {
		boolean existe = false;

		try {
			String sql = "SELECT COUNT(*) FROM funcionario WHERE id = ?";
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

	public boolean loginExiste(String login) {
		try {
			String sql = "SELECT COUNT(id) > 0 AS loginExiste FROM funcionario WHERE login = ?";

			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.setString(1, login);

			ResultSet resultado = stmt.executeQuery();
			FabricaConexao.connectionCommit();

			if (resultado.next()) {
				return resultado.getBoolean("loginExiste");
			}

		} catch (SQLException e) {
			FabricaConexao.connectionRollback();
			e.printStackTrace();
		}

		return false;
	}

	public boolean loginExisteUpdate(Long id, String login) {
		try {
			String sql = "SELECT COUNT(id) > 0 AS loginUpdateExiste FROM funcionario WHERE login = ? AND id != ?";

			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.setString(1, login);
			stmt.setLong(2, id);

			ResultSet resultado = stmt.executeQuery();
			FabricaConexao.connectionCommit();

			if (resultado.next()) {
				return resultado.getBoolean("loginUpdateExiste");
			}

		} catch (SQLException e) {
			FabricaConexao.connectionRollback();
			e.printStackTrace();
		}

		return false;
	}

}
