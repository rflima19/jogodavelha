package lima.jogodavelha.controller;

import java.util.Collections;
import java.util.List;

import lima.jogodavelha.exceptions.JogoDaVelhaExceptions;
import lima.jogodavelha.model.Jogador;
import lima.jogodavelha.view.TerminalMensagemView;

public class JogadorController {

	private TerminalMensagemView mensagem;

	public JogadorController() {
		super();
		this.mensagem = new TerminalMensagemView();
	}

	public boolean cadastrarJogador(Object[] objs) {
		if (objs == null) {
			this.mensagem.imprimirErro("Nome inválido");
			return false;
		}
		String nome = (String) objs[0];
		Jogador jogador = new Jogador(nome, 0, 0);
		try {
			jogador.cadastrar();
		} catch (JogoDaVelhaExceptions e) {
			this.mensagem.imprimirErro(e);
			return false;
		}
		return true;
	}

	public List<Jogador> listarJogadores() {
		List<Jogador> lista = null;
		try {
			lista = Jogador.listar();
			if (lista.isEmpty() == true) {
				this.mensagem.imprimir("Não existem jogadores cadastrados no sistema");
				return null;
			}
			Collections.sort(lista);
		} catch (JogoDaVelhaExceptions e) {
			this.mensagem.imprimirErro(e);
		}
		return lista;
	}
}
