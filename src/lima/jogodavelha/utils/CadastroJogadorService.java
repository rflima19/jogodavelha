package lima.jogodavelha.utils;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.Alert.AlertType;
import lima.jogodavelha.facade.FacadeConcrete;
import lima.jogodavelha.facade.FacadeOfSystem;

public class CadastroJogadorService extends Service<Void> {

	private FacadeOfSystem facade;
	private String nomeJogador;

	public CadastroJogadorService(String nomeJogador) {
		super();
		this.facade = FacadeConcrete.getFacade();
		this.nomeJogador = nomeJogador;
	}

	@Override
	protected Task<Void> createTask() {
		return new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				CadastroJogadorService.this.facade.cadastrarJogador(CadastroJogadorService.this.nomeJogador);
				if (this.isCancelled()) {
					return null;
				}
				return null;
			}
		};
	}

	@Override
	protected void succeeded() {
		AlertJavaFXUtils.alert(AlertType.INFORMATION, "Cadastro Jogador", "Jogador " + this.nomeJogador + " cadastrado com sucesso.");
	}
}
