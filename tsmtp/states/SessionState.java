package tsmtp.states;

import tsmtp.Session;
import tsmtp.Request;
import tsmtp.states.*;

public class SessionState {
	public boolean handle(Session session, Request request) {
		if (request.commandIs("HELO")) {
			session.respond("OK");
			session.setDomain(request.arguments);
			session.setState(new GreetedState());
			return true;
		}
		if (request.commandIs("QUIT")) {
			session.respond("BYE");
			session.setState(null);
			return true;
		}
		return false;
	}
}
