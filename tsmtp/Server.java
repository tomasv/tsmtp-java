package tsmtp;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;
import tsmtp.Session;

class Server {
	static private int default_port = 3490;
	private int port = 3490;
	private String host = "localhost";
	private ServerSocket serverSocket;
	private ExecutorService executor;

	public Server() throws Exception {
		this.serverSocket = new ServerSocket(this.port);
		this.executor = Executors.newCachedThreadPool();
	}

	public Server(int port) throws Exception {
		this.port = port;
		this.serverSocket = new ServerSocket(this.port);
		this.executor = Executors.newCachedThreadPool();
	}

	public void run() throws Exception {
		System.out.println("Using port " + port + ".");
		while(true){
			Socket clientSocket = serverSocket.accept();
			System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());
			executor.execute(new Session(clientSocket));
		}
	}

	public static void main(String[] args) {
		int port = default_port;
		try {
			if (args.length == 1) {
				new Server(Integer.parseInt(args[0])).run();
			} else {
				new Server().run();
			}
		} catch (Exception e) {
		}
	}
}
