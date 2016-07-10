package whispers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

public class MultiServerThread extends Thread {
	private Socket socket = null;

	public MultiServerThread(Socket socket) {
		this.socket = socket;
	}

	public void run() {
		try (BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));) {

			String outputLine;

			// Start the chat with a message from Server (you)
			out.println("Hello user" + socket.getInetAddress().toString());

			new ReadThread(in).start();

			while (true) {
				// Wait for message from Client (only one line)
				// inputLine = in.readLine();
				// System.out.println(inputLine);

				// Then send a reply (only one line)
				outputLine = stdIn.readLine();
				if (outputLine != null) {
					out.println(outputLine);
				}
			}

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	static class ReadThread extends Thread {
		private BufferedReader reader = null;

		public ReadThread(BufferedReader reader) {
			this.reader = reader;
		}

		public void run() {

			try {
				while (true) {
					System.out.println(reader.readLine());
				}
			} catch(SocketException e) {
				System.out.println("Client disconnected");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}
