class VetorReal extends Vetor
{
	private double[] posicoes;

	public VetorReal(String nome, int tamanho)
	{
		super(nome, tamanho);
		posicoes = new double[tamanho];
	}
	public double getValor(int indice)
	{
		return this.posicoes[indice];
	}

	public void setValor(int indice, double valor)
	{
		this.posicoes[indice] = valor;
	}
}