/*
* Classe com implementação do método para avaliar condições
* Autor: Maico Smaniotto
* E-mail: maicosmaniotto@gmail.com
*/

import java.util.*;

class Condicao {
	public static boolean avaliaCondicao(Escopo escopo, String condicao) throws Exception {
		Variavel var1 = null;
		Variavel var2 = null;
		double valor1 = 0.0;
		double valor2 = 0.0;
		
		ArrayList<String> tokens = Expressao.separaTokens(condicao);
		if (tokens != null && tokens.size() == 3) {
			var1 = escopo.buscaVariavel(tokens.get(0));
			if (var1 != null) {
				switch (var1.getTipo()) {
					case 'I':
						valor1 = (double)(((Inteiro)var1).getValor());
						break;
					case 'R':
						valor1 = ((Real)var1).getValor();
						break;
				}
			} else {
				try {
					valor1 = Double.parseDouble(tokens.get(0));
				} catch (NumberFormatException e) {
					throw new IllegalArgumentException("Numero invalido: " + tokens.get(0));
				}
			}
			
			var2 = escopo.buscaVariavel(tokens.get(2));			
			if (var2 != null) {
				switch (var2.getTipo()) {
					case 'I':
						valor2 = (double)(((Inteiro)var2).getValor());
						break;
					case 'R':
						valor2 = ((Real)var2).getValor();
						break;
				}
			} else {
				try {
					valor2 = Double.parseDouble(tokens.get(2));
				} catch (NumberFormatException e) {
					throw new IllegalArgumentException("Numero invalido: " + tokens.get(2));
				}
			}
			if (tokens.get(1).equals("=")) {
				return valor1 == valor2;
			} else if (tokens.get(1).equals(">")) {
				return valor1 > valor2;
			} else if (tokens.get(1).equals("<")) {
				return valor1 < valor2;
			} else if (tokens.get(1).equals(">=")) {
				return valor1 >= valor2;
			} else if (tokens.get(1).equals("<=")) {
				return valor1 <= valor2;
			} if (tokens.get(1).equals("!=")) {
				return valor1 != valor2;
			} else {
				throw new IllegalArgumentException("Operador desconhecido: " + tokens.get(1));
			}
		} else {
			throw new IllegalArgumentException("Condicao invalida");		
		}
	}
}