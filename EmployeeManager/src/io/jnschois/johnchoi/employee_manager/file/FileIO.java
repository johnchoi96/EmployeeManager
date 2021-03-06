/**
 * 
 */
package io.jnschois.johnchoi.employee_manager.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import io.jnschois.johnchoi.employee_manager.objects.Employee;
import io.jnschois.johnchoi.employee_manager.ui.AlertBox;

/**
 * Handles the file IO at the beginning of program execution and right before program termination.
 * 
 * @author John Choi
 * @since 08012018
 */
public class FileIO {

	/**
	 * Static method that takes in employee file name and saves data in the data structure.
	 * 
	 * @return list of employees
	 * @throws FileNotFoundException thrown if file cannot be opened
	 */
	public static ArrayList<Employee> loadFile() throws FileNotFoundException {
		ArrayList<Employee> employees = new ArrayList<>();
		File myFile = new File("input/employee.jns");
		Scanner readFile = new Scanner(myFile);
		while (readFile.hasNextLine()) {
			try {
				String firstName = readFile.next().trim();
				String middleName = readFile.next().trim();
				String lastName = readFile.next().trim();
				double payrate = readFile.nextDouble();
				Employee newEmployee = new Employee(firstName, middleName, lastName, payrate);
				employees.add(newEmployee);
			} catch (NoSuchElementException e) {
				break;
			}
		}
		readFile.close();
		return employees;
	}
	
	/**
	 * Static method that saves the current state of software.
	 * 
	 * @param employees list of employees
	 */
	public static void saveFile(ArrayList<Employee> employees) {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter("input/employee.jns");
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < employees.size(); i++) {
			pw.println(employees.get(i).saveInfo());
		}
		pw.println();
		pw.println();
		pw.println();
		pw.print("******* DO NOT MODIFY THIS FILE IN ANY WAY *******");
		pw.close();
	}
	
	/**
	 * Reads the file with tax rate information and returns tax rate.
	 * Index 0 is federal, index 1 is state.
	 * 
	 * @return tax rates
	 * @throws FileNotFoundException thrown if file cannot be opened
	 */
	public static double[] readTaxRate() throws FileNotFoundException {
		double[] returnVal = new double[2];
		File myFile = new File("input/taxrate.jns");
		Scanner readFile = new Scanner(myFile);
		try {
			returnVal[0] = readFile.nextDouble();
			returnVal[1] = readFile.nextDouble();
		} catch (NoSuchElementException e) {
			AlertBox.display("Error", "Error while reading the required tax rate file");
			System.exit(1);
		}
		readFile.close();
		return returnVal;
	}
	
	/**
	 * Edits the tax file information.
	 * 
	 * @param federal tax to set
	 * @param state tax to set
	 * @throws IOException if exception occurs
	 */
	public static void editTaxRate(double federal, double state) throws IOException {
		PrintWriter pw = new PrintWriter("input/taxrate.jns");
		pw.print(federal + " ");
		pw.println(state);
		pw.print("******* DO NOT MODIFY THIS FILE IN ANY WAY *******");
		pw.close();
	}
}
