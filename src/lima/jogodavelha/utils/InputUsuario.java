package lima.jogodavelha.utils;

import java.io.IOException;

public abstract class InputUsuario {

	protected String titulo;

	public InputUsuario(String titulo) {
		super();
		if (titulo == null) {
			throw new IllegalArgumentException("argumento string nulo");
		}
		this.titulo = titulo;
	}

	public String getTitulo() {
		return titulo;
	}
	
	public abstract Object lerDado() throws IOException;
}
