package client.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Provides fields and methods to create a your courses area
 * 
 * @author Wei Kang
 * @version 1.0
 * @since April 20, 2020
 *
 */
public class YourCourses extends JPanel {
	/**
	 * The text Area of the panel
	 */
	private JTextArea textArea;
	/**
	 * The scroll pane used
	 */
	private JScrollPane displayArea;

	/**
	 * Constructs a your course area
	 */
	public YourCourses() {
		setLayout(new BorderLayout());
		Dimension d = getPreferredSize();
		d.width = 400;
		setPreferredSize(d);
		setBorder(BorderFactory.createTitledBorder("Your Courses"));
		textArea = new JTextArea();
		textArea.setEditable(false);

		displayArea = new JScrollPane(textArea);

		add(displayArea, BorderLayout.CENTER);
	}

	/**
	 * Appends text to the your courses area
	 * 
	 * @param text The array of string to be appended
	 */
	public void appendText(ArrayList<String> text) {
		for (String s : text) {
			textArea.append(s);
		}
	}

	/**
	 * Append text to the your courses area
	 * 
	 * @param text The string to be appended
	 */
	public void appendText(String text) {
		textArea.append(text);
	}

	/**
	 * Sets the text area to the given argument
	 * 
	 * @param text The text
	 */
	public void setText(String text) {
		textArea.setText(text);
	}
}
