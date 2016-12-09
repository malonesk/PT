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
    public JButton b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15, b16, b17, b18, b19, b20, b21, b22, b23, b24, b0;
    public Control control;
    public JPanel pano;
    public JMenuBar barMenu;
    public JMenu menu;
    public JMenuItem new_game;
    public JMenuItem best_scores;
    public JLabel labelScore;


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
        menu = new JMenu("Options");
        new_game = new JMenuItem("Nouvelle partie");
        best_scores = new JMenuItem("Meilleurs scores");
        b1 = new JButton("1"){
            {
                setSize(150, 150);
                setMaximumSize(getSize());
            }
        };
        b2 = new JButton("2")
        {
            {
                setSize(150, 150);
                setMaximumSize(getSize());
            }
        };
        b3 = new JButton("3"){
            {
                setSize(150, 150);
                setMaximumSize(getSize());
            }
        };
        b4 = new JButton("4"){
            {
                setSize(150, 150);
                setMaximumSize(getSize());
            }
        };
        b5 = new JButton("5"){
            {
                setSize(150, 150);
                setMaximumSize(getSize());
            }
        };
        b6 = new JButton("6"){
            {
                setSize(150, 150);
                setMaximumSize(getSize());
            }
        };
        b7 = new JButton("7"){
            {
                setSize(150, 150);
                setMaximumSize(getSize());
            }
        };
        b8 = new JButton("8"){
            {
                setSize(150, 150);
                setMaximumSize(getSize());
            }
        };
        b9 = new JButton("9"){
            {
                setSize(150, 150);
                setMaximumSize(getSize());
            }
        };
        b10 = new JButton("10"){
            {
                setSize(150, 150);
                setMaximumSize(getSize());
            }
        };
        b11 = new JButton("11"){
            {
                setSize(150, 150);
                setMaximumSize(getSize());
            }
        };
        b12 = new JButton("12"){
            {
                setSize(150, 150);
                setMaximumSize(getSize());
            }
        };
        b13 = new JButton("13"){
            {
                setSize(150, 150);
                setMaximumSize(getSize());
            }
        };
        b14 = new JButton("14");
        b15 = new JButton("15"){
            {
                setPreferredSize(new Dimension(75, 75));
            }
        };
        b16 = new JButton("16");
        b17 = new JButton("17");
        b18 = new JButton("18");
        b19 = new JButton("19");
        b20 = new JButton("20");
        b21 = new JButton("21");
        b22 = new JButton("22");
        b23 = new JButton("23");
        b24 = new JButton("24");
        b0 = new JButton(" "){
            {
                setPreferredSize(new Dimension(75, 75));
            }
        };
        labelScore= new JLabel("0");
    }

    public void initAttribut(Model m) {
        model= new Model(m);
        control = new Control(model, this);
        pano = new JPanel();
        b1 = new JButton("1"){
            {
                setPreferredSize(new Dimension(75,75));
            }
        };
        b2 = new JButton("2");
        b3 = new JButton("3");
        b4 = new JButton("4");
        b5 = new JButton("5");
        b6 = new JButton("6");
        b7 = new JButton("7");
        b8 = new JButton("8");
        b9 = new JButton("9");
        b10 = new JButton("10");
        b11 = new JButton("11");
        b12 = new JButton("12");
        b13 = new JButton("13");
        b14 = new JButton("14");
        b15 = new JButton("15");
        b16 = new JButton("16");
        b17 = new JButton("17");
        b18 = new JButton("18");
        b19 = new JButton("19");
        b20 = new JButton("20");
        b21 = new JButton("21");
        b22 = new JButton("22");
        b23 = new JButton("23");
        b24 = new JButton("24");
        b0 = new JButton(" ");
    }


    public JPanel createPano() {
        pano.removeAll();
        if (model.taille==4) {
            pano.setLayout(new GridLayout(4, 4, 1, 1));
        }
        if(model.taille==3) {
            pano.setLayout(new GridLayout(3, 3, 1, 1));
        }
        if(model.taille==5) {
            pano.setLayout(new GridLayout(5, 5, 1, 1));
        }


        for (int i=0; i<model.taille*model.taille; i++) {
            ArrayList<Integer> grille = model.getGrille();
            switch (grille.get(i)) {
                case 1:
                    pano.add(b1);
                    break;
                case 2:
                    pano.add(b2);
                    break;
                case 3:
                    pano.add(b3);
                    break;
                case 4:
                    pano.add(b4);
                    break;
                case 5:
                    pano.add(b5);
                    break;
                case 6:
                    pano.add(b6);
                    break;
                case 7:
                    pano.add(b7);
                    break;
                case 8:
                    pano.add(b8);
                    break;
                case 9:
                    pano.add(b9);
                    break;
                case 10:
                    pano.add(b10);
                    break;
                case 11:
                    pano.add(b11);
                    break;
                case 12:
                    pano.add(b12);
                    break;
                case 13:
                    pano.add(b13);
                    break;
                case 14:
                    pano.add(b14);
                    break;
                case 15:
                    pano.add(b15);
                    break;
                case 16:
                    pano.add(b16);
                    break;
                case 17:
                    pano.add(b17);
                    break;
                case 18:
                    pano.add(b18);
                    break;
                case 19:
                    pano.add(b19);
                    break;
                case 20:
                    pano.add(b20);
                    break;
                case 21:
                    pano.add(b21);
                    break;
                case 22:
                    pano.add(b22);
                    break;
                case 23:
                    pano.add(b23);
                    break;
                case 24:
                    pano.add(b24);
                    break;

                case 0:
                    pano.add(b0);
                    break;


            }
        }
        return pano;
    }

    public void creerWidget() {
        b1.addActionListener(control);
        b2.addActionListener(control);
        b3.addActionListener(control);
        b4.addActionListener(control);
        b5.addActionListener(control);
        b6.addActionListener(control);
        b7.addActionListener(control);
        b8.addActionListener(control);
        b9.addActionListener(control);
        b10.addActionListener(control);
        b11.addActionListener(control);
        b12.addActionListener(control);
        b13.addActionListener(control);
        b14.addActionListener(control);
        b15.addActionListener(control);
        b16.addActionListener(control);
        b17.addActionListener(control);
        b18.addActionListener(control);
        b19.addActionListener(control);
        b20.addActionListener(control);
        b21.addActionListener(control);
        b22.addActionListener(control);
        b23.addActionListener(control);
        b24.addActionListener(control);

        b0.addActionListener(control);

        menu.add(new_game);
        menu.add(best_scores);
        barMenu.add(menu);
        setJMenuBar(barMenu);

        JPanel panoScore = new JPanel();

        panoScore.add(new JLabel("Score"));
        panoScore.add(new JLabel(String.valueOf(model.getScore())));

        JPanel panoFinal = new JPanel();

        JPanel pano = createPano();
        panoFinal.setLayout(new BoxLayout(panoFinal, BoxLayout.Y_AXIS));
        panoFinal.add(panoScore);
        panoFinal.add(pano);

        setContentPane(panoFinal);








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
