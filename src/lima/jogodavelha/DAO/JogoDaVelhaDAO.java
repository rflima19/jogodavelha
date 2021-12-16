package lima.jogodavelha.DAO;

import java.util.List;

import lima.jogodavelha.exceptions.DAOException;
import lima.jogodavelha.exceptions.JogoDaVelhaExceptions;
import lima.jogodavelha.model.Jogador;

public interface JogoDaVelhaDAO {
	
	public abstract void cadastarJogador(Jogador jogador) throws DAOException;

	public abstract List<Jogador> listarJogadores() throws DAOException;
	
	public abstract Jogador pesquisarJogador(String nome) throws DAOException;

}
