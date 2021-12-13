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
	public boolean cadastrarJogador(Object[] objs) {
		return this.jogadorController.cadastrarJogador(objs);
	}
	
	@Override
	public List<Jogador> listarJogadores() {
		return this.jogadorController.listarJogadores();	
	}
}
