package whispers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

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

		try (ServerSocket serverSocket = new ServerSocket(portNumber);
				Socket clientSocket = serverSocket.accept();
				BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
				PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));) {

			String inputLine, outputLine;

			// Start the chat with a message from Server (you)
			out.println("Hello user" + clientSocket.getInetAddress().toString());
			while (true) {
				// Wait for message from Client (only one line)
				inputLine = in.readLine();
				System.out.println(inputLine);
				
				// Then send a reply (only one line)
				outputLine = stdIn.readLine();
				if (outputLine != null) {
					out.println(outputLine);
				}
			}

		} catch (IOException e) {
			System.out.println(
					"Exception caught when trying to listen on port " + portNumber + " or listening for a connection");
			System.out.println(e.getMessage());
		}
	}

}
