package lima.jogodavelha.model;

public class Jogada {

	private int numColuna;
	private int numLinha;
	private Simbolo simbolo;

	public Jogada(int numColuna, int numLinha, Simbolo simbolo) {
		super();
		this.numColuna = numColuna;
		this.numLinha = numLinha;
		this.simbolo = simbolo;
	}

	public int getNumColuna() {
		return numColuna;
	}

	public int getNumLinha() {
		return numLinha;
	}

	public Simbolo getSimbolo() {
		return simbolo;
	}

}
