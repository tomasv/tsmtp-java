package tsmtp.states;

import tsmtp.Request;
import tsmtp.Session;

public class RcptToState implements SessionState {
	public void handle(Session session, Request request) {
		System.out.println("rcpt to state");
		if (request.isValid() && request.command.equals("DATA")) {
			session.setState(new DataState());
		} else {
			session.out.println("Wrong command for this state.");
		}
	}
}
