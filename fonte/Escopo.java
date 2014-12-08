/*
* Classe para tratar o processamento dos comandos dentro de um escopo
* Autor: Maico Smaniotto
* E-mail: maicosmaniotto@gmail.com
*/

import java.util.*;
import java.util.ArrayList;

class Escopo {
	private String comandos;
	private ArrayList<Variavel> vars;
	private	ArrayList<Vetor> vectors;

	private void declaraVariavel(String tipo, String nome, String valor) {
		Variavel var = null;
		if (!(existeVariavel(nome) || existeVetor(nome))) {
			if (tipo.equals("inteiro")) {
				var = new Inteiro(nome, valor.equals("") ? 0 : Integer.parseInt(valor));
			} else if (tipo.equals("real")) {
				var = new Real(nome, valor.equals("") ? 0.0 : Double.parseDouble(valor));
			} else if (tipo.equals("caractere")) {
				var = new Caractere(nome, valor != null && valor.length() > 1 ? valor.substring(1, valor.length() - 1) : "");
			}
		}
		if (var != null) {
			vars.add(var);
		}
	}

	private void declaraVetor(String tipo, String nome, int tamanho) {
		Vetor vector = null;
		if (!(existeVariavel(nome) || existeVetor(nome))) {
			if (tipo.equals("inteiro")) {
				vector = new VetorInteiro(nome, tamanho);
			} else if (tipo.equals("real")) {
				vector = new VetorReal(nome, tamanho);
			} else if (tipo.equals("caractere")) {
				vector = new VetorCaractere(nome, tamanho);
			}
		}
		if (vector != null) {
			vectors.add(vector);
		}
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

	public Vetor buscaVetor(String nome) {
		Vetor vector;
		
		// Testa se o nome passado tem o índice ainda, e então remove deixando apenas o nome (ex. vetor[1] fica vetor)
		int bracket = nome.indexOf("[");
		if (bracket != -1) {
			nome = nome.substring(0, bracket);
		}
		
		for (int i = 0; i < this.vectors.size(); i++) {
			vector = this.vectors.get(i);
			if (vector.getNome().equals(nome)) {
				return vector;
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
	
	public boolean existeVetor(String nome) {
		// verifica se já existe uma variável no escopo com o nome informado
		for (int i = 0; i < this.vectors.size(); i++) {
			if (this.vectors.get(i).getNome().equals(nome)) {
				return true;
			}
		}
		return false;
	}	
	
	public static String carregaBloco(String comandos, int inicio) throws Exception {
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
	
	public Escopo(String comandos, ArrayList<Variavel> variaveis, ArrayList<Vetor> vetores) {
		this.comandos = comandos;
		
		this.vars = new ArrayList<Variavel>(); // Funciona como um array, mas tem método add() para adicionar itens
		if (variaveis != null) {
			for (int i = 0; i < variaveis.size(); i++) {
				this.vars.add(variaveis.get(i));
			}
		}
		this.vectors = new ArrayList<Vetor>();
		if (vetores != null) {
			for (int i = 0; i < vetores.size(); i++) {
				this.vectors.add(vetores.get(i));
			}
		}
	}
	
	public void processa() throws Exception {
		String buffer = "";
		int i;
		boolean foundVetor = false;

		for (i = 0; i < this.comandos.length(); i++) {
			buffer = "";
			// Alimenta o buffer até achar um fim de comando
			while (i < this.comandos.length() && !Verificacao.fimComando(comandos.charAt(i)))  {
				buffer = buffer + comandos.charAt(i);
				i++;
			}

			ArrayList<String> tokens = Expressao.separaTokens(buffer);
			if (tokens == null || tokens.size() == 0) {
				break;
			}
			// tratamento da declaração de variáveis
			// funções são tratadas na classe Interpretador pois ficam declaradas fora do escopo
			if (tokens.get(0).equals("var")) {
				if (tokens.size() >= 2 && Verificacao.tipoValido(tokens.get(1))) {
					if (tokens.size() >= 3) {
						try {
							foundVetor = Vetor.isVetor(tokens.get(2));
						} catch (VetorIndexException e) {
							throw new RuntimeException(e.getMessage());
						}
						if (foundVetor) {
							String nome = tokens.get(2).substring(0, tokens.get(2).indexOf("["));
							if (Verificacao.nomeValido(nome)) {
								int tamanho;
								try {
									tamanho = Vetor.vetorIndexes(tokens.get(2), this);
								} catch (VetorIndexException e) {
									throw new RuntimeException(e.getMessage());
								}

								declaraVetor(tokens.get(1), nome, tamanho);
							} else {
								throw new IllegalArgumentException("Nome invalido para a variavel / vetor");							
							}
						} else {
							// Se não era um vetor, pode ter uma atribuição para a variável
							if (Verificacao.nomeValido(tokens.get(2))) {
								if (tokens.size() >= 4 && tokens.get(3).equals(":=")) {
									// declaração com atribuição, verifica se o valor que está atribuindo corresponde ao tipo declarado
									if (tokens.size() >= 5 && Verificacao.valorValido(tokens.get(1), tokens.get(4))) {
										// declara passando o tipo, nome e valor
										declaraVariavel(tokens.get(1), tokens.get(2), tokens.get(4));
									} else {
										throw new IllegalArgumentException("Esperado valor valido para a variavel " + tokens.get(2) + " do tipo " + tokens.get(1));
									}
								} else {
									declaraVariavel(tokens.get(1), tokens.get(2), "");
									if (tokens.size() >= 5) {
										if (! tokens.get(4).equals(";")) {
											throw new IllegalArgumentException("Simbolo " + tokens.get(4) + " invalido. Esperado ;");
										}
									}
								}
							} else {
								throw new IllegalArgumentException("Nome invalido para a variavel / vetor");							
							}
						}
					} else {
						throw new IllegalArgumentException("Esperado nome de variavel apos tipo");
					}
				} else {
					throw new IllegalArgumentException("Esperado tipo de variável apos var");
				}
			} else if (tokens.get(0).equals("imprima_linha")) {
				Saida.imprimeLinha(this, buffer.substring(buffer.indexOf("imprima_linha") + 13, buffer.length()));
			} else if (tokens.get(0).equals("imprima")) {
				Saida.imprime(this, buffer.substring(buffer.indexOf("imprima") + 7, buffer.length()));
			} else if (tokens.get(0).equals("leia_inteiro")) {
				int valorInt = Entrada.leInteiro();
				if (tokens.size() > 1) {
					Variavel var = this.buscaVariavel(tokens.get(1));
					if (var != null) {
						((Inteiro)var).setValor(valorInt);
					} else {
						throw new IllegalArgumentException("Variavel " + tokens.get(1) + " nao declarada");
					}
				}
			} else if (tokens.get(0).equals("leia_real")) {
				double valorReal = Entrada.leReal();
				if (tokens.size() > 1) {
					Variavel var = this.buscaVariavel(tokens.get(1));
					if (var != null) {
						((Real)var).setValor(valorReal);
					} else {
						throw new IllegalArgumentException("Variavel " + tokens.get(1) + " nao declarada");
					}
				}
			} else if (tokens.get(0).equals("leia_palavra") || tokens.get(0).equals("leia_linha")) {
				String linha;
				if (tokens.get(0).equals("leia_palavra")) {
					linha = Entrada.lePalavra();
				} else {
					linha = Entrada.leLinha();
				}
				if (tokens.size() > 1) {
					Variavel var = this.buscaVariavel(tokens.get(1));
					if (var != null) {
						((Caractere)var).setValor(linha);
					} else {
						throw new IllegalArgumentException("Variavel " + tokens.get(1) + " nao declarada");
					}
				}
			} else if (tokens.get(0).trim().equals("se")) {
				Escopo escopoSe = null, escopoSeNao = null;
				String expr, blocoSe = null, blocoSeNao = null;
				
				expr = buffer.substring(buffer.indexOf("se") + 2, buffer.length() - 1);
				
				// carrega todo o bloco do "se", começando da posição "i" que parou antes da chave "{"
				blocoSe = carregaBloco(comandos, i);
				
				// avança o "i" até o final do bloco do "se" para que o processamento continue no comando seguinte
				i += blocoSe.length() + 2;
				
				while (i < comandos.length() && Character.isWhitespace(comandos.charAt(i))) {
					i++;
				}

				if (i + 5 < comandos.length() && comandos.substring(i, i + 5).equals("senao")) {
					i += 5;

					// carrega todo o bloco do "senao", começando da posição "i" que parou depois da chave "}"
					blocoSeNao = carregaBloco(comandos, i);
				
					// avança o "i" até o final do bloco do "senao" para que o processamento continue no comando seguinte
					i += blocoSeNao.length() + 2;
				} else {
					i--;
				}
				// testa se a condição é verdadeira
				if (Condicao.avaliaCondicao(this, expr)) {
					escopoSe = new Escopo(blocoSe, this.vars, this.vectors);
					escopoSe.processa();
				} else if (blocoSeNao != null) { // se a condição for falsa, executa o "senao", caso existir
					escopoSeNao = new Escopo(blocoSeNao, this.vars, this.vectors);
					escopoSeNao.processa();
				}
			} else if (tokens.get(0).trim().equals("enquanto")) {
				Escopo escopoEnquanto = null;
				String expr, blocoEnquanto = null;
				
				expr = buffer.substring(buffer.indexOf("enquanto") + 8, buffer.length() - 1);
				
				// carrega todo o bloco do "enquanto", começando da posição "i" que parou antes da chave "{"
				blocoEnquanto = carregaBloco(comandos, i);
							
				// avança o "i" até o final do bloco do "enquanto" para que o processamento continue no comando seguinte
				i += blocoEnquanto.length() + 2;
				
				// repete enquanto a condição é verdadeira
				while (Condicao.avaliaCondicao(this, expr)) {
					escopoEnquanto = new Escopo(blocoEnquanto, this.vars, this.vectors);
					escopoEnquanto.processa();
				}
			} else {
				// Expressões de atribuição ex: a := a + 1; ou com vetores ex: v[0] := 5;
				Expressao.resolveExpressao(this, buffer);
			}		
		}		
	}	
}