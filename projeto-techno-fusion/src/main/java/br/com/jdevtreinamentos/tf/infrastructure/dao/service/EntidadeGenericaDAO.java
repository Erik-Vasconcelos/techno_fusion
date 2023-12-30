package br.com.jdevtreinamentos.tf.infrastructure.dao.service;

import java.util.List;
import java.util.Optional;

/**
 * Interface que definie as operações que as classes de DAO devem implementar e
 * disponibilizar que serem utilizadas.
 * 
 * @author Erik Vasconcelos
 * @since 2023-12-18
 * @version 0.1 2023-12-18
 */

public interface EntidadeGenericaDAO<T>{

	public T salvar(T entidade);

	public Optional<T> buscarPorId(Long id);
	
	public List<T> obterTodos();
	
	public List<T> obterRegistrosPaginados(Integer pagina);

	public boolean excluirPorId(Long id);
	
}
