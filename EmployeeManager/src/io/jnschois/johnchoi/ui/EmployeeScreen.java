/**
 * 
 */
package io.jnschois.johnchoi.ui;

import java.io.IOException;

import io.jnschois.johnchoi.manager.EmployeeManager;
import io.jnschois.johnchoi.manager.exceptions.DuplicateEmployeeException;
import io.jnschois.johnchoi.objects.Employee;
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
	@FXML private TextField first;
	@FXML private TextField middle;
	@FXML private TextField last;
	@FXML private TextField payrate;
	@FXML private TextField hours;
	@FXML private TextField minutes;
	@FXML private Label paycheck;
	@FXML private Label withoutTax;
	private String initFirst;
	private String initMiddle;
	private String initLast;
	private double initPay;

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
		String middleName = e.getMiddle();
		if (middleName.equals("null")) {
			middle.setText("");
		} else {
			middle.setText(middleName);
		}
		last.setText(employee.getLast());
		payrate.setText(Double.toString(employee.getPayrate()));
		paycheck.setText("0.00");
		withoutTax.setText("0.00");
		hours.setText("");
		minutes.setText("0");
		initFirst = first.getText();
		initMiddle = middle.getText();
		initLast = last.getText();
		initPay = employee.getPayrate();
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
		loader.setLocation(getClass().getResource("/io/jnschois/johnchoi/ui/main.fxml"));
		Parent mainViewParent = loader.load();
		Scene employeeViewScene = new Scene(mainViewParent);
		
		EmployeeManagerControllerUI controller = loader.getController();
		controller.initialize();
		
		Stage window = (Stage) ((Node)e.getSource()).getScene().getWindow();
		
		window.setScene(employeeViewScene);
	}
	
	/**
	 * Defines the save changes button's behavior.
	 * 
	 * @param e action
	 */
	@FXML
	public void saveChangesButton(ActionEvent e) {
		double pay = 0;
		try {
			pay = Double.parseDouble(payrate.getText());
		} catch (NumberFormatException e1) {
			AlertBox.display("Error", "Pay rate must be a number");
			return;
		}
		if (initFirst.equals(first.getText()) && initMiddle.equals(middle.getText()) && initLast.equals(last.getText()) && initPay == pay) {
			return;
		}
		wcm.removeEmployee(employee);
		
		if (middle.getText().equals("")) {
			try {
				wcm.addEmployee(first.getText(), "null", last.getText(), pay);
			} catch (DuplicateEmployeeException e1) {
				// should never reach here
				e1.printStackTrace();
			}
		} else {
			try {
				wcm.addEmployee(first.getText(), middle.getText(), last.getText(), pay);
			} catch (DuplicateEmployeeException e1) {
				// should never reach here
				e1.printStackTrace();
			}
		}
		wcm.saveState();
		if (initPay != pay) {
			hours.setText("");
			minutes.setText("0");
			paycheck.setText("0.00");
			withoutTax.setText("0.00");
		}
	}
	
	/**
	 * Defines help button's behavior.
	 * 
	 * @param e action
	 */
	@FXML
	public void helpButton(ActionEvent e) {
		AlertBox.display("Help", "To edit employee, make changes and click \n\"Save Changes\" button");
	}
}
