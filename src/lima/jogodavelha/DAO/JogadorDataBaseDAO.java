package lima.jogodavelha.DAO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import lima.jogodavelha.exceptions.DAOException;
import lima.jogodavelha.model.Jogador;

public class JogadorDataBaseDAO implements JogoDaVelhaDAO {

	private Properties props;
	private String driverClass;
	private String url;
	private String user;
	private String password;

	public JogadorDataBaseDAO() {
		super();
		Path arquivoProps = Paths.get("database.properties");
		try {
			this.props = new Properties();
			this.props.load(Files.newInputStream(arquivoProps, StandardOpenOption.READ));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		this.driverClass = this.props.getProperty("jdbc.driver");
		this.url = this.props.getProperty("jdbc.url");
		this.user = this.props.getProperty("jdbc.user");
		this.password = this.props.getProperty("jdbc.pass");
//		try {
//			Class.forName(this.driverClass);
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//			System.exit(1);
//		}
	}

	@Override
	public void cadastarJogador(Jogador jogador) throws DAOException {
		String sql = "INSERT INTO jogador (nome, qtd_vitorias, qtd_derrotas, qtd_empates) " + 
				"VALUES (?, ?, ?, ?);";
		try (Connection conn = DriverManager.getConnection(this.url, this.user, this.password)) {
			try (PreparedStatement stmt = conn.prepareStatement(sql)) {
				stmt.setString(1, jogador.getNome());
				stmt.setInt(2, jogador.getQuantidadeVitorias());
				stmt.setInt(3, jogador.getQuantidadeDerrotas());
				stmt.setInt(4, jogador.getQuantidadeEmpates());
				stmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("Falha ao cadastrar jogador", e);
		}
	}

	@Override
	public List<Jogador> listarJogadores() throws DAOException {
		List<Jogador> jogadores = new ArrayList<>();
		String sql = "SELECT j.nome, j.qtd_vitorias, j.qtd_derrotas, j.qtd_empates FROM jogador j;";
		try (Connection conn = DriverManager.getConnection(this.url, this.user, this.password)) {
			try (PreparedStatement stmt = conn.prepareStatement(sql)) {
				try (ResultSet rs = stmt.executeQuery()) {
					Jogador j = null;
					while (rs.next() == true) {
						j = new Jogador(rs.getString(1), rs.getInt(2), rs.getInt(3), rs.getInt(4));
						jogadores.add(j);
					}
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Falha ao listar jogadores", e);
		}
		return jogadores;
	}

	@Override
	public Jogador pesquisarJogador(String nome) throws DAOException {
		Jogador jogador = null;
		String sql = "SELECT j.nome, j.qtd_vitorias, j.qtd_derrotas, j.qtd_empates FROM jogador j "
				+ "WHERE j.nome = ?;";
		try (Connection conn = DriverManager.getConnection(this.url, this.user, this.password)) {
			try (PreparedStatement stmt = conn.prepareStatement(sql)) {
				stmt.setString(1, nome);
				try (ResultSet rs = stmt.executeQuery()) {
					if (rs.next() == true) {
						jogador = new Jogador(rs.getString(1), rs.getInt(2), rs.getInt(3), rs.getInt(4));
					}
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Falha ao pesquisar jogador", e);
		}
		return jogador;
	}

	@Override
	public void salvarPontuacaoVencedor(Jogador jogador) throws DAOException {
		String sql = "UPDATE jogador j SET j.qtd_vitorias = ? WHERE j.nome = ?;";
		try (Connection conn = DriverManager.getConnection(this.url, this.user, this.password)) {
			try (PreparedStatement stmt = conn.prepareStatement(sql)) {
				int vit = jogador.getQuantidadeVitorias();
				vit++;
				stmt.setInt(1, vit);
				stmt.setString(2, jogador.getNome());
				stmt.executeUpdate();
			}
		} catch (SQLException e) {
			throw new DAOException("Falha ao salvar pontuação do jogador", e);
		}
	}

	@Override
	public void salvarPontuacaoDerrotado(Jogador jogador) throws DAOException {
		String sql = "UPDATE jogador j SET j.qtd_derrotas = ? WHERE j.nome = ?;";
		try (Connection conn = DriverManager.getConnection(this.url, this.user, this.password)) {
			try (PreparedStatement stmt = conn.prepareStatement(sql)) {
				int der = jogador.getQuantidadeDerrotas();
				der++;
				stmt.setInt(1, der);
				stmt.setString(2, jogador.getNome());
				stmt.executeUpdate();
			}
		} catch (SQLException e) {
			throw new DAOException("Falha ao salvar pontuação do jogador", e);
		}
	}

	@Override
	public void salvarPontuacaoEmpate(Jogador jogador1, Jogador jogador2) throws DAOException {
		String sql = "UPDATE jogador j SET j.qtd_empates = ? WHERE j.nome = ?;";
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(this.url, this.user, this.password);
			try (PreparedStatement stmt = conn.prepareStatement(sql)) {
				conn.setAutoCommit(false);
				
				int emp = jogador1.getQuantidadeEmpates();
				emp++;
				stmt.setInt(1, emp);
				stmt.setString(2, jogador1.getNome());
				stmt.executeUpdate();
				
				emp = jogador2.getQuantidadeEmpates();
				emp++;
				stmt.setInt(1, emp);
				stmt.setString(2, jogador2.getNome());
				stmt.executeUpdate();
				
				conn.commit();
				conn.setAutoCommit(true);
			}
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.addSuppressed(e);
				throw new DAOException("Falha ao salvar pontuação do jogador", e1);
			}
			throw new DAOException("Falha ao salvar pontuação do jogador", e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					throw new DAOException("Falha ao fechar conexão", e);
				}
			}
		}
	}
}
