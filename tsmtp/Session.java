package tsmtp;

import java.net.Socket;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import tsmtp.Request;
import tsmtp.states.*;

public class Session implements Runnable {

	private Socket socket;
	private String buffer;
	private String domain;

	public PrintWriter out;
	public BufferedReader in;
	public Message message;

	private HashMap<String, String> response = new HashMap<String, String>();

	private SessionState state;

	public Session(Socket socket) {
		this.socket = socket;
		this.state = new NewState();
		this.message = new Message();
		initResponses();
	}

	public void run() {
		try {
			this.out = new PrintWriter(socket.getOutputStream(), true);
			this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			System.out.println("session start");
			// greet the user
			respond("HELLO");

			while((buffer = in.readLine()) != null) {
				try {
					Request req = new Request(buffer);
					state.handle(this, req);
					if (state == null)
						break;
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("Error inside the session loop.");
				}
			}

			System.out.println("session end: " + socket.getInetAddress().getHostAddress());
			in.close();
			out.close();
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error outside of the session loop.");
		}
	}

	public void reset() {
		this.message = new Message();
	}

	public void submit(Message message) {
		(new Sender(message)).run();
	}

	public void setState(SessionState state) {
		if (state != null)
			System.out.println("changing state to " + state.getClass().getName());
		else
			System.out.println("changing state to final");
		this.state = state;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getBuffer() {
		return buffer;
	}

	public void respond(String abrev) {
		out.println(getResponse(abrev));
	}

	public String getResponse(String abrev) {
		return response.get(abrev);
	}

	private void initResponses() {
		String[][] responses = {
			{ "HELLO", "220 SMTP tsmtp" },
			{ "BYE", "221 Bye" },
			{ "OK", "250 Ok" } ,
			{ "DATA", "354 End data with <CR><LF>.<CR><LF>" },
			{ "SYNTAX", "501 Syntax error" },
			{ "OOO" ,"503 Commands out of order: HELO -> (MAIL -> RCPT (+) -> DATA -> text -> CRLF.CRLF)(*) -> QUIT" }
		};
		for (String[] pair : responses)
			response.put(pair[0], pair[1]);
	}
}
