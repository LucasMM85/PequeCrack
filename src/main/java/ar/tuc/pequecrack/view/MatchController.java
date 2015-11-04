package ar.tuc.pequecrack.view;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import ar.tuc.pequecrack.Main;
import ar.tuc.pequecrack.db.DBConnect;
import ar.tuc.pequecrack.model.Match;
import ar.tuc.pequecrack.util.Constantes;
import ar.tuc.pequecrack.util.Utils;
import au.com.bytecode.opencsv.CSVWriter;

public class MatchController {
	
	@FXML private TableView<Match> matchTable;
	
	@FXML private TableColumn<Match, LocalDate> dateColumn;
	@FXML private TableColumn<Match, String> seasonColumn;
	@FXML private TableColumn<Match, Number> dayColumn;
	@FXML private TableColumn<Match, String> homeColumn;
	@FXML private TableColumn<Match, String> awayColumn;
	@FXML private TableColumn<Match, String> placeColumn;
	@FXML private TableColumn<Match, String> timeColumn;
	@FXML private TableColumn<Match, String> scoreColumn;
	
	@FXML private ComboBox<String> leagueCombo;
	@FXML private DatePicker dateField;
	@FXML private ComboBox<String> seasonField;
	@FXML private Spinner<Integer> dayField;
	@FXML private ComboBox<String> homeField;
	@FXML private ComboBox<String> awayField;
	@FXML private TextField placeField;
	@FXML private TextField timeField;
	@FXML private TextField scoreField;
	@FXML private Button exportButton;

	private Main mainApp;
	private DBConnect conn;
	
	public MatchController(){}
	
