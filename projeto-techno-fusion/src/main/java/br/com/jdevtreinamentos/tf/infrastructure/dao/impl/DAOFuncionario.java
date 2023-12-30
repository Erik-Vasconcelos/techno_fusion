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

/**
 * Classe que implementa o padrão de projeto Data Acess Object - DAO que separa
 * as regras de negócio do código de infraestrutura, neste caso, dos cógigos de
 * acesso ao banco de dados sobre tabela de <strong>Funcionário<strong>.
 * 
 * @author Erik Vasconcelos
 * @since 2023-12-18
 * @version 0.1 2023-12-18
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
			String sql = "INSERT INTO funcionario(nome, sexo, data_nascimento, email, salario, perfil, login, senha)"
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
			}

		} catch (SQLException e) {
			FabricaConexao.connectionRollback();
			e.printStackTrace();
		}

		return optional.orElse(new Funcionario());
	}

	public void salvarImagem(Funcionario funcionario, String imagem) {
		try {
			String sql = "UPDATE funcionario SET imagem = ? WHERE id = ?";
			stmt = conexao.prepareStatement(sql);
			stmt.setString(1, imagem);
			stmt.setLong(2, funcionario.getId());

			stmt.execute();
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
			String sql = "SELECT f.id, f.nome, f.sexo, f.data_nascimento, f.email, f.salario, f.imagem, f.login FROM Funcionario f WHERE id = ?";
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

	@Override
	public List<Funcionario> obterTodos() {
		List<Funcionario> funcionarios = new ArrayList<>();
		try {
			String sql = "SELECT f.id, f.nome, f.sexo, f.data_nascimemto, f.email, f.salario, f.imagem, f.login FROM Funcionario f";
			stmt = conexao.prepareStatement(sql);

			ResultSet resultado = stmt.executeQuery();
			FabricaConexao.connectionCommit();

			while (resultado.next()) {
				Funcionario funcionario = new Funcionario();
				funcionario.setId(resultado.getLong("id"));
				funcionario.setNome(resultado.getString("nome"));
				funcionario.setSexo(EnumSexo.toEnum(resultado.getString("sexo")));
				funcionario.setDataNascimento(resultado.getDate("data_nascimento").toLocalDate());
				funcionario.setEmail(resultado.getString("email"));
				funcionario.setSalario(resultado.getDouble("salario"));
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
	public List<Funcionario> obterRegistrosPaginados(Integer pagina) {
		return null;
	}

	@Override
	public boolean excluirPorId(Long id) {
		try {
			String sql = "DELETE FROM funcionario WHERE id = ?";
			stmt = conexao.prepareStatement(sql);
			stmt.setLong(1, id);

			stmt.execute();
			FabricaConexao.connectionCommit();
			
		} catch (SQLException e) {
			FabricaConexao.connectionRollback();
			e.printStackTrace();
		}

		return false;
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

}
