package lima.jogodavelha.view;

import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class JogoDaVelhaJavaFXController {
	
	public void initialize() {
		
	}

	@FXML
	public void cadastrarJogador(ActionEvent event) {
		try {
			JogoDaVelhaJavaFXView.setTelaCadastrarJogador();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void exibirRankingJogadores(ActionEvent event) {
		try {
			JogoDaVelhaJavaFXView.setRankingJogadores();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void jogar(ActionEvent event) {
		try {
			JogoDaVelhaJavaFXView.setJogo();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void sair(ActionEvent event) {
		Platform.exit();
	}
}
