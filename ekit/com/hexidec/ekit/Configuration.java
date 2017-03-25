package ekit.com.hexidec.ekit;

/**
 * Created by malonesk on 24/03/17.
 */

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

/**

        Classe      : Configuration

        Description :
        La classe Configuration va obtenir et mettre à jour les infos de
        configuration à partir des fichiers de configuration
        stockés sous [rep_courant]/config
        Le fichier doit existé ou une exception sera levée.


        *

        date        : 8/05/2004
@author     : PeeX Team
@version    : 1.0


        *

        /



// Les import
        import java.io.FileInputStream;
        import java.io.FileOutputStream;
        import java.util.Properties;
        import java.lang.Exception;
*/
public class Configuration
{
/**


 Methode     : getConfig



 Description :

 La méthode getConfig va retourner l'information de configuration

 désirée à partir d'un fichier de configuration


 *


 date        : 8/05/2004

 @param      : String fichier : Le nom du fichier de configuration

 @param      : String key     : La clé dont on veut obtenir la valeur



 @return     : String représentant la valeur de l'info


  */



 public String getConfig(String key) throws Exception
 {
 //On construit l'adresse du fichier
     String leFichier = "ekit/com/hexidec/ekit/config.properties";

 // On fait pointer notre Properties sur ke fichier
     FileInputStream fis = new FileInputStream(leFichier);
     Properties config = new Properties();
    config.load(fis);

    String tmp = config.getProperty(key);
    fis.close();

    if (tmp == null)
        {
            throw new Exception("La valeur correspondant à '" + key + "' n'existe pas dans le fichier config");
        }
     return tmp;

 }
 public String getUserName() throws Exception {
     //On construit l'adresse du fichier
     String leFichier = "ekit/com/hexidec/ekit/config.properties";

     // On fait pointer notre Properties sur ke fichier
     FileInputStream fis = new FileInputStream(leFichier);
     Properties config = new Properties();
     config.load(fis);

     String tmp = config.getProperty("online.user");
     fis.close();

     if (tmp == null)
     {
         throw new Exception("La valeur correspondant à user n'existe pas dans le fichier config");
     }
     return tmp;
 }
    public String getNickName() throws Exception {
        //On construit l'adresse du fichier
        String leFichier = "ekit/com/hexidec/ekit/config.properties";

        // On fait pointer notre Properties sur ke fichier
        FileInputStream fis = new FileInputStream(leFichier);
        Properties config = new Properties();
        config.load(fis);

        String tmp = config.getProperty("online.name");
        fis.close();

        if (tmp == null)
        {
            throw new Exception("La valeur correspondant à name n'existe pas dans le fichier config");
        }
        return tmp;
    }
    public String getPassword() throws Exception {
        //On construit l'adresse du fichier
        String leFichier = "ekit/com/hexidec/ekit/config.properties";

        // On fait pointer notre Properties sur ke fichier
        FileInputStream fis = new FileInputStream(leFichier);
        Properties config = new Properties();
        config.load(fis);

        String tmp = config.getProperty("online.password");
        fis.close();

        if (tmp == null)
        {
            throw new Exception("La valeur correspondant à user n'existe pas dans le fichier config");
        }
        return tmp;
    }
    public String getRootRepertory() throws Exception {
        //On construit l'adresse du fichier
        String leFichier = "ekit/com/hexidec/ekit/config.properties";

        // On fait pointer notre Properties sur ke fichier
        FileInputStream fis = new FileInputStream(leFichier);
        Properties config = new Properties();
        config.load(fis);

        String tmp = config.getProperty("online.root");
        fis.close();

        if (tmp == null)
        {
            throw new Exception("La valeur correspondant à root n'existe pas dans le fichier config");
        }
        return tmp;
    }

 /**


 Methode     : setConfig



 Description :

 La méthode setConfig va mettre à jour/ inserer l'information de configuration

 désirée à partir dans un fichier de configuration


  *


 date        : 8/05/2004

 @param      : String fichier : Le nom du fichier de configuration

 @param      : String key     : La clé dont on veut obtenir la valeur

 @param      : String valeur  : La valeur associée à la clé



 @return     : String représentant la valeur de l'info


  */


 public void setConfig(String key, String valeur) throws Exception
 {
 // La petite feinte : Il faur recharger entièrement le fichier
 // et le réecrire.

 //On construit l'adresse du fichier
 String leFichier = "ekit/com/hexidec/ekit/config.properties";

 // On fait pointer notre Properties sur le fichier
 Properties config = new Properties();
 System.getProperty("user.dir");
 FileInputStream fis = new FileInputStream(leFichier);
 config.load (fis);
 fis.close();
 FileOutputStream fos = new FileOutputStream(leFichier);

 config.setProperty(key,valeur);

 config.store (fos,"Dernière mise a jour :");
 fos.close();

 }
 }