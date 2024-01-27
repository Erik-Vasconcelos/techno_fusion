package br.com.jdevtreinamentos.tf.util;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe utilitária para encapsular o objeto e as informações de paginação.
 * 
 * @author Erik Vasconcelos
 * @since 2024-01-04
 * @version 0.1 2024-01-04
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Pagination<T> {

	private List<T> content;
	private Pageable pageable;
	private int totalPages;
	private int totalElements;
	
}
