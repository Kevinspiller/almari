class Vetor
{
	protected String nome;
	protected int tamanho;

	public Vetor(String nome, int tamanho)
	{
		this.nome = nome;
		this.tamanho = tamanho;
	}

	/**
	 *	Compara se uma String é na verdade um vetor, em outras palavras:
	 *	Compara se a string termina com "[inteiro]"
	 *
	 *	@return boolean
	 */
	public static boolean isVetor(String str) throws VetorIndexException
	{
		int startBracket = str.indexOf("["); // Procura por "["
		int endBracket   = str.indexOf("]"); // Procura por "]"

		// Retorna false caso não encontre algum dos Brackets
		if (startBracket == -1 || endBracket == -1) return false;

		// Pega a string entre os brackets: []
		String indexAnalysis = str.substring(startBracket + 1, endBracket);

		// Verifica se o número que está entre os Brackets é um inteiro
		try {
			Integer.parseInt(indexAnalysis);
		} catch (NumberFormatException e) {
			throw new VetorIndexException("\"" + indexAnalysis + "\" não é um índice válido para o vetor.");
		}

		return true;
	}

	public static int vetorIndexes(String str, Escopo escopo) throws VetorIndexException
	{
		int startBracket = str.indexOf("["); // Procura por "["
		int endBracket   = str.indexOf("]"); // Procura por "]"

		// Retorna false caso não encontre algum dos Brackets
		if (startBracket == -1 || endBracket == -1)
			throw new VetorIndexException("Não foi reonhecido brackets do vetor: " + str);

		// Pega a string entre os brackets: []
		String indexAnalysis = str.substring(startBracket + 1, endBracket);

		// Verifica se o que está entre os Brackets é uma variável
		Variavel var = escopo.buscaVariavel(indexAnalysis);
		if (var != null) {
			return ((Inteiro)var).getValor();
		}
		// Verifica se o número que está entre os Brackets é um valor inteiro
		try {
			return Integer.parseInt(indexAnalysis);
		} catch (NumberFormatException e) {
			throw new VetorIndexException("\"" + indexAnalysis + "\" nao e um indice valido para o vetor.");
		}
	}

	public String getNome()
	{
		return this.nome;
	}

	public int getTamanho()
	{
		return this.tamanho;
	}
}