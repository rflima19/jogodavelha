package lima.jogodavelha.utils;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import lima.jogodavelha.facade.FacadeConcrete;
import lima.jogodavelha.facade.FacadeOfSystem;

public class AnalisaJogadaService extends Service<Boolean> {

	private FacadeOfSystem facade;
	
	public AnalisaJogadaService() {
		super();
		this.facade = FacadeConcrete.getFacade();
	}

	@Override
	protected Task<Boolean> createTask() {
		return new Task<Boolean>() {
			@Override
			protected Boolean call() throws Exception {
				return AnalisaJogadaService.this.facade.isSequenciaEncontrada();
			}
		};
	}
}
