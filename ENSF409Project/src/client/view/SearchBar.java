package client.view;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

/**
 * Provides fields and methods to create a search bar
 * 
 * @author Wei Kang
 * @version 1.0
 * @since April 20, 2020
 *
 */
public class SearchBar extends JPanel {
	/**
	 * The text field used as a search bar
	 */
	private JTextField searchBar;

	/**
	 * Constructs a search bar with the given listener
	 * 
	 * @param listener The listener used by the search bar
	 */
	public SearchBar(SearchListener listener) {
		Border padding = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		Border innerBorder = BorderFactory.createTitledBorder("Search");
		setBorder(BorderFactory.createCompoundBorder(padding, innerBorder));
		setLayout(new BorderLayout());
		searchBar = new JTextField(20);

		searchBar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if ((e.getKeyChar() >= 'a' && e.getKeyChar() <= 'z') || (e.getKeyChar() >= 'A' && e.getKeyChar() <= 'Z')
						|| (e.getKeyChar() >= '0' && e.getKeyChar() <= '9') || e.getKeyChar() == 8) {
					listener.submitSearch(searchBar.getText());
				}
			}
		});

		add(searchBar, BorderLayout.CENTER);

	}
}
