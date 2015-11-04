package ar.tuc.pequecrack.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Utils {
	
	public static Alert generateAlert(AlertType type, String title, String header, String text){
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(text);
		return alert;
	}

}
