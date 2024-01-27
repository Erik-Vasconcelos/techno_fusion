package br.com.jdevtreinamentos.tf.model.enumeration;

import lombok.AllArgsConstructor;

/**
 * Enum do <strong>sexo<strong> dos funcionários.
 * 
 * @author Erik Vasconcelos
 * @since 2023-12-18
 * @version 0.1 2023-12-18
 */

@AllArgsConstructor
public enum EnumSexo {

	MASCULINO(1, "Masculino", "M"),
	FEMININO(2, "Feminino", "F");

	private Integer codigo;
	private String descricao;
	private String sigla;

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
	
	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	
	public static EnumSexo toEnum(Integer codigo) {
		if (codigo == null)
			return null;

		for (EnumSexo es : EnumSexo.values()) {
			if (codigo.equals(es.getCodigo())) {
				return es;
			}
		}

		throw new IllegalArgumentException("Código inválido: " + codigo);
	}
	
	public static EnumSexo toEnum(String name) {
		if (name == null)
			return null;

		for (EnumSexo es : EnumSexo.values()) {
			if (name.equals(es.name())) {
				return es;
			}
		}

		throw new IllegalArgumentException("Nome inválido: " + name);
	}
	
	public static EnumSexo toEnumBySigla(String sigla) {
		if (sigla == null)
			return null;

		for (EnumSexo es : EnumSexo.values()) {
			if (sigla.equals(es.getSigla())) {
				return es;
			}
		}

		throw new IllegalArgumentException("Sigla inválida: " + sigla);
	}

}
