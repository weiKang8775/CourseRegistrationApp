package client.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * Provides fields and methods to create a course info area
 * 
 * @author Wei Kang
 * @version 1.0
 * @since April 20, 2020
 *
 */
public class CourseInfoArea extends JPanel {
	/**
	 * The text area
	 */
	private JTextArea infoDisplayArea;
	/**
	 * The model for the combo box
	 */
	private DefaultComboBoxModel<String> model;
	/**
	 * The combo box used to select actions
	 */
	private JComboBox<String> actions;
	/**
	 * The combo box used to select sections
	 */
	private JComboBox<String> sections;
	/**
	 * The button that submits the action
	 */
	private JButton doActionButton;

	/**
	 * Constructs a new CourseInfoArea with the given listener
	 * 
	 * @param listener The listener used by the doActionButton
	 */
	public CourseInfoArea(CourseActionListener listener) {
		setLayout(new BorderLayout());
		infoDisplayArea = new JTextArea();
		infoDisplayArea.setEditable(false);
		setBorder(BorderFactory.createTitledBorder("Course Info"));
		Dimension d = getPreferredSize();
		d.width = 400;
		setPreferredSize(d);

		String[] theActions = { "Enroll", "Drop", "Do Nothing" };
		actions = new JComboBox<>(theActions);

		model = new DefaultComboBoxModel<>();

		sections = new JComboBox<>(model);

		doActionButton = new JButton("Do Action");
		doActionButton.addActionListener((ActionEvent e) -> {
			String action = String.valueOf(actions.getSelectedItem());
			String section = String.valueOf(sections.getSelectedItem());
			section = section.substring(2);
			listener.doAction(action, section);
			clearInfo();
			clearSection();
			disableButtons();
		});

		sections.setEnabled(false);
		actions.setEnabled(false);
		doActionButton.setEnabled(false);

		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new FlowLayout());

		buttonsPanel.add(sections);
		buttonsPanel.add(actions);
		buttonsPanel.add(doActionButton);

		add(infoDisplayArea, BorderLayout.CENTER);
		add(buttonsPanel, BorderLayout.SOUTH);
	}

	/**
	 * Enables the buttons
	 */
	public void enableButtons() {
		sections.setEnabled(true);
		actions.setEnabled(true);
		doActionButton.setEnabled(true);
	}

	/**
	 * Disables the buttons
	 */
	public void disableButtons() {
		sections.setEnabled(false);
		actions.setEnabled(false);
		doActionButton.setEnabled(false);
	}

	/**
	 * Clears the info area
	 */
	public void clearInfo() {
		infoDisplayArea.setText("");
	}

	/**
	 * Appends text to the info area
	 * 
	 * @param text The text
	 */
	public void appendText(String text) {
		infoDisplayArea.append(text + "\n");
	}

	/**
	 * Sets text of the info area
	 * 
	 * @param text The text
	 */
	public void setText(String text) {
		infoDisplayArea.setText(text);
	}

	/**
	 * Adds a section to the section combo box
	 * 
	 * @param num the section number
	 */
	public void addSection(int num) {
		model.addElement("L0" + num);
	}

	/**
	 * Clears the section combo box
	 */
	public void clearSection() {
		model.removeAllElements();
	}
}
