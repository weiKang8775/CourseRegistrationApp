package client.view;

/**
 * Provides fields and methods to listen to the login button on the login frame
 * 
 * @author Wei Kang
 * @version 1.0
 * @since April 20, 2020
 *
 */
public interface LoginListener {
	/**
	 * Submits the username and password to the server to authenticate
	 * 
	 * @param username The username
	 * @param password The password
	 */
	public void submit(String username, String password);
}
