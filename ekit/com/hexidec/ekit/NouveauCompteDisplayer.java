package ekit.com.hexidec.ekit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by malonesk on 24/03/17.
 */
public class NouveauCompteDisplayer extends JFrame {
    public Client client;
    public JLabel jlTitreCreer, jlNomUtilis, jlPassword;
    public JTextField jtfNom, jtfPassword;
    public JButton creer;
    public NouveauCompteDisplayer() {
        super();
        // TO DO : instanciation client
        init();

    }

    private void init() {
        jlTitreCreer = new JLabel("Creer un compte en ligne");
        jlNomUtilis = new JLabel("Nom d'utilisateur");
        jlPassword = new JLabel("Mot de passe");

        jtfNom = new JTextField();
        jtfPassword = new JTextField();

        creer = new JButton("Creer un compte");

        this.setSize(400,300);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        Insets insets = this.getInsets();
        jlTitreCreer.setBounds(insets.left+80,insets.top,200,80);
        this.add(jlTitreCreer);
        jlNomUtilis.setBounds(insets.left+50,insets.top+60,200,100);
        this.add(jlNomUtilis);
        jlPassword.setBounds(insets.left+50,insets.top+120,200,100);
        this.add(jlPassword);

        jtfNom.setBounds(insets.left+200,insets.top+100,180,20);
        this.add(jtfNom);
        jtfPassword.setBounds(insets.left+200,insets.top+160,180,20);
        this.add(jtfPassword);

        creer.setBounds(insets.left+100,insets.top+200,200,50);
        this.add(creer);



        creer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (((((JButton)e.getSource()).getText()).equals("save"))) {
                    creerCompte();
                }
            }
        });
        SwingUtilities.updateComponentTreeUI(this);
        this.setVisible(true);
    }

    private void creerCompte() {
        // Appel de la methode client de creation d'un nouveau compte sur le serveur

        // client.creerUnNouveauCompte(jtfNom.getText(),jtfPassword.getText());
    }
}
