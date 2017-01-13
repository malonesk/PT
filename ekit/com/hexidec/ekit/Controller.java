package ekit.com.hexidec.ekit;

import ekit.com.hexidec.ekit.test.ExplorateurFichiers;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

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

            ekit.explofich=new ExplorateurFichiers("D:/",ekit.ekitCore).getExplorateur();
            SwingUtilities.updateComponentTreeUI(ekit);
            ekit.resume.parse(ekit.ekitCore.getExtendedHtmlDoc());
            ekit.resume.getResume();
            ekit.getContentPane().repaint();
            //ekit.getContentPane().add(ekit.resume);
            ekit.resume.revalidate();
            try {
                ekit.resume.ecrire("salut.txt");
            } catch (IOException e1) {
                e1.printStackTrace();
            }


        }



    }

}
