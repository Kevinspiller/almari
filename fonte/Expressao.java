/*
* Classe para resolver expressões de atribuição de valores em variáveis, bem como operaçõe de soma, subtração, multiplicação e divisão 
* Autor: Maico Smaniotto
* E-mail: maicosmaniotto@gmail.com
*/

import java.util.*;

class Expressao {
	public static ArrayList<String> separaTokens(String expr) throws Exception {
		boolean entreAspa = false;
		
		ArrayList<String> tokens;
		String token = "";
		
		expr = expr.trim();
		if (expr.length() != 0) {   
			tokens = new ArrayList<String>();

			entreAspa = false;
			for (int i = 0; i < expr.length(); i++) {
				if (expr.charAt(i) == '"') {
					entreAspa = !entreAspa;
				}
				if ((!Character.isWhitespace(expr.charAt(i)) && expr.charAt(i) != ';') || entreAspa) {
					token += expr.charAt(i);
				} else {
					if (token.length() != 0) {
						tokens.add(token);
					}
					token = "";
				}
			}
			if (token.length() != 0) {
				tokens.add(token);
			}
			// se chegou no final e não encontrou o número correto de aspas, lança uma exceção avisando do erro
			if (entreAspa) {
				throw new IllegalArgumentException("Terminador da cadeia de caracteres \" não encontrado");
			}
			
			return tokens;
		} else {
			return null;
		}
	}

	public static int resolveInteiro(Escopo escopo, String esquerda, String operador, String direita) {
		int valor1 = 0;
		int valor2 = 0;
		Variavel var1 = escopo.buscaVariavel(esquerda);
		Variavel var2 = escopo.buscaVariavel(direita);
		if (var1 != null) {
			valor1 = ((Inteiro)var1).getValor();
		} else {
			try {
				valor1 = Integer.parseInt(esquerda);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Número inválido: " + esquerda);
			}
		}
		if (var2 != null) {
			valor2 = ((Inteiro)var2).getValor();
		} else {
			try {
				valor2 = Integer.parseInt(direita);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Número inválido: " + direita);
			}
		}
		if (operador.equals("+")) {
			return valor1 + valor2;
		} else if (operador.equals("-")) {
			return valor1 - valor2;
		} else if (operador.equals("*")) {
			return valor1 * valor2;
		} else if (operador.equals("/")) {
			return valor1 / valor2;
		} else if (operador.equals("%")) {
			return valor1 % valor2;
		} else {
			throw new IllegalArgumentException("Operador " + operador + " não definido");
		}
	}
	
	public static double resolveReal(Escopo escopo, String esquerda, String operador, String direita) {
		double valor1 = 0.0;
		double valor2 = 0.0;
		Variavel var1 = escopo.buscaVariavel(esquerda);
		Variavel var2 = escopo.buscaVariavel(direita);
		if (var1 != null) {
			valor1 = ((Real)var1).getValor();
		} else {
			try {
				valor1 = Double.parseDouble(esquerda);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Número inválido: " + esquerda);
			}
		}
		if (var2 != null) {
			valor2 = ((Real)var2).getValor();
		} else {
			try {
				valor2 = Double.parseDouble(direita);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Número inválido: " + direita);
			}
		}
		if (operador.equals("+")) {
			return valor1 + valor2;
		} else if (operador.equals("-")) {
			return valor1 - valor2;
		} else if (operador.equals("*")) {
			return valor1 * valor2;
		} else if (operador.equals("/")) {
			return valor1 / valor2;
		} else {
			throw new IllegalArgumentException("Operador " + operador + " não definido");
		}
	}
	
	public static String resolveCaractere(Escopo escopo, String esquerda, String operador, String direita) {
		String valor1 = "";
		String valor2 = "";
		Variavel var1 = escopo.buscaVariavel(esquerda);
		Variavel var2 = escopo.buscaVariavel(direita);
		if (var1 != null) {
			valor1 = ((Caractere)var1).getValor();
		} else {
			valor1 = esquerda;
		}
		if (var2 != null) {
			valor2 = ((Caractere)var2).getValor();
		} else {
			valor2 = direita;
		}
		if (operador.equals("+")) {
			return valor1 + valor2;
		} else {
			throw new IllegalArgumentException("Operador " + operador + " não definido");
		}		
	}
	
	public static void resolveExpressao(Escopo escopo, String expr) throws Exception {
		Variavel var1 = null;
		Variavel var2 = null;

		ArrayList<String> tokens = separaTokens(expr);
		if (tokens != null) {
			var1 = escopo.buscaVariavel(tokens.get(0));

			if (var1 != null) {
				if (tokens.size() > 1 && tokens.get(1).equals(":=")) {
					switch (var1.getTipo()) {
						case 'I':
							int resultInt = 0;
							if (tokens.size() == 3) {
								var2 = escopo.buscaVariavel(tokens.get(2));
								if (var2 != null) {
									resultInt = ((Inteiro)var2).getValor();
								}
							} else if (tokens.size() == 5) {
								resultInt = resolveInteiro(escopo, tokens.get(2), tokens.get(3), tokens.get(4));
							} else {
								throw new IllegalArgumentException("Expressao invalida");
							}
							((Inteiro)var1).setValor(resultInt);
							break;
						case 'R':
							double resultReal = 0.0;
							if (tokens.size() == 3) {
								var2 = escopo.buscaVariavel(tokens.get(2));
								if (var2 != null) {
									resultReal = ((Real)var2).getValor();
								}
							} else if (tokens.size() == 5) {
								resultReal = resolveReal(escopo, tokens.get(2), tokens.get(3), tokens.get(4));
							} else {
								throw new IllegalArgumentException("Expressao invalida");
							}
							((Real)var1).setValor(resultReal);
							break;
						case 'C':
							String resultCaractere = "";
							if (tokens.size() == 3) {
								var2 = escopo.buscaVariavel(tokens.get(2));
								if (var2 != null) {
									resultCaractere = ((Caractere)var2).getValor();
								}
							} else if (tokens.size() == 5) {
								resultCaractere = resolveCaractere(escopo, tokens.get(2), tokens.get(3), tokens.get(4));
							} else {
								throw new IllegalArgumentException("Expressão inválida");
							}
							((Caractere)var1).setValor(resultCaractere);
							break;
					}
				} else {
					throw new IllegalArgumentException("Esperado operador de atribuição :=");
				}
			} else {
				throw new IllegalArgumentException("Comando desconhecido: " + tokens.get(0));
			}
		} else {
		
		}
	}
}