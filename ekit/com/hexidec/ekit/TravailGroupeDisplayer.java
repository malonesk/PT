package ekit.com.hexidec.ekit;

import ekit.com.hexidec.ekit.test.ExplorateurFichiers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by malonesk on 24/03/17.
 */
public class TravailGroupeDisplayer extends JFrame {
    public JPanel arboLocale, arboServer, listeFichiersPartages, panelListeDeroul, panelPartage;
    public JTextField jtfPartager;
    public JComboBox listeNoms;
    public JScrollPane jspFichiersPartages;
    public JButton partager, servToLocal, localToServ;
    public Insets insets;
    public ExplorateurFichiers exploLocale, exploServ;
    public JScrollPane explofichLocal, explofichServ;
    Dimension dimension;
    int screenHeight;
    int screenWidth;
    public TravailGroupeControler controler;

    public TravailGroupeDisplayer() {
        super();
        init();
    }
    public void init() {
        this.setSize(800,600);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        insets = this.getInsets();

        arboLocale = new JPanel();
        arboServer = new JPanel();
        listeFichiersPartages = new JPanel();
        panelListeDeroul = new JPanel();
        panelPartage = new JPanel();

        jtfPartager = new JTextField("Nom");
        listeNoms = new JComboBox();
        jspFichiersPartages = new JScrollPane(listeFichiersPartages);

        partager = new JButton("Partager");
        servToLocal = new JButton("<--");
        localToServ = new JButton("-->");

        Configuration config = new Configuration();
        dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        screenHeight = (int)dimension.getHeight();
        screenWidth  = (int)dimension.getWidth();
        try {
            exploLocale = new ExplorateurFichiers(config.getConfig("online.root"), null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        explofichLocal = exploLocale.getExplorateur();

        try {
            setExploServ(config.getUserName(), config.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
        }
        setComponents();
        controler = new TravailGroupeControler();
        setController(controler);
    }
    public void setController(ActionListener l) {
        partager.addActionListener(l);
        servToLocal.addActionListener(l);
        localToServ.addActionListener(l);
    }
    public void setComponents() {
        arboLocale.setBounds(insets.left+50, insets.top+50, this.getWidth()/4,500);
        arboLocale.setPreferredSize(new Dimension(this.getWidth()/4,500));
        arboLocale.add(explofichLocal);
        servToLocal.setBounds(insets.left+this.getWidth()/4+this.getWidth()/8, insets.top+50, this.getWidth()/4,500);
        arboLocale.setPreferredSize(new Dimension(this.getWidth()/4,500));
        arboLocale.add(explofichServ);
        panelPartage.setBounds(insets.left+this.getWidth()/2+this.getWidth()/4, insets.top+50, this.getWidth()/4,500);
        panelPartage.setLayout(new BoxLayout(panelPartage, BoxLayout.Y_AXIS));
        panelPartage.add(listeNoms)

    }
    public void setExploServ(String nom, String mdp) {
        //traitement client
    }
    public void setPanelListeDeroul() {
    }
    public void setListeFichiersPartages() {

    }
}
