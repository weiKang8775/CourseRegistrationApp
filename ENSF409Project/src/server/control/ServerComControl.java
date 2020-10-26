package server.control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Provides fields and methods to communicate with the client
 * 
 * @author Wei Kang
 * @version 1.0
 * @since April 20, 2020
 *
 */
public class ServerComControl {
	/**
	 * The server socket
	 */
	private ServerSocket serverSocket;
	/**
	 * The socket used to communicate with the client
	 */
	private Socket socket;
	/**
	 * The BufferedReader used to read from the client
	 */
	private BufferedReader socketIn;
	/**
	 * The ObjectOutputStream used to write to the client
	 */
	private ObjectOutputStream objSocketOut;
	/**
	 * The thread pool used
	 */
	private ExecutorService threadPool;
	/**
	 * The data control of the server
	 */
	private ServerDataControl dataControl;

	/**
	 * Creates a new instance of the class with the given arguments
	 * 
	 * @param portNum     The port number that connects to the database
	 * @param dataControl The data control of the server
	 */
	public ServerComControl(int portNum, ServerDataControl dataControl) {
		this.dataControl = dataControl;
		try {
			serverSocket = new ServerSocket(portNum);
			threadPool = Executors.newCachedThreadPool();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creates a new thread when a new client connects to the server
	 */
	public void runServer() {
		try {
			while (true) {
				socket = serverSocket.accept();
				socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				objSocketOut = new ObjectOutputStream(socket.getOutputStream());

				DoThings dt = new DoThings(socket, socketIn, objSocketOut);
				dt.setDataControl(dataControl);

				threadPool.execute(dt);
			}
		} catch (IOException e) {
			e.printStackTrace();
			closeConnection();
		}
	}

	/**
	 * Closes the server
	 */
	public void closeConnection() {
		try {
			socketIn.close();
			objSocketOut.close();
			socket.close();
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		ServerDataControl sdc = new ServerDataControl("jdbc:mysql://localhost:3306", "root", "12345678");
		ServerComControl scc = new ServerComControl(9898, sdc);
		scc.runServer();
	}
}
