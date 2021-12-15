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
	
	public static Simbolo getSimbolo(char representacao) {
		if (Simbolo.CIRCULO.representacao == representacao) {
			return Simbolo.CIRCULO;
		} else if (Simbolo.CHIS.representacao == representacao) {
			return Simbolo.CHIS;
		} else {
			return null;
		}
	}
}
