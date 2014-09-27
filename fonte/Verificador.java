class Verificador {
	public static boolean tipoValido(String tipo) {
		return tipo.equals("inteiro") || tipo.equals("real") || tipo.equals("caractere") || tipo.equals("palavra");
	}

	public static boolean charValidoNome(char chr) {
		// retorna se o caractere informado é permitido em um nome de variável ou função
		// {toDo}
		return true;
	}
	
	public static boolean nomeValido(String nome) {
		if (nome.length() == 0) {
			return false;
		}
		
		/*
		// se já existe variável ou função no escopo com o nome informado, não permite declarar
		if (escopo.existeVariavel(nome) || escopo.existeFuncao(nome)) {
			return false;
		}
		*/
		
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

}