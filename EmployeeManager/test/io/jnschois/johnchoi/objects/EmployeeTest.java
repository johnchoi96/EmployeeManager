/**
 * 
 */
package io.jnschois.johnchoi.objects;

import static org.junit.Assert.*;

import org.junit.Test;

import io.jnschois.johnchoi.objects.Employee;

/**
 * Tests {@link io.jnschois.johnchoi.objects.Employee}.
 * 
 * @author John Choi
 * @since 08012018
 */
public class EmployeeTest {

	/**
	 * Test method for {@link io.jnschois.johnchoi.objects.Employee#saveInfo()}.
	 */
	@Test
	public void testSaveInfo() {
		Employee em = new Employee("John", "Choi", 8.50);
		assertEquals("John Choi 8.50", em.saveInfo());
	}

	/**
	 * Test method for {@link io.jnschois.johnchoi.objects.Employee#equals(java.lang.Object)}.
	 */
	@Test
	public void testEqualsObject() {
		Employee em = new Employee("John", "Choi", 8.50);
		Employee em1 = new Employee("Jane", "Doe", 7.25);
		assertFalse(em.equals(em1));
		Employee em3 = new Employee("John", "Choi", 8.50);
		assertTrue(em3.equals(em));
	}

	/**
	 * Test method for {@link io.jnschois.johnchoi.objects.Employee#toString()}.
	 */
	@Test
	public void testToString() {
		Employee em = new Employee("John", "Choi", 8.50);
		assertEquals("Employee[ name=John Choi, payrate=8.50 ]", em.toString());
	}

	/**
	 * Test method for {@link io.jnschois.johnchoi.objects.Employee#compareTo(io.jnschois.johnchoi.objects.Employee)}.
	 */
	@Test
	public void testCompareTo() {
		Employee em = new Employee("Apple", "Choi", 8.50);
		Employee em1 = new Employee("Beta", "Doe", 7.25);
		Employee em2 = new Employee("Charlie", "Choi", 11.5);
		Employee em3 = new Employee("Delta", "Choi", 8.50);
		assertTrue(em.compareTo(em1) < 0);
		assertTrue(em1.compareTo(em2) < 0);
		assertTrue(em2.compareTo(em3) < 0);
	}

}
