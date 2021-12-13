package lima.jogodavelha.view;

import java.io.IOException;

import lima.jogodavelha.exceptions.JogoDaVelhaExceptions;
import lima.jogodavelha.facade.FacadeConcrete;
import lima.jogodavelha.facade.FacadeOfSystem;
import lima.jogodavelha.utils.TerminalUtils;

public class TerminalJogoView {

	private TerminalUtils terminal;
	private TerminalMensagemView mensagem;
	private FacadeOfSystem facade;

	public TerminalJogoView() {
		super();
		this.terminal = new TerminalUtils();
		this.mensagem = new TerminalMensagemView();
		this.facade = FacadeConcrete.getFacade();
	}

	public void iniciarJogo() {
		this.terminal.imprimirCabecalho(" JOGO DA VELHA ");
		System.out.println();
		String[] opcoes = new String[] {"CADASTRAR JOGADOR", "JOGAR", "RANKING DE JOGADORES", "SAIR"};
		Integer opcao = null;
		while (true) {
			try {
				opcao = this.terminal.menuOpcoes(" MENU ", opcoes);
				this.facade.switchOpcao(opcao);
			} catch (IOException e) {
//				this.terminal.imprimirMensagemErro("Erro de I/O");
//				e.printStackTrace();
//				System.out.println();
				this.mensagem.imprimirErro("Erro de I/O", e);
			} catch (NumberFormatException e) {
//				this.terminal.imprimirMensagemErro("Digite um inteiro válido");
//				System.out.println();
				this.mensagem.imprimirErro("Digite um inteiro válido");
			}
			System.out.println();
		}
	}
}
