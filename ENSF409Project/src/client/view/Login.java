package client.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 * Provides fields and methods to make a login page
 * 
 * @author Wei Kang
 * @version 1.0
 * @since April 20, 2020
 *
 */
public class Login extends JFrame {
	/**
	 * The username field of the frame
	 */
	private JTextField username;
	/**
	 * The password field of the frame
	 */
	private JPasswordField password;
	/**
	 * The login button
	 */
	private JButton loginBtn;

	/**
	 * Creates a new instance of the class with the given listener
	 * 
	 * @param listener The listener used by the login button
	 */
	public Login(LoginListener listener) {
		super("Login");
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		username = new JTextField(15);
		password = new JPasswordField(15);
		loginBtn = new JButton("Login");

		loginBtn.addActionListener((ActionEvent e) -> {
			listener.submit(username.getText(), String.valueOf(password.getPassword()));
		});

		JPanel panel = new JPanel();

		panel.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.NONE;
		gc.insets = new Insets(0, 0, 20, 5);

		/*----------ROW 1----------*/
		gc.gridx = 0;
		gc.gridy = 3;
		gc.weightx = 0.2;
		gc.weighty = 0.2;
		gc.anchor = GridBagConstraints.LINE_END;

		panel.add(new JLabel("Username"), gc);

		gc.gridx = 1;
		gc.gridy = 3;
		gc.anchor = GridBagConstraints.LINE_START;

		panel.add(username, gc);

		/*----------ROW 2----------*/

		gc.gridx = 0;
		gc.gridy = 4;
		gc.anchor = GridBagConstraints.LINE_END;

		panel.add(new JLabel("Password"), gc);

		gc.gridx = 1;
		gc.gridy = 4;
		gc.anchor = GridBagConstraints.LINE_START;

		panel.add(password, gc);

		/*----------ROW 3----------*/

		gc.gridx = 1;
		gc.gridy = 5;
		gc.weightx = 5.0;
		gc.weighty = 5.0;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;

		panel.add(loginBtn, gc);

		panel.setBorder(new EmptyBorder(new Insets(50, 30, 50, 30)));

		add(panel);

		pack();
	}
}
