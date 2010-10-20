package tsmtp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collection;

public class Request {

	public String command;
	public String arguments;

	private boolean valid = false;

	public Request() {
	}

	public Request(String command, ArrayList<String> arguments) {
		parseCommand(command);
		parseArguments(arguments);
	}

	private void parseCommand(String message) {
		ArrayList<String> commands = new ArrayList<String>();
		commands.add("HELO");
		commands.add("MAIL FROM:");
		commands.add("RCPT TO:");
		commands.add("DATA");
		commands.add(".\r\n");
		commands.add("QUIT");
		
		int index = commands.indexOf(message);
		if (index != -1) {
			this.command = commands.get(index);
			this.valid = true;
		}
	}

	private void parseArguments(ArrayList<String> args) {
		ArrayList<String> commands = new ArrayList<String>();
		commands.add("HELO");
		commands.add("MAIL FROM:");
		commands.add("RCPT TO:");

		int index = commands.indexOf(this.command);
		if ((index == -1) && (!args.isEmpty())) {
			this.valid = false;
		} else {
			this.arguments = join(args, " ");
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
}
