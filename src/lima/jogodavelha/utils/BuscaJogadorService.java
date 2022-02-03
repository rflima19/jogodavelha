package lima.jogodavelha.utils;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.Alert.AlertType;
import lima.jogodavelha.facade.FacadeConcrete;
import lima.jogodavelha.facade.FacadeOfSystem;

public class BuscaJogadorService extends Service<String[]> {

	private FacadeOfSystem facade;
	private String nomeJogador;
	private int numJogador;
	
	public BuscaJogadorService(String nomeJogador, int numJogador) {
		super();
		this.facade = FacadeConcrete.getFacade();
		this.nomeJogador = nomeJogador;
		this.numJogador = numJogador;
	}

	@Override
	protected Task<String[]> createTask() {
		return new Task<String[]>() {
			@Override
			protected String[] call() throws Exception {
				boolean result = false;
				if (BuscaJogadorService.this.numJogador == 1) {
					result = BuscaJogadorService.this.facade.setJogador1(BuscaJogadorService.this.nomeJogador);
				} else if (BuscaJogadorService.this.numJogador == 2) {
					result = BuscaJogadorService.this.facade.setJogador2(BuscaJogadorService.this.nomeJogador);
				}
				if (result == false) {
					BuscaJogadorService.this.failed();
					return null;
				}
				if (BuscaJogadorService.this.numJogador == 1) {
					//System.out.println("service: 1");
					return BuscaJogadorService.this.facade.getJogador1();
				} else if (BuscaJogadorService.this.numJogador == 2) {
					return BuscaJogadorService.this.facade.getJogador2();
				}
				return null;
			}
		};
	}
	@Override
	protected void failed() {
		AlertJavaFXUtils.alert(AlertType.INFORMATION, "Buscar Jogador", "Nome " + this.nomeJogador + " não é válido.");
	}
}
