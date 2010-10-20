package tsmtp.states;

import tsmtp.Request;
import tsmtp.Session;

public class DataState implements SessionState {
	public void handle(Session session, Request request) {
		System.out.println("data state");
		if (session.getBuffer().equals(".\r\n")) {
			session.submit(session.message);
			session.reset();
			session.setState(new GreetedState());
		} else {
			session.message.appendBody(session.getBuffer());
		}
	}
}
