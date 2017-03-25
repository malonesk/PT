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
            int requete = 4;
            oosReq.writeInt(requete);
            oosReq.flush();
            //ok
            ok = oisReq.readBoolean();
        }

        System.out.println("Pseudo accepté !");

    }

    public void requestLoop() throws IOException,ClassNotFoundException {
        Configuration config = new Configuration();
        String reqLine = null;
        String[] reqParts = null;
        boolean stop = false;
        int nbTurn = 0;
        String advName = "";
        while (!stop) {
            try {
                oosReq.writeObject(config.getUserName());
                oosReq.flush();
                oosReq.writeObject(config.getPassword());
                oosReq.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
            stop=oisReq.readBoolean();

        }
    }
}
