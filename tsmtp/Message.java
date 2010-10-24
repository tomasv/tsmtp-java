package tsmtp;

public class Message {

	private String from;
	private String to;
	private String body;

	public Message() {
		this.body = "";
	}

	public Message(String from, String to, String body) {
		this.from = from;
		this.to = to;
		this.body = body;
	}

	// accessors
	public String getFrom() {
		return from;
	}
	public String getTo() {
		return to;
	}
	public String getBody() {
		return body;
	}

	// writers
	public void setFrom(String from) {
		this.from = from;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public void appendBody(String appendage) {
		this.body += appendage;
	}
}
