package server.control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import server.mod.CourseInfo;
import server.mod.CourseProper;
import server.mod.Student;
import server.mod.TranscriptItem;

/**
 * Provides fields and methods to perform tasks the client requests
 * 
 * @author Wei Kang
 * @version 1.0
 * @since April 20, 2020
 *
 */
public class DoThings implements Runnable {
	/**
	 * The socket used to communicate with the client
	 */
	private Socket socket;
	/**
	 * The BufferedReader used to read from the client
	 */
	private BufferedReader socketIn;
	/**
	 * The ObjectOutputStream used to write to the client
	 */
	private ObjectOutputStream objSocketOut;
	/**
	 * The data control of the server
	 */
	private ServerDataControl dataControl;
	/**
	 * The student that represents the client
	 */
	private Student student;
	/**
	 * The course to be worked on
	 */
	private CourseProper course;

	/**
	 * Creates a new instance with the given argument
	 * 
	 * @param socket       The socket
	 * @param socketIn     The socketIn used to communicate with the client
	 * @param objSocketOut The socketOut used to commuincate with the client
	 */
	public DoThings(Socket socket, BufferedReader socketIn, ObjectOutputStream objSocketOut) {
		this.socket = socket;
		this.socketIn = socketIn;
		this.objSocketOut = objSocketOut;
	}

	@Override
	/**
	 * Waits for the client's request then performs the request
	 */
	public void run() {
		try {
			while (true) {
				String action = socketIn.readLine();
				switch (action) {
				case "LOGIN":
					login();
					break;
				case "SEARCH":
					search();
					break;
				case "DETAIL":
					displayDetail();
					break;
				case "REGISTER":
					register();
					break;
				case "DROP":
					drop();
					break;
				default:
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();

		} catch (Exception e) {

		} finally {
			System.out.println("Connection Closed");
			closeConnection();
		}
	}

	/**
	 * Searches the database for a course with the key provided by the client
	 * 
	 * @throws IOException
	 */
	private void search() throws IOException {
		// Reads the String to be searched from the client
		String input = socketIn.readLine();

		ArrayList<String> output = null;

		if (dataControl != null) {
			output = dataControl.search(input);
		}

		objSocketOut.writeObject(output);
	}

	/**
	 * Registers the student to the course
	 */
	private void register() {
		try {
			int section = Integer.parseInt(socketIn.readLine());
			boolean checkStudent = student.register(
					new CourseInfo(course.getName(), course.getNumber(), course.getDescription(), course.getUnit()));
			boolean check = false;
			if (checkStudent) {
				check = dataControl.register(student, course, section);
			}
			if (check) {
				objSocketOut.writeObject("SUCCESS");
				CourseInfo ci = new CourseInfo(course.getName(), course.getNumber(), course.getDescription(),
						course.getUnit());
				TranscriptItem ti = new TranscriptItem(ci, "ENROLLED");
				student.addToTranscript(ti);
				objSocketOut.writeObject(String.valueOf(ti));
			} else {
				objSocketOut.writeObject("FAIL");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Drops a course from the student's courses
	 * @throws IOException
	 */
	private void drop() throws IOException {
		if (course == null || student == null) {
			objSocketOut.writeObject("FAIL");
			return;
		}
		
		int section = Integer.parseInt(socketIn.readLine());
		
		boolean check = dataControl.drop(student, course, section);
		
		if (check) {
			objSocketOut.writeObject("SUCCESS");
			sendTranscript();
		} else {
			objSocketOut.writeObject("FAIL");
		}
	}

	/**
	 * Sends the detail of the course that the client selected
	 * 
	 * @throws IOException
	 */
	private void displayDetail() throws IOException {
		String courseName = socketIn.readLine();
		String courseNum = socketIn.readLine();
		if (dataControl != null) {
			course = dataControl.getCourse(courseName, courseNum);
		}

		objSocketOut.writeObject(course.getPreReq());
		objSocketOut.writeObject(course.getSection());
	}

	/**
	 * Disconnects the server from the client
	 */
	private void closeConnection() {
		try {
			socket.close();
			socketIn.close();
			objSocketOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {

		}
	}

	/**
	 * Sets the dataControl of this class
	 * 
	 * @param dataControl The dataControl
	 */
	public void setDataControl(ServerDataControl dataControl) {
		this.dataControl = dataControl;
	}

	/**
	 * Authenticates the user and sends the student's transcript if authentication
	 * succeeded
	 */
	public void login() {
		try {
			// Reads user name from client
			String username = socketIn.readLine();

			// Reads password from client
			String password = socketIn.readLine();

			// Authenticate user and create the student object
			student = dataControl.login(username, password);

			// If authentication failed, sends "FAILED" to client
			if (student == null) {
				objSocketOut.writeObject("FAILED");

				// If authentication succeeded, sends "SUCCESS" as well as the student's
				// transcript to client
			} else {
				objSocketOut.writeObject("SUCCESS");
				sendTranscript();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sends the transcript of the user after logging in the user
	 */
	private void sendTranscript() {
		if (student != null) {
			ArrayList<String> result = new ArrayList<>();
			ArrayList<TranscriptItem> transcript = student.getTranscript();
			for (TranscriptItem ti : transcript) {
				result.add(String.valueOf(ti));
			}
			try {
				objSocketOut.writeObject(result);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
