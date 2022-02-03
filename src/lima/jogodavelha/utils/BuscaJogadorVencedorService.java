package lima.jogodavelha.utils;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import lima.jogodavelha.facade.FacadeConcrete;
import lima.jogodavelha.facade.FacadeOfSystem;

public class BuscaJogadorVencedorService extends Service<String[]> {

	private FacadeOfSystem facade;
	
	public BuscaJogadorVencedorService() {
		super();
		this.facade = FacadeConcrete.getFacade();
	}

	@Override
	protected Task<String[]> createTask() {
		return new Task<String[]>() {
			@Override
			protected String[] call() throws Exception {
				return BuscaJogadorVencedorService.this.facade.getVencedor();
			}
		};
	}
}
