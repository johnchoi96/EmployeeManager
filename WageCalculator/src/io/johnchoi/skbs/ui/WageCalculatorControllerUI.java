/**
 * 
 */
package io.johnchoi.skbs.ui;

import java.io.FileNotFoundException;
import java.io.IOException;

import io.johnchoi.skbs.manager.WageCalculatorManager;
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
 * @since 07302018
 */
public class WageCalculatorControllerUI {
	
	private WageCalculatorManager wcm;
	@FXML private TextField first;
	@FXML private TextField last;
	@FXML private TextField payrate;
	@FXML private TableView<Employee> employeeList;
	@FXML private TableColumn<Employee, String> firstNameCol;
	@FXML private TableColumn<Employee, String> lastNameCol;
	@FXML private TableColumn<Employee, Double> payrateCol;
	@FXML private TextField taxInfo;
	private ObservableList<Employee> employee;
	
	/**
	 * Initializes software.
	 */
	public void initialize() {
		try {
			wcm = new WageCalculatorManager("input/employee.txt", "input/taxrate.txt");
			this.firstNameCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("first"));
			this.lastNameCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("last"));
			this.payrateCol.setCellValueFactory(new PropertyValueFactory<Employee, Double>("payrate"));
//			this.taxInfo = new TextField(String.format("%.3f", wcm.getTaxRate()));
			this.taxInfo.setText(String.format("%.3f", wcm.getTaxRate()));
			this.employeeList.setItems(getEmployee());
		} catch (FileNotFoundException e) {
			AlertBox.display("Error", "Unable to open required file(s)");
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
		wcm.addEmployee(first.getText(), last.getText(), Double.parseDouble(payrate.getText()));
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
		wcm.removeEmployee(first.getText(), last.getText());
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
	 * @throws IOException 
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
			wcm.editTaxRate(Double.parseDouble(taxInfo.getText()));
		} catch (NumberFormatException e1) {
			AlertBox.display("Error", "Input must be a number");
			return;
		} catch (IOException e1) {
			AlertBox.display("Error", "Error editing tax rate");
			return;
		}
	}
	
	/**
	 * Defines behavior for save button.
	 * 
	 * @param e action
	 */
	@FXML
	public void saveButton(ActionEvent e) {
		wcm.saveState();
	}
}
