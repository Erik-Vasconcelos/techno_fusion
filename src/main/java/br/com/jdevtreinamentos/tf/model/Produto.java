package br.com.jdevtreinamentos.tf.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe modelo da <strong>entidade Produto<strong>.
 * 
 * @author Erik Vasconcelos
 * @since 2023-12-18
 * @version 0.1 2023-12-18
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Produto {

	private Long id;
	private String descricao;
	private String modelo;
	private String caracteristicas;
	private String imagem;
	private Double valor;
	private Double desconto;
	private Marca marca;
	private Funcionario cadastrador;

	public boolean isNovo() {
		if (id == null || id.equals(0L)) {
			return true;
		}

		return false;
	}

}
