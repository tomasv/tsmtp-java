package tsmtp.states;

import tsmtp.Session;
import tsmtp.Request;


public interface SessionState {
	public void handle(Session session, Request request);
}
