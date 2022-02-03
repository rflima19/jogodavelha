package lima.jogodavelha.view;

import java.io.IOException;

import javafx.collections.ObservableList;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import lima.jogodavelha.utils.AlertJavaFXUtils;
import lima.jogodavelha.utils.AnalisaJogadaService;
import lima.jogodavelha.utils.AnalisaTabuleiroCompletoService;
import lima.jogodavelha.utils.BuscaJogadorRodadaService;
import lima.jogodavelha.utils.BuscaJogadorService;
import lima.jogodavelha.utils.BuscaJogadorVencedorService;
import lima.jogodavelha.utils.BuscaTabuleiroService;
import lima.jogodavelha.utils.CriaNovoJogoService;
import lima.jogodavelha.utils.RegistraJogadaService;
import lima.jogodavelha.utils.SalvaPontuacaoDerrotadoService;
import lima.jogodavelha.utils.SalvaPontuacaoEmpateService;
import lima.jogodavelha.utils.SalvaPontuacaoVencedorService;

public class JogoJavaFXController {

	private final Image img1 = new Image("file:src/lima/jogodavelha/resources/img1.png");
	private final Image img2 = new Image("file:src/lima/jogodavelha/resources/img2.png");
	private final Image img3 = new Image("file:src/lima/jogodavelha/resources/img3.png");

	@FXML
	private GridPane gridPaneJogo;
	@FXML
	private Label lblJogador;
	@FXML
	private Button btnOk;
	@FXML
	private TextField txtNome;
	@FXML
	private BorderPane rootBorderPane;
	@FXML
	private HBox hbBuscarJogador;
	@FXML
	private VBox vbDadosJogadores;
	@FXML
	private Text txtDadosJog1;
	@FXML
	private Text txtDadosJog2;
	@FXML
	private Text txtMensagemJogo;
	@FXML
	private Button btnRestart;

	private BuscaJogadorService buscaJogadorService;
	private BuscaTabuleiroService buscaTabuleiroService;
	private BuscaJogadorRodadaService buscaJogadorRodadaService;
	private RegistraJogadaService registraJogadaService;
	private AnalisaJogadaService analisaJogadaService;
	private AnalisaTabuleiroCompletoService analisaTabuleiroCompletoService;
	private BuscaJogadorVencedorService buscaJogadorVencedorService;
	private SalvaPontuacaoVencedorService salvaPontuacaoVencedorService;
	private SalvaPontuacaoDerrotadoService salvaPontuacaoDerrotadoService;
	private SalvaPontuacaoEmpateService salvaPontuacaoEmpateService;
	private CriaNovoJogoService criaNovoJogoService;

	private String[] jogador1;
	private String[] jogador2;
	private char[][] tabuleiro;
	private String[] jogadorDaRodada;
	private int rodada;
	private int colunaSelecionada;
	private int linhaSelecionada;

	public JogoJavaFXController() {
		super();
		this.jogador1 = null;
		this.jogador2 = null;
		this.buscaTabuleiroService = new BuscaTabuleiroService();
		this.buscaJogadorRodadaService = new BuscaJogadorRodadaService();
		this.analisaJogadaService = new AnalisaJogadaService();
		this.analisaTabuleiroCompletoService = new AnalisaTabuleiroCompletoService();
		this.buscaJogadorVencedorService = new BuscaJogadorVencedorService();
		this.salvaPontuacaoVencedorService = new SalvaPontuacaoVencedorService();
		this.salvaPontuacaoDerrotadoService = new SalvaPontuacaoDerrotadoService();
		this.salvaPontuacaoEmpateService = new SalvaPontuacaoEmpateService();
		this.criaNovoJogoService = new CriaNovoJogoService();
		this.rodada = 0;
	}

