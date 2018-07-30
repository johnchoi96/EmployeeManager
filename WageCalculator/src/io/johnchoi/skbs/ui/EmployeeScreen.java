/**
 * 
 */
package io.johnchoi.skbs.ui;

import java.io.IOException;

import io.johnchoi.skbs.manager.WageCalculatorManager;
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
 * @since 07302018
 */
public class EmployeeScreen {
	
	private WageCalculatorManager wcm;
	private Employee employee;
	@FXML private Label first;
	@FXML private Label last;
	@FXML private Label payrate;
	@FXML private TextField hours;
	@FXML private TextField minutes;
	@FXML private Label paycheck;
	@FXML private Label withTax;

	/**
	 * Constructs this employee screen.
	 * 
	 * @param wcm manager
	 * @param e employee to show
	 */
	public void initialize(WageCalculatorManager wcm, Employee e) {
		this.wcm = wcm;
		employee = e;
		first.setText(e.getFirst());
		last.setText(employee.getLast());
		payrate.setText(Double.toString(employee.getPayrate()));
		paycheck.setText("0.00");
		withTax.setText("0.00");
		hours.requestFocus();
	}
	
	/**
	 * Defines the behavior of calculate button.
	 */
	@FXML
	public void calculateButton() {
		if (hours.getText().equals("") || minutes.getText().equals("")) {
			AlertBox.display("Error", "You must enter a value for hour and minute");
			return;
		}
		double numericHour = Double.parseDouble(hours.getText());
		double numericMinute = Double.parseDouble(minutes.getText()); //TODO calculate time in decimal
		paycheck.setText(String.format("%.2f", Double.toString(employee.calculateWage(Double.parseDouble(hours.getText())))));
		withTax.setText(String.format("%.2f", wcm.calculatePaycheckWithTax(employee)));
	}
	
	/**
	 * Defines the scene change button.
	 * @throws IOException 
	 */
	@FXML
	public void backToMain(ActionEvent e) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/io/johnchoi/skbs/ui/main.fxml"));
		Parent mainViewParent = loader.load();
		Scene employeeViewScene = new Scene(mainViewParent);
		
		WageCalculatorControllerUI controller = loader.getController();
		controller.initialize();
		
		Stage window = (Stage) ((Node)e.getSource()).getScene().getWindow();
		
		window.setScene(employeeViewScene);
	}
}
