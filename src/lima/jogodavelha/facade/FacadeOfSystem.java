package lima.jogodavelha.facade;

import java.util.List;

import lima.jogodavelha.model.Jogador;

/**
 * Fachada do sistema
 * */
public interface FacadeOfSystem {

	public void switchOpcao(int opcao);
	public boolean cadastrarJogador(Object[] objs);
	public List<Jogador> listarJogadores();
}
