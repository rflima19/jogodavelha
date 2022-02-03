package lima.jogodavelha.view;

import java.io.IOException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import lima.jogodavelha.utils.AlertJavaFXUtils;
import lima.jogodavelha.utils.CadastroJogadorService;

public class CadastroJogadorJavaFXController {

	@FXML
	private TextField txtfNomeJogador;
	@FXML
	private Button btnCadastrar;

	private CadastroJogadorService cadastroJogadorService;

	public CadastroJogadorJavaFXController() {
		super();
	}

	public void initialize() {
		this.btnCadastrar.disableProperty().bind(this.txtfNomeJogador.textProperty().isEmpty());
	}

	@FXML
	public void cadastrar(ActionEvent event) {
		String nome = this.txtfNomeJogador.getText();

		this.cadastroJogadorService = new CadastroJogadorService(nome);
		this.cadastroJogadorService.exceptionProperty().addListener(new ChangeListener<Throwable>() {
			@Override
			public void changed(ObservableValue<? extends Throwable> observable, Throwable oldValue,
					Throwable newValue) {
				AlertJavaFXUtils.alert(AlertType.ERROR, "Ocorreu um erro", newValue.getMessage());
			}
		});
		this.cadastroJogadorService.start();

		this.txtfNomeJogador.setText(null);
	}

	@FXML
	public void voltar(ActionEvent event) {
		try {
			JogoDaVelhaJavaFXView.setTelaMenu();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
