package client.view;

/**
 * Provides a method used by SearchBar
 * 
 * @author Wei Kang
 * @version 1.0
 * @since April 20, 2020
 *
 */
public interface SearchListener {
	/**
	 * Submits the text to the server for searching
	 * 
	 * @param text The text used as the key for searching
	 */
	public void submitSearch(String text);
}
