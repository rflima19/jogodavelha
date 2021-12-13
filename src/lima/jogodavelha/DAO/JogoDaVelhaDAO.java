package lima.jogodavelha.DAO;

import java.io.IOException;

import lima.jogodavelha.exceptions.JogoDaVelhaExceptions;
import lima.jogodavelha.model.Jogador;

public interface JogoDaVelhaDAO {
	
	public void cadastarJogador(Jogador jogador) throws JogoDaVelhaExceptions;

}
