package br.com.jdevtreinamentos.tf.util;

import javax.servlet.http.HttpServletRequest;

/**
 * Classe responsável por manipular a lógica de incremento de tempo de sessãp
 * para quando a sessão não expirar enquanto o usuário estuiver usando o
 * sistema.
 * 
 * @author Erik Vasconcelos
 * @since 2024-01-10
 * @version 0.1 2024-01-10
 */

public class SessaoUtil {

	private static final int TEMPO_PADRAO = 30;

	public static void atualizarSessao(HttpServletRequest request) {
		request.getSession().setMaxInactiveInterval(TEMPO_PADRAO * 60);
	}

	public static void atualizarSessao(HttpServletRequest request, int minutos) {
		request.getSession().setMaxInactiveInterval(minutos * 60);
	}

}
