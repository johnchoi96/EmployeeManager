/**
 * 
 */
package io.johnchoi.skbs.objects;

/**
 * Class that defines one employee.
 * 
 * @author John Choi
 * @since 07302018
 */
public class Employee implements Comparable<Employee> {

	private String first;
	private String last;
	private double payrate;
	
	/**
	 * Constructs one employee object.
	 * 
	 * @param first name of employee
	 * @param last name of employee
	 * @param wage of the employee
	 */
	public Employee(String first, String last, double wage) {
		this.first = first;
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
	 * Setter for first name.
	 * 
	 * @param first the first to set
	 */
	public void setFirst(String first) {
		this.first = first;
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
	 * Setter for last name.
	 * 
	 * @param last the last to set
	 */
	public void setLast(String last) {
		this.last = last;
	}

	/**
	 * Getter for wage.
	 * 
	 * @return the wage
	 */
	public double getPayrate() {
		return payrate;
	}

	/**
	 * Setter for wage.
	 * 
	 * @param wage the wage to set
	 */
	public void setPayrate(double payRate) {
		this.payrate = payRate;
	}
	
	/**
	 * Used to save current state of program.
	 * 
	 * @return info to write out to file
	 */
	public String saveInfo() {
		return String.format("%s %s %.2f", first, last, payrate);
	}
	
	/**
	 * Calculates wage to be paid for the week.
	 * TODO
	 * @param hours worked
	 * @return wage to be paid
	 */
	public double calculateWage(double hours) {
		return 0;
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
		if (Double.doubleToLongBits(payrate) != Double.doubleToLongBits(other.payrate))
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
		return String.format("Employee[ name=%s %s, payrate=%.2f ]", first, last, payrate);
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
