/**
 * Created by jeremy on 28/04/16.
 */
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by jtran on 26/04/16.
 */
public class ControlMenu implements ActionListener {
    private Vue v;

    public ControlMenu(Vue vue) {
        v = vue;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("go menu");
    }

}

