package client.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;

/**
 * Provides fields and methods to create a search result area
 * 
 * @author Wei Kang
 * @version 1.0
 * @since April 20, 2020
 *
 */
public class SearchResultArea extends JPanel {
	/**
	 * The list containing the result of a search
	 */
	private JList<String> results;
	/**
	 * The model for results
	 */
	private DefaultListModel<String> model;
	/**
	 * The listener for results
	 */
	private SearchResultListener listener;

	/**
	 * Constructs a search result area with a listener
	 * 
	 * @param e The listener used by results
	 */
	public SearchResultArea(SearchResultListener e) {
		Dimension d = getPreferredSize();
		d.width = 300;
		setPreferredSize(d);
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createTitledBorder("Results"));

		results = new JList<>();
		model = new DefaultListModel<>();
		results.setModel(model);
		add(results);

		setListener(e);
		results.addListSelectionListener((ListSelectionEvent event) -> {
			if (!event.getValueIsAdjusting()) {
				String selected = results.getSelectedValue();
				if (selected != null) {
					String[] temp = selected.split("\\s+");
					listener.submitResult(selected, temp[0], temp[1]);
				}
			}
		});
	}

	/**
	 * Adds the searchResult to the model and updates the results
	 * 
	 * @param searchResults The search result
	 */
	public void addResult(ArrayList<String> searchResults) {

		model.clear();
		for (String s : searchResults) {
			model.addElement(s);

		}
		results.setModel(model);
	}

	/**
	 * Sets the listener to the given argument
	 * 
	 * @param e The listener
	 */
	public void setListener(SearchResultListener e) {
		listener = e;
	}

	/**
	 * Clears the results
	 */
	public void clearResult() {
		model.removeAllElements();
	}
}
