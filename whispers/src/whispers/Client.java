package whispers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

	public static void main(String[] args) {
		if (args.length != 2) {
			System.err.println("Usage: java Client <host name> <port number>");
			System.exit(1);
		}

		String hostName = args[0];
		// default initialisation 
		int portNumber = -1;
		try {
			portNumber = Integer.parseInt(args[1]);
		} catch (NumberFormatException e) {
			System.err.println("Argument: " + args[1] + " is not a port number");
			System.exit(1);
		}

		if (portNumber < 1024 || portNumber > 65536) {
			System.err.println("Argument: " + portNumber + " is not a valid port number (between 1024 and 65536)");
			System.exit(1);
		}

		try (Socket kkSocket = new Socket(hostName, portNumber);
				PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
				BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
				BufferedReader in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));) {

			String fromServer;
			String fromUser;

			while (true) {
				// read message from server (only one line)
				fromServer = in.readLine();
				System.out.println("Server: " + fromServer);

				// then reply to server (only one line)
				fromUser = stdIn.readLine();
				if (fromUser != null) {
					System.out.println("Client: " + fromUser);
					out.println(fromUser);
				}
			}
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host " + hostName);
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to " + hostName);
			System.exit(1);
		}
	}

}
