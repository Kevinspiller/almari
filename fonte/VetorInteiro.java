class VetorInteiro extends Vetor
{
	private int[] posicoes;

	public VetorInteiro(String nome, int tamanho)
	{
		super(nome, tamanho);
		posicoes = new int[tamanho];
	}
	
	public int getValor(int indice)
	{
		return this.posicoes[indice];
	}

	public void setValor(int indice, int valor)
	{
		this.posicoes[indice] = valor;
	}
}