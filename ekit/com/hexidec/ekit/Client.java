package ekit.com.hexidec.ekit;

import java.net.*;
import java.io.*;

class Client {

    public static void usage() {
	System.err.println("usage : java Client server_ip port");
	System.exit(1);
    }

    public static void main(String[] args) {

	int port = 1234;
	ClientTCP client = null;
	try {
	    client = new ClientTCP("localhost", port);
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
