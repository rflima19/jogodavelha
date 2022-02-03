package lima.jogodavelha.view;

import java.io.IOException;

import lima.jogodavelha.utils.TerminalUtils;

public class TerminalMenuJogoView {

	private TerminalUtils terminal;

	public TerminalMenuJogoView() {
		super();
		this.terminal = new TerminalUtils();
	}

	public void imprimirMenu() {
		this.terminal.imprimirCabecalho(" JOGO DA VELHA ");
		System.out.println();
		String[] opcoes = new String[] {"CADASTRAR JOGADOR", "JOGAR", "RANKING DE JOGADORES", "SAIR"};
		Integer opcao = null;
		while (true) {
			try {
				opcao = this.terminal.menuOpcoes(" MENU ", opcoes);
				
				switch (opcao) {
					case 1 -> {
						new TerminalCadastroJogadorView().cadastrarJogador();
					}
					case 2 -> {
						new TerminalJogoView().iniciarJogo();
					}
					case 3 -> {
						new TerminalRankingJogadoresView().imprimirRancking();
					}
					case 4 -> System.exit(0);
					default -> {
						this.terminal.imprimirMensagemErro("Opção " + opcao + " não existe no menu");
					}
				}
			} catch (IOException e) {
				this.terminal.imprimirMensagemErro("Erro de I/O");
				e.printStackTrace();
				System.exit(1);
			}
			System.out.println();
		}
	}
}
