package lima.jogodavelha.view;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class JogoDaVelhaJavaFXView extends Application {
	
	private static Stage primaryStage;
	
	public void iniciar(String... args) {
		Application.launch(args);
	}
	
	@Override
	public void init() throws Exception {
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		JogoDaVelhaJavaFXView.primaryStage = primaryStage;
		JogoDaVelhaJavaFXView.primaryStage.setResizable(false);
		JogoDaVelhaJavaFXView.primaryStage.getIcons().add(new Image(this.getClass().getResource("../resources/icon.png").toExternalForm()));
		
		JogoDaVelhaJavaFXView.setTelaMenu();
		JogoDaVelhaJavaFXView.primaryStage.show();
	}
	
	private static void dimensionarStage(double width, double height) {
		Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
		//double x = bounds.getMinX() + (bounds.getWidth() - width) * 0.3;
		//double y = bounds.getMinY() + (bounds.getHeight() - height) * 0.7;
		double x = (bounds.getWidth() - width) / 2.0;
		double y = (bounds.getHeight() - height) / 2.0;
		JogoDaVelhaJavaFXView.primaryStage.setX(x);
		JogoDaVelhaJavaFXView.primaryStage.setY(y);
	}
	
	public static void setTelaMenu() throws IOException {
		JogoDaVelhaJavaFXView.primaryStage.setTitle("Jogo da Velha");
		URL url = new URL("file:src/lima/jogodavelha/resources/TelaMenuJogo.fxml");
		Parent root = FXMLLoader.load(url);
		
		Scene scene = new Scene(root, 400, 400);
		url = new URL("file:src/lima/jogodavelha/resources/stylesDefault.css");
		scene.getStylesheets().add(url.toExternalForm());
		JogoDaVelhaJavaFXView.primaryStage.setScene(scene);
		JogoDaVelhaJavaFXView.dimensionarStage(400, 400);
	}
	
	public static void setTelaCadastrarJogador() throws IOException {
		JogoDaVelhaJavaFXView.primaryStage.setTitle("Cadastrar Jogador");
		URL url = new URL("file:src/lima/jogodavelha/resources/TelaCadastroJogador.fxml");
		Parent root = FXMLLoader.load(url);
		Scene scene = new Scene(root, 360, 200);
		url = new URL("file:src/lima/jogodavelha/resources/stylesDefault.css");
		scene.getStylesheets().add(url.toExternalForm());
		url = new URL("file:src/lima/jogodavelha/resources/stylesCadastroJogador.css");
		scene.getStylesheets().add(url.toExternalForm());
        JogoDaVelhaJavaFXView.primaryStage.setScene(scene);
        JogoDaVelhaJavaFXView.dimensionarStage(360, 200);
	}
	
	public static void setRankingJogadores() throws IOException {
		JogoDaVelhaJavaFXView.primaryStage.setTitle("Ranking Jogadores");
		URL url = new URL("file:src/lima/jogodavelha/resources/TelaRankingJogadores.fxml");
		Parent root = FXMLLoader.load(url);
		Scene scene = new Scene(root, 700, 400);
		url = new URL("file:src/lima/jogodavelha/resources/stylesDefault.css");
		scene.getStylesheets().add(url.toExternalForm());
		url = new URL("file:src/lima/jogodavelha/resources/stylesRankingJogadores.css");
		scene.getStylesheets().add(url.toExternalForm());
        JogoDaVelhaJavaFXView.primaryStage.setScene(scene);
        JogoDaVelhaJavaFXView.dimensionarStage(700, 400);
	}
	
	public static void setJogo() throws IOException {
		JogoDaVelhaJavaFXView.primaryStage.setTitle("Ranking Jogadores");
		URL url = new URL("file:src/lima/jogodavelha/resources/Jogo.fxml");
		Parent root = FXMLLoader.load(url);
		Scene scene = new Scene(root, 600, 660);
		url = new URL("file:src/lima/jogodavelha/resources/stylesDefault.css");
		scene.getStylesheets().add(url.toExternalForm());
		url = new URL("file:src/lima/jogodavelha/resources/stylesJogo.css");
		scene.getStylesheets().add(url.toExternalForm());
        JogoDaVelhaJavaFXView.primaryStage.setScene(scene);
        JogoDaVelhaJavaFXView.dimensionarStage(600, 660);
	}
}
