/*
* Classe que define o tipo de variável "caractere"
* Autor: Alexandre Bernard Coletti
* E-mail: eu_axil@yahoo.com.br
*/

class Caractere extends Variavel {
	private String valor;
	
	public void setValor(String v) {
		this.valor = v;
	}
	
	public String getValor() {
		return this.valor;
	}
	
	public Caractere(String nome) {
		this.setTipo('C');
		this.setNome(nome);
	}
	
	public Caractere(String nome, String valor) {
		this(nome);
		this.valor = valor;
	}	
}