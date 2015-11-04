package ar.tuc.pequecrack;

import java.io.IOException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ar.tuc.pequecrack.model.Match;
import ar.tuc.pequecrack.view.MatchController;

public class Main extends Application {
	
	private Stage primaryStage;
	private BorderPane rootLayout;
	private ObservableList<Match> matchData = FXCollections.observableArrayList();
	
	public ObservableList<Match> getMatchData(){
		return matchData;
	}
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Pequeño Crack");
		
		initRootLayout();
		showMatches();
		
	}
	
	public void initRootLayout(){
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/RootLayout.fxml"));
			rootLayout = loader.load();
			
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void showMatches(){
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/Partidos.fxml"));
			AnchorPane matches = loader.load();
			
			rootLayout.setCenter(matches);
			
			MatchController controller = loader.getController();
			controller.setMainApp(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Stage getPrimaryStage(){
		return primaryStage;
	}
	

	public static void main(String[] args) {
		launch(args);
	}
	
	
}
