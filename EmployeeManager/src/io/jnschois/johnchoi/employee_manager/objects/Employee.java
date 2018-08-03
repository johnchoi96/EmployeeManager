/**
 * 
 */
package io.jnschois.johnchoi.employee_manager.objects;

/**
 * Class that defines one employee.
 * 
 * @author John Choi
 * @since 08032018
 */
public class Employee implements Comparable<Employee> {

	private String first;
	private String middle;
	private String last;
	private double payrate;
	
	/**
	 * Constructs one employee object.
	 * 
	 * @param first name of employee
	 * @param middle name of employee
	 * @param last name of employee
	 * @param wage of the employee
	 */
	public Employee(String first, String middle, String last, double wage) {
		this.first = first;
		this.middle = middle;
		this.last = last;
		this.payrate = wage;
	}
	
	/**
	 * Constructs one employee object.
	 * 
	 * @param first name of employee
	 * @param last name of employee
	 * @param wage of the employee
	 */
	public Employee(String first, String last, double wage) {
		this.first = first;
		this.middle = "null";
		this.last = last;
		this.payrate = wage;
	}

	/**
	 * Getter for first name.
	 * 
	 * @return the first
	 */
	public String getFirst() {
		return first;
	}
	
	/**
	 * Getter for middle name.
	 * 
	 * @return the middle
	 */
	public String getMiddle() {
		if (middle.equals("null")) {
			return "";
		}
		return middle;
	}
	
	/**
	 * Getter for last name.
	 * 
	 * @return the last
	 */
	public String getLast() {
		return last;
	}

	/**
	 * Getter for wage.
	 * 
	 * @return the wage
	 */
	public String getPayrate() {
		return String.format("%.2f", payrate);
	}
	
	/**
	 * Used to save current state of program.
	 * 
	 * @return info to write out to file
	 */
	public String saveInfo() {
		if (middle.equals("null")) {
			return String.format("%s null %s %.2f", first, last, payrate);
		}
		return String.format("%s %s %s %.2f", first, middle, last, payrate);
	}

	/**
	 * Returns true if two employees are equal.
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 * @param obj - employee to compare
	 * @return true if two employees are equal
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		if (first == null) {
			if (other.first != null)
				return false;
		} else if (!first.equals(other.first))
			return false;
		if (last == null) {
			if (other.last != null)
				return false;
		} else if (!last.equals(other.last))
			return false;
		if (middle == null) {
			if (other.middle != null)
				return false;
		} else if (!middle.equals(other.middle))
			return false;
		return true;
	}

	/**
	 * Returns the employee information.
	 * 
	 * @return formatted employee information
	 */
	@Override
	public String toString() {
		return String.format("Employee[ name=%s %s %s, payrate=%.2f ]", first, middle, last, payrate);
	}
	
	/**
	 * Used to sort employees by their first names.
	 * 
	 * @param o employee to compare with
	 * @return the difference
	 */
	@Override
	public int compareTo(Employee o) {
		return first.compareTo(o.first);
	}
}
