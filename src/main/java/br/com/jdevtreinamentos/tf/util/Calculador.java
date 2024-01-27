package br.com.jdevtreinamentos.tf.util;

public class Calculador {

	public static int obterTotalPaginas(int qtdRegistros, int registrosPorPagina) {
		int total = qtdRegistros / registrosPorPagina;

		if (qtdRegistros % registrosPorPagina > 0) {
			total++;
		}

		if (total == 0) {
			total++;
		}

		return total;
	}

}
