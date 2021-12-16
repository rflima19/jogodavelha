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

import lima.jogodavelha.exceptions.DAOException;
import lima.jogodavelha.exceptions.JogoDaVelhaExceptions;
import lima.jogodavelha.model.Jogador;

public class JogadorDAO implements JogoDaVelhaDAO {
	
	public enum SalvarPontuacao {
		VENCEDOR, DERROTADO;
	}

	public static final String USE_HOME = System.getProperty("user.home");
	public static final File DIRETORIO = new File(JogadorDAO.USE_HOME + File.separator + "files" + File.separator);
	public static final File ARQUIVO = new File(JogadorDAO.DIRETORIO, "jogo.txt");

	public synchronized void cadastarJogador(Jogador jogador) throws DAOException {
		try {
			this.criarBaseDados();
		} catch (IOException e) {
			throw new DAOException("Não foi possivel criar um novo arquivo no sistema", e);
		}

		try (Writer out = new FileWriter(JogadorDAO.ARQUIVO, true); 
				PrintWriter print = new PrintWriter(out)) {
			String nome = (jogador.getNome().length() > 20) ? 
					jogador.getNome().substring(0, 20) : jogador.getNome();
			String parse = nome + ";" + 
					jogador.getQuantidadeVitorias() + ";" + 
					jogador.getQuantidadeDerrotas() + ";" +
					jogador.getQuantidadeEmpates(); // nome_jogador;num_vitorias;num_derrotas;num_empates
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

		try (Reader in = new FileReader(JogadorDAO.ARQUIVO); 
				BufferedReader buffer = new BufferedReader(in)) {
			String line = null;
			String[] tokens = null;
			while ((line = buffer.readLine()) != null) {
				tokens = line.split(";");
				lista.add(new Jogador(tokens[0], Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3])));
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
		try (Reader in = new FileReader(JogadorDAO.ARQUIVO); 
				BufferedReader buffer = new BufferedReader(in)) {
			String line = null;
			String[] tokens = null;
			String n = null;
			while ((line = buffer.readLine()) != null) {
				tokens = line.split(";");
				n = tokens[0];
				if (n.equals(nome) == true) {
					jogador = new Jogador(n, Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]));
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
		this.salvarPontuacao(jogador, JogadorDAO.SalvarPontuacao.VENCEDOR);
	}
	
	@Override
	public void salvarPontuacaoDerrotado(Jogador jogador) throws DAOException {
		this.salvarPontuacao(jogador, JogadorDAO.SalvarPontuacao.DERROTADO);
	}
	
	@Override
	public void salvarPontuacaoEmpate(Jogador jogador1, Jogador jogador2) throws DAOException {
		this.salvarPontuacao(jogador1, jogador2);
	}
	
	private void salvarPontuacao(Jogador jogador, SalvarPontuacao sp) throws DAOException {
		if (jogador == null) {
			throw new IllegalArgumentException("Argumento jogador nulo");
		}
		
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
				Integer num = null;
				String str = null;
				while ((line = buffer.readLine()) != null) {
					tokens = line.split(";");
					nome = tokens[0];
					str = tokens[0] + ";" + tokens[1] + ";" + tokens[2] + ";" + tokens[3];
					if (JogadorDAO.SalvarPontuacao.VENCEDOR.equals(sp) == true) {
						if (nome.equals(jogador.getNome()) == true) {
							num = Integer.parseInt(tokens[1]);
							++num;
							str = tokens[0] + ";" + num + ";" + tokens[2] + ";" + tokens[3];
						}
					} else if (JogadorDAO.SalvarPontuacao.DERROTADO.equals(sp) == true) {
						if (nome.equals(jogador.getNome()) == true) {
							num = Integer.parseInt(tokens[2]);
							++num;
							str = tokens[0] + ";" + tokens[1] + ";" + num + ";" + tokens[3];
						}
					}
					print.println(str);
				}
			} catch (IOException e) {
				throw new DAOException("Não foi possível ler a base de dados", e);
			}
			
			try (Reader in = new FileReader(arquivoTemporario); 
					BufferedReader buffer = new BufferedReader(in);
					Writer out = new FileWriter(JogadorDAO.ARQUIVO); 
					PrintWriter print = new PrintWriter(out)) {
				String line = null;
				while ((line = buffer.readLine()) != null) {
					print.println(line);
				}
			} catch (IOException e) {
				throw new DAOException("Não foi possível restaurar a base de dados", e);
			}
		} finally {
			if (arquivoTemporario.exists() == true) {
				arquivoTemporario.delete();
			}
		}
	}
	
	private void salvarPontuacao(Jogador jogador1, Jogador jogador2) throws DAOException {
		if (jogador1 == null) {
			throw new IllegalArgumentException("Argumento jogador1 nulo");
		} else if (jogador2 == null) {
			throw new IllegalArgumentException("Argumento jogador2 nulo");
		}
		
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
				Integer num = null;
				String str = null;
				while ((line = buffer.readLine()) != null) {
					tokens = line.split(";");
					nome = tokens[0];
					if ((nome.equals(jogador1.getNome()) == true) ||
							(nome.equals(jogador2.getNome()) == true)) {
						num = Integer.parseInt(tokens[3]);
						++num;
						str = tokens[0] + ";" +  tokens[1] + ";" + tokens[2] + ";" + num;
					} else {
						str = tokens[0] + ";" + tokens[1] + ";" + tokens[2] + ";" + tokens[3];
					}
					print.println(str);
				}
			} catch (IOException e) {
				throw new DAOException("Não foi possível ler a base de dados", e);
			}
			
			try (Reader in = new FileReader(arquivoTemporario); 
					BufferedReader buffer = new BufferedReader(in);
					Writer out = new FileWriter(JogadorDAO.ARQUIVO); 
					PrintWriter print = new PrintWriter(out)) {
				String line = null;
				while ((line = buffer.readLine()) != null) {
					print.println(line);
				}
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
}
