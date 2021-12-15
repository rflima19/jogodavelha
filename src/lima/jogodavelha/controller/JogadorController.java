package lima.jogodavelha.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lima.jogodavelha.exceptions.JogoDaVelhaExceptions;
import lima.jogodavelha.model.Jogador;
import lima.jogodavelha.utils.ValidadorNome;
import lima.jogodavelha.view.TerminalMensagemView;

public class JogadorController {

	private TerminalMensagemView mensagem;

	public JogadorController() {
		super();
		this.mensagem = new TerminalMensagemView();
	}

	public boolean cadastrarJogador(String nome) {
		ValidadorNome vn = new ValidadorNome();
		if (vn.test(nome) == false) {
			this.mensagem.imprimirErro("Nome inválido");
			return false;
		}
		try {
			// String nome = (String) objs[0];
			Jogador jogador = Jogador.pesquisar(nome);
			if (jogador != null) {
				this.mensagem.imprimir("Já existe um jogador com o nome '" + nome + "' cadastrado no sistema");
				return false;
			} else {
				jogador = new Jogador(nome, 0, 0);
			}
			jogador.cadastrar();
		} catch (JogoDaVelhaExceptions e) {
			this.mensagem.imprimirErro(e);
			return false;
		}
		return true;
	}

	public List<String[]> listarJogadores() {
		List<Jogador> listaBD = null;
		List<String[]> listaView = new ArrayList<>();
		try {
			listaBD = Jogador.listar();
			if (listaBD.isEmpty() == true) {
				this.mensagem.imprimir("Não existem jogadores cadastrados no sistema");
				return null;
			}
			Collections.sort(listaBD);
			for (Jogador j : listaBD) {
				String nome = j.getNome();
				String qv = Integer.toString(j.getQuantidadeVitorias());
				String qd = Integer.toString(j.getQuantidadeDerrotas());
				listaView.add(new String[] {nome, qv, qd});
			}
		} catch (JogoDaVelhaExceptions e) {
			this.mensagem.imprimirErro(e);
		}
		return listaView;
	}

	public String[] pesquisarJogador(String nome) {
		ValidadorNome vn = new ValidadorNome();
		if (vn.test(nome) == false) {
			this.mensagem.imprimirErro("Nome inválido");
			return null;
		}
		String[] jogador = new String[3];
		Jogador j = null;
		try {
			j = Jogador.pesquisar(nome);
			if (j != null) {
				jogador[0] = j.getNome();
				jogador[1] = Integer.toString(j.getQuantidadeVitorias());
				jogador[2] = Integer.toString(j.getQuantidadeDerrotas());
			}
		} catch (JogoDaVelhaExceptions e) {
			this.mensagem.imprimirErro(e);
		}
		return jogador;
	}
}
