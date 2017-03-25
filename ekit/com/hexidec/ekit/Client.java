package ekit.com.hexidec.ekit;

import java.net.*;
import java.io.*;

public class Client extends Thread{
	public int requete;
	public Client(int requete) {
		this.requete=requete;
	}

	public void run() {
		System.out.println("un thread de client commencence...");
		int port = 12345;
		ClientTCP client = null;
		try {
			client = new ClientTCP("localhost", port, requete);
			client.initLoop();
			client.requestLoop();
		}
		catch(IOException e) {
			System.err.println("cannot communicate with server :" + e.getMessage());
			System.exit(1);
		}
		catch(ClassNotFoundException e) {
			System.err.println("cannot communicate with server :" + e.getMessage());
			System.exit(1);
		}
	}
}
