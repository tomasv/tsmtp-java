package tsmtp;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;
import tsmtp.Session;

class Server {
	private int port = 3490;
	private ServerSocket serverSocket;
	private ExecutorService executor;

	public Server(int port) throws Exception {
		this.port = port;
		this.serverSocket = new ServerSocket(this.port);
		this.executor = Executors.newCachedThreadPool();
	}

	public void run() throws Exception {
		while(true){
			Socket clientSocket = serverSocket.accept();
			System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());
			executor.execute(new Session(clientSocket));
		}
	}

	public static void main(String[] args) {
		System.out.println("Hello, world!");
		try {
			new Server(3490).run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
