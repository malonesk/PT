package ekit.com.hexidec.ekit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by malonesk on 24/03/17.
 */
public class LierCompteDisplayer extends JFrame{
    public JLabel jlTitreLier, jlNomUtilis, jlPassword;
    public JTextField jtfNom, jtfPassword;
    public JButton lier;
    public Configuration config;
    public LierCompteDisplayer() {
        super();
        // TO DO : instanciation client
        init();

    }

    private void init() {
        config = new Configuration();
        jlTitreLier = new JLabel("Lier l'application à un compte en ligne");
        jlNomUtilis = new JLabel("Nom d'utilisateur");
        jlPassword = new JLabel("Mot de passe");

        jtfNom = new JTextField();
        jtfPassword = new JTextField();

        lier = new JButton("Lier ce compte");

        this.setSize(400,300);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        Insets insets = this.getInsets();
        jlTitreLier.setBounds(insets.left+70,insets.top,320,80);
        this.add(jlTitreLier);
        jlNomUtilis.setBounds(insets.left+50,insets.top+60,200,100);
        this.add(jlNomUtilis);
        jlPassword.setBounds(insets.left+50,insets.top+120,200,100);
        this.add(jlPassword);

        jtfNom.setBounds(insets.left+200,insets.top+100,180,20);
        this.add(jtfNom);
        jtfPassword.setBounds(insets.left+200,insets.top+160,180,20);
        this.add(jtfPassword);

        lier.setBounds(insets.left+100,insets.top+200,200,50);
        this.add(lier);



        lier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (((((JButton)e.getSource()).getText()).equals("save"))) {
                    lierCompte();
                }
            }
        });
        SwingUtilities.updateComponentTreeUI(this);
        this.setVisible(true);
    }

    private void lierCompte() {
        try {
            config.setConfig("online.user", jlNomUtilis.getText());
            config.setConfig("online.password", jlPassword.getText());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Entrer tous les champs de config");
        }

        // Client get infos du compte sur serveur
        // client.getInfoClient(config.getUserName(), config.getPassword());

        /*
        try {
            config.setConfig("online.name", response.getName());
            config.setConfig("online.root", response.getRoot());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Entrer tous les champs de config");
        }
         */

        // CHARGER TOUTES LES DONNES CLIENTS SUR L'APPLI (comme quand on l'ouvre)
    }
}
