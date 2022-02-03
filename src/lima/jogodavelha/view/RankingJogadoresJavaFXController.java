package lima.jogodavelha.view;

import java.io.IOException;
import java.util.List;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import lima.jogodavelha.utils.ListarJogadoresService;

public class RankingJogadoresJavaFXController {

	@FXML
	private TableView<String[]> tblJogadores;
	@FXML
	private ProgressBar progressBar;
	private TableColumn<String[], String> tblcolPos;
	private TableColumn<String[], String> tblcolNome;
	private TableColumn<String[], Number> tblcolVitorias;
	private TableColumn<String[], Number> tblcolDerrotas;
	private TableColumn<String[], Number> tblcolEmpates;
	private TableColumn<String[], Number> tblcolJogos;

	private ListarJogadoresService listarJogadoresService;

	public RankingJogadoresJavaFXController() {
		super();
		tblcolPos = new TableColumn<>("Posição");
		tblcolNome = new TableColumn<>("Jogador");
		tblcolVitorias = new TableColumn<>("Vitórias");
		tblcolDerrotas = new TableColumn<>("Derrotas");
		tblcolEmpates = new TableColumn<>("Empates");
		tblcolJogos = new TableColumn<>("Total Jogos");
	}

	public void initialize() {
		this.listarJogadoresService = new ListarJogadoresService();
		this.progressBar.visibleProperty().bind(this.listarJogadoresService.runningProperty());
		this.progressBar.progressProperty().bind(this.listarJogadoresService.progressProperty());

		// this.criarTableCellPersonalizada();

		this.setValoresCelulas();

		this.tblcolPos.setCellFactory(new Callback<TableColumn<String[], String>, TableCell<String[], String>>() {
			@Override
			public TableCell<String[], String> call(TableColumn<String[], String> param) {
				TableCell<String[], String> indexCell = new TableCell<>();
				ReadOnlyObjectProperty<TableRow<String[]>> rowProperty = indexCell.tableRowProperty();
				ObjectBinding<String> rowBinding = Bindings.createObjectBinding(() -> {
					TableRow<String[]> row = rowProperty.get();
					if (row != null) {
						int rowIndex = row.getIndex();
						if (rowIndex < row.getTableView().getItems().size()) {
							++rowIndex;
							return Integer.toString(rowIndex).concat("º").toString();
						}
					}
					return null;
				}, rowProperty);
				indexCell.textProperty().bind(rowBinding);
				return indexCell;
			}
		});

		this.tblJogadores.getColumns().addAll(tblcolPos, tblcolNome, tblcolVitorias, tblcolDerrotas, tblcolEmpates,
				tblcolJogos);

		this.listarJogadoresService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				List<String[]> list = RankingJogadoresJavaFXController.this.listarJogadoresService.getValue();
				ObservableList<String[]> obsList = RankingJogadoresJavaFXController.this.tblJogadores.getItems();
				for (String[] strings : list) {
					obsList.add(strings);
				}
			}
		});
		this.listarJogadoresService.start();
	}

	@FXML
	public void fechar(ActionEvent event) {
		try {
			JogoDaVelhaJavaFXView.setTelaMenu();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void setValoresCelulas() {
		this.tblcolNome.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> param) {
						return new ReadOnlyStringWrapper(param.getValue()[0]);
					}
				});

		this.tblcolVitorias.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<String[], Number>, ObservableValue<Number>>() {
					@Override
					public ObservableValue<Number> call(TableColumn.CellDataFeatures<String[], Number> param) {
						return new ReadOnlyIntegerWrapper(Integer.parseInt(param.getValue()[1]));
					}
				});
		this.tblcolDerrotas.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<String[], Number>, ObservableValue<Number>>() {
					@Override
					public ObservableValue<Number> call(TableColumn.CellDataFeatures<String[], Number> param) {
						return new ReadOnlyIntegerWrapper(Integer.parseInt(param.getValue()[2]));
					}
				});
		this.tblcolEmpates.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<String[], Number>, ObservableValue<Number>>() {
					@Override
					public ObservableValue<Number> call(TableColumn.CellDataFeatures<String[], Number> param) {
						return new ReadOnlyIntegerWrapper(Integer.parseInt(param.getValue()[3]));
					}
				});
		this.tblcolJogos.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<String[], Number>, ObservableValue<Number>>() {
					@Override
					public ObservableValue<Number> call(TableColumn.CellDataFeatures<String[], Number> param) {
						int total = Integer.valueOf(param.getValue()[1]) + Integer.valueOf(param.getValue()[2])
								+ Integer.valueOf(param.getValue()[3]);
						return new ReadOnlyIntegerWrapper(total);
					}
				});
	}

	private void criarTableCellPersonalizada() {
		this.tblcolPos.setCellFactory(new Callback<TableColumn<String[], String>, TableCell<String[], String>>() {
			@Override
			public TableCell<String[], String> call(TableColumn<String[], String> param) {
				TableCell<String[], String> indexCell = new TableCell<>();
				ReadOnlyObjectProperty<TableRow<String[]>> rowProperty = indexCell.tableRowProperty();
				ObjectBinding<String> rowBinding = Bindings.createObjectBinding(() -> {
					TableRow<String[]> row = rowProperty.get();
					if (row != null) {
						int rowIndex = row.getIndex();
						if (rowIndex < row.getTableView().getItems().size()) {
							++rowIndex;
							return Integer.toString(rowIndex).concat("º").toString();
						}
					}
					return null;
				}, rowProperty);
				indexCell.textProperty().bind(rowBinding);
				return indexCell;
			}
		});
		tblcolNome.setCellFactory(new Callback<TableColumn<String[], String>, TableCell<String[], String>>() {
			@Override
			public TableCell<String[], String> call(TableColumn<String[], String> param) {
				TableCell<String[], String> cell = new TableCell<>() {
					@Override
					protected void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						if (empty || item == null) {
							setText(null);
							setGraphic(null);
						} else {
							setText(item.toString());
						}
					};
				};
				return cell;
			}
		});
		tblcolVitorias.setCellFactory(new Callback<TableColumn<String[], Number>, TableCell<String[], Number>>() {
			@Override
			public TableCell<String[], Number> call(TableColumn<String[], Number> param) {
				TableCell<String[], Number> cell = new TableCell<>() {
					@Override
					protected void updateItem(Number item, boolean empty) {
						super.updateItem(item, empty);
						if (empty || item == null) {
							setText(null);
							setGraphic(null);
						} else {
							setText(item.toString());
						}
					};
				};
				return cell;
			}
		});
		tblcolDerrotas.setCellFactory(new Callback<TableColumn<String[], Number>, TableCell<String[], Number>>() {
			@Override
			public TableCell<String[], Number> call(TableColumn<String[], Number> param) {
				TableCell<String[], Number> cell = new TableCell<>() {
					@Override
					protected void updateItem(Number item, boolean empty) {
						super.updateItem(item, empty);
						if (empty || item == null) {
							setText(null);
							setGraphic(null);
						} else {
							setText(item.toString());
						}
					};
				};
				return cell;
			}
		});
		tblcolEmpates.setCellFactory(new Callback<TableColumn<String[], Number>, TableCell<String[], Number>>() {
			@Override
			public TableCell<String[], Number> call(TableColumn<String[], Number> param) {
				TableCell<String[], Number> cell = new TableCell<>() {
					@Override
					protected void updateItem(Number item, boolean empty) {
						super.updateItem(item, empty);
						if (empty || item == null) {
							setText(null);
							setGraphic(null);
						} else {
							setText(item.toString());
						}
					};
				};
				return cell;
			}
		});
		tblcolJogos.setCellFactory(new Callback<TableColumn<String[], Number>, TableCell<String[], Number>>() {
			@Override
			public TableCell<String[], Number> call(TableColumn<String[], Number> param) {
				TableCell<String[], Number> cell = new TableCell<>() {
					@Override
					protected void updateItem(Number item, boolean empty) {
						super.updateItem(item, empty);
						if (empty || item == null) {
							setText(null);
							setGraphic(null);
						} else {
							setText(item.toString());
						}
					};
				};
				return cell;
			}
		});
	}
}
