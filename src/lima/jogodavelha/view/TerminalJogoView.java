package lima.jogodavelha.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lima.jogodavelha.exceptions.ControllerException;
import lima.jogodavelha.exceptions.JogoDaVelhaExceptions;
import lima.jogodavelha.facade.FacadeConcrete;
import lima.jogodavelha.facade.FacadeOfSystem;
import lima.jogodavelha.model.Jogador;
import lima.jogodavelha.utils.Console;
import lima.jogodavelha.utils.InputUsuario;
import lima.jogodavelha.utils.InputUsuarioString;
import lima.jogodavelha.utils.TerminalUtils;
import lima.jogodavelha.utils.ValidadorNome;

public class TerminalJogoView {

	private TerminalUtils terminal;
	// private TerminalMensagemView mensagem;
	private FacadeOfSystem facade;

	public TerminalJogoView() {
		super();
		this.terminal = new TerminalUtils();
		// this.mensagem = new TerminalMensagemView();
		this.facade = FacadeConcrete.getFacade();
	}

	public void iniciarJogo() throws IOException {
		String[] j1 = null;
		String[] j2 = null;
		char[][] tabuleiro = null;
		String[] jogadorDaRodada = null;
		String[] vencedor = null;
		String nome = null;
		int rodada = 1;
		String jogadaStr = null;
		boolean result = false;
		boolean fimJogo = false;
		
		this.terminal.imprimirCabecalho(" NOVO JOGO ");
		
		this.facade.criarNovoJogo();
		
		while (true) {
			System.out.println("Digite o nome do jogador(a) 1");
			System.out.print(">>");
			nome = Console.readString();
			try {
				result = this.facade.setJogador1(nome);
				if (result == false) {
					this.terminal.imprimirMensagemErro("Nome '" + nome + "' não válido");
					continue;
				}
				break;
			} catch (ControllerException e) {
				this.terminal.imprimirMensagemErro(e.getMessage());
				return;
			}
		}
		while (true) {
			System.out.println("Digite o nome do jogador(a) 2");
			System.out.print(">>");
			nome = Console.readString();
			try {
				result = this.facade.setJogador2(nome);
				if (result == false) {
					this.terminal.imprimirMensagemErro("Nome '" + nome + "' não válido");
					continue;
				}
				break;
			} catch (ControllerException e) {
				this.terminal.imprimirMensagemErro(e.getMessage());
				return;
			}
		}
		
		//this.facade.iniciarJogo();
		j1 = this.facade.getJogador1();
		if (j1 != null) {
//			this.mensagem.imprimir("Jogador(a) 1 - " + j1[0] + " [vitórias=" + j1[1] + ", derrotas=" + j1[2] + "] marca tabuleiro com " + j1[3]);
			this.terminal.imprimirMensagem("Jogador(a) 1 - " + j1[0] + " [vitórias=" + j1[1] + ", derrotas=" + 
					j1[2] + "] marca tabuleiro com " + j1[3]);
		}
		j2 = this.facade.getJogador2();
		if (j2 != null) {
//			this.mensagem.imprimir("Jogador(a) 2 - " + j2[0] + " [vitórias=" + j2[1] + ", derrotas=" + j2[2] + "] marca tabuleiro com " + j2[3]);
			this.terminal.imprimirMensagem("Jogador(a) 2 - " + j2[0] + " [vitórias=" + j2[1] + ", derrotas=" + 
					j2[2] + "] marca tabuleiro com " + j2[3]);
		}
		
		while (fimJogo == false) {
			System.out.println();
			System.out.println(rodada + "º rodada");
			System.out.println();
			tabuleiro = this.facade.getTabuleiro();
			this.imprimirTabuleiro(tabuleiro);
			System.out.println();
			
			jogadorDaRodada = this.facade.getJogadorDaRodada();
			System.out.println("Vez do jogador(a) " + jogadorDaRodada[0] + " jogar");
			
			while (true) {
				System.out.println("Digite a linha e coluna (EX.: 1, 1) da posição da jogada");
				System.out.print(">>");
				jogadaStr = Console.readString();
				
				result = this.facade.registrarJogada(jogadaStr, jogadorDaRodada[3]);
				if (result == true) {
					break;
				} else {
					// this.mensagem.imprimirErro("Jogada inválida");
					this.terminal.imprimirMensagemErro("Jogada inválida");
				}
			}
			
			result = this.facade.isSequenciaEncontrada();
			
			if (result == true) {
				vencedor = this.facade.getVencedor();
				fimJogo = true;
			}
			
			result = this.facade.isTabuleiroCompleto();
			
			if (result == true) {
				fimJogo = true;
			}
			
			rodada++;
			//break;
		}
		
		System.out.println();
		tabuleiro = this.facade.getTabuleiro();
		this.imprimirTabuleiro(tabuleiro);
		
		if (vencedor == null) {
			System.out.println();
			// this.mensagem.imprimir("Jogo terminou empatado");
			this.terminal.imprimirMensagem("Jogo terminou empatado");
			System.out.println();
		} else {
			System.out.println();
			// this.mensagem.imprimir("Jogador(a) " + vencedor[0] + " venceu o jogo");
			this.terminal.imprimirMensagem("Jogador(a) " + vencedor[0] + " venceu o jogo");
			System.out.println();
		}
		
		//this.mensagem.imprimir("quem ganhou o jogo");
	}
	
	private void imprimirTabuleiro(char[][] tabuleiro) {
		System.out.println("  1 | 2 | 3");
		for (int i = 0; i < tabuleiro.length; i++) {
			System.out.printf("%d ", i + 1);
			for (int j = 0; j < tabuleiro[i].length; j++) {
				System.out.print(tabuleiro[i][j]);
				if (j != (tabuleiro[i].length - 1)) {
					System.out.print(" | ");
				}
			}
			System.out.println();
			if (i != (tabuleiro.length - 1)) {
				System.out.println("-------------");
			}
		}
	}
	
//	public static void main(String[] args) {
//		char[][] c = new char[3][3];
//		new TerminalJogoView().imprimirTabuleiro(c);
//	}
	
//	public String solicitarNome() throws IOException {
//		System.out.println("Digite o nome do jogador 1");
//		System.out.print(">>");
//		return Console.readString();
//	}
}
