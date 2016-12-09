/**
 * Created by jtran on 29/03/16.
 */
import javax.swing.*; // Pour les composants graphiques que l'on ajoutera dans la méthode creerWidget
import java.awt.*;
import java.util.*;

public class Fenetre extends JFrame {
    /*
    Declaration des attributs du menu
     */
    public Vue v;
    protected Model model;
    JMenuBar barMenu;
    JMenu menu;
    JMenuItem new_game;
    JMenuItem best_scores;



    public Fenetre() {
        initAttribut();
        creerWidget();

        pack();                               // Fixe la taille par défaut
        setVisible(true);                                // Affiche la fenetre
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Gestion de la fermeture
    }

    private void initAttribut() {
        /*
        Initialisation des attributs du menu
         */
        barMenu = new JMenuBar();
        menu = new JMenu("Options");
        new_game = new JMenuItem("Nouvelle partie");
        best_scores = new JMenuItem("Meilleurs scores");
       // ImageIcon leftButtonIcon = createImageIcon("images/one.gif");
        model= new Model(model);

    }

    public void creerWidget() {
        /**
         * Creation du menu
         */
        menu.add(new_game);
        menu.add(best_scores);
        barMenu.add(menu);
        setJMenuBar(barMenu);





        /*
        pano.add(b1);
        pano.add(b2);
        pano.add(b3);
        pano.add(b4);

        pano.add(b5);
        pano.add(b6);
        pano.add(b7);
        pano.add(b8);

        pano.add(b9);
        pano.add(b10);
        pano.add(b11);
        pano.add(b12);

        pano.add(b13);
        pano.add(b14);
        pano.add(b15);
        pano.add(b0);
        */




    }

    /** Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createImageIcon(String path,
                                               String description) {
        java.net.URL imgURL = Appli.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }


}
