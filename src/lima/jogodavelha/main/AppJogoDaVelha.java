package lima.jogodavelha.main;

import lima.jogodavelha.view.JogoDaVelhaJavaFXView;
//import lima.jogodavelha.view.TerminalMenuJogoView;

public class AppJogoDaVelha {

	public static void main(String[] args) {
//		TerminalMenuJogoView ti = new TerminalMenuJogoView();
//		ti.imprimirMenu();
		
		JogoDaVelhaJavaFXView jogo = new JogoDaVelhaJavaFXView();
		jogo.iniciar(args);
	}
}
