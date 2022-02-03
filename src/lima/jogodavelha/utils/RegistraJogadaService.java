package lima.jogodavelha.utils;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import lima.jogodavelha.facade.FacadeConcrete;
import lima.jogodavelha.facade.FacadeOfSystem;

public class RegistraJogadaService extends Service<Boolean> {

	private FacadeOfSystem facade;
	private int linha;
	private int coluna;
	private String simbolo;
	
	public RegistraJogadaService() {
		super();
	}

	public RegistraJogadaService(int linha, int coluna, String simbolo) {
		super();
		this.facade = FacadeConcrete.getFacade();
		this.linha = linha;
		this.coluna = coluna;
		this.simbolo = simbolo;
	}

	@Override
	protected Task<Boolean> createTask() {
		return new Task<Boolean>() {
			@Override
			protected Boolean call() throws Exception {
				String jogada = RegistraJogadaService.this.linha + ", " + RegistraJogadaService.this.coluna;
				return RegistraJogadaService.this.facade.registrarJogada(jogada, RegistraJogadaService.this.simbolo);
			}
		};
	}
}
