package lima.jogodavelha.utils;

import java.io.IOException;
import java.util.List;

public class TerminalUtils {

	private final char caractere = '#';
	private final int limite = 120;

	public Integer menuOpcoes(String titulo, String[] titulos) throws IOException {
		if (titulos == null) {
			throw new IllegalArgumentException("Argumento nulo");
		}
		if (titulos.length == 0) {
			throw new IllegalArgumentException("Array com tamanho 0");
		}

		int opcao = 0;
		while (true) {
			this.imprimirCabecalho(titulo);
			for (int i = 0; i < titulos.length; i++) {
				System.out.println((i + 1) + " - " + titulos[i]);
			}
			try {
				System.out.print(">>");
				opcao = Console.readInteger();
			} catch (NumberFormatException e) {
				this.imprimirMensagemErro("Digite um valor inteiro");
				System.out.printf("%n%n");
				continue;
			}
			break;
		}
		return opcao;
	}

//	public Object[] formulario(String titulo, List<InputUsuario> inputs) throws IOException {
//		if (titulo == null) {
//			throw new IllegalArgumentException("argumento string nulo");
//		}
//		if (inputs == null) {
//			throw new IllegalArgumentException("argumento map nulo");
//		}
//		if (inputs.size() == 0) {
//			throw new IllegalArgumentException("coleção map vazia");
//		}
//		this.imprimirCabecalho(titulo);
//
//		Object[] arrayInputs = new Object[inputs.size()];
//		Object obj = null;
//		int cont = 0;
//		for (InputUsuario input : inputs) {
//			obj = input.lerDado();
//			if (obj == null) {
//				return null;
//			}
//			arrayInputs[cont] = obj;
//			++cont;
//		}
//		return arrayInputs;
//	}

	public void imprimirMensagem(String texto) {
		this.imprimirLinha();
		System.out.printf("%n\t%s%n", texto);
		this.imprimirLinha();
	}

	public void imprimirMensagemErro(String texto) {
		String s = String.format("\tErro: %s", texto);
		this.imprimirMensagem(s);
	}

	public void imprimirMensagemErro(Exception exc) {
		String s = String.format("\tErro: %s", exc.getMessage());
		this.imprimirMensagem(s);
	}

	public void imprimirCabecalho(String titulo) {
		int l = titulo.length();
		this.imprimirLinha();
		System.out.println();
		this.imprimirLinha((this.limite / 2) - (l / 2));
		System.out.print(titulo);
		if (l % 2 == 0) {
			this.imprimirLinha((this.limite / 2) - (l / 2));
		} else {
			this.imprimirLinha((this.limite / 2) - (l / 2) - 1);
		}
		System.out.println();
		this.imprimirLinha();
		System.out.println();
	}

	private void imprimirLinha() {
		for (int i = 0; i < this.limite; i++) {
			System.out.print(this.caractere);
		}
	}

	private void imprimirLinha(int limite) {
		for (int i = 0; i < limite; i++) {
			System.out.print(this.caractere);
		}
	}
}
