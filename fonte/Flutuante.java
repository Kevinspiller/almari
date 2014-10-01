class Flutuante extends Variavel {
	private double valor;
	
	public void setValor (double v) {
		this.valor = v;
	}
	
	public double getValor () {
		return this.valor;
	}
	
	public Flutuante (String n) {
		this.setTipo ('F');
		this.setNome(n);
	}

}