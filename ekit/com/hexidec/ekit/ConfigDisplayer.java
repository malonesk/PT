package ekit.com.hexidec.ekit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

/**
 * Created by malonesk on 24/03/17.
 */
public class ConfigDisplayer extends JFrame{
    public JLabel username, name, password, root, title, nomutilisateur;
    public JTextField surnom, mdp, sourceDos;
    public JButton saveConfig;
    public Configuration config;
    public ConfigDisplayer() {
        super();
        init();
    }

    public void init() {
        config = new Configuration();
        title = new JLabel("Paramètres de l'application");
        root = new JLabel("Votre dossier source");
        password = new JLabel("Votre mot de passe");
        name = new JLabel("Votre surnom");
        username = new JLabel("Votre nom d'utilisateur");

        try {
            nomutilisateur = new JLabel(config.getConfig("online.user"));
            surnom = new JTextField(config.getConfig("online.name"));
            mdp = new JTextField(config.getConfig("online.password"));
            sourceDos = new JTextField(config.getConfig("online.root"));
        } catch (Exception e) {
            e.printStackTrace();
        }


        saveConfig = new JButton("save");
        saveConfig.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (((((JButton)e.getSource()).getText()).equals("save"))) {
                    actualise();
                }
            }
        });


        this.setSize(400,800);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        Insets insets = this.getInsets();
        title.setBounds(insets.left+100,insets.top,200,100);
        this.add(title);
        username.setBounds(insets.left+30, insets.top+160, 200, 100);
        name.setBounds(insets.left+30, insets.top+260, 150, 100);
        password.setBounds(insets.left+30, insets.top+360, 150, 100);
        root.setBounds(insets.left+30, insets.top+460, 150, 100);
        this.add(username);
        this.add(name);
        this.add(password);
        this.add(root);
        nomutilisateur.setBounds(insets.left + 200, insets.top+200, 150,20);
        surnom.setBounds(insets.left + 200, insets.top+300, 150,20);
        mdp.setBounds(insets.left + 200, insets.top+400, 150,20);
        sourceDos.setBounds(insets.left + 200, insets.top+500, 150,20);
        this.add(nomutilisateur);
        this.add(surnom);
        this.add(mdp);
        this.add(sourceDos);
        saveConfig.setBounds(insets.left + 150, insets.top + 600, 100, 80);
        this.add(saveConfig);
        SwingUtilities.updateComponentTreeUI(this);
        this.setVisible(true);
    }

    private void actualise() {
        try {
            System.out.println(nomutilisateur.getText());
            config.setConfig("online.name", surnom.getText());
            config.setConfig("online.password", mdp.getText());
            config.setConfig("online.root", sourceDos.getText());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Entrer tous les champs de config");
        }
    }




}
