package client.view;

/**
 * Provides a method used by the SearchResultArea
 * 
 * @author Wei Kang
 * @version 1.0
 * @since April 20, 2020
 *
 */
public interface SearchResultListener {
	/**
	 * Submits the selected result to the server
	 * 
	 * @param wholeString The whole string from the selection
	 * @param name        The name of the course
	 * @param number      The number of the course
	 */
	public void submitResult(String wholeString, String name, String number);
}
