/**
 * 
 */
package io.johnchoi.skbs.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import io.johnchoi.skbs.objects.Employee;

/**
 * Handles the file IO at the beginning of program execution and right before program termination.
 * 
 * @author John Choi
 * @since 07302018
 */
public class FileIO {

	/**
	 * Static method that takes in employee file name and saves data in the data structure.
	 * 
	 * @param filename of employee
	 * @return list of employees
	 * @throws FileNotFoundException thrown if file cannot be opened
	 */
	public static ArrayList<Employee> loadFile(String filename) throws FileNotFoundException {
		ArrayList<Employee> employees = new ArrayList<>();
		File myFile = new File(filename);
		Scanner readFile = new Scanner(myFile);
		while (readFile.hasNextLine()) {
			String firstName = readFile.next().trim();
			String lastName = readFile.next().trim();
			double payrate = readFile.nextDouble();
			Employee newEmployee = new Employee(firstName, lastName, payrate);
			employees.add(newEmployee);
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
			pw = new PrintWriter("input/employees.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < employees.size(); i++) {
			pw.println(employees.get(i).saveInfo());
		}
		pw.close();
	}
	
	/**
	 * Reads the file with tax rate information and returns tax rate.
	 * 
	 * @param filename with tax info
	 * @return tax rate
	 * @throws FileNotFoundException thrown if file cannot be opened
	 */
	public static double readTaxRate(String filename) throws FileNotFoundException {
		File myFile = new File(filename);
		Scanner readFile = new Scanner(myFile);
		double taxrate = readFile.nextDouble();
		readFile.close();
		return taxrate;
	}
	
	/**
	 * Edits the tax file information.
	 * 
	 * @param taxrate to set
	 * @throws IOException if exception occurs
	 */
	public static void editTaxRate(double taxrate) throws IOException {
		PrintWriter pw = new PrintWriter("input/taxrate.txt");
		pw.print(taxrate);
		pw.close();
	}
}