	@FXML 
	private void initialize(){
		dateColumn.setCellValueFactory(cellData -> cellData.getValue().fechaProperty());
		seasonColumn.setCellValueFactory(cellData -> cellData.getValue().temporadaProperty());
		dayColumn.setCellValueFactory(cellData -> cellData.getValue().jornadaProperty());
		homeColumn.setCellValueFactory(cellData -> cellData.getValue().equipolocalProperty());
		awayColumn.setCellValueFactory(cellData -> cellData.getValue().equipovisitanteProperty());
		placeColumn.setCellValueFactory(cellData -> cellData.getValue().lugarProperty());
		timeColumn.setCellValueFactory(cellData -> cellData.getValue().horaComienzoProperty());
		scoreColumn.setCellValueFactory(cellData -> cellData.getValue().resultadoProperty());
		//TODO: Agregar validaciones
		try {
			String query = "SELECT id, title FROM pequecra_wpcrk.pcrck_leaguemanager_leagues";
			conn = DBConnect.getDbCon();
			ResultSet result = conn.query(query);
			ArrayList<String> options = new ArrayList<>();
			while(result.next()){
				int id = result.getInt("id");
				String league = result.getString("title");
				options.add(id+" - "+league);
			}
			ObservableList<String> options2 = FXCollections.observableArrayList(options);
			leagueCombo.getItems().clear();
			leagueCombo.setItems(options2);
			
			ArrayList<String> seasons = new ArrayList<>();
			seasons.add("2015");
			seasons.add("2015-2");
			ObservableList<String> seasonsObservable = FXCollections.observableArrayList(seasons);
			seasonField.getItems().clear();
			seasonField.setItems(seasonsObservable);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void handleLeagueCombo(){
		//TODO: Agregar validaciones
		if(leagueCombo.getSelectionModel().getSelectedIndex() >= 0){
			try {
				conn = DBConnect.getDbCon();
				String[] league = leagueCombo.getSelectionModel().getSelectedItem().split("-");
				String query = "SELECT distinct title FROM pequecra_wpcrk.pcrck_leaguemanager_teams where league_id = "+league[0];
				ResultSet result = conn.query(query);
				ArrayList<String> options = new ArrayList<>();
				while(result.next()){
					options.add(result.getString("title"));
				}
				ObservableList<String> options2 = FXCollections.observableArrayList(options);
				homeField.getItems().clear();
				homeField.setItems(options2);
				awayField.getItems().clear();
				awayField.setItems(options2);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	@FXML
	private void handleAddButton(){
		if(isValidForm()){
			checkOptionalFields();
			LocalDate fecha = dateField.getValue();
			String temporada = seasonField.getSelectionModel().getSelectedItem();
			Integer jornada = Integer.parseInt(dayField.getValue().toString());
			String equipoLocal = homeField.getSelectionModel().getSelectedItem();
			String equipoVisitante = awayField.getSelectionModel().getSelectedItem();
			String lugar = placeField.getText();
			String hora = timeField.getText();
			String resultado = scoreField.getText();
			Match partido = new Match(temporada, jornada, equipoLocal, equipoVisitante, fecha, lugar, hora, resultado);
			mainApp.getMatchData().add(partido);
			placeField.setText("");
			timeField.setText("");
			exportButton.setDisable(false);
		}
	}
	
	@FXML
	private void handleExportButton(){
		ObservableList<Match> matches = matchTable.getItems();
		CSVWriter writer = null;
		try {
			FileChooser fileChooser = new FileChooser();
			FileChooser.ExtensionFilter extFiler = new FileChooser.ExtensionFilter("Archivos CSV (*.csv)", "*.csv");
			fileChooser.getExtensionFilters().add(extFiler);
			
			File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());
			if(file != null){
				OutputStreamWriter os = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
				writer = new CSVWriter(os, '\t', CSVWriter.NO_QUOTE_CHARACTER);
				writer.writeNext(Constantes.MATCH_CSV_HEADER);
				String[] line = new String[Constantes.MATCH_CSV_HEADER.length];
				for(Match match : matches){
					line[0] = String.valueOf(match.getFecha());
					line[1] = String.valueOf(match.getTemporada());
					line[2] = String.valueOf(match.getJornada());
					line[3] = match.getEquipolocal();
					line[4] = match.getEquipovisitante();
					line[5] = match.getLugar();
					line[6] = match.getHoraComienzo();
					line[7] = "";
					line[8] = "";
					line[9] = "";
					line[10] = "";
					line[11] = "";
					writer.writeNext(line);
				}
				writer.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(writer != null){
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void clearFields(){
		leagueCombo.getSelectionModel().select(-1);;
		dateField.setValue(null);
		seasonField.getSelectionModel().select(-1);;
		homeField.getSelectionModel().select(-1);;
		awayField.getSelectionModel().select(-1);;
		placeField.setText(null);
		timeField.setText(null);
		scoreField.setText(null);
	}
	
	private boolean isValidForm(){
		String errorMsg = "";
		if(leagueCombo.getSelectionModel().getSelectedIndex() < 0)
			errorMsg = "Por favor, seleccione una liga";
		else if(dateField.getValue() == null)
			errorMsg = "Por favor, elija la fecha";
		else if(seasonField.getSelectionModel().getSelectedIndex() < 0)
			errorMsg = "Por favor, seleccione la temporada";
		else if(homeField.getSelectionModel().getSelectedIndex() < 0)
			errorMsg = "Por favor, seleccione el equipo local";
		else if(awayField.getSelectionModel().getSelectedIndex() < 0)
			errorMsg = "Por favor, seleccione el equipo visitante";
		else if(awayField.getSelectionModel().getSelectedItem().equals(homeField.getSelectionModel().getSelectedItem()))
			errorMsg = "Equipo local y visitante no pueden ser iguales";
		
		if(errorMsg.length() == 0)
			return true;
		else {
			Utils.generateAlert(AlertType.ERROR, "ERROR!", null, errorMsg).showAndWait();
			return false;
		}
	}
	
	private void checkOptionalFields(){
		if(placeField.getText() == null || placeField.getText().equals(""))
			placeField.setText(Constantes.UNSPECIFIED);
		if(timeField.getText() == null || timeField.getText().equals(""))
			timeField.setText(Constantes.DEFAULT_START_TIME);
	}
	
	public void setMainApp(Main mainApp){
		this.mainApp = mainApp;
		matchTable.setItems(mainApp.getMatchData());	
	}
	
}
