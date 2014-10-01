class Variavel {
	private char tipo;
	private String nome;
		
	//todos os métodos são protected, pois os únicos que podem setar os tipos e nomes são as filhas, quando forem instanciadas.
	//Uma variavel não muda de tipo ou de nome durante a execução do programa (senão ela é só outra variavel).
	//Assim, esses dois atributos (nome e tipo) são definos no momento da declaração da variavel (método construtor das filhas) e não mudam mais.
	protected void setTipo(char t) {
		this.tipo = t;
	}
	public char getTipo () {
		return this.tipo;
	}
	protected void setNome(String n) {
		this.nome = new String(n);
	}
	public String getNome () {
		return this.nome;
	}
}