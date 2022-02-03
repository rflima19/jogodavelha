package lima.jogodavelha.utils;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import lima.jogodavelha.facade.FacadeConcrete;
import lima.jogodavelha.facade.FacadeOfSystem;

public class SalvaPontuacaoVencedorService extends Service<Void> {

	private FacadeOfSystem facade;

	public SalvaPontuacaoVencedorService() {
		super();
		this.facade = FacadeConcrete.getFacade();
	}

	@Override
	protected Task<Void> createTask() {
		return new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				SalvaPontuacaoVencedorService.this.facade.salvarPontuacaoVencedor();
				if (this.isCancelled()) {
					return null;
				}
				return null;
			}
		};
	}
}
