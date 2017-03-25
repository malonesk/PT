package ekit.com.hexidec.ekit;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Antonin on 24/03/2017.
 */
public class Serveur {

    public static void splitFichIntoByte(ObjectOutputStream oos, File fich){

    }





    public static void main(String args[]) {
        Socket sock=null;
        ServerSocket conn=null;
        int port=12345;
        ObjectOutputStream oos=null;
        ObjectInputStream ois=null;

        try {
            conn=new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Connection connection = null;

        Statement statement = null;

        ResultSet resultSet = null;

        ResultSetMetaData metaData = null;

        int numberOfColumns = 0;

        try

        {

            Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection("jdbc:mysql://localhost/pt", "root", "0206");

            statement = connection.createStatement();

            System.out.println("Connection Established");


            //Test connection bdd
            resultSet = statement.executeQuery("SELECT * FROM UTILISATEUR");

            while(resultSet.next())

            {

                for (int i = 1; i <= 4; i++)

                {

                    System.out.printf("%-8s\t", resultSet.getObject(i));

                }

                System.out.println();

            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        while(true){
            try {
                sock=conn.accept();
                ois = new ObjectInputStream(sock.getInputStream());
                oos = new ObjectOutputStream(sock.getOutputStream());
                int requete=ois.readInt();
                String mdp;
                String pseudo;
                String surnom;
                String source;
                switch (requete){
                    case 1:
                        try {
                            String username=(String)ois.readObject();
                            mdp=(String)ois.readObject();
                            resultSet = statement.executeQuery("SELECT * FROM UTILISATEUR WHERE pseudo='"+username+"' AND mdp='"+mdp+"'");
                            String racineArbo="";
                            int iduser=0;
                            List<String> listUserPartage=new ArrayList<>();
                            while(resultSet.next())

                            {
                                racineArbo=(String)resultSet.getObject(4);
                                iduser=(Integer)resultSet.getObject(1);


                            }
                            if(!racineArbo.equals("")){
                                oos.writeBoolean(true);
                                File root=new File(racineArbo);
                                FileTreeModel model=new FileTreeModel(root);
                                JTree tree=new JTree(model);
                                oos.writeObject(tree);

                                resultSet = statement.executeQuery("SELECT UTILISATEUR.pseudo FROM FICHIER " +
                                        "INNER JOIN DROIT ON DROIT.id_fichier=FICHIER.id_FICHIER " +
                                        "INNER JOIN UTILISATEUR ON FICHIER.id_createur=UTILISATEUR.id_user" +
                                        "WHERE DROIT.id_utilisateur="+iduser);

                                while(resultSet.next())

                                {
                                    listUserPartage.add((String)resultSet.getObject(1));


                                }

                            }
                            else{
                                oos.writeBoolean(false);
                            }


                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 2: //envoie du fichier le client envoie le TreePath
                        BufferedInputStream bis = null;
                        FileInputStream fis = null;
                        TreePath path = null;
                        try {
                            path = (TreePath)ois.readObject();
                            File myFile = (File)path.getLastPathComponent();
                            byte [] mybytearray  = new byte [(int)myFile.length()];
                            fis = new FileInputStream(myFile);
                            bis = new BufferedInputStream(fis);
                            bis.read(mybytearray,0,mybytearray.length);

                            System.out.println("Sending " + myFile.getName() + "(" + mybytearray.length + " bytes)");
                            oos.write(mybytearray,0,mybytearray.length);
                            oos.flush();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }



                        break;

                    case 3:  //Le client envoi la string d'ou il veut sauvegarder le fichier puis les info fichier
                        String FILE_TO_RECEIVED=(String)ois.readObject();
                        int bytesRead;
                        int current = 0;
                        FileOutputStream fos = null;
                        BufferedOutputStream bos = null;

                        int FILE_SIZE=6022386;
                        byte [] mybytearray  = new byte [FILE_SIZE];
                        InputStream is = sock.getInputStream();

                        fos = new FileOutputStream(FILE_TO_RECEIVED);
                        bos = new BufferedOutputStream(fos);

                        bytesRead = is.read(mybytearray,0,mybytearray.length);
                        current = bytesRead;

                        do {
                            bytesRead =
                                    is.read(mybytearray, current, (mybytearray.length-current));
                            if(bytesRead >= 0) current += bytesRead;
                        } while(bytesRead > -1);

                        bos.write(mybytearray, 0 , current);
                        bos.flush();
                        System.out.println("File " + FILE_TO_RECEIVED
                                + " downloaded (" + current + " bytes read)");
                        break;

                    case 4: //Creation d'un utilisateur

                        pseudo=(String)ois.readObject();
                        mdp=(String)ois.readObject();
                        try {
                            resultSet = statement.executeQuery("SELECT * FROM UTILISATEUR WHERE pseudo="+pseudo);
                            if (!resultSet.next() ) {
                                resultSet=statement.executeQuery("INSERT INTO UTILISATEUR VALUES (NULL,'"+pseudo+"','"+mdp+"', '/serveur/"+pseudo+"'"); //path provisoire
                                oos.writeBoolean(true);
                            }else{
                                oos.writeBoolean(false);
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        break;

                    case 5 : //Update d'un utilisateur
                        pseudo=(String)ois.readObject();
                        mdp=(String)ois.readObject();
                        surnom=(String)ois.readObject();
                        source=(String)ois.readObject();

                        resultSet = statement.executeQuery("UPDATE UTILISATEUR SET password='"+mdp+"', surnom='"+surnom+"', source='"+source+"' WHERE pseudo="+pseudo);
                        oos.writeBoolean(true);






                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
