/**
 * 
 */
package io.jnschois.johnchoi.employee_manager.ui;

/**
 * Exception that is thrown when user clicks "cancel".
 * 
 * @author John Choi
 * @since 09092018
 */
public class CancelException extends Exception {

	/**
	 * Generated serial for this exception.
	 */
	private static final long serialVersionUID = 2148784614575967278L;
	
	/**
	 * Constructs this exception.
	 */
	public CancelException() {
		super();
	}

	/**
	 * Constructs this exception with message.
	 * 
	 * @param message to throw
	 */
	public CancelException(String message) {
		super(message);
	}
}
