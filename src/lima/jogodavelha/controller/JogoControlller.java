package lima.jogodavelha.controller;

import java.io.IOException;

import lima.jogodavelha.exceptions.JogoDaVelhaExceptions;
import lima.jogodavelha.model.Jogada;
import lima.jogodavelha.model.Jogador;
import lima.jogodavelha.model.Simbolo;
import lima.jogodavelha.model.Tabuleiro;
import lima.jogodavelha.utils.ValidadorNome;
import lima.jogodavelha.view.TerminalCadastroJogador;
import lima.jogodavelha.view.TerminalJogoView;
import lima.jogodavelha.view.TerminalMensagemView;
import lima.jogodavelha.view.TerminalRankingJogadoresView;

public class JogoControlller {
	
	private TerminalMensagemView mensagem; //TODO desanexar camada controller com a view
	private Jogador jogador1;
	private Jogador jogador2;
	private Jogador jogadorDaRodada;
	private Jogador vencedor;
	private Jogador[] jogadores;
	private int index;
	private Tabuleiro tabuleiro;
		
	public JogoControlller() {
		super();
		this.mensagem = new TerminalMensagemView();
		this.criarNovoJogo();
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
		case 2 -> {
			try {
				new TerminalJogoView().iniciarJogo();
			} catch (IOException e) {
				this.mensagem.imprimirErro("Erro de I/O ao iniciar o jogor", e);
			}
		}
		case 3 -> {
			new TerminalRankingJogadoresView().imprimirRancking();
		}
		case 4 -> System.exit(0);
		default -> {
			this.mensagem.imprimirErro("Opção inválida");
		}
		}
	}
	
	public boolean setJogador1(String nome) {
		ValidadorNome vn = new ValidadorNome();
		if (vn.test(nome) == false) {
			this.mensagem.imprimirErro("Nome inválido");
			return false;
		}
		try {
			this.jogador1 = Jogador.pesquisar(nome);
			
			if (this.jogador1 == null) {
				this.jogador1 = new Jogador(nome);
				this.jogador1.cadastrar();
			}
			
			this.jogador1.setSimbolo(Simbolo.CHIS);
			this.jogadores[0] = this.jogador1;
		} catch (JogoDaVelhaExceptions e) {
			this.mensagem.imprimirErro(e);
			return false;
		}
		return true;
	}
	
	public boolean setJogador2(String nome) {
		ValidadorNome vn = new ValidadorNome();
		if (vn.test(nome) == false) {
			this.mensagem.imprimirErro("Nome inválido");
			return false;
		}
		try {
			this.jogador2 = Jogador.pesquisar(nome);
			
			if (this.jogador2 == null) {
				this.jogador2 = new Jogador(nome);
				this.jogador2.cadastrar();
			}
			
			this.jogador2.setSimbolo(Simbolo.CIRCULO);
			this.jogadores[1] = this.jogador2;
		} catch (JogoDaVelhaExceptions e) {
			this.mensagem.imprimirErro(e);
			return false;
		}
		return true;
	}
	
	public String[] getJogador1() {
		if (this.jogador1 == null) {
			return null;
		}
		String[] j = new String[4];
		j[0] = this.jogador1.getNome();
		j[1] = Integer.toString(this.jogador1.getQuantidadeVitorias());
		j[2] = Integer.toString(this.jogador1.getQuantidadeDerrotas());
		j[3] = Character.toString(this.jogador1.getSimbolo().getRepresentacao());
		return j;
	}
	
	public String[] getJogador2() {
		if (this.jogador2 == null) {
			return null;
		}
		String[] j = new String[4];
		j[0] = this.jogador2.getNome();
		j[1] = Integer.toString(this.jogador2.getQuantidadeVitorias());
		j[2] = Integer.toString(this.jogador2.getQuantidadeDerrotas());
		j[3] = Character.toString(this.jogador2.getSimbolo().getRepresentacao());
		return j;
	}
	
	public char[][] getTabuleiro() {
		return tabuleiro.getMatriz();
	}
	
	public String[] getJogadorDaRodada() {
		this.index = (this.index + 1) % this.jogadores.length;
		this.jogadorDaRodada = this.jogadores[index];
		String[] j = new String[4];
		j[0] = jogadorDaRodada.getNome();
		j[1] = Integer.toString(jogadorDaRodada.getQuantidadeVitorias());
		j[2] = Integer.toString(jogadorDaRodada.getQuantidadeDerrotas());
		j[3] = Character.toString(jogadorDaRodada.getSimbolo().getRepresentacao());
		return j;
	}

	public boolean registrarJogada(String jogada, String simbolo) {
		Jogada j = Jogada.parseJogada(jogada);
		if (j == null) {
			return false;
		}
		Simbolo s = Simbolo.getSimbolo(simbolo.charAt(0));
		return this.tabuleiro.inserirJogada(j, s);
	}

	public boolean isTabuleiroCompleto() {
		return this.tabuleiro.isTabuleiroCompleto();
	}

	public boolean isSequenciaEncontrada() {
		boolean result = this.tabuleiro.isSequenciaEncontrada();
		if (result == true) {
			this.vencedor = this.jogadorDaRodada;
			return true;
		}
		return false;
	}

	public String[] getVencedor() {
		if (this.vencedor == null) {
			return null;
		}
		String[] j = new String[4];
		j[0] = this.vencedor.getNome();
		j[1] = Integer.toString(this.vencedor.getQuantidadeVitorias());
		j[2] = Integer.toString(this.vencedor.getQuantidadeDerrotas());
		j[3] = Character.toString(this.vencedor.getSimbolo().getRepresentacao());
		return j;
	}

	public void criarNovoJogo() {
		this.jogador1 = null;
		this.jogador2 = null;
		this.jogadorDaRodada = null;
		this.vencedor = null;
		this.jogadores = new Jogador[2];
		this.index = 1;
		this.tabuleiro = new Tabuleiro();
	}
}
