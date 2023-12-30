package br.com.jdevtreinamentos.tf.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe modelo da <strong>entidade Telefone<strong>.
 * 
 * @author Erik Vasconcelos
 * @since 2023-12-18
 * @version 0.1 2023-12-18
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Telefone {
	
	private Long id;
	private String numero;
	private Funcionario funcionario;

}
