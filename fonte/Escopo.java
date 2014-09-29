import java.util.*;

class Escopo {
	private String comandos, novobloco;
	private ArrayList<Variavel> vars;
	private int i, j, h, l, blocos;
	
	private boolean fimComando(char c) {
		return (c != ';' || c != '{');
	}
	
	private Variavel declaraVariavel(String tipo, String nome, String valor) throws IllegalArgumentException {
		Variavel var = null;
		if (! existeVariavel(nome)) {
			if (tipo.equals("inteiro")) {
				var = new Inteiro(nome, valor.equals("") ? 0 : Integer.parseInt(valor));
			} else if (tipo.equals("real")) {
				var = new Real(nome, valor.equals("") ? 0.0 : Double.parseDouble(valor));
			} else if (tipo.equals("caractere")) {
				var = new Caractere(nome, valor);
			} else {
				throw new IllegalArgumentException("Tipo inválido: " + tipo);
			}
		}
		return var;
	}
	
	public Variavel buscaVariavel(String nome) {
		Variavel var;
		for (int i = 0; i < this.vars.size(); i++) {
			if ((var = this.vars.get(i)).getNome().equals(nome)) {
				return var;
			}
		}
		return null;
	}
	
	public boolean existeVariavel(String nome) {
		// verifica se já existe uma variável no escopo com o nome informado
		for (int i = 0; i < this.vars.size(); i++) {
			if (this.vars.get(i).getNome().equals(nome)) {
				return true;
			}
		}
		return false;
	}
	
	public Escopo(String comandos, ArrayList<Variavel> variaveis) {
		this.comandos = comandos;
		
		this.vars = new ArrayList<Variavel>(); // Funciona como um array, mas tem método add() para adicionar itens
		if (variaveis != null) {
			for (int i = 0; i < variaveis.size(); i++) {
				this.vars.add(variaveis.get(i));
			}
		}
	}
	
	public void processa() throws IllegalArgumentException {
		String buffer = "";
		String instruct = "";
	
		for (i = 0; i < this.comandos.length(); i++) {
			j = 0;
			buffer = buffer + comandos.charAt(i + j);
			while (!fimComando(buffer.charAt(j)))  { //aqui alimentamos o buffer até achar um fim de comando
				j++;
				buffer = buffer + comandos.charAt(i + j);
			}
			System.out.println("buffer = " + buffer);
			//////////////////////////////////////////
			//////////////////////////////////////////
			String[] tokens = buffer.split(" +|\t+|\n+|\r+");

			// tratamento da declaração de variáveis
			// funções são tratadas na classe Interpretador pois ficam declaradas fora do escopo
			if (tokens[0].equals("var")) {
				if (tokens.length >= 2 && Verificacao.tipoValido(tokens[1])) {
					if (tokens.length >= 3 && Verificacao.nomeValido(tokens[2])) {
						if (tokens.length >= 4 && tokens[3].equals(":=")) {
							// declaração com atribuição, verifica se o valor que está atribuindo corresponde ao tipo declarado
							if (tokens.length >= 5 && Verificacao.valorValido(tokens[1], tokens[4])) {
								// declara passando o tipo, nome e valor
								declaraVariavel(tokens[1], tokens[2], tokens[4]);
							} else {
								throw new IllegalArgumentException("Esperado valor válido para a variável " + tokens[2] + " do tipo " + tokens[1]);
							}
						} else {
							declaraVariavel(tokens[0], tokens[2], "");
							if (tokens.length >= 5) {
								if (tokens[4].equals(",")) {
									// declaração de múltiplas variáveis separadas por vírgula
								} else if (! tokens[4].equals(";")) {
									throw new IllegalArgumentException("Símbolo " + tokens[4] + " inválido. Esperado , ou ;");
								}
							}
						}
					} else {
						throw new IllegalArgumentException("Esperado nome de variavel apos tipo");
					}
				} else {
					throw new IllegalArgumentException("Esperado tipo de variável apos var");
				}
			} else if (tokens[0].equals("imprima_linha")) {
				System.out.println("aqui");
				Saida.imprimeLinha(this, buffer.substring(buffer.indexOf("imprima_linha") + 13, buffer.length()));
			}
			
			/* else if (tokens[0].equals("func")) {
				if (Verificacao.tipoValido(tokens[1])) {
					if (Verificacao.nomeValido(tokens[2])) {
						// declara a função
					} else {
						throw new IllegalArgumentException("Nome de função inválido: " + tokens[2]);
					}
				} else {
					throw new IllegalArgumentException("Tipo de função inválido: " + tokens[j + 1]);
				}
			}
			*/
			/////////////////////////////////////
			/////////////////////////////////////


		
			if (buffer.charAt(j) == '{') { //se o fim de comando foi o "{", então são quatro possibilidades, while, if, função ou erro
				for (l = 0; !Character.isWhitespace(buffer.charAt(l)); l++); //aqui eliminamos os primeiros espaços, TAB ou \n (sintaxe flexivel)
				h = 0;
				while (buffer.charAt(l) != ' ' && buffer.charAt(l) != '{' && buffer.charAt(l) != '(') { //por ser sintaxe flexivel, os parenteses podem estar colados no comando
					instruct = instruct + buffer.charAt(l); //aqui alimentamos a instrução (while, função, if ou erro)
					h++;
					l++;				
				}
				
				if (instruct.equals("while")) { //se for um while, vamos descobrir a condição e armazenar numa string
					while (buffer.charAt(l) != '(') { //se o ponteiro não tiver no parenteses, vamos deixar lá
						if (buffer.charAt(l) == '{') { //se após a instrução tem uma chave ao inves de um parentes, erro, tem q ter parenteses
							//ABORTAR PROGRAMA
							throw new IllegalArgumentException("Esperado perêntese direito após expressão");
						}
						l++;
					}
					blocos = 1;
					novobloco  = new String();
					while (blocos > 0) {
						//for (h = 0; 
						
						
					}
				
					
				}
				
			}
		
		
		}		
	}
}