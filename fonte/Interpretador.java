/*
* Classe para identificar a função principal do programa e instanciar seu escopo para execução
* Autor: Maico Smaniotto
* E-mail: maicosmaniotto@gmail.com
*/

class Interpretador {
	public void interpreta(String comandos) {
		int i = 0;
		String bloco = "";
		Escopo principal;
		
		while (i < comandos.length()) {
			i = comandos.indexOf("func", i);
			i += 5;
			while (Character.isWhitespace(comandos.charAt(i)) && i < comandos.length()) {
				i++;
			}
			if (comandos.substring(i, i + 9).equals("principal")) {
				i += 9;
				try {
					bloco = Escopo.carregaBloco(comandos, i);
					principal = new Escopo(bloco, null, null);
					principal.processa();
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				break;
			}
		}
	}
}