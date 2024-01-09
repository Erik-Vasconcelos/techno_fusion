package br.com.jdevtreinamentos.tf.model;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.jdevtreinamentos.tf.model.enumeration.EnumSexo;
import br.com.jdevtreinamentos.tf.model.enumeration.PerfilFuncionario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe modelo da <strong>entidade Funcionário<strong>.
 * 
 * @author Erik Vasconcelos
 * @since 2023-12-18
 * @version 0.1 2023-12-18
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Funcionario {

	private Long id;
	private String nome;
	private EnumSexo sexo;
	private LocalDate dataNascimento;
	private String email;
	private Double salario;
	private String imagem;
	private PerfilFuncionario perfil;
	private String login;
	private String senha;
	private Set<Telefone> telefones = new LinkedHashSet<>();

	@JsonIgnore
	public boolean isNovo() {
		if (id == null || id.equals(0L)) {
			return true;
		}

		return false;
	}

}
