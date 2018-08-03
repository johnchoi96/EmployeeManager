package io.jnschois.johnchoi.ui;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * Initializes GUI for Employee Manager.
 * 
 * @author John Choi
 * @since 08022018
 * @version 2.0
 */
public class EmployeeManagerUI extends Application {
	
	/**
	 * Initializes GUI.
	 * 
	 * @param primaryStage main stage
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/io/jnschois/johnchoi/ui/main.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root, 594, 581);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			EmployeeManagerControllerUI controller = loader.getController();
			controller.initialize();
			primaryStage.setTitle("Employee Manager - Main Menu");
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.centerOnScreen();
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Main method for this software.
	 * Launches GUI.
	 * 
	 * @param args command-line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
