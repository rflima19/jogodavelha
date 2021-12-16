package lima.jogodavelha.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lima.jogodavelha.exceptions.ControllerException;
import lima.jogodavelha.exceptions.DAOException;
import lima.jogodavelha.exceptions.JogoDaVelhaExceptions;
import lima.jogodavelha.model.Jogador;
import lima.jogodavelha.utils.ValidadorNome;
import lima.jogodavelha.view.TerminalMensagemView;

public class JogadorController {

	public JogadorController() {
		super();
		// this.mensagem = new TerminalMensagemView();
	}

	public void cadastrarJogador(String nome) throws ControllerException {
		if (Jogador.validarNome(nome) == false) {
			throw new ControllerException("Nome '" + nome + "' não válido para cadastro");
			// return false;
		}
		Jogador jogador = null;
		try {
			// String nome = (String) objs[0];
			jogador = Jogador.pesquisar(nome);
			if (jogador != null) {
				// this.mensagem.imprimir("Já existe um jogador com o nome '" + nome + "' cadastrado no sistema");
				// return false;
				throw new ControllerException("Já existe um jogador com o nome '" + nome + "' cadastrado no sistema");
			}
		} catch (DAOException e) {
			throw new ControllerException("Erro ao pesquisar jogador na base de dados", e);
		}
		try {
			jogador = new Jogador(nome, 0, 0, 0);
			jogador.cadastrar();
		} catch (DAOException e) {
			throw new ControllerException("Erro ao cadastrar jogador na base de dados", e);
		} 
		// return true;
	}

	public List<String[]> listarJogadores() throws ControllerException {
		List<Jogador> listaBD = null;
		List<String[]> listaView = new ArrayList<>();
		try {
			listaBD = Jogador.listar();
			if (listaBD.isEmpty() == true) {
				// this.mensagem.imprimir("Não existem jogadores cadastrados no sistema");
				// return null;
				throw new ControllerException("Não existem jogadores cadastrados no sistema");
			}
			Collections.sort(listaBD);
			for (Jogador j : listaBD) {
				String nome = j.getNome();
				String qv = Integer.toString(j.getQuantidadeVitorias());
				String qd = Integer.toString(j.getQuantidadeDerrotas());
				String qe = Integer.toString(j.getQuantidadeEmpates());
				listaView.add(new String[] {nome, qv, qd, qe});
			}
		} catch (DAOException e) {
			// this.mensagem.imprimirErro(e);
			throw new ControllerException("Erro ao listar jogadores da base de dados", e);
		}
		return listaView;
	}

	public String[] pesquisarJogador(String nome) throws ControllerException {
		if (Jogador.validarNome(nome) == false) {
			throw new ControllerException("Nome '" + nome + "' não válido");
		}
		String[] jogador = new String[4];
		Jogador j = null;
		try {
			j = Jogador.pesquisar(nome);
			if (j != null) {
				jogador[0] = j.getNome();
				jogador[1] = Integer.toString(j.getQuantidadeVitorias());
				jogador[2] = Integer.toString(j.getQuantidadeDerrotas());
				jogador[3] = Integer.toString(j.getQuantidadeEmpates());
			}
		} catch (DAOException e) {
			// this.mensagem.imprimirErro(e);
			throw new ControllerException("Erro ao pesquisar jogador na base de dados", e);
		}
		return jogador;
	}
}
