package lima.jogodavelha.DAO;

import java.util.List;

import lima.jogodavelha.exceptions.DAOException;
import lima.jogodavelha.exceptions.JogoDaVelhaExceptions;
import lima.jogodavelha.model.Jogador;

public interface JogoDaVelhaDAO {
	
	public abstract void cadastarJogador(Jogador jogador) throws DAOException;

	public abstract List<Jogador> listarJogadores() throws DAOException;
	
	public abstract Jogador pesquisarJogador(String nome) throws DAOException;

	public abstract void salvarPontuacaoVencedor(Jogador jogador) throws DAOException;

	public abstract void salvarPontuacaoDerrotado(Jogador jogador) throws DAOException;

	public abstract void salvarPontuacaoEmpate(Jogador jogador1, Jogador jogador2) throws DAOException;

}
