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
	
	/**
	 * Initializes software.
	 */
	public void initialize() {
		try {
			wcm = new WageCalculatorManager("input/sample_employee.txt", "input/sample_taxrate.txt");
			this.firstNameCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("first"));
			this.lastNameCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("last"));
			this.payrateCol.setCellValueFactory(new PropertyValueFactory<Employee, Double>("payrate"));
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
		ObservableList<Employee> employee = FXCollections.observableArrayList(wcm.getEmployees());
		return employee;
	}
	
	/**
	 * Defines the add button behavior.
	 * 
	 * @param e action
	 */
	@FXML
	public void addEmployeeButton(ActionEvent e) {
		
	}
	
	/**
	 * Defines the remove button behavior.
	 * 
	 * @param e action
	 */
	@FXML
	public void removeEmployeeButton(ActionEvent e) {
		
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
		controller.initialize(wcm, employeeList.getSelectionModel().getSelectedItem());
		
		Stage window = (Stage) ((Node)e.getSource()).getScene().getWindow();
		
		window.setScene(employeeViewScene);
	}
	
	/**
	 * Defines behavior for save button.
	 */
	@FXML
	public void saveButton(ActionEvent e) {
		wcm.saveState();
	}
}
