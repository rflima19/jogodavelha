package lima.jogodavelha.model;

import java.io.IOException;
import java.util.List;

import lima.jogodavelha.DAO.JogadorDAO;
import lima.jogodavelha.DAO.JogoDaVelhaDAO;
import lima.jogodavelha.exceptions.DAOException;
import lima.jogodavelha.exceptions.JogoDaVelhaExceptions;
import lima.jogodavelha.utils.ValidadorNome;

public class Jogador implements Comparable<Jogador> {

	private static final JogoDaVelhaDAO REPOSITORIO = new JogadorDAO();;
	
	private String nome;
	private int quantidadeVitorias;
	private int quantidadeDerrotas;
	private int quantidadeEmpates;
	private Simbolo simbolo;
	
	public Jogador(String nome, int quantidadeVitorias, int quantidadeDerrotas, int quantidadeEmpates) {
		super();
		if (Jogador.validarNome(nome) == false) {
			throw new IllegalArgumentException("Nome inválido");
		}
		this.nome = nome;
		this.quantidadeVitorias = quantidadeVitorias;
		this.quantidadeDerrotas = quantidadeDerrotas;
		this.quantidadeEmpates = quantidadeEmpates;
	}
	
	public Jogador(String nome) {
		this(nome, 0, 0, 0);
	}

	public String getNome() {
		return nome;
	}
	
	public int getQuantidadeVitorias() {
		return quantidadeVitorias;
	}
	
	public int getQuantidadeDerrotas() {
		return quantidadeDerrotas;
	}
	
	public int getQuantidadeEmpates() {
		return quantidadeEmpates;
	}
	
	public void setSimbolo(Simbolo simbolo) {
		this.simbolo = simbolo;
	}
	
	public Simbolo getSimbolo() {
		return simbolo;
	}
	
	public void cadastrar() throws DAOException {
		Jogador.REPOSITORIO.cadastarJogador(this);
	}
	
	public static List<Jogador> listar() throws DAOException {
		return Jogador.REPOSITORIO.listarJogadores();
	}
	
	public static Jogador pesquisar(String nome) throws DAOException {
		return Jogador.REPOSITORIO.pesquisarJogador(nome);
	}
	
	public static void salvarPontuacaoVencedor(Jogador vencedor) throws DAOException {
		Jogador.REPOSITORIO.salvarPontuacaoVencedor(vencedor);
	}
	
	public static void salvarPontuacaoDerrotado(Jogador derrotado) throws DAOException {
		Jogador.REPOSITORIO.salvarPontuacaoDerrotado(derrotado);
	}
	
	public static void salvarPontuacaoEmpate(Jogador jogador1, Jogador jogador2) throws DAOException {
		Jogador.REPOSITORIO.salvarPontuacaoEmpate(jogador1, jogador2);
	}
	
	public static boolean validarNome(String nome) {
		return new ValidadorNome().test(nome);
	}
	
	@Override
	public String toString() {
		return String.format("[nome=%s,num_vit=%d,num_der=%d]" , this.nome, this.quantidadeVitorias, this.quantidadeDerrotas);
	}
	
	@Override
	public int compareTo(Jogador o) {
		if (this.quantidadeVitorias < o.getQuantidadeVitorias()) {
			return 1;
		}
		if (this.quantidadeVitorias > o.getQuantidadeVitorias()) {
			return -1;
		}
		return 0;
	}
}
