package lima.jogodavelha.utils;

import java.io.IOException;

public class InputUsuarioString extends InputUsuario {

	private String texto;
	private ValidadorString validador;
		
	public InputUsuarioString(String titulo, ValidadorString validador) {
		super(titulo);
		if (validador == null) {
			throw new IllegalArgumentException("Argumento validador nulo");
		}
		this.validador = validador;
	}
	
	public String getTexto() {
		return texto;
	}
	
	public ValidadorString getValidador() {
		return validador;
	}

	@Override
	public Object lerDado() throws IOException {
		System.out.println(this.titulo);
		System.out.print(">>");
		this.texto = Console.readString();
		if (this.validador.test(this.texto) == false) {
			return null;
		}
		return this.texto;
	}

}
