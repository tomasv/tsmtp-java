package tsmtp.states;

import tsmtp.Request;
import tsmtp.Session;
import tsmtp.states.*;

public class GreetedState extends SessionState {
	public boolean handle(Session session, Request request) {
		if (super.handle(session, request))
			return true;

		if (request.commandIs("MAIL FROM:")) {
			session.respond("OK");
			session.message.setFrom(request.arguments);
			session.setState(new MailFromState());
		} else {
			if (request.isValid())
				session.respond("OOO");
			else
				session.respond("SYNTAX");
		}
		return true;
	}
}
