package tsmtp;

import java.net.Socket;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

import tsmtp.Request;
import tsmtp.states.*;

public class Session implements Runnable {

	private Socket socket;
	private String buffer;
	private String domain;

	public PrintWriter out;
	public BufferedReader in;
	public Message message;

	private SessionState state;

	public Session(Socket socket) {
		this.socket = socket;
		this.state = new NewState();
		this.message = new Message();
	}

	public void run() {
		try {
			this.out = new PrintWriter(socket.getOutputStream(), true);
			this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			System.out.println("Running a session");

			while((buffer = in.readLine()) != null) {
				System.out.println("Received message");
				Request req = parseRequest(buffer);
				state.handle(this, req);
			}

			System.out.println("Client disconnected: " + socket.getInetAddress().getHostAddress());
			in.close();
			out.close();
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void reset() {
		this.message = new Message();
	}

	public void submit(Message message) {
		(new Sender(message)).run();
	}

	private Request parseRequest(String message) {
		ArrayList<String> tokens = new ArrayList<String>(Arrays.asList(message.split("\t ")));
		String command = tokens.get(0);
		tokens.remove(0);
		ArrayList<String> arguments = tokens;
		return new Request(command, arguments);
	}

	public void setState(SessionState state) {
		this.state = state;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getBuffer() {
		return buffer;
	}
}
