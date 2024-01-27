package br.com.jdevtreinamentos.tf.exception;

/**
 * Classe de excessão personalizada para quando os dados forem inválidos.
 * 
 * @author Erik Vasconcelos
 * @since 2023-12-30
 * @version 0.1 2023-12-30
 */

public class DadosInvalidosException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private static final String MSG_PADRAO = "Os dados inseridos são inválidos, verifique os campos!";

	public DadosInvalidosException() {
		super(MSG_PADRAO);
	}

	public DadosInvalidosException(String message, Throwable cause) {
		super(message, cause);
	}

	public DadosInvalidosException(String message) {
		super(message);
	}

}
