package br.com.jdevtreinamentos.tf.model;

import java.util.LinkedHashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe modelo da <strong>entidade Marca<strong>.
 * 
 * @author Erik Vasconcelos
 * @since 2023-12-18
 * @version 0.1 2023-12-18
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Marca {
	
	private Long id;
	private String nome;
	private Set<Produto> produtos = new LinkedHashSet<>();
	
	public boolean isNovo() {
		if (id == null || id.equals(0L)) {
			return true;
		}

		return false;
	}

}
