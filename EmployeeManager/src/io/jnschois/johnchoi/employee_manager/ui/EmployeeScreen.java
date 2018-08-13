/**
 * 
 */
package io.jnschois.johnchoi.employee_manager.ui;

import java.io.IOException;

import io.jnschois.johnchoi.employee_manager.manager.EmployeeManager;
import io.jnschois.johnchoi.employee_manager.manager.exceptions.DuplicateEmployeeException;
import io.jnschois.johnchoi.employee_manager.objects.Employee;
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
 * @since 08122018
 */
public class EmployeeScreen {
	
	/** North Carolina's minimum wage of 2018 */
	private static final double MIN_WAGE = 7.25;
	/** Tax rate based on state of North Carolina */
	private final double TAX_RATE = 0.0765;
	private EmployeeManager em;
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
	 * @param em manager
	 * @param e employee to show
	 */
	public void initialize(EmployeeManager em, Employee e) {
		this.em = em;
		employee = e;
		first.setText(e.getFirst());
		String middleName = e.getMiddle();
		if (middleName.equals("null")) {
			middle.setText("");
		} else {
			middle.setText(middleName);
		}
		last.setText(employee.getLast());
		payrate.setText(employee.getPayrate());
		paycheck.setText("0.00");
		withoutTax.setText("0.00");
		hours.setText("");
		minutes.setText("0");
		initFirst = first.getText();
		initMiddle = middle.getText();
		initLast = last.getText();
		initPay = Double.parseDouble(employee.getPayrate());
		hours.requestFocus();
	}
	
	/**
	 * Defines the behavior of calculate button.
	 */
	@FXML
	public void calculateButton() {
		if (hours.getText().equals("") || minutes.getText().equals("")) {
			hours.setText("0");
			minutes.setText("0");
			AlertBox.display("Error", "Input must be a number");
			return;
		}
		int numericHour = 0;
		int numericMinute = 0;
		try {
			numericHour = Integer.parseInt(hours.getText());
			numericMinute = Integer.parseInt(minutes.getText());
		} catch (NumberFormatException e) {
			hours.setText("0");
			minutes.setText("0");
			AlertBox.display("Error", "Input must be a number");
			hours.requestFocus();
			return;
		}
		if (numericHour < 0 || (numericMinute < 0 || numericMinute > 59)) {
			hours.setText("0");
			minutes.setText("0");
			AlertBox.display("Error", "Invalid time");
			hours.requestFocus();
			return;
		}
		double convertedTime = calculateTime(numericHour, numericMinute);
		double totalPaycheck = convertedTime * Double.parseDouble(payrate.getText());
		paycheck.setText(String.format("%.2f", totalPaycheck));
		
		double woTax = 0;
		double tempResult = Double.parseDouble(String.format("%.2f", totalPaycheck * TAX_RATE));
		woTax = totalPaycheck - tempResult - em.getFederalTaxRate() - em.getStateTaxRate();
		if (woTax < 0) {
			withoutTax.setText("0.00");
		} else {
			withoutTax.setText(String.format("%.2f", woTax));
		}
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
		loader.setLocation(getClass().getResource("/io/jnschois/johnchoi/employee_manager/ui/main.fxml"));
		Parent mainViewParent = loader.load();
		Scene employeeViewScene = new Scene(mainViewParent);
		
		EmployeeManagerControllerUI controller = loader.getController();
		controller.initialize();
		
		Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
		window.setTitle("Employee Manager - Main Menu");
		
		window.setScene(employeeViewScene);
	}
	
	/**
	 * Defines the save changes button's behavior.
	 * 
	 * @param e action
	 */
	@FXML
	public void saveChangesButton(ActionEvent e) {
		String newFirst = first.getText().trim();
		String newMiddle = middle.getText().trim();
		String newLast = last.getText().trim();
		
		//if first, last, and pay rate inputs are missing, do nothing
		if (newFirst.equals("") || newLast.equals("") || payrate.getText().equals("")) {
			first.setText(initFirst);
			middle.setText(initMiddle);
			last.setText(initLast);
			payrate.setText(Double.toString(initPay));
			AlertBox.display("Error", "Please enter first, last, and payrate of editing employee");
			return;
		}
		// if first, middle, and last names contain spaces, do nothing
		if (newFirst.contains(" ") || newMiddle.contains(" ") || newLast.contains(" ")) {
			first.setText(initFirst);
			middle.setText(initMiddle);
			last.setText(initLast);
			payrate.setText(Double.toString(initPay));
			AlertBox.display("Error", "First, middle, and last names cannot contain spaces");
			return;
		}
		double newPayRate = 0;
		try {
			newPayRate = Double.parseDouble(payrate.getText());
			if (newPayRate < 0) {
				first.setText(initFirst);
				middle.setText(initMiddle);
				last.setText(initLast);
				payrate.setText(Double.toString(initPay));
				AlertBox.display("Error", "Pay rate cannot be less than zero");
				return;
			}
			if (newPayRate < MIN_WAGE) {
				AlertBox.display("Alert", String.format("Pay check is less than the current minimum wage, %.2f", MIN_WAGE));
			}
			payrate.setText(String.format("%.2f", newPayRate));
		} catch (NumberFormatException e1) {
			AlertBox.display("Error", "Pay rate must be a number");
			first.setText(initFirst);
			middle.setText(initMiddle);
			last.setText(initLast);
			payrate.setText(Double.toString(initPay));
			return;
		}
		
		// if only pay rate is getting updated
		if (initFirst.equals(first.getText()) && initMiddle.equals(middle.getText()) && initLast.equals(last.getText())) {
			if (initPay != newPayRate) {
				em.removeEmployee(employee);
				try {
					em.addEmployee(newFirst, newMiddle, newLast, newPayRate);
					hours.setText("");
					minutes.setText("0");
					paycheck.setText("0.00");
					withoutTax.setText("0.00");
					initFirst = newFirst;
					initMiddle = newMiddle;
					initLast = newLast;
					initPay = newPayRate;
					em.saveState();
					return;
				} catch (DuplicateEmployeeException e1) {
					// should never reach here
					e1.printStackTrace();
				}
			} else {
				return;
			}
		}
		
		// check to see if editing employee exists
		Employee check = new Employee(newFirst, newMiddle, newLast, newPayRate);
		if (em.findEmployee(check) != null) {
			first.setText(initFirst);
			middle.setText(initMiddle);
			last.setText(initLast);
			payrate.setText(String.format("%.2f", initPay));
			AlertBox.display("Error", "Employee with this information already exists");
			return;
		}
		
		//first, remove the current employee
		em.removeEmployee(employee);
		
		// add employee without middle name
		if (middle.getText().equals("")) {
			try {
				em.addEmployee(first.getText(), "null", last.getText(), newPayRate);
			} catch (DuplicateEmployeeException e1) {
				// should never reach here
				e1.printStackTrace();
			}
		} else { // add employee with middle name
			try {
				em.addEmployee(first.getText(), middle.getText(), last.getText(), newPayRate);
			} catch (DuplicateEmployeeException e1) {
				// should never reach here
				e1.printStackTrace();
			}
		}
		em.saveState();
		// reset init* values to new values for next run
		initFirst = newFirst;
		initMiddle = newMiddle;
		initLast = newLast;
		initPay = newPayRate;
		// if pay rate has been modified
		if (initPay != newPayRate) {
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
