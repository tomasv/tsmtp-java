package tsmtp;

import tsmtp.Message;

public class Sender implements Runnable {

	Message message;

	public Sender(Message message) {
		this.message = message;
	}

	public void run() {
		System.out.println("Sending message:");
		System.out.println("FROM: " + message.getFrom());
		System.out.println("TO: " + message.getTo());
		System.out.println("BODY: " + message.getBody());
	}
}
