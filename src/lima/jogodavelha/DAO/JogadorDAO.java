package lima.jogodavelha.DAO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import lima.jogodavelha.exceptions.JogoDaVelhaExceptions;
import lima.jogodavelha.model.Jogador;

public class JogadorDAO implements JogoDaVelhaDAO {
	
	public static final String USE_HOME = System.getProperty("user.home");
	public static final File DIRETORIO = new File(JogadorDAO.USE_HOME + File.separator + "files" + File.separator);
	public static final File ARQUIVO = new File(JogadorDAO.DIRETORIO, "jogo.txt");

	public synchronized void cadastarJogador(Jogador jogador) throws JogoDaVelhaExceptions {
		this.criarBaseDados();
		
		try (Writer out = new FileWriter(JogadorDAO.ARQUIVO, true);
				PrintWriter print = new PrintWriter(out)) {
			String nome = (jogador.getNome().length() > 20) ? 
				jogador.getNome().substring(0, 20) :
				jogador.getNome();
			String parse = nome + ";" + 
				jogador.getQuantidadeVitorias() + ";" + 
				jogador.getQuantidadeDerrotas(); // nome_jogador;num_vitorias;num_derrotas
			print.println(parse);
		} catch (IOException e) {
			throw new JogoDaVelhaExceptions("N�o foi possivel gravar os dados do jogador no arquivo", e);
		}
	}
	
	@Override
	public synchronized List<Jogador> listarJogadores() throws JogoDaVelhaExceptions {
		this.criarBaseDados();
		
		List<Jogador> lista = new ArrayList<>();
		try (Reader in = new FileReader(JogadorDAO.ARQUIVO);
				BufferedReader buffer = new BufferedReader(in)) {
			String line = null;
			String[] tokens = null;
			while ((line = buffer.readLine()) != null) {
				tokens = line.split(";");
				lista.add(new Jogador(tokens[0], Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2])));
			}
		} catch (IOException e) {
			throw new JogoDaVelhaExceptions("N�o foi poss�vel ler a base de dados", e);
		}
		return lista;
	}
	
	@Override
	public synchronized Jogador pesquisarJogador(String nome) throws JogoDaVelhaExceptions {
		this.criarBaseDados();
		
		Jogador jogador = null;
		try (Reader in = new FileReader(JogadorDAO.ARQUIVO);
				BufferedReader buffer = new BufferedReader(in)) {
			String line = null;
			String[] tokens = null;
			String n = null;
			while ((line = buffer.readLine()) != null) {
				tokens = line.split(";");
				n = tokens[0];
				if (n.equals(nome) == true) {
					jogador = new Jogador(n, Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]));
					break;
				}
			}
		} catch (IOException e) {
			throw new JogoDaVelhaExceptions("N�o foi poss�vel ler a base de dados", e);
		}
		return jogador;
	}
	
	private synchronized void criarBaseDados() throws JogoDaVelhaExceptions {
		try {
			if (JogadorDAO.ARQUIVO.exists() == false) {
				if (JogadorDAO.DIRETORIO.exists() == false) {
					JogadorDAO.DIRETORIO.mkdir();
				}
				JogadorDAO.ARQUIVO.createNewFile();
			}
		} catch (IOException e) {
			throw new JogoDaVelhaExceptions("N�o foi possivel criar um novo arquivo no sistema", e);
		}
	}
}
