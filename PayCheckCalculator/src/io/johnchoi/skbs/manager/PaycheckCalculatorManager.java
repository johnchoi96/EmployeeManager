/**
 * 
 */
package io.johnchoi.skbs.manager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import io.johnchoi.skbs.file.FileIO;
import io.johnchoi.skbs.objects.Employee;

/**
 * Main manager for this software.
 * 
 * @author John Choi
 * @since 07312018
 * @version 1.2.1
 */
public class PaycheckCalculatorManager {
	
	private double federalTax;
	private double stateTax;
	private ArrayList<Employee> employees;

	/**
	 * Constructs this manager with employee file and tax rate file passed in.
	 * 
	 * @param employeeFile file that contains employee information
	 * @param taxRateFile file that contains tax rate information
	 * @throws FileNotFoundException thrown if the file cannot be opened
	 */
	public PaycheckCalculatorManager(String employeeFile, String taxRateFile) throws FileNotFoundException {
		employees = FileIO.loadFile(employeeFile);
		double[] taxes = FileIO.readTaxRate(taxRateFile);
		federalTax = taxes[0];
		stateTax = taxes[1];
	}
	
	/**
	 * Searches and returns employee with passed in name.
	 * 
	 * @param first name of employee
	 * @param last name of employee
	 * @return found employee, null if not found
	 */
	public Employee findEmployee(String first, String last) {
		Employee temp = new Employee(first, last, 7.25);
		if (employees.contains(temp)) {
			for (int i = 0; i < employees.size(); i++) {
				if (employees.get(i).equals(temp)) {
					return employees.get(i);
				}
			}
			return null; //should never reach here
		} else {
			return null;
		}
	}
	
	/**
	 * Searches and removes the employee from the list.
	 * 
	 * @param first name of employee to remove
	 * @param last name of employee to remove
	 */
	public void removeEmployee(String first, String last) {
		Employee temp = new Employee(first, last, 7.25);
		if (employees.contains(temp)) {
			for (int i = 0; i < employees.size(); i++) {
				if (employees.get(i).equals(temp)) {
					employees.remove(i);
					break;
				}
			}
		}
	}
	
	/**
	 * Saves the current state of program to the file.
	 */
	public void saveState() {
		FileIO.saveFile(employees);
	}
	
	/**
	 * Getter for list of employees.
	 * 
	 * @return list of employees
	 */
	public ArrayList<Employee> getEmployees() {
		Collections.sort(employees);
		return employees;
	}
	
	/**
	 * Adds the employee to the data structure.
	 * 
	 * @param first name of the employee
	 * @param last name
	 * @param payrate of employee
	 */
	public void addEmployee(String first, String last, double payrate) {
		Employee employee = new Employee(first, last, payrate);
		employees.add(employee);
	}
	
	/**
	 * Edits the tax rate and saves it.
	 * 
	 * @param newFederalTax to set
	 * @param newStateTax to set
	 * @throws IOException if file IO exception occurs
	 */
	public void editTaxRate(double newFederalTax, double newStateTax) throws IOException {
		federalTax = newFederalTax;
		stateTax = newStateTax;
		FileIO.editTaxRate(federalTax, stateTax);
	}
	
	/**
	 * Getter for tax rate.
	 * 
	 * @return the tax rate
	 */
	public double getFederalTaxRate() {
		return federalTax;
	}
	
	/**
	 * Getter for tax rate.
	 * 
	 * @return the tax rate
	 */
	public double getStateTaxRate() {
		return stateTax;
	}
}
