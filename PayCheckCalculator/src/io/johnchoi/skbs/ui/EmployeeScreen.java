/**
 * 
 */
package io.johnchoi.skbs.ui;

import java.io.IOException;

import io.johnchoi.skbs.manager.EmployeeManager;
import io.johnchoi.skbs.objects.Employee;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller for employee screen.
 * 
 * @author John Choi
 * @since 08012018
 */
public class EmployeeScreen {
	
	private final double TAX_RATE = 0.0765;
	private EmployeeManager wcm;
	private Employee employee;
	@FXML private Label first;
	@FXML private Label last;
	@FXML private Label payrate;
	@FXML private TextField hours;
	@FXML private TextField minutes;
	@FXML private Label paycheck;
	@FXML private Label withoutTax;

	/**
	 * Constructs this employee screen.
	 * 
	 * @param wcm manager
	 * @param e employee to show
	 */
	public void initialize(EmployeeManager wcm, Employee e) {
		this.wcm = wcm;
		employee = e;
		first.setText(e.getFirst());
		last.setText(employee.getLast());
		payrate.setText(Double.toString(employee.getPayrate()));
		paycheck.setText("0.00");
//		withoutTax = new Label("0.00");
		withoutTax.setText("0.00");
		hours.setText("");
		minutes.setText("0");
		hours.requestFocus();
	}
	
	/**
	 * Defines the behavior of calculate button.
	 */
	@FXML
	public void calculateButton() {
		if (hours.getText().equals("") || minutes.getText().equals("")) {
			AlertBox.display("Error", "Input must be a number");
			return;
		}
		int numericHour = 0;
		int numericMinute = 0;
		try {
			numericHour = Integer.parseInt(hours.getText());
			numericMinute = Integer.parseInt(minutes.getText());
		} catch (NumberFormatException e) {
			AlertBox.display("Error", "Input must be a number");
			hours.setText("");
			minutes.setText("0");
			return;
		}
		double convertedTime = calculateTime(numericHour, numericMinute);
		double totalPaycheck = convertedTime * Double.parseDouble(payrate.getText());
		paycheck.setText(String.format("%.2f", totalPaycheck));
		double woTax = 0;
		woTax = totalPaycheck - (totalPaycheck * TAX_RATE) - wcm.getFederalTaxRate() - wcm.getStateTaxRate();
		withoutTax.setText(String.format("%.2f", woTax));
	}
	
	/**
	 * Helper method that takes in hour and minutes to return decimal format of time.
	 * 
	 * @param hours to convert
	 * @param minutes to convert
	 * @return converted time
	 */
	private double calculateTime(int hours, int minutes) {
		return hours + (double) (minutes / 60.0);
	}
	
	/**
	 * Defines the scene change button.
	 * 
	 * @param e action
	 * @throws IOException if there is problem reading file
	 */
	@FXML
	public void backToMain(ActionEvent e) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/io/johnchoi/skbs/ui/main.fxml"));
		Parent mainViewParent = loader.load();
		Scene employeeViewScene = new Scene(mainViewParent);
		
		EmployeeManagerControllerUI controller = loader.getController();
		controller.initialize();
		
		Stage window = (Stage) ((Node)e.getSource()).getScene().getWindow();
		
		window.setScene(employeeViewScene);
	}
}
