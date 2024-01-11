package br.com.jdevtreinamentos.tf.util;

import org.apache.commons.codec.digest.DigestUtils;

import br.com.jdevtreinamentos.tf.model.Funcionario;

/**
 * Classe reponsável por gerenciar a geraão de senha padrão do funcionário.
 * 
 * @author Erik Vasconcelos
 * @since 2023-12-28
 * @version 0.3 2024-01-10
 */

public class GeradorSenha {

	public static String encriptarSenha(String senha) {
		return DigestUtils.sha1Hex(senha);
	}

	public static String gerarSenhaPadrão(Funcionario funcionario) {
		if (verificarRequisitios(funcionario)) {
			String senha = DigestUtils.sha1Hex(funcionario.getEmail() + funcionario.getLogin());

			return senha;
		}

		throw new IllegalArgumentException("Argumentos inválidos para geração de senha - email: "
				+ funcionario.getEmail() + " login: " + funcionario.getLogin());
	}

	private static boolean verificarRequisitios(Funcionario funcionario) {
		if (funcionario != null) {
			boolean emailValido = funcionario.getEmail() != null ? !funcionario.getEmail().isBlank() : false;
			boolean loginValido = funcionario.getLogin() != null ? !funcionario.getLogin().toString().isBlank() : false;

			if (emailValido && loginValido) {
				return true;
			}

		}

		return false;
	}

}
