package tsmtp.states;

import tsmtp.Request;
import tsmtp.Session;

public class MailFromState implements SessionState {
	public void handle(Session session, Request request) {
		System.out.println("mail from state");
		if (request.isValid() && request.command.equals("RCPT TO:")) {
			session.message.setTo(request.arguments);
			session.setState(new RcptToState());
		} else {
			session.out.println("Wrong command for this state.");
		}
	}
}
