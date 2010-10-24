package tsmtp.states;

import tsmtp.Request;
import tsmtp.Session;
import tsmtp.states.*;

public class DataState extends SessionState {
	public boolean handle(Session session, Request request) {
		if (session.getBuffer().equals(".")) {
			session.respond("OK");
			session.submit(session.message);
			session.reset();
			session.setState(new GreetedState());
		} else {
			String buffer = session.getBuffer();
			if (buffer == null) {
				session.message.appendBody("\n");
			} else {
				session.message.appendBody("\n" + buffer);
			}
		}
		return true;
	}
}
