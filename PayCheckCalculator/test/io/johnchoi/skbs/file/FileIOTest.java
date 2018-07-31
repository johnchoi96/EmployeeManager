/**
 * 
 */
package io.johnchoi.skbs.file;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.Test;

import io.johnchoi.skbs.manager.WageCalculatorManager;

/**
 * Tests {@link io.johnchoi.skbs.file.FileIO}.
 * 
 * @author John Choi
 * @since 07302018
 */
public class FileIOTest {

	/**
	 * Test method for {@link io.johnchoi.skbs.file.FileIO#saveFile(java.util.ArrayList)}.
	 * @throws FileNotFoundException thrown if file is not found
	 */
	@Test
	public void testSaveFile() throws FileNotFoundException {
		WageCalculatorManager wcm = new WageCalculatorManager("input/employee.txt", "input/taxrate.txt");
		wcm.saveState();
		assertNotNull(wcm);
	}
}
