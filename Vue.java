import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

/**
 * Created by jeremy on 28/04/16.
 */
public class Vue extends JFrame {
    public Model model;
    public Control control;
    public JPanel pano;
    public JMenuBar barMenu;
    public JMenu menu;
    public JMenuItem nouveau;
    public JMenuItem editer;
    public JMenu edt;
    public JMenuItem afficher;
    public JMenuItem config;


    public Vue(Model model) {
        this.model=model;
        initAttribut();
        creerWidget();

        pack();                               // Fixe la taille par défaut
        setVisible(true);                                // Affiche la fenetre
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Gestion de la fermeture
    }

    public Vue(Model m, Vue v) {

        v.initAttribut(m);
        v.creerWidget();
        v.pack();
        v.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Gestion de la fermeture
    }

    public void initAttribut() {
        model= new Model();
        control = new Control(model, this);
        pano=new JPanel();
        barMenu = new JMenuBar();
        menu = new JMenu("Fichier");
        nouveau = new JMenuItem("Nouveau");
        editer = new JMenuItem("Editer");
        edt = new JMenu("Emploi du temps");
        afficher = new JMenuItem("Afficher");
        config = new JMenuItem("Configurer...");
    }

    public void initAttribut(Model m) {
        model= new Model();
        control = new Control(model, this);
        pano = new JPanel();
    }


    public JPanel createPano() {
        pano.removeAll();
        return pano;
    }

    public void creerWidget() {

    }
    public String creerDialogWin(){
        JOptionPane victoire = new JOptionPane();
        return (victoire.showInputDialog(null, "Bravo, vous avez gagné ! \n Entrer votre nom pour la postérité : "));
        //On crée une nouvelle instance de notre JDialog
        /*JDialog dialog = new JDialog();
        dialog.setSize(300, 200);//On lui donne une taille
        dialog.setTitle("Bravo, vous avez gagné !"); //On lui donne un titre
        dialog.setVisible(true);//On la rend visible
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //On dit à l'application de se fermer lors du clic sur la croix
        */

    }
    public void display() {

        setVisible(true);
    }
}
