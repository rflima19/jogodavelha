package lima.jogodavelha.facade;

import java.util.List;

import lima.jogodavelha.controller.JogadorController;
import lima.jogodavelha.controller.JogoControlller;
import lima.jogodavelha.exceptions.JogoDaVelhaExceptions;
import lima.jogodavelha.model.Jogador;

/**
 * Segue o padrão singleton
 * */
public class FacadeConcrete implements FacadeOfSystem {

	private static FacadeConcrete facade = null;
	
	private JogoControlller jogoController;
	private JogadorController jogadorController;
	
	private FacadeConcrete() {
		this.jogoController = new JogoControlller();
		this.jogadorController = new JogadorController();
	}
	
	public static FacadeConcrete getFacade() {
		if (FacadeConcrete.facade == null) {
			FacadeConcrete.facade = new FacadeConcrete();
		}
		return FacadeConcrete.facade;
	}
	
	@Override
	public void switchOpcao(int opcao) {
		this.jogoController.switchOpcao(opcao);
	}
	
	@Override
	public boolean cadastrarJogador(String nome) {
		return this.jogadorController.cadastrarJogador(nome);
	}
	
	@Override
	public List<String[]> listarJogadores() {
		return this.jogadorController.listarJogadores();	
	}
	
	@Override
	public String[] pesquisarJogador(String nome) {
		return this.jogadorController.pesquisarJogador(nome);
	}
	
	@Override
	public boolean setJogador1(String nome) {
		return this.jogoController.setJogador1(nome);
	}
	
	@Override
	public boolean setJogador2(String nome) {
		return this.jogoController.setJogador2(nome);
	}
	
	@Override
	public String[] getJogador1() {
		return this.jogoController.getJogador1();
	}
	
	@Override
	public String[] getJogador2() {
		return this.jogoController.getJogador2();
	}
	
	@Override
	public char[][] getTabuleiro() {
		return this.jogoController.getTabuleiro();
	}
	
	@Override
	public String[] getJogadorDaRodada() {
		return this.jogoController.getJogadorDaRodada();
	}
	
	@Override
	public boolean registrarJogada(String jogada, String simbolo) {
		return this.jogoController.registrarJogada(jogada, simbolo);
	}
	
	@Override
	public boolean isTabuleiroCompleto() {
		return this.jogoController.isTabuleiroCompleto();
	}
	
	@Override
	public boolean isSequenciaEncontrada() {
		return this.jogoController.isSequenciaEncontrada();
	}
	
	@Override
	public String[] getVencedor() {
		return this.jogoController.getVencedor();
	}
	
	@Override
	public void criarNovoJogo() {
		this.jogoController.criarNovoJogo();
	}
	
//	@Override
//	public void iniciarJogo() {
//		this.jogoController.iniciarJogo();
//	}
}
