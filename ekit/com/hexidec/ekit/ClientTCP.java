package ekit.com.hexidec.ekit;

import java.io.*;
import java.net.*;
import java.util.*;

public class ClientTCP  {

    Socket commReq;
    ObjectInputStream oisReq;
    ObjectOutputStream oosReq;
    public int requete;
    private String pseudo;
	private String mdp; //pas ouf niveau sécu
    
    BufferedReader consoleIn; // flux de lecture lignes depuis clavier

    public ClientTCP(String serverIp, int serverPort, int requete) throws IOException {
        this.requete = requete;
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
            oosReq.writeInt(requete);
            oosReq.flush();
            //ok
            ok = oisReq.readBoolean();
        }

        System.out.println("Pseudo accepté !");

    }

    public void requestLoop() throws IOException,ClassNotFoundException {
        Configuration config = new Configuration();
        boolean stop = false;
        while (!stop) {
            switch (requete) {
                case 4 :
                    try {
                        oosReq.writeObject(config.getConfig("new_account.user"));
                        oosReq.flush();
                        oosReq.writeObject(config.getConfig("new_account.password"));
                        oosReq.flush();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    stop=oisReq.readBoolean();
                    break;
            }
        }
    }
}
