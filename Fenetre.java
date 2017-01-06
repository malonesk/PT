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
    JMenuItem nouveau;
    JMenuItem editer;



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
        menu = new JMenu("Fichier");
        nouveau = new JMenuItem("Nouveau");
        editer = new JMenuItem("Editer");
       // ImageIcon leftButtonIcon = createImageIcon("images/one.gif");
        model= new Model();
        v=new Vue(model);

    }

    public void creerWidget() {
        menu.add(nouveau);
        menu.add(editer);
        barMenu.add(menu);
        setJMenuBar(barMenu);

    }


}
