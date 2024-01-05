package br.com.jdevtreinamentos.tf.controller.infra;

import lombok.AllArgsConstructor;

/**
 * Enum da <strong>operação<strong> que deu erro.
 * 
 * @author Erik Vasconcelos
 * @since 2024-01-05
 * @version 0.1 2024-01-05
 */

@AllArgsConstructor
public enum OperacaoErro {

	SAVE(1, "Salvar"), OTHER(2, "Outro");

	private Integer codigo;
	private String descricao;
	
	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public static OperacaoErro toEnum(Integer codigo) {
		if (codigo == null)
			return null;

		for (OperacaoErro o : OperacaoErro.values()) {
			if (codigo.equals(o.getCodigo())) {
				return o;
			}
		}

		throw new IllegalArgumentException("Código inválido: " + codigo);
	}

}