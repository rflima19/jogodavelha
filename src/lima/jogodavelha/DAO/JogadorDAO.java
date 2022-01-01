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
import java.util.function.BiPredicate;
import java.util.function.Function;

import lima.jogodavelha.exceptions.DAOException;
import lima.jogodavelha.model.Jogador;

public class JogadorDAO implements JogoDaVelhaDAO {

	public static final String USE_HOME = System.getProperty("user.home");
	public static final File DIRETORIO = new File(JogadorDAO.USE_HOME + File.separator + "files" + File.separator);
	public static final File ARQUIVO = new File(JogadorDAO.DIRETORIO, "jogo.txt");

	public synchronized void cadastarJogador(Jogador jogador) throws DAOException {
		try {
			this.criarBaseDados();
		} catch (IOException e) {
			throw new DAOException("Não foi possivel criar um novo arquivo no sistema", e);
		}

		try (Writer out = new FileWriter(JogadorDAO.ARQUIVO, true); PrintWriter print = new PrintWriter(out)) {
			String nome = (jogador.getNome().length() > 20) ? jogador.getNome().substring(0, 20) : jogador.getNome();
			String parse = nome + ";" + jogador.getQuantidadeVitorias() + ";" + jogador.getQuantidadeDerrotas() + ";"
					+ jogador.getQuantidadeEmpates(); // nome_jogador;num_vitorias;num_derrotas;num_empates
			print.println(parse);
		} catch (IOException e) {
			throw new DAOException("Não foi possivel gravar os dados do jogador no arquivo", e);
		}
	}

