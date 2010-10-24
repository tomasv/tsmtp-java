package tsmtp.states;

import tsmtp.Request;
import tsmtp.Session;
import tsmtp.states.*;

public class RcptToState extends SessionState {
	public boolean handle(Session session, Request request) {
		if (super.handle(session, request))
			return true;

		if (request.commandIs("DATA")) {
			session.respond("DATA");
			session.setState(new DataState());
		} else {
			if (request.isValid())
				session.respond("OOO");
			else
				session.respond("SYNTAX");
		}
		return true;
	}
}
