package tsmtp.states;

import tsmtp.Request;
import tsmtp.Session;
import tsmtp.states.*;

public class MailFromState extends SessionState {
	public boolean handle(Session session, Request request) {
		if (super.handle(session, request))
			return true;

		if (request.commandIs("RCPT TO:")) {
			session.respond("OK");
			session.message.setTo(request.arguments);
			session.setState(new RcptToState());
		} else {
			if (request.isValid())
				session.respond("OOO");
			else
				session.respond("SYNTAX");
		}
		return true;
	}
}
