package br.com.jdevtreinamentos.tf.model.enumeration;

import lombok.AllArgsConstructor;

/**
 * Enum do <strong>perfil<strong> dos funcionários.
 * 
 * @author Erik Vasconcelos
 * @since 2023-12-18
 * @version 0.1 2023-12-18
 */

@AllArgsConstructor
public enum PerfilFuncionario {

	GERENTE(1, "Gerente"), FUNCIONARIO(2, "Funcionário");

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

	public static PerfilFuncionario toEnum(Integer codigo) {
		if (codigo == null)
			return null;

		for (PerfilFuncionario perfil : PerfilFuncionario.values()) {
			if (codigo.equals(perfil.getCodigo())) {
				return perfil;
			}
		}

		throw new IllegalArgumentException("Código inválido: " + codigo);
	}
	
	public static PerfilFuncionario toEnum(String name) {
		if (name == null)
			return null;

		for (PerfilFuncionario p : PerfilFuncionario.values()) {
			if (name.equals(p.name())) {
				return p;
			}
		}

		throw new IllegalArgumentException("Nome inválido: " + name);
	}

}
