package br.com.jdevtreinamentos.tf.controller.infra;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe que encapsula o status da operação, o objeto e a mensagem a ser enviada para a view
 * do <strong>Funcionário<strong>.
 * 
 * @author Erik Vasconcelos
 * @since 2023-12-28
 * @version 0.1 2023-12-28
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponseEntity<T> {

	private StatusResposta status;
	private T artefato;
	private String mensagem;
}
