package br.com.jdevtreinamentos.tf.controller.infra;

import lombok.AllArgsConstructor;

/**
 * Enum do <strong>status da reposta<strong> das operações.
 * 
 * @author Erik Vasconcelos
 * @since 2023-12-28
 * @version 0.2 2024-01-05
 */

@AllArgsConstructor
public enum StatusResposta {

	SUCCESS(1, "SUCESSO"), INFORMATION(2, "INFORMACAO"), ERROR(3, "ERRO");

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

	public static StatusResposta toEnum(Integer codigo) {
		if (codigo == null)
			return null;

		for (StatusResposta s : StatusResposta.values()) {
			if (codigo.equals(s.getCodigo())) {
				return s;
			}
		}

		throw new IllegalArgumentException("Código inválido: " + codigo);
	}

}
