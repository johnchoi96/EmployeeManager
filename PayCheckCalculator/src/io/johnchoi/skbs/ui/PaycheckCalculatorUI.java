package io.johnchoi.skbs.ui;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class PaycheckCalculatorUI extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/io/johnchoi/skbs/ui/main.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root, 594, 568);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			PaycheckCalculatorControllerUI controller = loader.getController();
			controller.initialize();
			primaryStage.setTitle("JNS CHOIS - Pay Check Calculator");
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.centerOnScreen();
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}