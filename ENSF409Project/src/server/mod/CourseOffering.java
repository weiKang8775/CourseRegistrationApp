package server.mod;

import java.util.ArrayList;

/**
 * Provides informations about a course offering
 * 
 * @author Wei Kang
 * @version 1.0
 * @since April 20, 2020
 *
 */
public class CourseOffering {
	/**
	 * The capacity of the course offering
	 */
	private Integer capacity;
	/**
	 * The section of the course offering
	 */
	private Integer section;
	/**
	 * The number of enrolled students of the course offering
	 */
	private Integer enrolled;
	/**
	 * The instructor of the course offering
	 */
	private Instructor instructor;

	/**
	 * Constructs a course offering with the given arguments
	 * 
	 * @param section    The section
	 * @param capacity   The capacity
	 * @param enrolled   The number of students enrolled
	 * @param instructor The instructor
	 */
	public CourseOffering(Integer section, Integer capacity, Integer enrolled, Instructor instructor) {
		setCapacity(capacity);
		setSection(section);
		setEnrolled(enrolled);
		setInstructor(instructor);
	}

	/**
	 * Provides a description of the course offering
	 */
	public String toString() {
		String s = "Section: L0" + section + "\nCapacity: " + enrolled + "/" + capacity + "\nInstructor: "
				+ instructor.getName();
		return s;
	}

	// Getters and Setters
	/**
	 * Sets the capacity
	 * 
	 * @param c The capacity
	 */
	public void setCapacity(Integer c) {
		capacity = c;
	}

	/**
	 * Returns the capacity
	 * 
	 * @return The capacity
	 */
	public Integer getCapacity() {
		return capacity;
	}

	/**
	 * Sets the section
	 * 
	 * @param s The section
	 */
	public void setSection(Integer s) {
		section = s;
	}

	/**
	 * Returns the section
	 * 
	 * @return The section
	 */
	public Integer getSection() {
		return section;
	}

	/**
	 * Sets the instructor
	 * 
	 * @param i The instructor
	 */
	public void setInstructor(Instructor i) {
		instructor = i;
	}

	/**
	 * Returns the instructor
	 * 
	 * @return The instructor
	 */
	public Instructor getInstructor() {
		return instructor;
	}

	/**
	 * Returns the number of enrolled students
	 * 
	 * @return enrolled
	 */
	public Integer getEnrolled() {
		return enrolled;
	}

	/**
	 * Sets the number of enrolled student
	 * 
	 * @param enrolled The number of enrolled students
	 */
	public void setEnrolled(Integer enrolled) {
		this.enrolled = enrolled;
	}
	
	/**
	 * Adds a student to this course offering
	 * @return the number of enrolled
	 */
	public int addStudent() {
		return ++enrolled;
	}
	
	/**
	 * Removes a student from this course offering
	 * @return the number of enrolled
	 */
	public int removeStudent() {
		if (enrolled - 1 < 0) {
			return enrolled;
		} else {
			return --enrolled;
		}

	}
}
