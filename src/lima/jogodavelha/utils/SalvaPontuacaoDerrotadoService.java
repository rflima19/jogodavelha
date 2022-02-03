package lima.jogodavelha.utils;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import lima.jogodavelha.facade.FacadeConcrete;
import lima.jogodavelha.facade.FacadeOfSystem;

public class SalvaPontuacaoDerrotadoService extends Service<Void> {

	private FacadeOfSystem facade;

	public SalvaPontuacaoDerrotadoService() {
		super();
		this.facade = FacadeConcrete.getFacade();
	}

	@Override
	protected Task<Void> createTask() {
		return new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				SalvaPontuacaoDerrotadoService.this.facade.salvarPontuacaoDerrotado();
				if (this.isCancelled()) {
					return null;
				}
				return null;
			}
		};
	}
}
