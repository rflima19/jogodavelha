package lima.jogodavelha.controller;

import java.io.IOException;

import lima.jogodavelha.exceptions.JogoDaVelhaExceptions;
import lima.jogodavelha.view.TerminalCadastroJogador;
import lima.jogodavelha.view.TerminalMensagemView;
import lima.jogodavelha.view.TerminalRankingJogadoresView;

public class JogoControlller {
	
	private TerminalMensagemView mensagem;
		
	public JogoControlller() {
		super();
		this.mensagem = new TerminalMensagemView();
	}

	public void switchOpcao(int opcao) {
		switch (opcao) {
		case 1 -> {
			try {
				new TerminalCadastroJogador().cadastrarJogador();
			} catch (IOException e) {
				this.mensagem.imprimirErro("Erro de I/O ao cadastar jogador", e);
			}
		}
		case 2 -> System.out.println("2");
		case 3 -> {
			new TerminalRankingJogadoresView().imprimirRancking();
		}
		case 4 -> System.exit(0);
		default -> {
			this.mensagem.imprimirErro("Opção inválida");
		}
		}
	}

}
