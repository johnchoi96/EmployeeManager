/**
 * 
 */
package io.jnschois.johnchoi.manager.exceptions;

/**
 * Exception to be thrown in case of duplicate employee.
 * 
 * @author John Choi
 * @since 08012018
 */
public class DuplicateEmployeeException extends Exception {

	/**
	 * Generated serial.
	 */
	private static final long serialVersionUID = -4327502745599042806L;

	/**
	 * Constructs this exception.
	 */
	public DuplicateEmployeeException() {
		super();
	}

	/**
	 * Constructs this exception with message.
	 * 
	 * @param message for exception
	 */
	public DuplicateEmployeeException(String message) {
		super(message);
	}
}
