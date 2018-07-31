/**
 * 
 */
package io.johnchoi.skbs.ui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Shows the alert box.
 * 
 * @author John Choi
 * @since 07312018
 */
public class AlertBox {

	public static void display(String title, String message) {
		Stage window = new Stage();
		
		//requirement: you cant do anything else until you clear the alert box
		window.initModality(Modality.APPLICATION_MODAL); //block any user interaction until this box is closed
		window.setTitle(title);
		window.setMinWidth(350);
		window.setMinHeight(150);
		
		Label label = new Label();
		label.setText(message);
		Button closeButton = new Button("OK");
		closeButton.setPrefWidth(100);
		closeButton.setOnAction(e -> window.close());
		VBox layout = new VBox(10);
		layout.getChildren().addAll(label, closeButton);
		layout.setAlignment(Pos.CENTER);
		window.setResizable(false); //prevents user from resizing the alert box
		
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait(); //will display window and it needs to be closed
	}
}
