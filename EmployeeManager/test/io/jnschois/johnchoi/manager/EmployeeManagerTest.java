/**
 * 
 */
package io.jnschois.johnchoi.manager;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.Test;

import io.jnschois.johnchoi.manager.EmployeeManager;
import io.jnschois.johnchoi.manager.exceptions.DuplicateEmployeeException;
import io.jnschois.johnchoi.objects.Employee;

/**
 * Tests {@link io.jnschois.johnchoi.manager.EmployeeManager}.
 * 
 * @author John Choi
 * @since 08012018
 */
public class EmployeeManagerTest {

	/**
	 * Test method for {@link io.jnschois.johnchoi.manager.EmployeeManager#findEmployee(java.lang.String, java.lang.String)}.
	 * @throws FileNotFoundException thrown if file is not found
	 */
	@Test
	public void testFindEmployee() throws FileNotFoundException {
		Employee employee = new Employee("John", "Choi", 8.5);
		Employee temp = new Employee("John", "Choi", 8.5);
		assertTrue(employee.equals(temp));
	}

	/**
	 * Test method for {@link io.jnschois.johnchoi.manager.EmployeeManager#removeEmployee(java.lang.String, java.lang.String)}.
	 * @throws FileNotFoundException thrown if file cannot be found
	 * @throws DuplicateEmployeeException thrown if adding employee is a duplicate
	 */
	@Test
	public void testRemoveEmployee() throws FileNotFoundException, DuplicateEmployeeException {
		EmployeeManager wcm = new EmployeeManager();
		assertEquals(7, wcm.getEmployees().size());
		wcm.removeEmployee("John", "Choi");
		assertEquals(7, wcm.getEmployees().size());
		wcm.addEmployee("John", "", "Choi", 8.50);
	}
}
