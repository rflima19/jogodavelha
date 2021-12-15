package lima.jogodavelha.model;

import java.io.IOException;
import java.util.List;

import lima.jogodavelha.DAO.JogadorDAO;
import lima.jogodavelha.DAO.JogoDaVelhaDAO;
import lima.jogodavelha.exceptions.JogoDaVelhaExceptions;
import lima.jogodavelha.utils.ValidadorNome;

public class Jogador implements Comparable<Jogador> {

	private static final JogoDaVelhaDAO REPOSITORIO = new JogadorDAO();;
	
	private String nome;
	private int quantidadeVitorias;
	private int quantidadeDerrotas;
	private Simbolo simbolo;
	
	public Jogador(String nome, int quantidadeVitorias, int quantidadeDerrotas) {
		super();
		if (new ValidadorNome().test(nome) == false) {
			throw new IllegalArgumentException("Nome inválido");
		}
		this.nome = nome;
		this.quantidadeVitorias = quantidadeVitorias;
		this.quantidadeDerrotas = quantidadeDerrotas;
	}
	
	public Jogador(String nome) {
		this(nome, 0, 0);
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
	
	public void setSimbolo(Simbolo simbolo) {
		this.simbolo = simbolo;
	}
	
	public Simbolo getSimbolo() {
		return simbolo;
	}
	
	public void cadastrar() throws JogoDaVelhaExceptions {
		Jogador.REPOSITORIO.cadastarJogador(this);
	}
	
	public static List<Jogador> listar() throws JogoDaVelhaExceptions {
		return Jogador.REPOSITORIO.listarJogadores();
	}
	
	public static Jogador pesquisar(String nome) throws JogoDaVelhaExceptions {
		return Jogador.REPOSITORIO.pesquisarJogador(nome);
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
