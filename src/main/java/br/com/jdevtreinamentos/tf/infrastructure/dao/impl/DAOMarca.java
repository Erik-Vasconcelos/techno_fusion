package br.com.jdevtreinamentos.tf.infrastructure.dao.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;

import br.com.jdevtreinamentos.tf.infrastructure.connection.FabricaConexao;
import br.com.jdevtreinamentos.tf.infrastructure.dao.service.EntidadeGenericaDAO;
import br.com.jdevtreinamentos.tf.model.Marca;
import br.com.jdevtreinamentos.tf.model.Produto;
import br.com.jdevtreinamentos.tf.util.Calculador;
import br.com.jdevtreinamentos.tf.util.ModeloGrafico;
import br.com.jdevtreinamentos.tf.util.Pageable;
import br.com.jdevtreinamentos.tf.util.Pagination;

/**
 * Classe que implementa o padrão de projeto Data Acess Object - DAO que separa
 * as regras de negócio do código de infraestrutura, neste caso, dos códigos de
 * acesso ao banco de dados sobre a tabela de <strong>marca<strong>.
 * 
 * @author Erik Vasconcelos
 * @since 2024-01-12
 * @version 0.1 2024-01-12
 */

public class DAOMarca implements Serializable, EntidadeGenericaDAO<Marca> {

	private static final long serialVersionUID = 1L;

	private Connection conexao;
	private PreparedStatement stmt;

	private DAOProduto daoProduto;

	public DAOMarca() {
		conexao = FabricaConexao.getConnection();
	}

	@Override
	public Marca salvar(Marca entidade) {
		Optional<Marca> optional = Optional.empty();
		try {
			String sql = "";
			if (entidade.isNovo()) {
				sql = "INSERT INTO marca(nome) VALUES(?)";
				stmt = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
				stmt.setString(1, entidade.getNome());
				stmt.execute();
				FabricaConexao.connectionCommit();

				ResultSet resultado = stmt.getGeneratedKeys();

				boolean temProximo = resultado.next();

				if (temProximo) {
					optional = buscarPorId(resultado.getLong("id"));
					entidade.setId(optional.get().getId());
				}
			} else {
				sql = "UPDATE marca SET nome=? WHERE id=?";
				stmt = conexao.prepareStatement(sql);
				stmt.setString(1, entidade.getNome());
				stmt.setLong(2, entidade.getId());

				stmt.executeUpdate();
				FabricaConexao.connectionCommit();
				optional = buscarPorId(entidade.getId());
			}
		} catch (SQLException e) {
			FabricaConexao.connectionRollback();
			e.printStackTrace();
		}

		return optional.orElse(new Marca());
	}

	@Override
	public Optional<Marca> buscarPorId(Long id) {
		Optional<Marca> optional = Optional.empty();
		try {
			String sql = "SELECT id, nome FROM marca WHERE id = ?";
			stmt = conexao.prepareStatement(sql);
			stmt.setLong(1, id);

			ResultSet resultado = stmt.executeQuery();
			FabricaConexao.connectionCommit();

			if (resultado.next()) {
				Marca marca = new Marca();
				marca.setId(resultado.getLong("id"));
				marca.setNome(resultado.getString("nome"));

				optional = Optional.of(marca);
			}
		} catch (SQLException e) {
			FabricaConexao.connectionRollback();
			e.printStackTrace();
		}

		return optional;
	}

	public Optional<Marca> buscarPorIdComProdutos(Long id) {
		Optional<Marca> optional = Optional.empty();
		try {
			String sql = "SELECT id, nome FROM marca WHERE id = ?";
			stmt = conexao.prepareStatement(sql);
			stmt.setLong(1, id);

			ResultSet resultado = stmt.executeQuery();
			FabricaConexao.connectionCommit();

			if (resultado.next()) {
				Marca marca = new Marca();
				marca.setId(resultado.getLong("id"));
				marca.setNome(resultado.getString("nome"));

				instanciarDAOProduto();

				List<Produto> produtos = daoProduto.obterPorMarca(marca.getId());
				marca.setProdutos(new LinkedHashSet<Produto>(produtos));
				optional = Optional.of(marca);
			}
		} catch (SQLException e) {
			FabricaConexao.connectionRollback();
			e.printStackTrace();
		}

		return optional;
	}

