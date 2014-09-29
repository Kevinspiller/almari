class Interpretador {
	public void interpreta(String comandos) throws IllegalArgumentException {
		int i = 0;
		int inicioBloco, chaveInicio, chaveFim;
		String bloco = "";
		Escopo principal;
		
		while (i < comandos.length()) {
			i = comandos.indexOf("func", i);
			i += 5;
			while (Character.isWhitespace(comandos.charAt(i)) && i < comandos.length()) {
				i++;
			}
			if (comandos.substring(i, i + 9).equals("principal")) {
				//System.out.println(comandos.substring(i, i + 9));
				i += 9;
				while (Character.isWhitespace(comandos.charAt(i)) && i < comandos.length()) {
					i++;
				}
				if (comandos.charAt(i) == '{') {
					inicioBloco = ++i;
					chaveInicio = 1;
					chaveFim = 0;
					while (i < comandos.length() && chaveInicio > chaveFim) {
						if (comandos.charAt(i) == '{') {
							chaveInicio++;
						} else if (comandos.charAt(i) == '}') {
							chaveFim++;
						}
						i++;
					}
					if (chaveInicio == chaveFim) {
						bloco = comandos.substring(inicioBloco, i - 1);
						//System.out.println(bloco);
						principal = new Escopo(comandos, null);
						principal.processa();
					} else {
						throw new IllegalArgumentException("Esperado caractere de fim de bloco \"}\"");
					}
				} else {
					throw new IllegalArgumentException("Esparedo caractere de inicio de bloco \"{\"");
				}
				break;
			}
		}
		//System.out.println(comandos);
	}
}