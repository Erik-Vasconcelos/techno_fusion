package br.com.jdevtreinamentos.tf.exception;

/**
 * Classe de excessão personalizada para quando os dados forem inválidos.
 * 
 * @author Erik Vasconcelos
 * @since 2023-12-30
 * @version 0.1 2023-12-30
 */

public class RecursoNaoEncontradoException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private static final String MSG_PADRAO = "O recurso procurado não foi encontrado";

	public RecursoNaoEncontradoException() {
		super(MSG_PADRAO);
	}

	public RecursoNaoEncontradoException(String message, Throwable cause) {
		super(message, cause);
	}

	public RecursoNaoEncontradoException(String message) {
		super(message);
	}

}
