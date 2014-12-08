class VetorCaractere extends Vetor
{
	private String[] posicoes;

	public VetorCaractere(String nome, int tamanho)
	{
		super(nome, tamanho);
		posicoes = new String[tamanho];
	}

	public String getValor(int indice)
	{
		return this.posicoes[indice];
	}

	public void setValor(int indice, String valor)
	{
		this.posicoes[indice] = valor;
	}
}