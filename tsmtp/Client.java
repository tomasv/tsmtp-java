package tsmtp;

import java.net.Socket;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Client {

	private String from;
	private String to;
	private String message;
	private String host;
	private Integer port;

	private Socket client_socket;
	public PrintWriter out;
	public BufferedReader in;

	public Client(String host, int port, String from, String to, String message) {
		this.host = host;
		this.port = port;
		this.from = from;
		this.to = to;
		this.message = message;
		initMessages();
	}

	public static void main(String[] args) {
		String host, from, to, message;
		Integer port;
		if (args.length == 5) {
			host = args[0];
			try {
				port = Integer.parseInt(args[1]);
			} catch (Exception e) {
				port = 3490;
			}
			from = args[2];
			to = args[3];
			message = args[4];
			Client client = new Client(host, port, from, to, message);
			client.run();
		}
	}

	public void run() {
		try {
			this.client_socket = new Socket(host, port);
			this.out = new PrintWriter(client_socket.getOutputStream(), true);
			this.in = new BufferedReader(new InputStreamReader(client_socket.getInputStream()));
			waitFor("HELLO");
			send("HELLO", "localhost");
			waitFor("OK");
			send("MAILFROM", from);
			waitFor("OK");
			send("RCPTTO", to);
			waitFor("OK");
			send("DATA", null);
			waitFor("OKDATA");
			send(null, message);
			send("ENDDATA", null);
			waitFor("OK");
			send("QUIT", null);
			System.out.println("Sent message successfully.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private HashMap<String, String> commands = new HashMap<String, String>();
	private HashMap<String, String> responses = new HashMap<String, String>();

	private String getCommand(String s) {
		return commands.get(s);
	}

	private String getResponse(String s) {
		return responses.get(s);
	}

	private void send(String code, String arg) {
		String buffer = getCommand(code);
		if (buffer == null)
			buffer = "";
		if (arg != null)
			buffer += arg;
		out.println(buffer);
	}

	private void waitFor(String code) throws Exception {
		String buffer = in.readLine();
		System.out.println("Got response: " + buffer);
		if (!buffer.startsWith(getResponse(code)))
			throw new Exception("Bad response!");
	}

	private void initMessages() {
		String[][] responses_temp = {
			{ "HELLO", "220" },
			{ "BYE", "221" },
			{ "OK", "250" } ,
			{ "OKDATA", "354" },
		};
		for (String[] pair : responses_temp)
			responses.put(pair[0], pair[1]);

		String[][] commands_temp = {
			{ "HELLO", "HELO " },
			{ "MAILFROM", "MAIL FROM:" },
			{ "RCPTTO", "RCPT TO:" },
			{ "DATA", "DATA" },
			{ "ENDDATA", "\r\n.\r" },
			{ "QUIT", "QUIT" }
		};
		for (String[] pair : commands_temp)
			commands.put(pair[0], pair[1]);
	}

}
