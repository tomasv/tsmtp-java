package tsmtp.states;

import tsmtp.Request;
import tsmtp.Session;

public class NewState implements SessionState {
	public void handle(Session session, Request request) {
		if (request.isValid() && request.command.equals("HELO")) {
			System.out.println("aaa");
			session.setDomain(request.arguments);
			session.setState(new GreetedState());
		} else {
			System.out.println("bbb");
			session.out.println("Wrong command for this state.");
		}
		System.out.println("new state");
	}
}
