package ekit.com.hexidec.ekit;

import net.fortuna.ical4j.model.Component;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.util.regex.Pattern;

/**
 * Created by malonesk on 04/01/17.
 */
public class Case {
    public double width, height;
    public IcsParser icsp;
    public Component component;
    public JLabel location;
    public JLabel prof;
    public JLabel groupe;
    public JLabel matiere;
    public java.util.Date datedeb;
    public java.util.Date datefin;

    public Case(Component c) {
        super();
        icsp = new IcsParser("ekit/com/hexidec/ekit/ADECal.ics");
        component=c;
        width=1200/6;
        height=1000/(10);
        setLocation();
        int index=setGroupe();
        setMatiere();
        setProf(index);
        try {
            datedeb=icsp.stringToDate2(c.getProperty("DTSTART"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            datefin=icsp.stringToDate2(c.getProperty("DTEND"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    public void setLocation() {
        location= new JLabel(icsp.getLocation(component));
        location.setPreferredSize(new Dimension((int)width,(int)height/4));
    }

    public void setMatiere() {
        matiere= new JLabel(icsp.getClasses(component));
        matiere.setPreferredSize(new Dimension((int)width,(int)height/4));
    }
    public String[] splitGroupeEtProf() {
        String groupeEtProf="";
        groupeEtProf= icsp.getGroupesEtProfs(component);
        Pattern pattern = Pattern.compile(Pattern.quote("\n"));
        String[] data = pattern.split(groupeEtProf);
        String[] p;
        p = new String[data.length-2];
        for (int i=1;i<data.length-1;i++) {
            p[i-1]=data[i];
        }
        return p;
    }
    public void setProf(int index) {
        if (index==-1) {
            prof=new JLabel("");
            return;
        }
        String[] groupeEtProf;
        groupeEtProf=splitGroupeEtProf();
        if (groupeEtProf.length <2) {
            prof=new JLabel("");
        }
        else {
            String profe="";
            for (int i=index;i<groupeEtProf.length;i++) {
                profe+=groupeEtProf[i];
            }
            prof=new JLabel(profe);
            prof.setPreferredSize(new Dimension((int)width,(int)height/4));
        }

    }
    public int setGroupe() {
        String[] groupeEtProf;
        groupeEtProf=splitGroupeEtProf();
        int i=0;
        if (groupeEtProf.length==0) {
            String grp = "";
            groupe = new JLabel(grp);
            groupe.setPreferredSize(new Dimension((int) width, (int) height / 4));
            return -1;
        }
        else {
            String grp = "";
            while ( i < groupeEtProf.length && groupeEtProf[i].length() < 7 ) {
                grp += groupeEtProf[i];
                i++;
            }
            groupe = new JLabel(grp);
            groupe.setPreferredSize(new Dimension((int) width, (int) height / 4));
            if (i==groupeEtProf.length) return -1;
            return i;
        }
    }
    public JPanel buildComponent() {
        JPanel jp = new JPanel();
        jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));
        jp.add(matiere);
        jp.add(location);
        jp.add(groupe);
        jp.add(prof);
        jp.setPreferredSize(new Dimension((int)width,(int)height));
        Dimension size = jp.getPreferredSize();

        return jp;
    }

    public String toString() {
        return ""+matiere+" "+prof+" "+location+" "+groupe;
    }

}
