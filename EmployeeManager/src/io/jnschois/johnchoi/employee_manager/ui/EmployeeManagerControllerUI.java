/**
 * 
 */
package io.jnschois.johnchoi.employee_manager.ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

import io.jnschois.johnchoi.employee_manager.manager.EmployeeManager;
import io.jnschois.johnchoi.employee_manager.manager.exceptions.DuplicateEmployeeException;
import io.jnschois.johnchoi.employee_manager.objects.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * Main controller for this software GUI.
 * 
 * @author John Choi
 * @since 09092018
 */
public class EmployeeManagerControllerUI {
	
	/** North Carolina's minimum wage of 2018 */
	private static final double MIN_WAGE = 7.25;
	protected EmployeeManager em;
	@FXML private TextField first;
	@FXML private TextField middle;
	@FXML private TextField last;
	@FXML private TextField payrate;
	@FXML private TableView<Employee> employeeList;
	@FXML private TableColumn<Employee, String> firstNameCol;
	@FXML private TableColumn<Employee, String> middleNameCol;
	@FXML private TableColumn<Employee, String> lastNameCol;
	@FXML private TableColumn<Employee, Double> payrateCol;
	@FXML private TextField stateTax;
	@FXML private TextField federalTax;
	private ObservableList<Employee> employee;

	private String initFTax;
	private String initSTax;
	
	/**
	 * Initializes software.
	 */
	public void initialize() {
		initFTax = federalTax.getText();
		initSTax = stateTax.getText();
		try {
			em = new EmployeeManager();
			this.firstNameCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("first"));
			this.middleNameCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("middle"));
			this.lastNameCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("last"));
			this.payrateCol.setCellValueFactory(new PropertyValueFactory<Employee, Double>("payrate"));
			this.stateTax.setText(String.format("%.2f", em.getStateTaxRate()));
			this.federalTax.setText(String.format("%.2f", em.getFederalTaxRate()));
			this.employeeList.setItems(getEmployee());
		} catch (FileNotFoundException e) {
			AlertBox.display("Error", "Unable to open required file(s).\nProgram will now terminate.");
			System.exit(1);
		}
	}

	/**
	 * Returns observable list of employee objects.
	 * 
	 * @return observable list of employees
	 */
	private ObservableList<Employee> getEmployee() {
		employee = FXCollections.observableArrayList(em.getEmployees());
		return employee;
	}
	
	/**
	 * Defines the add button behavior.
	 * 
	 * @param e action
	 */
	@FXML
	public void addEmployeeButton(ActionEvent e) {
		if (first.getText().equals("") || last.getText().equals("") || payrate.getText().equals("")) {
			AlertBox.display("Error", "Please enter first, last, and payrate of adding employee");
			return;
		}
		if (first.getText().trim().contains(" ") || middle.getText().trim().contains(" ") || last.getText().trim().contains(" ")) {
			AlertBox.display("Error", "First, middle, and last names cannot contain a space");
			return;
		}
		try {
			double pay = Double.parseDouble(payrate.getText());
			if (pay < 0) {
				payrate.setText("");
				AlertBox.display("Error", "Pay rate cannot be less than zero");
				payrate.requestFocus();
				return;
			}
			if (pay < MIN_WAGE) {
				AlertBox.display("Alert", String.format("Pay check is less than the current minimum wage, %.2f", MIN_WAGE));
			}
			em.addEmployee(first.getText().trim(), middle.getText().trim(), last.getText().trim(), pay);
		} catch (NumberFormatException e1) {
			AlertBox.display("Error", "Pay rate must be a number");
			return;
		} catch (DuplicateEmployeeException e1) {
			AlertBox.display("Error", "Employee already exists in the database");
			return;
		}
		this.employeeList.setItems(getEmployee());
		first.setText("");
		middle.setText("");
		last.setText("");
		payrate.setText("");
		em.saveState();
		first.requestFocus();
	}
	
	/**
	 * Defines the remove button behavior.
	 * 
	 * @param e action
	 */
	@FXML
	public void removeEmployeeButton(ActionEvent e) {
		Employee removingEmployee = employeeList.getSelectionModel().getSelectedItem();
		if (removingEmployee == null) {
			AlertBox.display("Error", "You must select the employee you want to delete");
			return;
		}
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Are you sure?");
		alert.setHeaderText(null);
		alert.setContentText("Are you sure you want to delete " + removingEmployee.getFirst() + "?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			// ... user chose OK
			if (removingEmployee.getMiddle().equals("")) {
				em.removeEmployee(removingEmployee.getFirst(), removingEmployee.getLast());
			} else {
				em.removeEmployee(removingEmployee.getFirst(), removingEmployee.getMiddle(), removingEmployee.getLast());
			}
			em.saveState();
			initialize();
		} else {
			return;
		}
	}
	
	/**
	 * Defines the scene changer view button.
	 * 
	 * @param e action
	 * @throws IOException if there is problem reading file
	 */
	@FXML
	public void changeSceneButton(ActionEvent e) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/io/jnschois/johnchoi/employee_manager/ui/employeeScreen.fxml"));
		Parent employeeViewParent = loader.load();
		Scene employeeViewScene = new Scene(employeeViewParent);
		
		EmployeeScreen controller = loader.getController();
		try {
			controller.initialize(em, employeeList.getSelectionModel().getSelectedItem());
		} catch (Exception exception) {
			AlertBox.display("Error", "You must select a employee");
			return;
		}
		
		Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
		window.setTitle("Employee Details");
		
		window.setScene(employeeViewScene);
	}
	
	/**
	 * Defines change button's behavior.
	 * 
	 * @param e action
	 */
	@FXML
	public void changeButton(ActionEvent e) {
		double fedTax = 0;
		double sTax = 0;
		try {
			fedTax = Double.parseDouble(federalTax.getText());
			sTax = Double.parseDouble(stateTax.getText());
			if (fedTax < 0 || sTax < 0) {
				federalTax.setText(initFTax);
				stateTax.setText(initSTax);
				AlertBox.display("Error", "Invalid input");
				return;
			}
			em.editTaxRate(fedTax, sTax);
		} catch (NumberFormatException e1) {
			federalTax.setText(initFTax);
			stateTax.setText(initSTax);
			AlertBox.display("Error", "Input must be a number");
			return;
		} catch (IOException e1) {
			federalTax.setText(initFTax);
			stateTax.setText(initSTax);
			AlertBox.display("Error", "Error editing tax rate file");
			return;
		}
		String newF = String.format("%.2f", fedTax);
		String newS = String.format("%.2f", sTax);
		federalTax.setText(newF);
		stateTax.setText(newS);
		initFTax = newF;
		initSTax = newS;
	}
}
