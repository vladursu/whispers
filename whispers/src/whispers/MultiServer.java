package whispers;

import java.io.IOException;
import java.net.ServerSocket;

public class MultiServer {

	public static void main(String[] args) {

		if (args.length != 1) {
			System.err.println("Usage: java Server <port number>");
			System.exit(1);
		}

		// default initialisation
		int portNumber = -1;
		try {
			portNumber = Integer.parseInt(args[0]);
		} catch (NumberFormatException e) {
			System.err.println("Argument: " + args[0] + " is not a port number");
			System.exit(1);
		}

		if (portNumber < 1024 || portNumber > 65536) {
			System.err.println("Argument: " + portNumber + " is not a valid port number (between 1024 and 65536)");
			System.exit(1);
		}

		try (ServerSocket serverSocket = new ServerSocket(portNumber);) {

			while (true) {
				new MultiServerThread(serverSocket.accept()).start();
			}

		} catch (IOException e) {
			System.out.println(
					"Exception caught when trying to listen on port " + portNumber + " or listening for a connection");
			System.out.println(e.getMessage());
		}
	}

}