	@Override
	public List<Marca> obterTodos() {
		List<Marca> marcas = new ArrayList<>();
		try {
			String sql = "SELECT id, nome FROM marca ORDER BY nome ASC";
			stmt = conexao.prepareStatement(sql);

			ResultSet resultado = stmt.executeQuery();
			FabricaConexao.connectionCommit();

			while (resultado.next()) {
				Marca marca = new Marca();
				marca.setId(resultado.getLong("id"));
				marca.setNome(resultado.getString("nome"));

				marcas.add(marca);
			}
		} catch (SQLException e) {
			FabricaConexao.connectionRollback();
			e.printStackTrace();
		}

		return marcas;
	}

	public List<Marca> obterTodosComProdutos() {
		List<Marca> marcas = new ArrayList<>();
		try {
			String sql = "SELECT id, nome FROM marca ORDER BY nome ASC";
			stmt = conexao.prepareStatement(sql);

			ResultSet resultado = stmt.executeQuery();
			FabricaConexao.connectionCommit();

			while (resultado.next()) {
				Marca marca = new Marca();
				marca.setId(resultado.getLong("id"));
				marca.setNome(resultado.getString("nome"));

				instanciarDAOProduto();

				List<Produto> produtos = daoProduto.obterPorMarca(marca.getId());
				marca.setProdutos(new LinkedHashSet<Produto>(produtos));

				marcas.add(marca);
			}
		} catch (SQLException e) {
			FabricaConexao.connectionRollback();
			e.printStackTrace();
		}

		return marcas;
	}

	public ModeloGrafico<String, Double> obterValorMedioProdutosMarca() {
		ModeloGrafico<String, Double> modelo = new ModeloGrafico<>();

		try {
			String sql = "SELECT m.id AS id_marca, m.nome AS nome_marca, ROUND(AVG(p.valor), 2) AS media FROM produto p "
					+ "INNER JOIN marca m ON p.marca_id = m.id GROUP BY m.id, m.nome ORDER By m.nome ASC";

			stmt = conexao.prepareStatement(sql);

			ResultSet resultado = stmt.executeQuery();
			FabricaConexao.connectionCommit();

			List<String> marcas = new ArrayList<>();
			List<Double> medias = new ArrayList<>();

			while (resultado.next()) {
				String nome = resultado.getString("nome_marca");

				double valorMedio = resultado.getDouble("media");

				marcas.add(nome);
				medias.add(valorMedio);
			}

			modelo.setLabels(marcas);
			modelo.setDataset(medias);

		} catch (SQLException e) {
			FabricaConexao.connectionRollback();
			e.printStackTrace();
		}

		return modelo;
	}

	public ModeloGrafico<String, Double> obterValorMedioProdutosMarcaValorMaiorIgualQue(double valor) {
		ModeloGrafico<String, Double> modelo = new ModeloGrafico<>();

		try {
			String sql = "SELECT m.id AS id_marca, m.nome AS nome_marca, ROUND(AVG(p.valor), 2) AS media "
					+ "FROM produto p INNER JOIN marca m ON p.marca_id = m.id " + "GROUP BY m.id, m.nome "
					+ "HAVING ROUND(AVG(p.valor), 2) >= ? ORDER BY nome_marca ASC";

			stmt = conexao.prepareStatement(sql);
			stmt.setDouble(1, valor);

			ResultSet resultado = stmt.executeQuery();
			FabricaConexao.connectionCommit();

			List<String> marcas = new ArrayList<>();
			List<Double> medias = new ArrayList<>();

			while (resultado.next()) {
				String nome = resultado.getString("nome_marca");

				double valorMedio = resultado.getDouble("media");

				marcas.add(nome);
				medias.add(valorMedio);
			}

			modelo.setLabels(marcas);
			modelo.setDataset(medias);

		} catch (SQLException e) {
			FabricaConexao.connectionRollback();
			e.printStackTrace();
		}

		return modelo;
	}

	public ModeloGrafico<String, Double> obterValorMedioProdutosMarcaValorMenorIgualQue(double valor) {
		ModeloGrafico<String, Double> modelo = new ModeloGrafico<>();

		try {
			String sql = "SELECT m.id AS id_marca, m.nome AS nome_marca, ROUND(AVG(p.valor), 2) AS media "
					+ "FROM produto p INNER JOIN marca m ON p.marca_id = m.id " + "GROUP BY m.id, m.nome "
					+ "HAVING ROUND(AVG(p.valor), 2) <= ? ORDER BY nome_marca ASC";

			stmt = conexao.prepareStatement(sql);
			stmt.setDouble(1, valor);

			ResultSet resultado = stmt.executeQuery();
			FabricaConexao.connectionCommit();

			List<String> marcas = new ArrayList<>();
			List<Double> medias = new ArrayList<>();

			while (resultado.next()) {
				String nome = resultado.getString("nome_marca");

				double valorMedio = resultado.getDouble("media");

				marcas.add(nome);
				medias.add(valorMedio);
			}

			modelo.setLabels(marcas);
			modelo.setDataset(medias);

		} catch (SQLException e) {
			FabricaConexao.connectionRollback();
			e.printStackTrace();
		}

		return modelo;
	}

