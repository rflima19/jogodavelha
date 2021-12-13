package lima.jogodavelha.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lima.jogodavelha.controller.JogadorController;
import lima.jogodavelha.exceptions.JogoDaVelhaExceptions;
import lima.jogodavelha.facade.FacadeConcrete;
import lima.jogodavelha.facade.FacadeOfSystem;
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
		String nome = null;
		List<InputUsuario> inputs = new ArrayList<>();
		inputs.add(new InputUsuarioString("Digite o nome do jogador", new ValidadorNome()));
		Object[] objs = this.terminal.formulario(" CADASTRAR JOGADOR ", inputs);
		boolean result = this.facade.cadastrarJogador(objs);
		if (result == true) {
			// this.terminal.imprimirMensagem("Jogador cadastrado com sucesso!");
			this.mensagem.imprimir("Jogador cadastrado com sucesso!");
		}
		System.out.println();
	}
}
