package server.control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import server.mod.CourseInfo;
import server.mod.CourseOffering;
import server.mod.CourseProper;
import server.mod.Instructor;
import server.mod.Student;
import server.mod.TranscriptItem;

/**
 * Provides fields and methods to communicate with the database
 * 
 * @author Wei Kang
 * @version 1.0
 * @since April 20, 2020
 *
 */
public class ServerDataControl {
	/**
	 * The connection to the database
	 */
	private Connection connection;

	/**
	 * The statement made from connection
	 */
	private Statement s;

	/**
	 * The result set of the statement
	 */
	private ResultSet rs;

	/**
	 * Constructs a new instance of the class with the given arguments
	 * 
	 * @param url      The url of the database
	 * @param username The username of the database
	 * @param password The password of the database
	 */
	public ServerDataControl(String url, String username, String password) {
		try {
			connection = DriverManager.getConnection(url, username, password);
			s = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Searches the database with the target string as a key
	 * 
	 * @param target The String used as a key
	 * @return The result of the search as a String array
	 */
	public ArrayList<String> search(String target) {
		ArrayList<String> result = new ArrayList<>();
		try {

			// Chooses the correct database
			s.execute("USE ensf409_wk_course");

			// Splits the target into two parts - course name and course number
			String[] temp = target.split("\\s+");
			String query;

			// If the target is just white space, return an empty list
			if (temp.length < 1) {
				return result;
			}

			// If the target only contains the course name or course number
			if (temp.length == 1) {
				query = "SELECT name, number, description FROM course_catalogue WHERE name REGEXP ('^" + temp[0]
						+ "') OR number REGEXP ('^" + temp[0] + "')";
			} else {
				query = "SELECT name, number, description FROM course_catalogue WHERE name REGEXP ('^" + temp[0]
						+ "') AND number REGEXP ('^" + temp[1] + "')";
			}

			rs = s.executeQuery(query);

			while (rs.next()) {
				String element = rs.getString("name") + " " + rs.getString("number") + " - "
						+ rs.getString("description");
				result.add(element);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Creates a course and sets it to the course member to be used
	 * 
	 * @param name   The name of the course
	 * @param number The number of the course
	 * @return the course
	 */
	public CourseProper getCourse(String name, String number) {
		CourseProper course = null;
		try {
			s.execute("USE ensf409_wk_course");

			String query = "SELECT * FROM course_catalogue WHERE name = '" + name + "' AND number = " + number;

			rs = s.executeQuery(query);

			rs.next();

			course = new CourseProper(name, number, rs.getString("description"),
					Integer.parseInt(rs.getString("unit")));

			getOffering(course);

			getPreReq(course);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return course;
	}

	/**
	 * Gets the course offerings of the course from the database
	 * 
	 * @param course The course
	 * @throws SQLException
	 */
	private void getOffering(CourseProper course) throws SQLException {
		s.execute("USE ensf409_wk_course");

		String query = "SELECT * FROM course_offering co JOIN course_catalogue cc ON co.course_id = cc.id WHERE cc.name = '"
				+ course.getName() + "' AND cc.number = " + course.getNumber();

		rs = s.executeQuery(query);

		while (rs.next()) {
			if (rs.getString("name") == null) {
				break;
			}
			int section = Integer.parseInt(rs.getString("section"));
			int capacity = Integer.parseInt(rs.getString("capacity"));
			int enrolled = Integer.parseInt(rs.getString("enrolled"));
			CourseOffering co = new CourseOffering(section, capacity, enrolled,
					new Instructor(rs.getString("instructor_id")));
			course.addOffering(co);
		}

	}

	/**
	 * Gets the pre-req of the course from the database
	 * 
	 * @param course the course
	 * @throws SQLException
	 */
	private void getPreReq(CourseProper course) throws SQLException {
		s.execute("USE ensf409_wk_course");

		String query = "SELECT ccp.name, ccp.number, ccp.description, ccp.unit FROM course_catalogue cc JOIN pre_req pr ON cc.id = pr.course_id LEFT JOIN course_catalogue ccp ON ccp.id = pr.pre_req_id WHERE cc.name = '"
				+ course.getName() + "' AND cc.number = " + course.getNumber();

		rs = s.executeQuery(query);

		while (rs.next()) {
			if (rs.getString("name") == null) {
				break;
			}
			String courseName = rs.getString("name");
			String courseNumber = rs.getString("number");
			String description = rs.getString("description");
			Integer unit = Integer.parseInt(rs.getString("unit"));
			CourseInfo ci = new CourseInfo(courseName, courseNumber, description, unit);
			course.addPreReq(ci);
		}

	}

	/**
	 * Registers the student to the course
	 * 
	 * @param student The student
	 * @param course  The course
	 * @param section The section of the course
	 * @return True if registration was successful or false otherwise
	 */
	public boolean register(Student student, CourseProper course, int section) {
		if (!course.checkPreReq(student.getTranscript())) {
			return false;
		}
		try {
			s.execute("USE ensf409_wk_course");

			int enrolled = course.getOffering(section).addStudent();

			String query = "UPDATE course_offering SET enrolled = " + enrolled
					+ " WHERE course_id = (SELECT id FROM course_catalogue WHERE name = '" + course.getName()
					+ "' AND number = " + course.getNumber() + ") AND section = " + section;

			s.executeUpdate(query);

			query = "INSERT INTO ensf409_wk_people.transcript VALUES (" + student.getId()
					+ ", (SELECT id FROM course_catalogue WHERE name = '" + course.getName() + "' AND number = "
					+ course.getNumber() + "), 1)";

			s.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Authenticates the student's login information and creates a new Student
	 * object for the user
	 * 
	 * @param username The user name of the user
	 * @param password The password of the user
	 * @return The student object containing all the student record
	 */
	public Student login(String username, String password) {
		Student student = null;
		try {
			s.execute("USE ensf409_wk_people");

			int id = authenticate(username, password);

			// If authentication failed, return null
			if (id == -1) {
				return null;
			}

			// Getting the first name and last name of the student
			String query = "SELECT first_name, " + "last_name " + "FROM info " + "WHERE id = " + id;

			rs = s.executeQuery(query);

			rs.next();

			String firstName = rs.getString("first_name");
			String lastName = rs.getString("last_name");

			student = new Student(firstName, lastName, id);

			getTranscript(student);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return student;
	}

	/**
	 * Authenticates the user name and password. Return the student ID if successful
	 * or -1 if not
	 * 
	 * @param username The user name of the client
	 * @param password The password of the client
	 * @return The student ID if authentication is successful or -1 if not
	 */
	private int authenticate(String username, String password) throws SQLException {
		int id = -1;

		// Select the correct data base
		s.execute("USE ensf409_wk_people");

		// Selecting a table where id and login matches the argument
		String query = "SELECT id FROM student_login WHERE username = '" + username + "' AND password = '" + password
				+ "'";

		rs = s.executeQuery(query);

		// If there is no match, return -1
		if (!rs.next()) {
			return -1;
		}

		// Gets the student ID
		id = Integer.parseInt(rs.getString("id"));

		// If there is more than one match, return -1
		if (rs.next()) {
			return -1;
		}

		return id;
	}

	/**
	 * Drops a course from student's courses
	 * 
	 * @param student The student
	 * @param course  The course
	 * @param section The section of the course offering
	 * @return true if dropped successfully or false otherwise
	 */
	public boolean drop(Student student, CourseProper course, int section) {
		boolean check = false;
		for (TranscriptItem ti : student.getTranscript()) {
			if (ti.getCourse().compareTo(course) == 0
					&& (ti.getStatus().equals("ENROLLED") || ti.getStatus().equals("IN PROGRESS"))) {
				check = true;
				break;
			}
		}

		if (check) {
			try {
				s.execute("USE ensf409_wk_course");

				String query = "SELECT id FROM course_catalogue cc JOIN course_offering co ON cc.id = co.course_id WHERE cc.name = '"
						+ course.getName() + "' AND cc.number = " + course.getNumber();

				int enrolled = course.getOffering(section).removeStudent();

				rs = s.executeQuery(query);

				rs.next();

				int id = Integer.parseInt(rs.getString("id"));

				query = "UPDATE course_offering SET enrolled = " + enrolled + " WHERE course_id = " + id
						+ " AND section = " + section;

				s.executeUpdate(query);

				s.execute("USE ensf409_wk_student");

				query = "DELETE FROM transcript WHERE id = " + student.getId() + " AND course_id = " + id;

				s.executeUpdate(query);
				
				getTranscript(student);

				return true;
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * Gets the transcript of the student on record
	 * 
	 * @param id The id of the student
	 * @return An ArrayList containing the each transcript item of the student
	 * @throws SQLException
	 */
	private void getTranscript(Student student) throws SQLException {
		student.emptyTranscript();
		String query = "SELECT cc.name AS course_name, " + "cc.number AS course_number, " + "cc.description, "
				+ "cc.unit, " + "p.name AS status " + "FROM info i " + "JOIN transcript t " + "ON i.id = t.id "
				+ "JOIN ensf409_wk_course.course_catalogue cc " + "ON t.course_id = cc.id " + "JOIN  progress p "
				+ "ON t.progress = p.id " + "WHERE i.id = " + student.getId();

		rs = s.executeQuery(query);

		while (rs.next()) {
			String status = rs.getString("status");
			String courseName = rs.getString("course_name");
			String courseNumber = rs.getString("course_number");
			String description = rs.getString("description");
			int unit = Integer.parseInt(rs.getString("unit"));
			CourseInfo course = new CourseInfo(courseName, courseNumber, description, unit);
			student.addToTranscript(new TranscriptItem(course, status));
		}
	}
}
