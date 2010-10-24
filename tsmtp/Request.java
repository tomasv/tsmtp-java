package tsmtp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collection;
import java.util.Arrays;

public class Request {

	public String command;
	public String arguments;

	private boolean valid = false;

	public Request() {
	}

	public Request(String message) {
		if (!message.isEmpty()) {
			message = parseCommand(message);
			parseArguments(message);
		}
	}

	private String parseCommand(String message) {
		ArrayList<String> commands = new ArrayList<String>();
		commands.add("HELO");
		commands.add("MAIL FROM:");
		commands.add("RCPT TO:");
		commands.add("DATA");
		commands.add(".\r\n");
		commands.add("QUIT");
		
		int index = -1;
		for (String command : commands) {
			if (message.startsWith(command)) {
				index = commands.indexOf(command);
				message = message.replaceFirst("^" + command + "\\s*", "");
			}
		}

		if (index != -1) {
			this.command = commands.get(index);
		}

		return message;
	}

	private void parseArguments(String message) {
		ArrayList<String> commands = new ArrayList<String>();
		commands.add("HELO");
		commands.add("MAIL FROM:");
		commands.add("RCPT TO:");

		int index = commands.indexOf(this.command);
		if ((index != -1) && (!message.isEmpty())) {
			this.arguments = message;
			this.valid = true;
		}
		if ((index == -1) && (message.isEmpty())) {
			this.arguments = null;
			this.valid = true;
		}
	}

	static String join(Collection<?> s, String delimiter) {
		StringBuilder builder = new StringBuilder();
		Iterator iter = s.iterator();
		while (iter.hasNext()) {
			builder.append(iter.next());
			if (!iter.hasNext()) {
				break;                  
			}
			builder.append(delimiter);
		}
		return builder.toString();
	}


	public boolean isValid() {
		return valid;
	}

	public boolean commandIs(String name) {
		if (this.isValid() && this.command.equals(name))
			return true;
		return false;
	}
}
