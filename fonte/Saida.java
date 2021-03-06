/*
* Classe com implementação dos métodos de saída/impressão
* Autor: Ricardo Augusto Müller
* E-mail: ricardoam0908@gmail.com
*/
class Saida {

	public static void imprime(Escopo escopo, String linha){
		int i = 0, j;
		boolean entreAspa = false;
		while (i < linha.length()) {
			if (linha.charAt(i) == '"') { //Testa se aquela parte da string contém aspas 
				entreAspa = !entreAspa;
				for (i++; i < linha.length() && entreAspa && linha.charAt(i) != '"'; i++) { 		//vai imprimindo tudo que esta entre as aspas, até achar uma outra aspa
					System.out.print(linha.charAt(i));
				}
			} else if (Verificacao.charValidoNome(linha.charAt(i))) {   //testa se o caractere é uma letra, que caracteriza o inicio do nome da variavel 
				String nomVar = "";
				j = 0;

				while (i < linha.length() && Verificacao.charValidoNome(linha.charAt(i))) {                         //aqui ele salva todos os caracteres seguintes, até o espaço, que seria o nome da variavel																							nomVar += linha.charAt(i);													//aqui apenas salva esses caracteres na nomVar					
					nomVar += linha.charAt(i);
					i++;
				}
				Vetor vector;
				Variavel var;
				if ((var = escopo.buscaVariavel(nomVar)) != null) {
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
				} else if ((vector = escopo.buscaVetor(nomVar)) != null) {
					int indice;
					try {
						indice = Vetor.vetorIndexes(nomVar + linha.substring(i, linha.indexOf("]", i) + 1), escopo);
						i += linha.indexOf("]", i) + 1;
					} catch (VetorIndexException e) {
						throw new RuntimeException(e.getMessage());
					}
					if (vector instanceof VetorInteiro) {
						System.out.print(((VetorInteiro)vector).getValor(indice));
					} else if (vector instanceof VetorReal) {
						System.out.print(((VetorReal)vector).getValor(indice));
					} else if (vector instanceof VetorCaractere) {
						System.out.print(((VetorCaractere)vector).getValor(indice));
					}
				} else {
					// não encontrou variável com esse nome, verifica se é um número, tentando converter para double
					try {
						System.out.print(Double.parseDouble(nomVar));
					} catch (NumberFormatException e) {
						System.out.print("\nVariavel " + nomVar + " nao declarada");
					}
				}
			} else /* if (Character.isWhitespace(linha.charAt(i))) */ {
				i++;
			}
		}
	}
	
	public static void imprimeLinha(Escopo escopo, String linha){
		imprime(escopo, linha);
		System.out.print("\n");
	}
}