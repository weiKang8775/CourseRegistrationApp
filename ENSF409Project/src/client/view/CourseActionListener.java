package client.view;

/**
 * Provides a method that the listeners of the CourseInfoArea uses
 * 
 * @author Wei Kang
 * @version 1.0
 * @since April 20, 2020
 *
 */
public interface CourseActionListener {
	/**
	 * Requests the server to perform action given in the argument
	 * 
	 * @param action  The action
	 * @param section The section of the course offering
	 */
	public void doAction(String action, String section);
}
