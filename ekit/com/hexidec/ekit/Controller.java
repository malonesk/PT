package ekit.com.hexidec.ekit;

import ekit.com.hexidec.ekit.test.ExplorateurFichiers;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Antonin on 05/01/2017.
 */
public class Controller implements ActionListener {
    private TestEkit ekit;

    public Controller (TestEkit ek){
        ekit=ek;

    }
    public void actionPerformed(ActionEvent e) {
        if ((((JButton)e.getSource()).getText()).equals("Rafraichir")){

            ekit.explofich=new ExplorateurFichiers("/home/malonesk",ekit.ekitCore).getExplorateur();
            SwingUtilities.updateComponentTreeUI(ekit);

        }



    }

}
