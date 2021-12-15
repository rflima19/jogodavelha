package lima.jogodavelha.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lima.jogodavelha.controller.JogadorController;
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
	private TerminalMensagemView mensagem;
	private FacadeOfSystem facade;

	public TerminalCadastroJogador() {
		super();
		this.terminal = new TerminalUtils();
		this.mensagem = new TerminalMensagemView();
		this.facade = FacadeConcrete.getFacade();
	}

	public void cadastrarJogador() throws IOException {
		//List<InputUsuario> inputs = new ArrayList<>();
		//inputs.add(new InputUsuarioString("Digite o nome do jogador", new ValidadorNome()));
		// Object[] objs = this.terminal.formulario(" CADASTRAR JOGADOR ", inputs);
		String nome = null;
		this.terminal.imprimirCabecalho(" CADASTRAR JOGADOR ");
		System.out.println("Digite o nome do jogador");
		System.out.print(">>");
		nome = Console.readString();
		boolean result = this.facade.cadastrarJogador(nome);
		if (result == true) {
			// this.terminal.imprimirMensagem("Jogador cadastrado com sucesso!");
			this.mensagem.imprimir("Jogador cadastrado com sucesso!");
		}
		System.out.println();
	}
}
