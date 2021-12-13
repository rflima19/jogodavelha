package lima.jogodavelha.model;

import java.io.IOException;
import java.util.List;

import lima.jogodavelha.DAO.JogadorDAO;
import lima.jogodavelha.DAO.JogoDaVelhaDAO;
import lima.jogodavelha.exceptions.JogoDaVelhaExceptions;
import lima.jogodavelha.utils.ValidadorNome;

public class Jogador implements Comparable<Jogador> {

	private String nome;
	private int quantidadeVitorias;
	private int quantidadeDerrotas;
	private JogoDaVelhaDAO repositorio;

//	public Jogador(String nome) {
//		super();
//		if (new ValidadorNome().test(nome) == false) {
//			throw new IllegalArgumentException("Nome inválido");
//		}
//		this.nome = nome;
//		this.repositorio = new JogadorDAO();
//	}
	
	public Jogador(String nome, int quantidadeVitorias, int quantidadeDerrotas) {
		super();
		if (new ValidadorNome().test(nome) == false) {
			throw new IllegalArgumentException("Nome inválido");
		}
		this.nome = nome;
		this.quantidadeVitorias = quantidadeVitorias;
		this.quantidadeDerrotas = quantidadeDerrotas;
		this.repositorio = new JogadorDAO();
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
	
	public void cadastrar() throws JogoDaVelhaExceptions {
		this.repositorio.cadastarJogador(this);
	}
	
	public static List<Jogador> listar() throws JogoDaVelhaExceptions {
		return JogadorDAO.listarJogadores();
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
