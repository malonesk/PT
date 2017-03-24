import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.*;

/**
 * Created by Antonin on 24/03/2017.
 */
public class Serveur {
    private static Socket sock;




    public static void main(String args[]) {
        ObjectOutputStream oos=null;
        ObjectInputStream ois=null;
        Connection connection = null;

        Statement statement = null;

        ResultSet resultSet = null;

        ResultSetMetaData metaData = null;

        int numberOfColumns = 0;

        try

        {

            Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection("jdbc:mysql://localhost/pt", "root", "");

            statement = connection.createStatement();

            System.out.println("Connection Established");


            //Test connection bdd
            resultSet = statement.executeQuery("SELECT * FROM utilisateur");

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


    }
}
