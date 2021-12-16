package lima.jogodavelha.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lima.jogodavelha.controller.JogadorController;
import lima.jogodavelha.exceptions.ControllerException;
import lima.jogodavelha.exceptions.JogoDaVelhaExceptions;
import lima.jogodavelha.facade.FacadeConcrete;
import lima.jogodavelha.facade.FacadeOfSystem;
import lima.jogodavelha.utils.Console;
import lima.jogodavelha.utils.InputUsuario;
import lima.jogodavelha.utils.InputUsuarioString;
import lima.jogodavelha.utils.TerminalUtils;
import lima.jogodavelha.utils.ValidadorNome;

public class TerminalCadastroJogador {

	private TerminalUtils terminal;
	private FacadeOfSystem facade;

	public TerminalCadastroJogador() {
		super();
		this.terminal = new TerminalUtils();
		this.facade = FacadeConcrete.getFacade();
	}

	public void cadastrarJogador() throws IOException {
		//List<InputUsuario> inputs = new ArrayList<>();
		//inputs.add(new InputUsuarioString("Digite o nome do jogador", new ValidadorNome()));
		// Object[] objs = this.terminal.formulario(" CADASTRAR JOGADOR ", inputs);
		String nome = null;
		this.terminal.imprimirCabecalho(" CADASTRAR JOGADOR ");
		System.out.println("Digite o nome do jogador(a)");
		System.out.print(">>");
		nome = Console.readString();
		try {
			this.facade.cadastrarJogador(nome);
			//if (result == true) {
				// this.terminal.imprimirMensagem("Jogador cadastrado com sucesso!");
			this.terminal.imprimirMensagem("Jogador(a) cadastrado com sucesso!");
			//}
		} catch (ControllerException e) {
			this.terminal.imprimirMensagemErro(e.getMessage());
		}
		System.out.println();
	}
}
