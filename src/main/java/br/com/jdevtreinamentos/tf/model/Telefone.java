package br.com.jdevtreinamentos.tf.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	
	@JsonIgnore
	private Funcionario funcionario;
	
	@JsonIgnore
	public boolean isNovo() {
		if (id == null || id.equals(0L)) {
			return true;
		}

		return false;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Telefone other = (Telefone) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	
}
