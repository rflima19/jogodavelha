package lima.jogodavelha.view;

import lima.jogodavelha.utils.TerminalUtils;

public class TerminalMensagemView {

	private TerminalUtils terminal;

	public TerminalMensagemView() {
		super();
		this.terminal = new TerminalUtils();
	}
	
	public void imprimir(String mensagem) {
		this.terminal.imprimirMensagem(mensagem);
		System.out.println();
	}
	
	public void imprimirErro(String mensagem) {
		this.terminal.imprimirMensagemErro(mensagem);
		System.out.println();
	}
	
	public void imprimirErro(Exception e) {
		this.terminal.imprimirMensagemErro(e);
		System.out.println();
	}
	
	public void imprimirErro(String mensagem, Exception e) {
		if (e == null) {
			new IllegalArgumentException("Exceção nula");
		}
		this.terminal.imprimirMensagemErro(e);
		e.printStackTrace();
		System.out.println();
	}
}
