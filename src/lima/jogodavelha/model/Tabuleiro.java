package lima.jogodavelha.model;

public class Tabuleiro {

	private char[][] matriz;

	public Tabuleiro() {
		super();
		this.matriz = new char[3][3];
		this.zerarTabuleiro();
	}
	
	public char[][] getMatriz() {
		return matriz;
	}
	
	private void zerarTabuleiro() {
		for (int i = 0; i < this.matriz.length; i++) {
			for (int j = 0; j < this.matriz[i].length; j++) {
				this.matriz[i][j] = ' ';
			}
		} 
	}
	
	public boolean inserirJogada(Jogada jogada, Simbolo simbolo) {
		int linha = jogada.getNumLinha() - 1;
		int coluna = jogada.getNumColuna() - 1;
		if ((linha < 0) || (linha > 2) || (coluna < 0) || (coluna > 2)) {
			return false;
		}
		if (this.matriz[linha][coluna] != ' ') {
			return false;
		}
		this.matriz[linha][coluna] = simbolo.getRepresentacao();
		return true;
	}
	
	public boolean isTabuleiroCompleto() {
		for (int i = 0; i < this.matriz.length; i++) {
			for (int j = 0; j < this.matriz[0].length; j++) {
				if (this.matriz[i][j] == ' ') {
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean isSequenciaEncontrada() {
		if(matriz[0][0] == matriz[0][1] && matriz[0][1] == matriz[0][2] && matriz[0][0] != ' ') {
			return true;
		}
		
		if(matriz[1][0] == matriz[1][1] && matriz[1][1] == matriz[1][2] && matriz[1][0] != ' ') {
			return true;
		}
		
		if(matriz[2][0] == matriz[2][1] && matriz[2][1] == matriz[2][2] && matriz[2][0] != ' ') {
			return true;
		}
		
		if(matriz[0][0] == matriz[1][0] && matriz[1][0] == matriz[2][0] && matriz[0][0] != ' ') {
			return true;
		}
		
		if(matriz[0][1] == matriz[1][1] && matriz[1][1] == matriz[2][1] && matriz[0][1] != ' ') {
			return true;
		}
	
		if(matriz[0][2] == matriz[1][2] && matriz[1][2] == matriz[2][2] && matriz[0][2] != ' ') {
			return true;
		}
		
		if(matriz[0][0] == matriz[1][1] && matriz[1][1] == matriz[2][2] && matriz[0][0] != ' ') {
			return true;
		}
		
		if(matriz[0][2] == matriz[1][1] && matriz[1][1] == matriz[2][0] && matriz[0][2] != ' ') {
			return true;
		}
	
		return false;
	}	
}
