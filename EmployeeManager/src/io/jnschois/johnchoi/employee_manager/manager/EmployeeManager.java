/**
 * 
 */
package io.jnschois.johnchoi.employee_manager.manager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import io.jnschois.johnchoi.employee_manager.file.FileIO;
import io.jnschois.johnchoi.employee_manager.manager.exceptions.DuplicateEmployeeException;
import io.jnschois.johnchoi.employee_manager.objects.Employee;

/**
 * Main manager for this software.
 * 
 * @author John Choi
 * @since 08012018
 * @version 2.0
 */
public class EmployeeManager {
	
	private double federalTax;
	private double stateTax;
	private ArrayList<Employee> employees;

	/**
	 * Constructs this manager with employee file and tax rate file passed in.
	 * 
	 * @throws FileNotFoundException thrown if the file cannot be opened
	 */
	public EmployeeManager() throws FileNotFoundException {
		employees = FileIO.loadFile();
		double[] taxes = FileIO.readTaxRate();
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
	public Employee findEmployee(Employee e) {
		if (employees.contains(e)) {
			for (int i = 0; i < employees.size(); i++) {
				if (employees.get(i).equals(e)) {
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
	 * Searches and removes the employee from the list.
	 * 
	 * @param first name of employee to remove
	 * @param last name of employee to remove
	 */
	public void removeEmployee(String first, String middle, String last) {
		Employee temp = new Employee(first, middle, last, 7.25);
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
	 * Searches and removes the employee from the list.
	 * 
	 * @param employee to remove
	 */
	public void removeEmployee(Employee employee) {
		if (employees.contains(employee)) {
			for (int i = 0; i < employees.size(); i++) {
				if (employees.get(i).equals(employee)) {
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
	 * @throws DuplicateEmployeeException thrown if adding employee is duplicate
	 */
	public void addEmployee(String first, String middle, String last, double payrate) throws DuplicateEmployeeException {
		Employee employee = null;
		if (middle.equals("") || middle.equals("null")) {
			employee = new Employee(first, last, payrate);
		} else {
			employee = new Employee(first, middle, last, payrate);
		}
		if (employees.contains(employee)) {
			throw new DuplicateEmployeeException();
		}
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