	@Override
	public synchronized List<Jogador> listarJogadores() throws DAOException {
		List<Jogador> lista = new ArrayList<>();

		try {
			boolean isCriouArquivo = this.criarBaseDados();
			if (isCriouArquivo == true) {
				return lista; // criou a base de dados vazia, retorna lista vazia
			}
		} catch (IOException e) {
			throw new DAOException("Não foi possivel criar um novo arquivo no sistema", e);
		}

		try (Reader in = new FileReader(JogadorDAO.ARQUIVO); BufferedReader buffer = new BufferedReader(in)) {
			String line = null;
			String[] tokens = null;
			while ((line = buffer.readLine()) != null) {
				tokens = line.split(";");
				lista.add(new Jogador(tokens[0], Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]),
						Integer.parseInt(tokens[3])));
			}
		} catch (IOException e) {
			throw new DAOException("Não foi possível ler a base de dados", e);
		}
		return lista;
	}

	@Override
	public synchronized Jogador pesquisarJogador(String nome) throws DAOException {
		try {
			boolean isCriouArquivo = this.criarBaseDados();
			if (isCriouArquivo == true) {
				return null; // criou a base de dados vazia
			}
		} catch (IOException e) {
			throw new DAOException("Não foi possivel criar um novo arquivo no sistema", e);
		}

		Jogador jogador = null;
		try (Reader in = new FileReader(JogadorDAO.ARQUIVO); BufferedReader buffer = new BufferedReader(in)) {
			String line = null;
			String[] tokens = null;
			String n = null;
			while ((line = buffer.readLine()) != null) {
				tokens = line.split(";");
				n = tokens[0];
				if (n.equals(nome) == true) {
					jogador = new Jogador(n, Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]),
							Integer.parseInt(tokens[3]));
					break;
				}
			}
		} catch (IOException e) {
			throw new DAOException("Não foi possível ler a base de dados", e);
		}
		return jogador;
	}

	@Override
	public void salvarPontuacaoVencedor(Jogador jogador) throws DAOException {
		if (jogador == null) {
			throw new IllegalArgumentException("Argumento jogador nulo");
		}
		this.salvarPontuacao((String s1, String s2) -> {
					return s1.equals(s2);
				}, 
				(String[] array) -> {
					int num = Integer.parseInt(array[1]);
					++num;
					String str = array[0] + ";" + num + ";" + array[2] + ";" + array[3];
					return str;
				}, 
				jogador);
	}

	@Override
	public void salvarPontuacaoDerrotado(Jogador jogador) throws DAOException {
		if (jogador == null) {
			throw new IllegalArgumentException("Argumento jogador nulo");
		}
		
		this.salvarPontuacao((String s1, String s2) -> {
					return s1.equals(s2);
				}, 
				(String[] array) -> {
					int num = Integer.parseInt(array[2]);
					++num;
					String str = array[0] + ";" + array[1] + ";" + num + ";" + array[3];
					return str;
				}, 
				jogador);
	}

	@Override
	public void salvarPontuacaoEmpate(Jogador jogador1, Jogador jogador2) throws DAOException {
		if (jogador1 == null) {
			throw new IllegalArgumentException("Argumento jogador1 nulo");
		} else if (jogador2 == null) {
			throw new IllegalArgumentException("Argumento jogador2 nulo");
		}
		
		this.salvarPontuacao((String s1, String s2) -> {
					return s1.equals(s2);
				}, 
				(String[] array) -> {
					int num = Integer.parseInt(array[3]);
					++num;
					String str = array[0] + ";" + array[1] + ";" + array[2] + ";" + num;
					return str;
				}, 
				jogador1, jogador2);
	}
	
	private synchronized void salvarPontuacao(BiPredicate<String, String> predicate, Function<String[], String> function, Jogador... jogadores) throws DAOException {
		try {
			this.criarBaseDados();
		} catch (IOException e) {
			throw new DAOException("Não foi possivel criar um novo arquivo no sistema", e);
		}
		if (JogadorDAO.ARQUIVO.length() == 0) {
			throw new DAOException("Base de dados vazia");
		}
		File arquivoTemporario = new File(JogadorDAO.DIRETORIO, "jogo_temp.txt");
		try {
			arquivoTemporario.createNewFile();
		} catch (IOException e) {
			throw new DAOException("Não foi possivel criar o arquivo temporário", e);
		}
		try {
			try (Reader in = new FileReader(JogadorDAO.ARQUIVO);
					BufferedReader buffer = new BufferedReader(in);
					Writer out = new FileWriter(arquivoTemporario);
					PrintWriter print = new PrintWriter(out)) {
				String line = null;
				String[] tokens = null;
				String nome = null;
				String str = null;
				while ((line = buffer.readLine()) != null) {
					tokens = line.split(";");
					nome = tokens[0];
					for (int i = 0; i < jogadores.length; i++) {
						if (predicate.test(nome, jogadores[i].getNome()) == true) {
							str = function.apply(tokens);
						} else {
							str = tokens[0] + ";" + tokens[1] + ";" + tokens[2] + ";" + tokens[3];
						}
						print.println(str);
					}	
				}
			} catch (IOException e) {
				throw new DAOException("Não foi possível ler a base de dados", e);
			}

			try {
				JogadorDAO.this.copiarArquivo(arquivoTemporario, JogadorDAO.ARQUIVO);
			} catch (IOException e) {
				throw new DAOException("Não foi possível restaurar a base de dados", e);
			}
		} finally {
			if (arquivoTemporario.exists() == true) {
				arquivoTemporario.delete();
			}
		}
	}

	private synchronized boolean criarBaseDados() throws IOException {
		boolean result = false;
		if (JogadorDAO.ARQUIVO.exists() == false) {
			if (JogadorDAO.DIRETORIO.exists() == false) {
				JogadorDAO.DIRETORIO.mkdir();
			}
			JogadorDAO.ARQUIVO.createNewFile();
			result = true;
		}
		return result;
	}
	
	private synchronized void copiarArquivo(File fonte, File destino) throws IOException {
		try (Reader in = new FileReader(fonte);
				BufferedReader buffer = new BufferedReader(in);
				Writer out = new FileWriter(destino);
				PrintWriter print = new PrintWriter(out)) {
			String line = null;
			while ((line = buffer.readLine()) != null) {
				print.println(line);
			}
		}
	}
}
