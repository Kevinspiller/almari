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