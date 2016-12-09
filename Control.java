

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;

/**
 * Created by jeremy on 28/04/16.
 */
public class Control implements ActionListener {
    private Vue v;
    private Model m;
    public Control(Model model, Vue v) {
        this.m = model;
        this.v = v;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

            if (e.getSource() == v.bouton) {}

        }
}
