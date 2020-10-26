package server.mod;

/**
 * Provides information about a transcript item
 * 
 * @author Wei kang
 * @version 1.0
 * @since April 20, 2020
 *
 */
public class TranscriptItem {
	/**
	 * The course of the transcript item
	 */
	private CourseInfo course;
	/**
	 * The status of the transcript item
	 */
	private String status;

	/**
	 * Creates a TranscriptItem with the given arguments
	 * 
	 * @param course The course of the transcript item
	 * @param status The status of the transcript item
	 */
	public TranscriptItem(CourseInfo course, String status) {
		setCourse(course);
		setStatus(status);
	}

	@Override
	/**
	 * Provides information about this transcript item
	 */
	public String toString() {
		String s = "Course: " + course.getName() + " " + course.getNumber();
		s += "\nUnit: " + course.getUnit();
		s += "\nStatus: " + status + "\n\n";

		return s;
	}

	// Getters and Setters
	/**
	 * Sets the course
	 * 
	 * @param course The course
	 */
	public void setCourse(CourseInfo course) {
		this.course = course;
	}

	/**
	 * Returns the course
	 * 
	 * @return course
	 */
	public CourseInfo getCourse() {
		return course;
	}

	/**
	 * Returns the status
	 * 
	 * @return status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the status
	 * 
	 * @param status The status
	 */
	public void setStatus(String status) {
		this.status = status;
	}
}
