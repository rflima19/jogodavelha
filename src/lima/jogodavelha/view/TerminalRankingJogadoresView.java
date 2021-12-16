package lima.jogodavelha.view;

import java.util.List;

import lima.jogodavelha.exceptions.ControllerException;
import lima.jogodavelha.facade.FacadeConcrete;
import lima.jogodavelha.facade.FacadeOfSystem;
import lima.jogodavelha.model.Jogador;
import lima.jogodavelha.utils.TerminalUtils;

public class TerminalRankingJogadoresView {

	private TerminalUtils terminal;
	private FacadeOfSystem facade;

	public TerminalRankingJogadoresView() {
		super();
		this.terminal = new TerminalUtils();
		this.facade = FacadeConcrete.getFacade();
	}

	public void imprimirRancking() {
		try {
			List<String[]> lista = this.facade.listarJogadores();
			if (lista == null) {
				return;
			}
			this.terminal.imprimirCabecalho(" RANKING JOGADORES ");
			for (int i = 0; i < 120; i++) {
				System.out.print("-");
			}
			System.out.println();
			System.out.printf("%-20s\t%-30s\t%-20s\t%-20s\t%-20s%n", 
					"POSIÇÃO", "JOGADOR", "VITÓRIAS", "DERROTAS", "TOTAL JOGOS");
			for (int i = 0; i < 120; i++) {
				System.out.print("-");
			}
			System.out.println();
			for (int i = 0; i < lista.size(); i++) {
				String[] j = lista.get(i);
				String nome = j[0];
				int qv = Integer.parseInt(j[1]);
				int qd = Integer.parseInt(j[2]);
				System.out.printf("%-20s\t%-30s\t%-20s\t%-20s\t%-20s%n", (i + 1) + "º", nome, qv, qd, (qv + qd));
				for (int k = 0; k < 120; k++) {
					System.out.print("-");
				}
				System.out.println();
			}
		} catch (ControllerException e) {
			this.terminal.imprimirMensagemErro(e.getMessage());
			System.out.println();
		}
	}
}
