import sun.security.x509.EDIPartyName;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JPanel;
/**
 * Created by malonesk on 04/01/17.
 */

public class GridFactory extends JPanel {
    public GridFactory() {
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        this.quadriller(g);
    }
    public void quadriller(Graphics g) {
        g.setColor(Color.BLACK);
        int width = this.getWidth();
        int height = this.getHeight();
        double length = Math.sqrt(width * width + height * height);
        int dw = (int)(width/6);
        int dh = (int)(height/10);
        for (int i = 0; i < width; i += dw) {
            g.drawLine(i, 0, i, height);
        }
        for (int j = 0; j < height; j += dh) {
            g.drawLine(0, j, width, j);
        }
    }

}