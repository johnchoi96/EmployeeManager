/**
 * 
 */
package io.johnchoi.skbs.ui;

import java.io.FileNotFoundException;
import java.io.IOException;

import io.johnchoi.skbs.manager.PaycheckCalculatorManager;
import io.johnchoi.skbs.objects.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * Main controller for this software GUI.
 * 
 * @author John Choi
 * @since 07312018
 */
public class PaycheckCalculatorControllerUI {
	
	private PaycheckCalculatorManager wcm;
	@FXML private TextField first;
	@FXML private TextField last;
	@FXML private TextField payrate;
	@FXML private TableView<Employee> employeeList;
	@FXML private TableColumn<Employee, String> firstNameCol;
	@FXML private TableColumn<Employee, String> lastNameCol;
	@FXML private TableColumn<Employee, Double> payrateCol;
	@FXML private TextField stateTax;
	@FXML private TextField federalTax;
	private ObservableList<Employee> employee;
	
	/**
	 * Initializes software.
	 */
	public void initialize() {
		try {
			wcm = new PaycheckCalculatorManager("input/employee.txt", "input/taxrate.txt");
			this.firstNameCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("first"));
			this.lastNameCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("last"));
			this.payrateCol.setCellValueFactory(new PropertyValueFactory<Employee, Double>("payrate"));
			this.stateTax.setText(String.format("%.2f", wcm.getStateTaxRate()));
			this.federalTax.setText(String.format("%.2f", wcm.getFederalTaxRate()));
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
		employee = FXCollections.observableArrayList(wcm.getEmployees());
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
		if (first.getText().trim().contains(" ") || last.getText().trim().contains(" ")) {
			AlertBox.display("Error", "First and last names cannot contain a space");
			return;
		}
		wcm.addEmployee(first.getText().trim(), last.getText().trim(), Double.parseDouble(payrate.getText()));
		this.employeeList.setItems(getEmployee());
		first.setText("");
		last.setText("");
		payrate.setText("");
		wcm.saveState();
	}
	
	/**
	 * Defines the remove button behavior.
	 * 
	 * @param e action
	 */
	@FXML
	public void removeEmployeeButton(ActionEvent e) {
		if (first.getText().equals("") || last.getText().equals("")) {
			AlertBox.display("Error", "Please enter first and last names of deleting employee");
			return;
		}
		wcm.removeEmployee(first.getText().trim(), last.getText().trim());
		this.employeeList.setItems(getEmployee());
		first.setText("");
		last.setText("");
		payrate.setText("");
		wcm.saveState();
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
		loader.setLocation(getClass().getResource("/io/johnchoi/skbs/ui/employeeScreen.fxml"));
		Parent employeeViewParent = loader.load();
		Scene employeeViewScene = new Scene(employeeViewParent);
		
		EmployeeScreen controller = loader.getController();
		try {
			controller.initialize(wcm, employeeList.getSelectionModel().getSelectedItem());
		} catch (Exception exception) {
			AlertBox.display("Error", "You must select a employee");
			return;
		}
		
		Stage window = (Stage) ((Node)e.getSource()).getScene().getWindow();
		
		window.setScene(employeeViewScene);
	}
	
	/**
	 * Defines change button's behavior.
	 * 
	 * @param e action
	 */
	@FXML
	public void changeButton(ActionEvent e) {
		try {
			wcm.editTaxRate(Double.parseDouble(federalTax.getText()), Double.parseDouble(stateTax.getText()));
		} catch (NumberFormatException e1) {
			AlertBox.display("Error", "Input must be a number");
			return;
		} catch (IOException e1) {
			AlertBox.display("Error", "Error editing tax rate file");
			return;
		}
	}
}
