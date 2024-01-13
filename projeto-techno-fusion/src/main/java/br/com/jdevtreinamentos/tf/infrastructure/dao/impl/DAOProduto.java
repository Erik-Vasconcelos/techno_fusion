package br.com.jdevtreinamentos.tf.infrastructure.dao.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import br.com.jdevtreinamentos.tf.infrastructure.connection.FabricaConexao;
import br.com.jdevtreinamentos.tf.infrastructure.dao.service.EntidadeGenericaDAO;
import br.com.jdevtreinamentos.tf.model.Funcionario;
import br.com.jdevtreinamentos.tf.model.Marca;
import br.com.jdevtreinamentos.tf.model.Produto;
import br.com.jdevtreinamentos.tf.util.Calculador;
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

public class DAOProduto implements Serializable, EntidadeGenericaDAO<Produto> {

	private static final long serialVersionUID = 1L;

	private Connection conexao;
	private PreparedStatement stmt;
	private DAOMarca daoMarca;
	private DAOFuncionario daoFuncionario;

	public DAOProduto() {
		conexao = FabricaConexao.getConnection();
		daoMarca = new DAOMarca();
		daoFuncionario = new DAOFuncionario();
	}

	@Override
	public Produto salvar(Produto entidade) {
		Optional<Produto> optional = Optional.empty();
		try {

			String sql = "";
			if (entidade.isNovo()) {
				sql = "INSERT INTO produto(descricao, modelo, caracteristicas, imagem, valor, desconto, marca_id, cadastrador_id) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
				stmt = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
				stmt.setString(1, entidade.getDescricao());
				stmt.setString(2, entidade.getModelo());
				stmt.setString(3, entidade.getCaracteristicas());
				stmt.setString(4, entidade.getImagem());
				stmt.setDouble(5, entidade.getValor());
				stmt.setDouble(6, entidade.getDesconto());
				stmt.setLong(7, entidade.getMarca().getId());
				stmt.setLong(8, entidade.getCadastrador().getId());
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
				sql = "UPDATE PRODUTO SET descricao=?, modelo=?, caracteristicas=?, imagem=?, valor=?, desconto=?, marca_id=?, cadastrador_id=? WHERE id=?";
				stmt = conexao.prepareStatement(sql);
				stmt.setString(1, entidade.getDescricao());
				stmt.setString(2, entidade.getModelo());
				stmt.setString(3, entidade.getCaracteristicas());
				stmt.setString(4, entidade.getImagem());
				stmt.setDouble(5, entidade.getValor());
				stmt.setDouble(6, entidade.getDesconto());
				stmt.setLong(7, entidade.getMarca().getId());
				stmt.setLong(8, entidade.getCadastrador().getId());
				stmt.setLong(9, entidade.getId());

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

			throw new RuntimeException("Não foi possível salvar o produto!");
		}

		return optional.orElse(new Produto());
	}

	public void salvarImagem(Produto produto) {
		try {
			String sql = "UPDATE produto SET imagem = ? WHERE id = ?";
			stmt = conexao.prepareStatement(sql);
			stmt.setString(1, produto.getImagem());
			stmt.setLong(2, produto.getId());

			stmt.execute();
			FabricaConexao.connectionCommit();

		} catch (SQLException e) {
			FabricaConexao.connectionRollback();
			e.printStackTrace();
		}
	}

	@Override
	public Optional<Produto> buscarPorId(Long id) {
		Optional<Produto> optional = Optional.empty();
		try {
			String sql = "SELECT p.id, p.descricao, p.modelo, p.caracteristicas, p.imagem, p.valor, p.desconto, p.marca_id, p.cadastrador_id FROM Produto p WHERE id = ?";
			stmt = conexao.prepareStatement(sql);
			stmt.setLong(1, id);

			ResultSet resultado = stmt.executeQuery();
			FabricaConexao.connectionCommit();

			if (resultado.next()) {
				Produto produto = new Produto();
				produto.setId(resultado.getLong("id"));
				produto.setDescricao(resultado.getString("descricao"));
				produto.setModelo(resultado.getString("modelo"));
				produto.setCaracteristicas(resultado.getString("caracteristicas"));
				produto.setImagem(resultado.getString("imagem"));
				produto.setValor(resultado.getDouble("valor"));
				produto.setDesconto(resultado.getDouble("desconto"));

				// Recuperar a marca associada ao produto
				Long marcaId = resultado.getLong("marca_id");
				Marca marca = daoMarca.buscarPorId(marcaId).get();
				produto.setMarca(marca);

				// Recuperar o funcionário cadastrador associado ao produto
				Long cadastradorId = resultado.getLong("cadastrador_id");
				Funcionario cadastrador = daoFuncionario.buscarNomePorId(cadastradorId).get();
				produto.setCadastrador(cadastrador);

				optional = Optional.of(produto);

			}

		} catch (SQLException e) {
			FabricaConexao.connectionRollback();
			e.printStackTrace();
		}

		return optional;
	}

	@Override
	public List<Produto> obterTodos() {
		List<Produto> produtos = new ArrayList<>();
		try {
			String sql = "SELECT p.id, p.descricao, p.modelo, p.caracteristicas, p.imagem, p.valor, p.desconto, p.marca_id, p.cadastrador_id FROM produto p ORDER BY id DESC";
			stmt = conexao.prepareStatement(sql);

			ResultSet resultado = stmt.executeQuery();
			FabricaConexao.connectionCommit();

			while (resultado.next()) {
				Produto produto = new Produto();
				produto.setId(resultado.getLong("id"));
				produto.setDescricao(resultado.getString("descricao"));
				produto.setModelo(resultado.getString("modelo"));
				produto.setCaracteristicas(resultado.getString("caracteristicas"));
				produto.setImagem(resultado.getString("imagem"));
				produto.setValor(resultado.getDouble("valor"));
				produto.setDesconto(resultado.getDouble("desconto"));

				// Recuperar a marca associada ao produto
				Long marcaId = resultado.getLong("marca_id");
				Marca marca = new Marca();
				marca.setId(marcaId);
				produto.setMarca(marca);

				// Recuperar o funcionário cadastrador associado ao produto
				Long cadastradorId = resultado.getLong("cadastrador_id");
				Funcionario cadastrador = new Funcionario();
				cadastrador.setId(cadastradorId);
				produto.setCadastrador(cadastrador);

				produtos.add(produto);
			}

		} catch (SQLException e) {
			FabricaConexao.connectionRollback();
			e.printStackTrace();
		}

		return produtos;
	}

	@Override
	public boolean excluirPorId(Long id) {
		try {
			String sql = "DELETE FROM produto WHERE id = ?";
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
		int totalProdutos = 0;

		try {
			String sql = "SELECT COUNT(*) FROM produto";
			stmt = conexao.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				totalProdutos = rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return totalProdutos;
	}
	
	@Override
	public Pagination<Produto> obterRegistrosPaginadosPreview(Integer numeroPagina, Integer registrosPorPagina, Long idUsuarioLogado) {
		Pagination<Produto> pagination = new Pagination<>();

		try {
			int offset = (numeroPagina - 1) * registrosPorPagina;

			String sql = "SELECT p.id, p.descricao, p.modelo, p.caracteristicas, p.imagem, p.valor, p.desconto, p.marca_id, p.cadastrador_id FROM produto p ORDER BY id DESC LIMIT ? OFFSET ?";
			stmt = conexao.prepareStatement(sql);
			stmt.setInt(1, registrosPorPagina);
			stmt.setInt(2, offset);

			ResultSet resultado = stmt.executeQuery();
			FabricaConexao.connectionCommit();

			List<Produto> produtos = new ArrayList<>();
			while (resultado.next()) {
				Produto produto = new Produto();
				produto.setId(resultado.getLong("id"));
				produto.setDescricao(resultado.getString("descricao"));
				produto.setModelo(resultado.getString("modelo"));
				produto.setCaracteristicas(resultado.getString("caracteristicas"));
				produto.setImagem(resultado.getString("imagem"));
				produto.setValor(resultado.getDouble("valor"));
				produto.setDesconto(resultado.getDouble("desconto"));

				Long marcaId = resultado.getLong("marca_id");
				Marca marca = daoMarca.buscarPorId(marcaId).get();
				produto.setMarca(marca);

				// Recuperar o funcionário cadastrador associado ao produto
				Long cadastradorId = resultado.getLong("cadastrador_id");
				Funcionario cadastrador = daoFuncionario.buscarNomePorId(cadastradorId).get();
				produto.setCadastrador(cadastrador);

				produtos.add(produto);
			}

			int qtdRegistros = obterTotalRegistros();
			int totalPaginas = Calculador.obterTotalPaginas(qtdRegistros, registrosPorPagina);

			pagination.setContent(produtos);
			pagination.setTotalPages(totalPaginas);
			pagination.setTotalElements(qtdRegistros);
			pagination.setPageable(new Pageable(offset, registrosPorPagina, numeroPagina));

		} catch (SQLException e) {
			FabricaConexao.connectionRollback();
			e.printStackTrace();
		}

		return pagination;
	}

	public Pagination<Produto> obterRegistrosPaginadosPreviewPorDescricao(String parteDescricao, Integer numeroPagina,
			Integer registrosPorPagina) {
		Pagination<Produto> pagination = new Pagination<>();

		try {
			int offset = (numeroPagina - 1) * registrosPorPagina;

			String sql = "SELECT p.id, p.descricao, p.modelo, p.caracteristicas, p.imagem, p.valor, p.desconto, "
					+ "p.marca_id, p.cadastrador_id FROM produto p "
					+ "WHERE LOWER(p.descricao) LIKE LOWER(?) ORDER BY id DESC LIMIT ? OFFSET ?";

			stmt = conexao.prepareStatement(sql);
			stmt.setString(1, "%" + parteDescricao + "%");
			stmt.setInt(2, registrosPorPagina);
			stmt.setInt(3, offset);

			ResultSet resultado = stmt.executeQuery();
			FabricaConexao.connectionCommit();

			List<Produto> produtos = new ArrayList<>();
			while (resultado.next()) {
				Produto produto = new Produto();
				produto.setId(resultado.getLong("id"));
				produto.setDescricao(resultado.getString("descricao"));
				produto.setModelo(resultado.getString("modelo"));
				produto.setCaracteristicas(resultado.getString("caracteristicas"));
				produto.setImagem(resultado.getString("imagem"));
				produto.setValor(resultado.getDouble("valor"));
				produto.setDesconto(resultado.getDouble("desconto"));

				Marca marca = daoMarca.buscarPorId(resultado.getLong("marca_id")).get();
				produto.setMarca(marca);

				Funcionario cadastrador = daoFuncionario.buscarNomePorId(resultado.getLong("cadastrador_id")).get();
				produto.setCadastrador(cadastrador);

				produtos.add(produto);
			}

			int qtdRegistros = obterTotalRegistrosPorDescricao(parteDescricao);
			int totalPaginas = Calculador.obterTotalPaginas(qtdRegistros, registrosPorPagina);

			pagination.setContent(produtos);
			pagination.setTotalPages(totalPaginas);
			pagination.setTotalElements(qtdRegistros);
			pagination.setPageable(new Pageable(offset, registrosPorPagina, numeroPagina));

		} catch (SQLException e) {
			FabricaConexao.connectionRollback();
			e.printStackTrace();
		}

		return pagination;
	}

	public int obterTotalRegistrosPorDescricao(String parteDescricao) {
		int total = 0;
		try {
			String sql = "SELECT COUNT(*) FROM produto WHERE LOWER(descricao) LIKE LOWER(?)";
			PreparedStatement stmtCount = conexao.prepareStatement(sql);
			stmtCount.setString(1, "%" + parteDescricao + "%");
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
			String sql = "SELECT COUNT(*) FROM produto WHERE id = ?";
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
