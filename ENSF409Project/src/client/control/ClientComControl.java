package client.control;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Provides fields and methods to communicate with the server
 * 
 * @author Wei Kang
 * @version 1.0
 * @since April 20, 2020
 *
 */
public class ClientComControl {
	/**
	 * The PrintWriter used to write to the server
	 */
	private PrintWriter socketOut;
	/**
	 * The socket used
	 */
	private Socket socket;
	/**
	 * The ObjectInputStream to read from the server
	 */
	private ObjectInputStream objSocketIn;

	/**
	 * Constructs a new instance of the class with the given arguments
	 * 
	 * @param serverName The name of the server
	 * @param portNumber The port number to connect to the server
	 */
	public ClientComControl(String serverName, int portNumber) {
		try {
			socket = new Socket(serverName, portNumber);
			socketOut = new PrintWriter(socket.getOutputStream(), true);
			objSocketIn = new ObjectInputStream(socket.getInputStream());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	/**
	 * Requests the server to search a course
	 * 
	 * @param target The key used for searching
	 * @return The server's respond
	 */
	public ArrayList<String> search(String target) {
		socketOut.println("SEARCH");
		socketOut.println(target);
		ArrayList<String> result = null;

		try {
			result = (ArrayList<String>) objSocketIn.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Displays details of a selected course
	 * 
	 * @param name   The name of the course
	 * @param number The number of the course
	 */
	public void displayDetail(String name, String number) {
		socketOut.println("DETAIL");
		socketOut.println(name);
		socketOut.println(number);
	}

	@SuppressWarnings("unchecked")
	/**
	 * Gets the pre-requisite of the selected course from the server
	 * 
	 * @return The information about the pre-requisite of the selected course
	 */
	public ArrayList<String> getPreReq() {
		try {
			ArrayList<String> preReq = (ArrayList<String>) objSocketIn.readObject();
			return preReq;
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	/**
	 * Gets the sections (course offerings) of the selected course from the server
	 * 
	 * @return The information about the sections of the selected course
	 */
	public ArrayList<String> getSection() {
		try {
			ArrayList<String> section = (ArrayList<String>) objSocketIn.readObject();
			return section;
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String drop(String section) {
		try {
			socketOut.println("DROP");
			socketOut.println(section);
			String result = (String)objSocketIn.readObject();
			return result;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return "FAIL";
	}

	/**
	 * Disconnects from the server
	 */
	public void closeConnection() {
		try {
			socketOut.close();
			objSocketIn.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Registers the student to the selected course
	 * 
	 * @param section The section of the course offering
	 * @return the server's response
	 */
	public String register(String section) {
		try {
			socketOut.println("REGISTER");
			socketOut.println(section);

			String result = (String) objSocketIn.readObject();

			if (result.equals("SUCCESS")) {
				String courseInfo = (String) objSocketIn.readObject();
				return courseInfo;
			}

			return result;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return "FAIL";
	}

	/**
	 * Checks the credentials with the data from the server to login the user
	 * 
	 * @param username The username of the user
	 * @param password The password of the user
	 * @return The server's response
	 */
	public String login(String username, String password) {
		String result = null;
		try {
			socketOut.println("LOGIN");
			socketOut.println(username);
			socketOut.println(password);

			result = (String) objSocketIn.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	/**
	 * Gets the transcript of the student from the server
	 * 
	 * @return The server's response
	 */
	public ArrayList<String> recieveTranscript() {
		ArrayList<String> result = null;
		try {
			result = (ArrayList<String>) objSocketIn.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return result;
	}
}
