class Saida {

	public void imprime(Escopo escopo, String linha){
		int i = 0, j;
		while (i < linha.length()) {                                          // vai rodar o While enquanto o a não for maior que o tamanho do vetor
			if (linha.charAt(i) == '"') { //Testa se aquela parte do vetor (token) contém aspas 
				for (i++; linha.charAt(i) != '"' && i < linha.length(); i++) { 		//vai imprimindo tudo que esta entre as aspas, até achar uma outra aspa
					System.out.print(linha.charAt(i));
				}
			} else if (Verificador.charValidoNome(linha.charAt(i))) {   //testa se o caractere é uma letra, que caracteriza o inicio do nome da variavel 
				String nomVar = "";												// seria a criação de um vetor char, para salvar esses caracteres
				j = 0;

				for (; (! Verificador.ehEspacoEmBranco(linha.charAt(i))) && (! Verificador.ehSimbolo(linha.charAt(i))) && i < linha.length(); i++, j++) {                         //aqui ele salva todos os caracteres seguintes, até o espaço, que seria o nome da variavel																		
					nomVar += linha.charAt(i);													//aqui apenas salva esses caracteres na nomVar					
				}

				Variavel var = escopo.buscaVariavel(nomVar); // passa como parâmetro a string com o nome da variavel para outro método
				if (var != null) {
					switch (var.getTipo()) {
						case 'I': 
							System.out.print(((Inteiro)var).getValor());
							break;
						case 'R':
							System.out.print(((Real)var).getValor());
							break;
						case 'C': 
							System.out.print(((Caractere)var).getValor());
							break;
					}
				} else {
					// não encontrou variável com esse nome, verifica se é um número, tentando converter para double
					try {
						System.out.print(Double.parseDouble(nomVar));
					} catch (NumberFormatException e) {
						System.out.print("\nVariável " + nomVar + " não declarada");
					}
				}
			} else if (Verificador.ehEspacoEmBranco(linha.charAt(i))) {
				i++;
			}
		}
	}
	
	public void imprimeLinha(Escopo escopo, String linha){ // Método igual ao imprime, mas que imprime e da um /n no final
		this.imprime(escopo, linha);						 // chama o outro método, pois faz a mesma coisa
		System.out.print("\n");							 // da o \n
	}
}
	
	
	//chegando aqui, espero que ele tenha impresso TUDO o que deveria ser impresso. Não haverá caractere especial fora das aspas né? como &, %, @ e etc etc?
	
	
