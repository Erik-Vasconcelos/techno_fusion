package br.com.jdevtreinamentos.tf.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe utilitária para encapsular as informações da pagina solicitada.
 * 
 * @author Erik Vasconcelos
 * @since 2024-01-04
 * @version 0.1 2024-01-04
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Pageable {

	private int offset;
	private int pageSize;
	private int pageNumber;

}
