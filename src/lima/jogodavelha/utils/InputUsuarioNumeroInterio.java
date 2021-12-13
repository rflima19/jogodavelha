package lima.jogodavelha.utils;

import java.io.IOException;

public class InputUsuarioNumeroInterio extends InputUsuario {

	private int valor;
	private ValidadorInteiro validador;

	public InputUsuarioNumeroInterio(String titulo, ValidadorInteiro validador) {
		super(titulo);
		if (validador == null) {
			throw new IllegalArgumentException("Argumento validador nulo");
		}
		this.validador = validador;
	}

	public int getValor() {
		return valor;
	}

	public ValidadorInteiro getValidador() {
		return validador;
	}

	@Override
	public Object lerDado() throws IOException {
		System.out.println(this.titulo);
		System.out.print(">>");
		this.valor = Console.readInteger();
		if (this.validador.test(this.valor) == false) {
			return null;
		}
		return this.valor;
	}

}
