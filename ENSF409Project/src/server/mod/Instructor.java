package server.mod;

/**
 * Provides information about an instructor
 * 
 * @author Wei Kang
 * @version 1.0
 * @since April 20, 2020
 *
 */
public class Instructor {
	/**
	 * The name of the instructor
	 */
	private String name;

	/**
	 * Creates a new Instructor with the given name
	 * 
	 * @param name The name of the instructor
	 */
	public Instructor(String name) {
		setName(name);
	}

	/**
	 * Returns the name of the instructor
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the instructor
	 * 
	 * @param name The name of the instructor
	 */
	public void setName(String name) {
		this.name = name;
	}
}
