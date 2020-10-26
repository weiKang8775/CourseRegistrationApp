package client.control;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import client.view.CourseActionListener;
import client.view.CourseInfoArea;
import client.view.Login;
import client.view.LoginListener;
import client.view.SearchBar;
import client.view.SearchResultArea;
import client.view.YourCourses;

/**
 * Provides fields and methods to communicate with the user
 * 
 * @author Wei Kang
 * @version 1.0
 * @since April 20, 2020
 *
 */
public class MainFrame extends JFrame {
	/**
	 * The search bar of the GUI
	 */
	private SearchBar searchBar;
	/**
	 * The search result area of the GUI
	 */
	private SearchResultArea resultArea;
	/**
	 * The course info area of the GUI
	 */
	private CourseInfoArea infoArea;
	/**
	 * The your courses are of the GUI
	 */
	private YourCourses yourCourseArea;
	/**
	 * The communication control of the client
	 */
	private ClientComControl comControl;
	/**
	 * The login frame of the GUI
	 */
	private Login login;

	/**
	 * Constructs a MainFrame with all the GUI elements
	 * 
	 * @param comControl The comControl of the MainFrame
	 */
	public MainFrame(ClientComControl comControl) {
		super("Main Screen");
		this.comControl = comControl;

		// Initializing the search bar
		searchBar = new SearchBar((String text) -> {
			if (text.length() == 0) {
				resultArea.clearResult();
			} else {
				search(text);
			}
		});

		// Initializing the search result area
		resultArea = new SearchResultArea((String courseDescription, String name, String number) -> {
			infoArea.setText(courseDescription + "\n\n");
			displayDetail(name, number);
			infoArea.enableButtons();
		});

		// Initializing the course info area
		infoArea = new CourseInfoArea(new CourseActionListener() {
			public void doAction(String action, String section) {
				if (action.equals("Enroll")) {
					register(section);
				} else if (action.equals("Drop")) {
					drop(section);
				}
			}
		});

		// Initializing the your courses area
		yourCourseArea = new YourCourses();

		// Initializing login frame
		login = new Login(new LoginListener() {
			public void submit(String username, String password) {
				login(username, password);
			}
		});

		// Adding window listener for login
		login.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				comControl.closeConnection();
			}
		});

		setLayout(new BorderLayout());

		add(searchBar, BorderLayout.NORTH);
		add(resultArea, BorderLayout.WEST);
		add(infoArea, BorderLayout.CENTER);
		add(yourCourseArea, BorderLayout.EAST);

		// Adding window listener for main screen
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				comControl.closeConnection();
			}
		});

		setSize(1200, 800);
		setVisible(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * Displays detail of the course
	 * 
	 * @param name   The name of the course
	 * @param number The number of the course
	 */
	public void displayDetail(String name, String number) {
		comControl.displayDetail(name, number);
		ArrayList<String> preReq = comControl.getPreReq();
		ArrayList<String> section = comControl.getSection();

		infoArea.appendText("Pre-Requisite\n");

		if (preReq.size() != 0) {
			for (String s : preReq) {
				infoArea.appendText(s);
			}
		} else {
			infoArea.appendText("NONE");
		}

		infoArea.appendText("\nSection\n");

		if (section.size() != 0) {
			for (String s : section) {
				infoArea.appendText(s);
			}
			infoArea.clearSection();
			for (int i = 1; i <= section.size(); i++) {
				infoArea.addSection(i);
			}
		} else {
			infoArea.appendText("NONE");
		}
	}

	/**
	 * Registers the student to the course offering with the section number
	 * 
	 * @param section The section number of the course offering
	 */
	public void register(String section) {
		String result = comControl.register(section);
		if (result.equals("FAIL")) {
			JOptionPane.showMessageDialog(this, "Enrollment Failed", "Error", JOptionPane.WARNING_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(this, "Successfully Enrolled");
			yourCourseArea.appendText(result);
		}
	}

	/**
	 * Requests the server to drop a course
	 * 
	 * @param section The section of the course offering
	 */
	public void drop(String section) {
		String result = comControl.drop(section);

		if (result.equals("SUCCESS")) {
			JOptionPane.showMessageDialog(this, "Successfully Dropped");
			ArrayList<String> transcript = comControl.recieveTranscript();
			yourCourseArea.setText("");
//			for (String s : transcript) {
//				System.out.println(s);
//			}
			yourCourseArea.appendText(transcript);
		} else {
			JOptionPane.showMessageDialog(this, "Drop Failed", "Error", JOptionPane.WARNING_MESSAGE);
		}
	}

	/**
	 * Requests the server to search for a course
	 * 
	 * @param target The key used for searching
	 */
	public void search(String target) {
		ArrayList<String> result = comControl.search(target);
		if (result != null) {
			resultArea.addResult(result);
		}
	}

	/**
	 * Logs the client in with the given credentials
	 * 
	 * @param username the username
	 * @param password the password
	 */
	public void login(String username, String password) {
		String result = comControl.login(username, password);
		if (result.equals("SUCCESS")) {
			setVisible(true);
			login.setVisible(false);
			displayTranscript();
		} else {
			JOptionPane.showMessageDialog(this, "Your username and password do not match our record.", "Error",
					JOptionPane.WARNING_MESSAGE);

		}
	}

	/**
	 * Displays the transcript of the student in the your courses area
	 */
	public void displayTranscript() {
		ArrayList<String> result = comControl.recieveTranscript();
		yourCourseArea.appendText(result);
	}

	public static void main(String[] args) {
		ClientComControl comControl = new ClientComControl("localhost", 9898);

		SwingUtilities.invokeLater(() -> {
			MainFrame mf = new MainFrame(comControl);
		});
	}
}