	@Override
	public boolean excluirPorId(Long id) {
		if (new DAOProduto().obterPorMarca(id).size() > 0) {
			throw new UnsupportedOperationException(
					"Não foi possível excluir a marca! Exclua primeiro os produtos relacionados a ela.");
		}

		try {
			String sql = "DELETE FROM marca WHERE id = ?";
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

	@Override
	public int obterTotalRegistros() {
		int totalMarcas = 0;
		try {
			String sql = "SELECT COUNT(*) FROM marca";
			stmt = conexao.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				totalMarcas = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return totalMarcas;
	}

	private void instanciarDAOProduto() {
		if (daoProduto != null) {
			return;
		}

		daoProduto = new DAOProduto();
	}

	@Override
	public Pagination<Marca> obterRegistrosPaginadosPreview(Integer numeroPagina, Integer registrosPorPagina,
			Long idUsuarioLogado) {
		Pagination<Marca> pagination = new Pagination<>();

		try {
			int offset = (numeroPagina - 1) * registrosPorPagina;

			String sql = "SELECT id, nome FROM marca ORDER BY id DESC LIMIT ? OFFSET ?";
			stmt = conexao.prepareStatement(sql);
			stmt.setInt(1, registrosPorPagina);
			stmt.setInt(2, offset);

			ResultSet resultado = stmt.executeQuery();
			FabricaConexao.connectionCommit();

			List<Marca> marcas = new ArrayList<>();
			while (resultado.next()) {
				Marca marca = new Marca();
				marca.setId(resultado.getLong("id"));
				marca.setNome(resultado.getString("nome"));

				marcas.add(marca);
			}

			int qtdRegistros = obterTotalRegistros();
			int totalPaginas = Calculador.obterTotalPaginas(qtdRegistros, registrosPorPagina);

			pagination.setContent(marcas);
			pagination.setTotalPages(totalPaginas);
			pagination.setTotalElements(qtdRegistros);
			pagination.setPageable(new Pageable(offset, registrosPorPagina, numeroPagina));

		} catch (SQLException e) {
			FabricaConexao.connectionRollback();
			e.printStackTrace();
		}

		return pagination;
	}

	public Pagination<Marca> obterRegistrosPaginadosPreviewPorNome(String parteNome, Integer numeroPagina,
			Integer registrosPorPagina) {
		Pagination<Marca> pagination = new Pagination<>();

		try {
			int offset = (numeroPagina - 1) * registrosPorPagina;

			String sql = "SELECT m.id, m.nome FROM marca m " + "WHERE LOWER(m.nome) LIKE LOWER(?) "
					+ "ORDER BY id DESC LIMIT ? OFFSET ?";

			stmt = conexao.prepareStatement(sql);
			stmt.setString(1, "%" + parteNome + "%");
			stmt.setInt(2, registrosPorPagina);
			stmt.setInt(3, offset);

			ResultSet resultado = stmt.executeQuery();
			FabricaConexao.connectionCommit();

			List<Marca> marcas = new ArrayList<>();
			while (resultado.next()) {
				Marca marca = new Marca();
				marca.setId(resultado.getLong("id"));
				marca.setNome(resultado.getString("nome"));

				marcas.add(marca);
			}

			int qtdRegistros = obterTotalRegistrosPorNome(parteNome);
			int totalPaginas = Calculador.obterTotalPaginas(qtdRegistros, registrosPorPagina);

			pagination.setContent(marcas);
			pagination.setTotalPages(totalPaginas);
			pagination.setTotalElements(qtdRegistros);
			pagination.setPageable(new Pageable(offset, registrosPorPagina, numeroPagina));

		} catch (SQLException e) {
			FabricaConexao.connectionRollback();
			e.printStackTrace();
		}

		return pagination;
	}

	public int obterTotalRegistrosPorNome(String parteNome) {
		int total = 0;
		try {
			String sql = "SELECT COUNT(*) FROM marca WHERE LOWER(nome) LIKE LOWER(?)";
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
			String sql = "SELECT COUNT(*) FROM marca WHERE id = ?";
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
