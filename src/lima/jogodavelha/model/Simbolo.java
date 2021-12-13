package lima.jogodavelha.model;

public enum Simbolo {

	CIRCULO('O'), CHIS('X');
	
	private char representacao;
	
	private Simbolo(char representacao) {
		this.representacao = representacao;
	}
	
	public char getRepresentacao() {
		return this.representacao;
	}	
}
