package lima.jogodavelha.utils;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import lima.jogodavelha.facade.FacadeConcrete;
import lima.jogodavelha.facade.FacadeOfSystem;

public class BuscaTabuleiroService extends Service<char[][]> {

	private FacadeOfSystem facade;
	
	public BuscaTabuleiroService() {
		super();
		this.facade = FacadeConcrete.getFacade();
	}

	@Override
	protected Task<char[][]> createTask() {
		return new Task<char[][]>() {
			@Override
			protected char[][] call() throws Exception {
				return BuscaTabuleiroService.this.facade.getTabuleiro();
			}
		};
	}
}
