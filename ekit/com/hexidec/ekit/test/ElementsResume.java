package ekit.com.hexidec.ekit.test;

import com.sun.java.swing.plaf.motif.MotifBorders;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by Sami on 12/01/2017.
 */
public class ElementsResume {
    String balise, contenu;
    private ArrayList<ElementsResume> enfants = new ArrayList<>();
    int level = 0;

    public ElementsResume(String b, String c)
    {
        balise = b;
        c= c.replaceAll("[\r\n\t]+", "");
        c= c.replaceAll("&#233;", "\u00e9"); //é
        c= c.replaceAll("&#226;", "\u00e2"); //â
        c= c.replaceAll("&#224;", "\u00e0"); //à
        c= c.replaceAll("&#244;", "\u00f4"); //ô
        c= c.replaceAll("&#251;", "\u00fb"); //û
        c= c.replaceAll("&#238;", "\u00ee"); //î
        c= c.replaceAll("&#234;", "\u00ea"); //ê
        c= c.replaceAll("&#232;", "\u00e8"); //è
        c = new String(c.getBytes(), Charset.forName("UTF-8"));
        System.out.println("c : " + c);

        int i;

        for(i = 0; i < c.length(); i++)
        {
            if (c.charAt(i) != ' ')
            {
                break;
            }
        }
        c = c.substring(i);

        contenu = c;
        level = Resume.getLevel(balise);
    }

    public static String getNumerotation(int num, int level)
    {
        if (level == 1)
            return intToRoman(num) + ") "; //I)
        else if (level == 2)
            return Character.toString((char)(num + 65)) + ")"; //A
        else if (level == 3)
            return (num+1) + "\u00b0)"; //1°)
        else if (level == 4)
            return Character.toString((char)(num + 97)) + ")"; //a
        return "";
    }

    //très sale
    public static String intToRoman(int i)
    {
        if (i == 0)
            return "I";
        if (i == 1)
            return "II";
        if (i == 2)
            return "III";
        if (i == 3)
            return "IV";
        if (i == 4)
            return "V";
        if (i == 5)
            return "VI";
        if (i == 6)
            return "VII";
        return "";
    }

    public void add(ElementsResume e)
    {
        enfants.add(e);
    }

    public ArrayList<ElementsResume> getEnfants()
    {
        return enfants;
    }

    public boolean isTitre()
    {return false;}

    public boolean isEmpty()
    {
        return contenu.equals("");
    }

    public String toString()
    {
            return arborescenceRecur(this, 1);
    }

    public String arborescenceRecur(ElementsResume e, int level)
    {
        String s = e.balise + " : \"" + e.contenu + "\" level : " + level;
        s+= "\n";
        String espace = "";

        //On enlève les espaces du début causés par l'HTML
        for (int i = 0; i < level; i++)
        {
            espace += "  ";
        }


        ElementsResume t = (ElementsResume)e;
        ArrayList<ElementsResume> list = t.getEnfants();
        for(int i = 0; i < list.size(); i++)
        {
            s += espace + arborescenceRecur(list.get(i), level + 1);
        }


        return s;
    }

    public void arborescenceRecurJPanel(JPanel p, ElementsResume e, int level, int num)
    {
        String str= getNumerotation(num, e.level) + " " + e.contenu;

        JLabel label = new JLabel(str);

        if (e.level == 7)
            label.setForeground(new Color(200, 100, 20));

        if (e.level != 0)
            p.add(label);
        //p.setBorder(BorderFactory.createBevelBorder(0));

        ArrayList<ElementsResume> list = e.getEnfants();

        JPanel panel = new JPanel();


        //panel.add(new JLabel(e.contenu));
        panel.setBorder(new MotifBorders.BevelBorder(true, Color.DARK_GRAY, Color.LIGHT_GRAY));
        //p.setBorder(new EmptyBorder(0, 10, 0, 0));
        if (e.level != 0)
            panel.setBorder(new EmptyBorder(4, 20, 4, 4));
        else
            panel.setBorder(new EmptyBorder(4, 10, 4, 4));
        
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.setBackground(new Color(220, 220, 220));




        for(int i = 0; i < list.size(); i++)
        {
            arborescenceRecurJPanel(panel, list.get(i), level +1, i);
        }
        if (list.size() != 0)
            p.add(panel, BorderLayout.NORTH);

        //return null;
    }
}
