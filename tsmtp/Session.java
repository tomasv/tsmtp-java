package tsmtp;

import java.net.Socket;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;

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
				out.println(buffer);
			}
			System.out.println("Client disconnected: " + socket.getInetAddress().getHostAddress());
			in.close();
			out.close();
			socket.close();
		} catch (Exception e) {
		}
	}
}
