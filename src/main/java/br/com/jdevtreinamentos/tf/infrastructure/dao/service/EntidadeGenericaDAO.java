package br.com.jdevtreinamentos.tf.infrastructure.dao.service;

import java.util.List;
import java.util.Optional;

import br.com.jdevtreinamentos.tf.util.Pagination;

/**
 * Interface que definie as operações que as classes de DAO devem implementar e
 * disponibilizar que serem utilizadas.
 * 
 * @author Erik Vasconcelos
 * @since 2023-12-18
 * @version 0.2 2024-01-08
 */

public interface EntidadeGenericaDAO<T> {

	public T salvar(T entidade);

	public Optional<T> buscarPorId(Long id);

	public List<T> obterTodos();

	public Pagination<T> obterRegistrosPaginadosPreview(Integer numeroPagina, Integer registrosPorPagina, Long idUsuarioLogado);

	public boolean excluirPorId(Long id);

	public int obterTotalRegistros();

	public boolean registroExiste(Long id);

}
