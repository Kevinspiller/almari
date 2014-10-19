/*
* Classe que define o tipo de variável "real"
* Autor: Alexandre Bernard Coletti
* E-mail: eu_axil@yahoo.com.br
*/

class Real extends Variavel {
	private double valor;
	
	public void setValor(double v) {
		this.valor = v;
	}
	
	public double getValor() {
		return this.valor;
	}
	
	public Real(String nome) {
		this.setTipo('R');
		this.setNome(nome);
	}
	
	public Real(String nome, double valor) {
		this(nome);
		this.valor = valor;
	}	
}