/**
 * 
 */
package io.johnchoi.skbs.objects;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests {@link io.johnchoi.skbs.objects.Employee}.
 * 
 * @author John Choi
 * @since 07302018
 */
public class EmployeeTest {

	/**
	 * Test method for {@link io.johnchoi.skbs.objects.Employee#setFirst(java.lang.String)}.
	 */
	@Test
	public void testSetFirst() {
		Employee em = new Employee("John", "Choi", 8.50);
		assertEquals("John", em.getFirst());
		em.setFirst("Dave");
		assertEquals("Dave", em.getFirst());
		em.setFirst("John");
	}

	/**
	 * Test method for {@link io.johnchoi.skbs.objects.Employee#setLast(java.lang.String)}.
	 */
	@Test
	public void testSetLast() {
		Employee em = new Employee("John", "Choi", 8.50);
		assertEquals("Choi", em.getLast());
		em.setLast("Dave");
		assertEquals("Dave", em.getLast());
		em.setLast("Choi");
	}

	/**
	 * Test method for {@link io.johnchoi.skbs.objects.Employee#setPayrate(double)}.
	 */
	@Test
	public void testSetPayrate() {
		Employee em = new Employee("John", "Choi", 8.50);
		assertEquals(8.50, em.getPayrate(), 0.1);
		em.setPayrate(9.0);
		assertEquals(9.0, em.getPayrate(), 0.1);
		em.setPayrate(8.50);
	}

	/**
	 * Test method for {@link io.johnchoi.skbs.objects.Employee#saveInfo()}.
	 */
	@Test
	public void testSaveInfo() {
		Employee em = new Employee("John", "Choi", 8.50);
		assertEquals("John Choi 8.50", em.saveInfo());
	}

	/**
	 * Test method for {@link io.johnchoi.skbs.objects.Employee#equals(java.lang.Object)}.
	 */
	@Test
	public void testEqualsObject() {
		Employee em = new Employee("John", "Choi", 8.50);
		Employee em1 = new Employee("Jane", "Doe", 7.25);
		assertFalse(em.equals(em1));
		Employee em2 = new Employee("John", "Choi", 11.5);
		assertFalse(em2.equals(em));
		Employee em3 = new Employee("John", "Choi", 8.50);
		assertTrue(em3.equals(em));
	}

	/**
	 * Test method for {@link io.johnchoi.skbs.objects.Employee#toString()}.
	 */
	@Test
	public void testToString() {
		Employee em = new Employee("John", "Choi", 8.50);
		assertEquals("Employee[ name=John Choi, payrate=8.50 ]", em.toString());
	}

	/**
	 * Test method for {@link io.johnchoi.skbs.objects.Employee#compareTo(io.johnchoi.skbs.objects.Employee)}.
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
