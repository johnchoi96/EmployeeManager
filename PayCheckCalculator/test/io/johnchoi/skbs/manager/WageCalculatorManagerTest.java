/**
 * 
 */
package io.johnchoi.skbs.manager;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.Test;

import io.johnchoi.skbs.objects.Employee;

/**
 * Tests {@link io.johnchoi.skbs.manager.WageCalculatorManager}.
 * 
 * @author John Choi
 * @since 07302018
 */
public class WageCalculatorManagerTest {

	/**
	 * Test method for {@link io.johnchoi.skbs.manager.WageCalculatorManager#findEmployee(java.lang.String, java.lang.String)}.
	 * @throws FileNotFoundException thrown if file is not found
	 */
	@Test
	public void testFindEmployee() throws FileNotFoundException {
		WageCalculatorManager wcm = new WageCalculatorManager("input/employee.txt", "input/taxrate.txt");
		Employee temp1 = wcm.findEmployee("John", "Choi");
		Employee temp = new Employee("John", "Choi", 8.5);
		assertTrue(temp1.equals(temp));
	}

	/**
	 * Test method for {@link io.johnchoi.skbs.manager.WageCalculatorManager#removeEmployee(java.lang.String, java.lang.String)}.
	 * @throws FileNotFoundException 
	 */
	@Test
	public void testRemoveEmployee() throws FileNotFoundException {
		WageCalculatorManager wcm = new WageCalculatorManager("input/employee.txt", "input/taxrate.txt");
		assertEquals(7, wcm.getEmployees().size());
		wcm.removeEmployee("John", "Choi");
		assertEquals(6, wcm.getEmployees().size());
		wcm.addEmployee("John", "Choi", 8.50);
	}
}
