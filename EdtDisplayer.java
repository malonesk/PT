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
    public JPanel gridEdt;
    public ArrayList<Case> listeCours;
    public ArrayList<JPanel> listePanelCours;
    public IcsParser icsp;
    public Case caseTest;
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
        //icsp.updateCurrentWeekInfo(icsp.getWeekInfo());
        //icsp.sortComponentByDate();
        icsp.initWeekComponent(currentWeek);
        lc=icsp.getWeekComponent();
        System.out.println(lc.toString());
        /**/
        if (setListeCours(lc)<0) System.out.println("taille listeCours 0");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String deb = sdf.format(currentWeek.getRangeStart());
        String fin = sdf.format(currentWeek.getRangeEnd());
        this.setTitle("Semaine du "+deb+" au "+fin);

        //this.setSize(798,831);
        this.setSize(1200,1050); //Case : 150x100
        this.setLocationRelativeTo(null);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gridEdt=new EdtFactory();
        //((EdtFactory)gridEdt).fill(listeCours);
        //((EdtFactory)gridEdt).setPanel(icsp.buildCase(lc.get(0)));
        //System.out.println(icsp.buildCase(lc.get(0)).matiere);
        gridEdt.paintComponents(g);


        this.setLayout(null);
/*
        JPanel jp= new JPanel();
        jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));
        jp.add(new JLabel(" ojnrogjneogr"));
        jp.add(new JLabel(" ojnrogjneogr"));
        jp.add(new JLabel(" ojnrogjneogr"));
        jp.add(new JLabel(" ojnrogjneogr"));
        jp.setPreferredSize(new Dimension(200,100));

        JPanel jp2 = new JPanel();
        jp2.setLayout(new BoxLayout(jp2, BoxLayout.Y_AXIS));
        jp2.add(new JLabel(" ojnrogjneogr"));
        jp2.add(new JLabel(" ojnrogjneogr"));
        jp2.add(new JLabel(" ojnrogjneogr"));
        jp2.add(new JLabel(" ojnrogjneogr"));

        jp2.setPreferredSize(new Dimension(200,100));
        Insets insets = this.getInsets();

        Dimension size = jp.getPreferredSize();
        Dimension size2 = jp2.getPreferredSize();

        jp.setBounds(insets.left+150, insets.top+100, size.width, size.height);
        jp2.setBounds(insets.left, insets.top, size.width, size.height);
        this.add(jp);
        this.add(jp2);
*/

        Insets insets = this.getInsets();
        gridEdt.setBounds(insets.left,insets.top,1200,1000);
        this.add(gridEdt);
        for (Component c : lc) {
            Case ca = new Case(c);
            JPanel jp;
            jp = ca.buildComponent();
            int[] tab = computePositionXY(ca);
            if (tab[0]<0) tab[0]=0;
            jp.setBounds(tab[0],tab[1],200, tab[2]);
            jp.setBackground(Color.WHITE);
            System.out.println(tab[0]+" "+tab[1]+" "+tab[2]+" "+ca.datedeb);
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
        this.getContentPane().add(nextW);
        this.getContentPane().add(prevW);





       // this.setContentPane(gridEdt);
        /*
        for (Component c : lc) {
            this.getContentPane().add(icsp.buildCase(c).buildComponent());
        }

        */
        SwingUtilities.updateComponentTreeUI(this);
        this.setVisible(true);
       // this.getContentPane().add(listeCours.get(0));
    }

    public void actualise() {
        //nextWeek();
            this.setVisible(false);
            this.removeAll();
            ed2 = new EdtDisplayer(this.currentWeek);
            ed2.currentWeek=this.currentWeek;
            ed2.icsp=new IcsParser("ADECal.ics", ed2.currentWeek);
            ed2.icsp.initWeekComponent(ed2.currentWeek);
            List<Component> lc = ed2.icsp.getWeekComponent();
            ed2.setListeCours(lc);
            ed2.init(ed2.currentWeek);
            // ed2.nextWeek();
            ed2.setVisible(true);
    }

    public int[] computePositionXY(Case c) {
        int[] pos = new int[3];
        int width=200;
        int height=100;

        SimpleDateFormat sdf = new SimpleDateFormat("kk");
        String hh = sdf.format(c.datedeb);
        String hhFin = sdf.format(c.datefin);
        System.out.println(c.matiere.getText()+" :::: "+"COMPUTEPOS heures: "+hh+" "+hhFin);
        int heure = Integer.parseInt(hh)+1;
        int heureFin = Integer.parseInt(hhFin)+1;

        SimpleDateFormat sdf2 = new SimpleDateFormat("u");
        String snoDay = sdf2.format(c.datedeb);
        int noDay = Integer.parseInt(snoDay)-1;
        System.out.println("COMPUTEPOS col : "+noDay);

        SimpleDateFormat sdf3 = new SimpleDateFormat("mm");
        String mm = sdf3.format(c.datedeb);
        int minute = Integer.parseInt(mm);
        System.out.println("COMPUTEPOS min: "+minute);

        String mmFin = sdf3.format(c.datefin);
        int minuteFin = Integer.parseInt(mmFin);
        System.out.println("COMPUTEPOS min fin: "+minuteFin);

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

        /*
        int z = height * (heureFin - heure);
        if (heureFin-heure>0) {
            z = (heure-8)*height-height;
            if (minute != 0) {
                if (minute==15) z-=height/4;
                if (minute==30) z-=height/2;
                if (minute==45) z-=height*3/4;
            }
            else if(minuteFin!=0){
                if (minuteFin==15) z+=height/4;
                if (minuteFin==30) z+=height/2;
                if (minuteFin==45) z+=height*3/4;
            }
        }
        pos[2]=z;
        */
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
    public JPanel createParentPanel(List<Component> lc) {
        JPanel jp = new JPanel();
        jp.setLayout(new GridLayout(10,5));
        int i=0;
        for (Component c : lc) {
            jp.add(createPanel(lc,i));
            i++;
        }
        return jp;
    }

    public JPanel createPanel(List<Component> lc, int i) {
        int width = this.getWidth()/6;
        int height = this.getHeight()/10;
        JPanel jp = new JPanel();
        jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));
        jp.add(new JLabel(icsp.getClasses(lc.get(i))));
        jp.add(new JLabel(icsp.getLocation(lc.get(i))));
        jp.add(new JLabel(icsp.getGroupesEtProfs(lc.get(i))));
        jp.setPreferredSize(new Dimension(width,height));
        jp.add(new JLabel(icsp.getClasses(lc.get(i))));
        return jp;
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
        //System.out.println(edtd.listeCours);

    }

}