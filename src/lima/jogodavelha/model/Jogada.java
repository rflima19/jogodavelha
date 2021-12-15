package lima.jogodavelha.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Jogada {

	private int numLinha;
	private int numColuna;

	public Jogada(int numLinha, int numColuna) {
		super();
		this.numLinha = numLinha;
		this.numColuna = numColuna;
	}

	public int getNumColuna() {
		return numColuna;
	}

	public int getNumLinha() {
		return numLinha;
	}
	
	public static Jogada parseJogada(String jogada) {
		Pattern pattern = Pattern.compile("\\d,\\s?\\d");
		Matcher matcher = pattern.matcher(jogada);
		if (matcher.matches() == false) {
			return null;
		}
		String[] tokens = jogada.split(",\\s?");
		return new Jogada(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]));
	}
}
