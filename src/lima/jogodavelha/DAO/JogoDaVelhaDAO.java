package lima.jogodavelha.DAO;

import java.util.List;

import lima.jogodavelha.exceptions.JogoDaVelhaExceptions;
import lima.jogodavelha.model.Jogador;

public interface JogoDaVelhaDAO {
	
	public abstract void cadastarJogador(Jogador jogador) throws JogoDaVelhaExceptions;

	public abstract List<Jogador> listarJogadores() throws JogoDaVelhaExceptions;
	
	public abstract Jogador pesquisarJogador(String nome) throws JogoDaVelhaExceptions;

}