	public void initialize() {
		int col = this.gridPaneJogo.getColumnCount();
		int row = this.gridPaneJogo.getRowCount();
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				ImageView imgv = new ImageView(this.img1);
				imgv.setFitHeight(150.0);
				imgv.setFitWidth(150.0);
				imgv.setOnMouseClicked(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						ImageView imagemClicada = (ImageView) event.getSource();
						JogoJavaFXController.this.colunaSelecionada = GridPane.getColumnIndex(imagemClicada) + 1;
						JogoJavaFXController.this.linhaSelecionada = GridPane.getRowIndex(imagemClicada) + 1;
						JogoJavaFXController.this.registraJogadaService = new RegistraJogadaService(
								JogoJavaFXController.this.linhaSelecionada, JogoJavaFXController.this.colunaSelecionada,
								JogoJavaFXController.this.jogadorDaRodada[4]);
						JogoJavaFXController.this.registraJogadaService
								.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
									@Override
									public void handle(WorkerStateEvent event) {
										boolean result = JogoJavaFXController.this.registraJogadaService.getValue();
										if (result == true) {
											JogoJavaFXController.this.analisaJogadaService.restart();
										} else {
											AlertJavaFXUtils.alert(AlertType.INFORMATION, "Jogada Invalida",
													"A sua jogada é invalida");
										}
									}
								});
						JogoJavaFXController.this.registraJogadaService.restart();
					}
				});
				this.gridPaneJogo.add(imgv, j, i);
			}
		}

		this.buscaTabuleiroService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				JogoJavaFXController.this.tabuleiro = JogoJavaFXController.this.buscaTabuleiroService.getValue();
				JogoJavaFXController.this.atualizarTabuleiro();
			}
		});
		this.buscaJogadorRodadaService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				JogoJavaFXController.this.jogadorDaRodada = JogoJavaFXController.this.buscaJogadorRodadaService
						.getValue();
				JogoJavaFXController.this.txtMensagemJogo.setText(JogoJavaFXController.this.rodada
						+ "º rodada: Vez do jogador(a) " + JogoJavaFXController.this.jogadorDaRodada[0] + " jogar");
			}
		});
		this.analisaJogadaService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				boolean result = JogoJavaFXController.this.analisaJogadaService.getValue();
				if (result == true) {
					JogoJavaFXController.this.buscaJogadorVencedorService.restart();
				} else {
					JogoJavaFXController.this.analisaTabuleiroCompletoService.restart();
				}
			}
		});
		this.analisaTabuleiroCompletoService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				boolean result = JogoJavaFXController.this.analisaTabuleiroCompletoService.getValue();
				if (result == true) {
					JogoJavaFXController.this.salvaPontuacaoEmpateService.restart();
					JogoJavaFXController.this.buscaTabuleiroService.restart();
					AlertJavaFXUtils.alert(AlertType.INFORMATION, "Fim de jogo", "Jogo terminou empatado");
					JogoJavaFXController.this.txtMensagemJogo.setText("Jogo terminou empatado");
					JogoJavaFXController.this.vbDadosJogadores.setVisible(false);
				} else {
					JogoJavaFXController.this.jogar();
				}
			}
		});
		this.buscaJogadorVencedorService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				JogoJavaFXController.this.salvaPontuacaoVencedorService.restart();
				JogoJavaFXController.this.salvaPontuacaoDerrotadoService.restart();
				JogoJavaFXController.this.buscaTabuleiroService.restart();
				String[] jogadorVencedor = JogoJavaFXController.this.buscaJogadorVencedorService.getValue();
				AlertJavaFXUtils.alert(AlertType.INFORMATION, "Fim de jogo",
						"Jogador(a) " + jogadorVencedor[0] + " venceu o jogo");
				JogoJavaFXController.this.txtMensagemJogo
						.setText("Jogador(a) " + jogadorVencedor[0] + " venceu o jogo");
				JogoJavaFXController.this.vbDadosJogadores.setVisible(false);
			}
		});

		this.txtNome.requestFocus();
		this.btnRestart.setDisable(true);
		this.gridPaneJogo.setDisable(true);
		this.vbDadosJogadores.setVisible(false);
		this.lblJogador.setText("Jogador 1:");
		this.txtMensagemJogo.setText("Digite o nome dos jogadores");
	}

	@FXML
	public void setJogador(ActionEvent event) {
		String nome = this.txtNome.getText();
		if (this.jogador1 == null) {
			this.buscaJogadorService = new BuscaJogadorService(nome, 1);
			this.buscaJogadorService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
				@Override
				public void handle(WorkerStateEvent event) {
					JogoJavaFXController.this.jogador1 = JogoJavaFXController.this.buscaJogadorService.getValue();
					JogoJavaFXController.this.lblJogador.setText("Jogador 2:");
					JogoJavaFXController.this.txtNome.setText(null);
				}
			});
			this.buscaJogadorService.restart();

		} else if (this.jogador2 == null) {
			if (nome.equalsIgnoreCase(this.jogador1[0])) {
				AlertJavaFXUtils.alert(AlertType.INFORMATION, "Seleção de jogador",
						"Jogador(a) " + nome + " já está selecionado como jogador 1");
				return;
			}
			this.buscaJogadorService = new BuscaJogadorService(nome, 2);
			this.buscaJogadorService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
				@Override
				public void handle(WorkerStateEvent event) {
					JogoJavaFXController.this.jogador2 = JogoJavaFXController.this.buscaJogadorService.getValue();
					String[] j1 = JogoJavaFXController.this.jogador1;
					String dadosJogador1 = "Jogador(a) 1 - " + j1[0] + " [vitórias=" + j1[1] + ", derrotas=" + j1[2]
							+ ", empates=" + j1[3] + "] marca tabuleiro com " + j1[4];
					String[] j2 = JogoJavaFXController.this.jogador2;
					String dadosJogador2 = "Jogador(a) 2 - " + j2[0] + " [vitórias=" + j2[1] + ", derrotas=" + j2[2]
							+ ", empates=" + j2[3] + "] marca tabuleiro com " + j2[4];
					JogoJavaFXController.this.txtDadosJog1.setText(dadosJogador1);
					JogoJavaFXController.this.txtDadosJog2.setText(dadosJogador2);
					JogoJavaFXController.this.hbBuscarJogador.setVisible(false);
					JogoJavaFXController.this.vbDadosJogadores.setVisible(true);
					JogoJavaFXController.this.gridPaneJogo.setDisable(false);
					JogoJavaFXController.this.btnRestart.setDisable(false);
					JogoJavaFXController.this.jogar();
				}
			});
			this.buscaJogadorService.restart();
		}
	}

	private void jogar() {
		this.buscaTabuleiroService.restart();
		this.buscaJogadorRodadaService.restart();
		this.rodada++;
	}

	private void atualizarTabuleiro() {
		ObservableList<Node> oblList = this.gridPaneJogo.getChildren();
		int column = this.tabuleiro.length;
		int row = this.tabuleiro[0].length;
		int aux = 1;
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
				Node node = oblList.get(aux);
				if (node instanceof ImageView) {
					ImageView imageView = (ImageView) node;
					if (this.tabuleiro[i][j] == ' ') {
						imageView.setImage(this.img1);
					} else if (this.tabuleiro[i][j] == 'O') {
						imageView.setImage(this.img3);
					}
					if (this.tabuleiro[i][j] == 'X') {
						imageView.setImage(this.img2);
					}
				}
				aux++;
			}
		}
	}

	private void limparTabuleiro() {
		ObservableList<Node> oblList = this.gridPaneJogo.getChildren();
		oblList.stream().forEach((Node n) -> {
			if (n instanceof ImageView) {
				ImageView imageView = (ImageView) n;
				imageView.setImage(this.img1);
			}
		});
	}

	@FXML
	public void restart(ActionEvent event) {
		this.criaNovoJogoService.restart();
		this.rodada = 0;
		this.jogador1 = null;
		this.jogador2 = null;
		this.tabuleiro = null;
		this.jogadorDaRodada = null;
		this.limparTabuleiro();
		this.vbDadosJogadores.setVisible(false);
		this.hbBuscarJogador.setVisible(true);
		this.btnRestart.setDisable(true);
		this.lblJogador.setText("Jogador 1:");
		this.txtNome.setText(null);
		this.txtMensagemJogo.setText("Digite o nome dos jogadores");
	}

	@FXML
	public void cancelar(ActionEvent event) {
		try {
			this.criaNovoJogoService.restart();
			JogoDaVelhaJavaFXView.setTelaMenu();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
