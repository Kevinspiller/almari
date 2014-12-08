/*
* Classe com implementação dos métodos de verificação de nomes e valores de variáveis 
* Autor: Maico Smaniotto
* E-mail: maicosmaniotto@gmail.com
*/

class Verificacao {
	public static boolean tipoValido(String tipo) {
		return tipo.equals("inteiro") || tipo.equals("real") || tipo.equals("caractere");
	}

	public static boolean charValidoNome(char chr) {
		// retorna se o caractere informado é permitido em um nome de variável ou vetor
		return Character.isLetterOrDigit(chr) || chr == '_';
	}
	
	public static boolean nomeValido(String nome) {
		if (nome.length() == 0) {
			return false;
		}
		// inválido se começa com número
		if (nome.charAt(0) >= '0' && nome.charAt(0) <= '9') {
			return false;
		}
		// testa se todos são válidos
		for (int i = 0; i < nome.length(); i++) {
			if (! charValidoNome(nome.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public static boolean valorValido(String tipo, String valor) {
		if (tipo.equals("inteiro")) {
			try {
				Integer.parseInt(valor);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		} else if (tipo.equals("real")) {
			try {
				Double.parseDouble(valor);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		} else if (tipo.equals("caractere")) {
			// {toDo}
			// verificar se está entre aspas
			return true;			
		} else {
			return false;
		}
	}
	
	public static boolean fimComando(char c) {
		return (c == ';' || c == '{' || c == '}');
	}	

}