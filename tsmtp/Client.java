package tsmtp;

public class Client {

	public Client(String host, int port) {
		System.out.println(host + " " + port);
	}

	public static void main(String[] args) {
		String host;
		Integer port;
		if (args.length == 2) {
			host = args[0];
			try {
				port = Integer.parseInt(args[1]);
			} catch (Exception e) {
				port = 3490;
			}
		} else {
			host = "localhost";
			port = 3490;
		}
		new Client(host, port);
	}
}
