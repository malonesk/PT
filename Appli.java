import java.util.Scanner;

/**
 * Created by jtran on 30/03/16.
 */
public class Appli {
    public static final Scanner input = new Scanner(System.in);
    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater( new Runnable() {

            public void run() {
                Model m = new Model();
                Vue v= new Vue(m);
                Control control = new Control(m, v);
            }

        });
    }
}
