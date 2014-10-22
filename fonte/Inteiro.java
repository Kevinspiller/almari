/*
* Classe que define o tipo de variável "inteiro"
* Autor: Alexandre Bernard Coletti
* E-mail: eu_axil@yahoo.com.br
*/

class Inteiro extends Variavel {
	private int valor;
	
	public void setValor(int v) {
		this.valor = v;
	}
	
	public int getValor() {
		return this.valor;
	}
	
	public Inteiro(String nome) {
		this.setTipo('I');
		this.setNome(nome);
	}
	
	public Inteiro(String nome, int valor) {
		this(nome);
		this.valor = valor;
	}
}