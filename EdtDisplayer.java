import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.DateRange;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * Created by malonesk on 05/01/17.
 */
public class EdtDisplayer extends JFrame{
    public DateRange currentWeek;
    public Graphics g;
    public Graphics h;
    public JPanel gridEdt;
    public ArrayList<Case> listeCours;
    public IcsParser icsp;
    public JButton nextW;
    public JButton prevW;
    public EdtDisplayer ed2;
    public EdtDisplayer(DateRange sem) {
        super();
        currentWeek=sem;
        init(sem);
    }
    public EdtDisplayer() {
        super();
        init(null);
    }
    public void init(DateRange dr) {
        if (dr!=null) {
            icsp = new IcsParser("ADECal.ics",dr);
            currentWeek=icsp.initDateRangeCal(dr);
        }
        else {
            icsp = new IcsParser("ADECal.ics");
            currentWeek=icsp.initDateRangeCal();
        }

        listeCours=new ArrayList<>();
        List<Component> lc;
        icsp.initWeekComponent(currentWeek);
        lc=icsp.getWeekComponent();
        icsp.sortComponentByDate();

        setListeCours(lc);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String deb = sdf.format(currentWeek.getRangeStart());
        String fin = sdf.format(currentWeek.getRangeEnd());
        this.setTitle("Semaine du "+deb+" au "+fin);

        this.setSize(1320,1050);
        this.setLocationRelativeTo(null);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gridEdt=new GridFactory();
        gridEdt.paintComponents(g);

        this.setLayout(null);

        Insets insets = this.getInsets();
        nommerAxes(insets);
        gridEdt.setBounds(insets.left+50,insets.top+20,1200,1000);
        this.add(gridEdt);

        for (int i=0;i<lc.size();i++) {
            Case ca = new Case(lc.get(i));
            JPanel jp;
            jp = ca.buildComponent();
            int[] tab = computePositionXY(ca);
            if (tab[0]<0) tab[0]=0;
            jp.setBounds(tab[0]+50,tab[1]+20,200, tab[2]);
            jp.setBackground(new Color(194,227,239) );
            jp.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
            this.add(jp);
        }

        nextW = new JButton("Next");
        nextW.setPreferredSize(new Dimension(75,50));
        nextW.setBounds(insets.left+this.getWidth()-75, this.getHeight()/2-75, 75, 50);
        nextW.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (((((JButton)e.getSource()).getText()).equals("Next"))) {
                    nextWeek();
                    actualise();
                } else {
                    nextW.setEnabled(true);
                }
            }
        });

        prevW = new JButton("Prev");
        prevW.setPreferredSize(new Dimension(75,50));
        prevW.setBounds(insets.left+this.getWidth()-75, this.getHeight()/2-25, 75, 50);
        prevW.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (((((JButton)e.getSource()).getText()).equals("Prev"))) {
                    previousWeek();
                    actualise();
                } else {
                    prevW.setEnabled(true);
                }
            }
        });

        this.add(nextW);
        this.add(prevW);
        this.getContentPane().add(gridEdt);
        SwingUtilities.updateComponentTreeUI(this);
        this.setVisible(true);
    }

    public void nommerAxes(Insets ins) {

        for (int i = 0; i < 6; i++) {
            switch (i) {
                case 0:
                    nommageAxesJoursFactory("Lundi", i, ins);
                    break;
                case 1:
                    nommageAxesJoursFactory("Mardi", i, ins);
                    break;
                case 2:
                    nommageAxesJoursFactory("Mercredi", i, ins);
                    break;
                case 3:
                    nommageAxesJoursFactory("Jeudi", i, ins);
                    break;
                case 4:
                    nommageAxesJoursFactory("Vendredi", i, ins);
                    break;
                case 5:
                    nommageAxesJoursFactory("Samedi", i, ins);
                    break;

            }
        }

        for (int j=0; j<11;j++) {
            switch(j) {
                case 0:
                    nommageAxesHeuresFactory("08:00", j, ins);
                    break;
                case 1:
                    nommageAxesHeuresFactory("09:00", j, ins);
                    break;
                case 2:
                    nommageAxesHeuresFactory("10:00", j, ins);
                    break;
                case 3:
                    nommageAxesHeuresFactory("11:00", j, ins);
                    break;
                case 4:
                    nommageAxesHeuresFactory("12:00", j, ins);
                    break;
                case 5:
                    nommageAxesHeuresFactory("13:00", j, ins);
                    break;
                case 6:
                    nommageAxesHeuresFactory("14:00", j, ins);
                    break;
                case 7:
                    nommageAxesHeuresFactory("15:00", j, ins);
                    break;
                case 8:
                    nommageAxesHeuresFactory("16:00", j, ins);
                    break;
                case 9:
                    nommageAxesHeuresFactory("17:00", j, ins);
                    break;
                case 10:
                    nommageAxesHeuresFactory("18:00", j, ins);
                    break;

            }
        }
    }

    public void nommageAxesJoursFactory(String jour, int i, Insets ins) {
        int widthX = 200;
        int heightX = 20;
        JPanel lun = new JPanel();
        lun.add("Center", new JLabel(jour));
        lun.setPreferredSize(new Dimension(widthX, heightX));
        lun.setBounds(50 + i * widthX, ins.top, widthX, heightX);
        this.add(lun);
    }

    public void nommageAxesHeuresFactory(String heure, int i, Insets ins) {
        int widthY = 50;
        int heightY = 100;
        JPanel lun = new JPanel();
        lun.add("Center", new JLabel(heure));
        lun.setPreferredSize(new Dimension(widthY, heightY));
        lun.setBounds(ins.left, 8+i*heightY, widthY, heightY);
        this.add(lun);
    }

    public void actualise() {
        //nextWeek();
            this.setVisible(false);
            this.removeAll();
            ed2 = new EdtDisplayer(this.currentWeek);
            ed2.currentWeek=this.currentWeek;
            //ed2.icsp=new IcsParser("ADECal.ics", ed2.currentWeek);
            ed2.icsp.initWeekComponent(ed2.currentWeek);
            List<Component> lc = ed2.icsp.getWeekComponent();
            ed2.setListeCours(lc);
            ed2.init(ed2.currentWeek);
            // ed2.nextWeek();
            ed2.setVisible(true);
    }

    /*
    public void actualise() {
        this.removeAll();
        icsp = new IcsParser("ADECal.ics", currentWeek);
        icsp.initWeekComponent(currentWeek);
        List<Component> lc = icsp.getWeekComponent();
        setListeCours(lc);
        init(currentWeek);
        this.setVisible(false);
        SwingUtilities.updateComponentTreeUI(this);
        this.setContentPane(this.getContentPane());
        this.setVisible(true);

    }*/

    public int[] computePositionXY(Case c) {
        int[] pos = new int[3];
        int width=200;
        int height=100;

        SimpleDateFormat sdf = new SimpleDateFormat("kk");
        String hh = sdf.format(c.datedeb);
        String hhFin = sdf.format(c.datefin);
        int heure = Integer.parseInt(hh)+1;
        int heureFin = Integer.parseInt(hhFin)+1;

        SimpleDateFormat sdf2 = new SimpleDateFormat("u");
        String snoDay = sdf2.format(c.datedeb);
        int noDay = Integer.parseInt(snoDay)-1;

        SimpleDateFormat sdf3 = new SimpleDateFormat("mm");
        String mm = sdf3.format(c.datedeb);
        int minute = Integer.parseInt(mm);

        String mmFin = sdf3.format(c.datefin);
        int minuteFin = Integer.parseInt(mmFin);

        int x = noDay*width;
        int y = (heure-8)*height;
        pos[0]=x;
        pos[1]=y;

        if (heureFin-heure>0) {
            pos[2] = height * (heureFin - heure);
        } else pos[2]=0;
        if (minute==15) {
            pos[2]-=height/4;
            pos[1]+=height/4;
        }
        if (minute==30) {
            pos[2]-=height/2;
            pos[1]+=height/2;
        }
        if (minute==45) {
            pos[2]-=height*3/4;
            pos[1]+=height*3/4;
        }
        if (minuteFin==15) pos[2]+=height/4;
        if (minuteFin==30) pos[2]+=height/2;
        if (minuteFin==45) pos[2]+=height*3/4;
        return pos;
    }
    public int setListeCours(List<Component> lc) {
        if (lc == null || lc.size()==0) {
            listeCours=new ArrayList<>();
            return -1;
        }
        listeCours=new ArrayList<>();
        for (Component c : lc) {
            listeCours.add(icsp.buildCase(c));
        }
        return 0;
    }

    public Date ajouterJour(Date date, int nbJour) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, nbJour);
        return cal.getTime();
    }

    public void nextWeek() {
        currentWeek = new DateRange(ajouterJour(currentWeek.getRangeStart(),7),ajouterJour(currentWeek.getRangeEnd(),7));
    }

    public void previousWeek() {
        currentWeek = new DateRange(ajouterJour(currentWeek.getRangeStart(),-7),ajouterJour(currentWeek.getRangeEnd(),-7));
    }

    public static void main(String args[]) {
        EdtDisplayer edtd= new EdtDisplayer();
    }

}
