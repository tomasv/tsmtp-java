package tsmtp.states;

import tsmtp.Request;
import tsmtp.Session;

public class GreetedState implements SessionState {
	public void handle(Session session, Request request) {
		System.out.println("greeted state");
		if (request.isValid() && request.command.equals("MAIL FROM:")) {
			session.message.setFrom(request.arguments);
			session.setState(new MailFromState());
		} else {
			session.out.println("Wrong command for this state.");
		}
	}
}
