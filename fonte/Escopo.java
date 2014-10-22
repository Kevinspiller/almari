/*
* Classe para tratar o processamento dos comandos dentro de um escopo
* Autor: Maico Smaniotto
* E-mail: maicosmaniotto@gmail.com
*/

import java.util.*;

class Escopo {
	private String comandos, novobloco;
	private ArrayList<Variavel> vars;
	private int i, j, h, l, blocos;
	
	private boolean fimComando(char c) {
		return (c == ';' || c == '{' || c == '}');
	}
	
	private boolean declaraVariavel(String tipo, String nome, String valor) {
		Variavel var = null;
		if (! existeVariavel(nome)) {
			if (tipo.equals("int")) {
				var = new Inteiro(nome, valor.equals("") ? 0 : Integer.parseInt(valor));
			} else if (tipo.equals("real")) {
				var = new Real(nome, valor.equals("") ? 0.0 : Double.parseDouble(valor));
			} else if (tipo.equals("caractere")) {
				var = new Caractere(nome, valor);
			}
		}
		if (var != null) {
			vars.add(var);
			return true;
		}
		return false;
	}
	
	public Variavel buscaVariavel(String nome) {
		Variavel var;
		for (int i = 0; i < this.vars.size(); i++) {
			var = this.vars.get(i);
			if (var.getNome().equals(nome)) {
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
	
	public void processa() throws Exception {
		String buffer = "";
		String instruct = "";
		
		System.out.println("processando");	
		
		for (i = 0; i < this.comandos.length(); i++) {
			j = 0;
			buffer = "";
			//buffer = buffer + comandos.charAt(i + j);
			while (!fimComando(comandos.charAt(i + j)))  { //aqui alimentamos o buffer até achar um fim de comando
				buffer = buffer + comandos.charAt(i + j);
				j++;
			}
			i += j;
			
			String[] tokens = buffer.trim().split(" +|\t+|\n+|\r+");
			
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
								throw new IllegalArgumentException("Esperado valor valido para a variavel " + tokens[2] + " do tipo " + tokens[1]);
							}
						} else {
							declaraVariavel(tokens[1], tokens[2], "");
							if (tokens.length >= 5) {
								if (! tokens[4].equals(";")) {
									throw new IllegalArgumentException("Simbolo " + tokens[4] + " invalido. Esperado ;");
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
				Saida.imprimeLinha(this, buffer.substring(buffer.indexOf("imprima_linha") + 13, buffer.length()));
			} else if (tokens[0].equals("imprima")) {
				Saida.imprime(this, buffer.substring(buffer.indexOf("imprima") + 7, buffer.length()));
			} else if (tokens[0].equals("leia_inteiro")) {
				int valorInt = Entrada.leInteiro();
				if (tokens.length > 1) {
					Variavel var = this.buscaVariavel(tokens[1]);
					if (var != null) {
						((Inteiro)var).setValor(valorInt);
					} else {
						throw new IllegalArgumentException("Variavel " + tokens[1] + " nao declarada");
					}
				}
			} else if (tokens[0].equals("leia_real")) {
				double valorReal = Entrada.leReal();
				if (tokens.length > 1) {
					Variavel var = this.buscaVariavel(tokens[1]);
					if (var != null) {
						((Real)var).setValor(valorReal);
					} else {
						throw new IllegalArgumentException("Variavel " + tokens[1] + " nao declarada");
					}
				}
			} else if (tokens[0].equals("leia_palavra") || tokens[0].equals("leia_linha")) {
				String linha;
				if (tokens[0].equals("leia_palavra")) {
					linha = Entrada.lePalavra();
				} else {
					linha = Entrada.leLinha();
				}
				if (tokens.length > 1) {
					Variavel var = this.buscaVariavel(tokens[1]);
					if (var != null) {
						((Caractere)var).setValor(linha);
					} else {
						throw new IllegalArgumentException("Variavel " + tokens[1] + " nao declarada");
					}
				}
			} else if (tokens[0].trim().equals("se")) {
				Escopo escopoSe = null, escopoSeNao = null;
				String expr, blocoSe = null, blocoSeNao = null;
				
				expr = buffer.substring(buffer.indexOf("se") + 2, buffer.length() - 1);
				
				// carrega todo o bloco do "se", começando da posição "i" que parou antes da chave "{"
				blocoSe = carregaBloco(comandos, i);
							
				// avança o "i" até o final do bloco do "se" para que o processamento continue no comando seguinte
				i += blocoSe.length();
				
				while (Character.isWhitespace(comandos.charAt(i))) {
					i++;
				}
				if (i + 5 < comandos.length() && comandos.substring(i, i + 5).equals("senao")) {
					// carrega todo o bloco do "senao", começando da posição "i" que parou depois da chave "}"
					blocoSeNao = carregaBloco(comandos, i);
				
					// avança o "i" até o final do bloco do "senao" para que o processamento continue no comando seguinte
					i += blocoSeNao.length();
				}
				
				// testa se a condição é verdadeira
				if (Condicao.avaliaCondicao(this, expr)) {
					escopoSe = new Escopo(blocoSe, this.vars);
					escopoSe.processa();
				} else if (blocoSeNao != null) { // se a condição for falsa, executa o "senao", caso existir
					escopoSeNao = new Escopo(blocoSeNao, this.vars);
					escopoSeNao.processa();
				}
			} else {
				Expressao.resolveExpressao(this, buffer);
			}		
		}		
	}
	
	public String carregaBloco(String comandos, int inicio) throws Exception {
		int posicao, chaveInicio, chaveFim;
		posicao = inicio;
		while (Character.isWhitespace(comandos.charAt(posicao))) {
			posicao++;
		}
		if (comandos.charAt(posicao) == '{') {
			inicio = ++posicao;
			chaveInicio = 1;
			chaveFim = 0;
			while (posicao < comandos.length() && chaveInicio > chaveFim) {
				if (comandos.charAt(posicao) == '{') {
					chaveInicio++;
				} else if (comandos.charAt(posicao) == '}') {
					chaveFim++;
				}
				posicao++;
			}
			if (chaveInicio == chaveFim) {
				return comandos.substring(inicio, posicao - 1);
			} else {
				throw new IllegalArgumentException("Esperado caractere de fim de bloco \"}\"");
			}
		} else {
			throw new IllegalArgumentException("Esparedo caractere de inicio de bloco \"{\"");
		}				
	}	
}