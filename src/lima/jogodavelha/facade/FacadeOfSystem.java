package lima.jogodavelha.facade;

import java.util.List;

import lima.jogodavelha.model.Jogador;

/**
 * Fachada do sistema
 * */
public interface FacadeOfSystem {

	public abstract void switchOpcao(int opcao);
	public abstract boolean cadastrarJogador(String nome);
	public abstract List<String[]> listarJogadores();
	public abstract String[] pesquisarJogador(String nome);
	public abstract boolean setJogador1(String nome);
	public abstract boolean setJogador2(String nome);
	//public abstract void iniciarJogo();
	public abstract String[] getJogador1();
	public abstract String[] getJogador2();
	public abstract char[][] getTabuleiro();
	public abstract String[] getJogadorDaRodada();
	public abstract boolean registrarJogada(String jogada, String simbolo);
	public abstract boolean isTabuleiroCompleto();
	public abstract boolean isSequenciaEncontrada();
	public abstract String[] getVencedor();
	public abstract void criarNovoJogo();
}
