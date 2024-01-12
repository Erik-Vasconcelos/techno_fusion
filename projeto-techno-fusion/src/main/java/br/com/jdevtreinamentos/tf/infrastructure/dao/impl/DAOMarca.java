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
import br.com.jdevtreinamentos.tf.model.Marca;
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

public class DAOMarca implements Serializable, EntidadeGenericaDAO<Marca> {

    private static final long serialVersionUID = 1L;

    private Connection conexao;
    private PreparedStatement stmt;

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

    @Override
    public List<Marca> obterTodos() {
        List<Marca> marcas = new ArrayList<>();
        try {
            String sql = "SELECT id, nome FROM marca ORDER BY id DESC";
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

    @Override
    public boolean excluirPorId(Long id) {
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

    // Adicione outros métodos específicos se necessário

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

    @Override
    public Pagination<Marca> obterRegistrosPaginadosPreview(Integer numeroPagina, Integer registrosPorPagina) {
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

            String sql = "SELECT m.id, m.nome FROM marca m "
                    + "WHERE LOWER(m.nome) LIKE LOWER(?) " + "ORDER BY id DESC LIMIT ? OFFSET ?";

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

                // Adicione outros atributos da entidade Marca conforme necessário

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
