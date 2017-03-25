package ekit.com.hexidec.ekit;

import java.io.*;
import java.net.*;
import java.util.*;

class ClientTCP  {

    Socket commReq;
    ObjectInputStream oisReq;
    ObjectOutputStream oosReq;

    private String pseudo;
	private String mdp; //pas ouf niveau sécu
    
    BufferedReader consoleIn; // flux de lecture lignes depuis clavier

    public ClientTCP(String serverIp, int serverPort) throws IOException {

	commReq = new Socket(serverIp, serverPort);
	oosReq = new ObjectOutputStream(commReq.getOutputStream());
	oisReq = new ObjectInputStream(commReq.getInputStream()); 

	consoleIn = new BufferedReader(new InputStreamReader(System.in));
    }

    public void initLoop() throws IOException,ClassNotFoundException {

	String line = null;
	boolean ok = false;



	//requête 1 ici (première connexion)

	while (!ok) {

		System.out.println("Entre donc ton pseudo : ");
	    pseudo = consoleIn.readLine();
		System.out.println("Entre donc ton mdp : ");
		mdp = consoleIn.readLine();

	    oosReq.writeUTF(pseudo);
	    oosReq.flush();

		//ok
	    ok = oisReq.readBoolean();
	}

	System.out.println("Pseudo accepté !");

    }

    public void requestLoop() throws IOException,ClassNotFoundException {

	String reqLine = null;
	String[] reqParts = null;
	boolean stop = false;
	int nbTurn = 0;
	String advName = "";

	while (!stop) {

	    System.out.print(pseudo + "> ");
	    reqLine = consoleIn.readLine();
	    reqParts = reqLine.split(" ");

	    if (reqParts[0].equals("req2")) {

			byte[] infosFichier;
			//ici uncaught Unknown class Exception je crois d'où l'erreur
		}

	    else if (reqParts[0].equals("req2"))
	    {



	    }
		}
    }
}
