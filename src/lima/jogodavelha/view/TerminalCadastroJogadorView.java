package lima.jogodavelha.view;

import java.io.IOException;

import lima.jogodavelha.exceptions.ControllerException;
import lima.jogodavelha.facade.FacadeConcrete;
import lima.jogodavelha.facade.FacadeOfSystem;
import lima.jogodavelha.utils.Console;
import lima.jogodavelha.utils.TerminalUtils;

public class TerminalCadastroJogadorView {

	private TerminalUtils terminal;
	private FacadeOfSystem facade;

	public TerminalCadastroJogadorView() {
		super();
		this.terminal = new TerminalUtils();
		this.facade = FacadeConcrete.getFacade();
	}

	public void cadastrarJogador() throws IOException {
		String nome = null;
		this.terminal.imprimirCabecalho(" CADASTRAR JOGADOR ");
		System.out.println("Digite o nome do jogador(a)");
		System.out.print(">>");
		nome = Console.readString();
		try {
			this.facade.cadastrarJogador(nome);
			this.terminal.imprimirMensagem("Jogador(a) cadastrado com sucesso!");
		} catch (ControllerException e) {
			this.terminal.imprimirMensagemErro(e.getMessage());
		}
		System.out.println();
	}
}
