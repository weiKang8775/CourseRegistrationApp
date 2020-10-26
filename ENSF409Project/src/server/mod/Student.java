package server.mod;

import java.util.ArrayList;

/**
 * Provides fields and methods for a Student object
 * 
 * @author Wei Kang
 * @version 1.0
 * @since April 20, 2020
 *
 */
public class Student implements Comparable<Student> {
	/**
	 * The first name of the student
	 */
	private String firstName;
	/**
	 * The last name of the student
	 */
	private String lastName;
	/**
	 * The id of the student
	 */
	private Integer id;
	/**
	 * The transcript of the student
	 */
	private ArrayList<TranscriptItem> transcript;

	/**
	 * Constructs a new Student with the given arguments
	 * 
	 * @param firstName The first name of the student
	 * @param lastName  The last name of the student
	 * @param id        The id of the student
	 */
	public Student(String firstName, String lastName, Integer id) {
		setFirstName(firstName);
		setLastName(lastName);
		setId(id);
		transcript = new ArrayList<>();
	}

	/**
	 * Adds an item to the transcript
	 * 
	 * @param e The transcript item
	 */
	public void addToTranscript(TranscriptItem e) {
		transcript.add(e);
	}

	/**
	 * Checks if the student is already enrolled in a course
	 * 
	 * @param ci The course
	 * @return true if the student has enrolled or has taken the course or false
	 *         otherwise
	 */
	public boolean register(CourseInfo ci) {
		for (TranscriptItem ti : transcript) {
			if (ti.getCourse().compareTo(ci) == 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Sends the enrollment status of the student
	 * 
	 * @return The TranscriptItems that have ENROLLED as the status
	 */
	public ArrayList<TranscriptItem> sendEnrollmentStatus() {
		ArrayList<TranscriptItem> result = new ArrayList<>();
		for (TranscriptItem ti : transcript) {
			if (ti.getStatus().equals("ENROLLED")) {
				result.add(ti);
			}
		}
		return result;
	}

	/**
	 * Compares the id of another student
	 */
	public int compareTo(Student s) {
		return id.compareTo(s.getId());
	}
	
	/**
	 * Removes an item from transcript
	 * @param courseName The name of the course to be removed
	 * @param courseNumber The number of the course to be removed
	 */
	public void removeFromTranscript(String courseName, String courseNumber) {
		for (TranscriptItem t : transcript) {
			if (t.getCourse().getName().equals(courseName) && t.getCourse().getNumber().equals(courseNumber)) {
				transcript.remove(t);
			}
		}
	}

	// Getters and Setters
	/**
	 * Returns the id of the student
	 * 
	 * @return id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Sets the id of the student
	 * 
	 * @param id The new id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Returns the first name of the student
	 * 
	 * @return firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name of the student
	 * 
	 * @param firstName The new first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Returns the last name of the student
	 * 
	 * @return lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the last name of the student
	 * 
	 * @param lastName The new last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Returns the transcript of the student
	 * 
	 * @return transcript
	 */
	public ArrayList<TranscriptItem> getTranscript() {
		return transcript;
	}
	
	/**
	 * Empties the transcript
	 */
	public void emptyTranscript() {
		transcript = new ArrayList<>();
	}
}
