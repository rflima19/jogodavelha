package lima.jogodavelha.utils;

import java.util.ArrayList;
import java.util.List;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import lima.jogodavelha.facade.FacadeConcrete;
import lima.jogodavelha.facade.FacadeOfSystem;

public class ListarJogadoresService extends Service<List<String[]>> {

	private FacadeOfSystem facade;

	public ListarJogadoresService() {
		super();
		this.facade = FacadeConcrete.getFacade();
	}

	@Override
	protected Task<List<String[]>> createTask() {
		return new Task<List<String[]>>() {
			@Override
			protected List<String[]> call() throws Exception {
				List<String[]> result = new ArrayList<>();
				List<String[]> listaArray = ListarJogadoresService.this.facade.listarJogadores();
				
				for (int i = 0; i < listaArray.size(); i++) {
					String[] j = listaArray.get(i);
					result.add(j);
					if (this.isCancelled()) {
						break;
					}
					Thread.sleep(100);
					this.updateProgress(i + 1, listaArray.size());
				}
				return result;
			}
		};
	}
}
