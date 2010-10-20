package tsmtp;

import java.net.Socket;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Session implements Runnable {

	private Socket socket;
	private String buffer;
	PrintWriter out;
	BufferedReader in;

	public Session(Socket socket) {
		this.socket = socket;
	}

	public void run() {
		try {
			this.out = new PrintWriter(socket.getOutputStream(), true);
			this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			System.out.println("Running a session");
			while((buffer = in.readLine()) != null) {
				parseMessage(buffer);
				out.println(buffer);
			}
			System.out.println("Client disconnected: " + socket.getInetAddress().getHostAddress());
			in.close();
			out.close();
			socket.close();
		} catch (Exception e) {
		}
	}

	private Request parseMessage(String message) {
		ArrayList<String> tokens = message.split("\t ");
		String command = tokens.get(0);
		tokens.remove(0);
		ArrayList<String> arguments = tokens;
		return new Request(command, arguments);
	}
}
