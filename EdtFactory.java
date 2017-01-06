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

public class EdtFactory extends JPanel {
    int width = this.getWidth();
    int height = this.getHeight();
    double length = Math.sqrt(width * width + height * height);

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        this.quadriller(g);
    }

    public void quadriller(Graphics g) {

        g.setColor(Color.BLUE);

        int width = this.getWidth();
        int height = this.getHeight();
        double length = Math.sqrt(width * width + height * height);

            // get width & height here (w,h)

            // define grid width (dh, dv)
            int dw = (int)(width/6);
            int dh = (int)(height/10);
            for (int i = 0; i < width; i += dw) {
                g.drawLine(i, 0, i, height);
            }
            for (int j = 0; j < height; j += dh) {
                g.drawLine(0, j, width, j);
            }
    }
/*
    public void setPanel(Case c) {
        Date datedeb = c.datedeb;
        SimpleDateFormat sdf = new SimpleDateFormat("HH");
        String hours=sdf.format(datedeb);
        String days = sdf.format(datedeb);
        int hour = Integer.parseInt(hours);
        int day = Integer.parseInt(days);
        JPanel panel = new JPanel();
        panel=c;
        panel.setLayout(null);
        panel.setBounds(day*this.getWidth(),(hour-8)*this.getHeight(), this.getWidth(), this.getHeight());
        this.add(panel);

    }

    public void fill(ArrayList<Case> listeCours) {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        for (JPanel jp : listeCours) {
            jp.setLayout(null);
            jp.setBounds(0,0, width/6, height/10);
            panel.add(jp);
        }
        this.add(panel);
    }*/

}