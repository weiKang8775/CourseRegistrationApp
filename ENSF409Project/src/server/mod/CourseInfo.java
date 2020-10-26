package server.mod;

/**
 * Provides the basic information about a course
 * 
 * @author Wei Kang
 * @version 1.0
 * @since April 20, 2020
 *
 */
public class CourseInfo implements Comparable<CourseInfo> {
	/**
	 * The name of the course
	 */
	private String name;
	/**
	 * The number of the course
	 */
	private String number;
	/**
	 * The description of the course
	 */
	private String description;
	/**
	 * The number of units of the course
	 */
	private Integer unit;

	/**
	 * Constructs a new CourseInfo with the given argument
	 * 
	 * @param name        The name of the course
	 * @param number      The number of the course
	 * @param description The description of the course
	 * @param unit        The unit of the course
	 */
	public CourseInfo(String name, String number, String description, Integer unit) {
		setName(name);
		setNumber(number);
		setDescription(description);
		setUnit(unit);
	}

	@Override
	/**
	 * Provides a description of the course
	 */
	public String toString() {
		String s = "Course: " + name + " " + number + " - " + description;
		return s;
	}

	/**
	 * Compares to see if two courses are the same by comparing their name and
	 * number
	 */
	public int compareTo(CourseInfo ci) {
		int check = name.compareTo(ci.getName());
		if (check == 0) {
			check += number.compareTo(ci.getNumber());
		}
		return check;
	}
	
	public int compareTo(CourseProper cp) {
		int check = name.compareTo(cp.getName());
		if (check == 0) {
			check += number.compareTo(cp.getNumber());
		}
		return check;
	}

	// Getters and Setters
	/**
	 * Returns the name of the course
	 * 
	 * @return The name of the course
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the course
	 * 
	 * @param name The new name of the course
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the number of the course
	 * 
	 * @return The number of the course
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * Sets the number of the course
	 * 
	 * @param number The new number of the course
	 */
	public void setNumber(String number) {
		this.number = number;
	}

	/**
	 * Returns the description of the course
	 * 
	 * @return The description of the course
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description of the course
	 * 
	 * @param description The new description of the course
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Returns the number of units of the course
	 * 
	 * @return The number of units of the course
	 */
	public Integer getUnit() {
		return unit;
	}

	/**
	 * Sets the number of units of the course
	 * 
	 * @param unit The new number of units of the course
	 */
	public void setUnit(Integer unit) {
		this.unit = unit;
	}

}
