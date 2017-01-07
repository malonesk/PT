import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.model.Calendar;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
 * Created by malonesk on 04/01/17.
 */
public class IcsParser {
    public FileInputStream fin;
    public Calendar cal;
    public DateRange currentWeek;
    public List<String> weekLocations;
    private List<String> weekClasses;
    private List<String> weekGroupes_profs;
    public List<String> locations;
    private List<String> classes;
    private List<String> groupes_profs;
    public List<Component> weekComponent;
    public GridFactory edtDisp;
    public Case cell;

    public  IcsParser(String path) {
        try {
            fin = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        CalendarBuilder builder = new CalendarBuilder();
        try {
            cal = builder.build(fin);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserException e) {
            e.printStackTrace();
        }
        currentWeek=initDateRangeCal();
        weekClasses=new ArrayList<>();
        weekGroupes_profs=new ArrayList<>();
        weekLocations=new ArrayList<>();
        weekComponent=new ArrayList<>();
    }

    public IcsParser(String path, DateRange dr ) {
        try {
            fin = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        CalendarBuilder builder = new CalendarBuilder();
        try {
            cal = builder.build(fin);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserException e) {
            e.printStackTrace();
        }
        currentWeek=initDateRangeCal(dr);
        weekClasses=new ArrayList<>();
        weekGroupes_profs=new ArrayList<>();
        weekLocations=new ArrayList<>();
        weekComponent=new ArrayList<>();
        setWeekComponents();
    }

    public DateRange initDateRangeCal(DateRange dr) {

        Date start, end, today;
        SimpleDateFormat formaterThisWeeksMonday = new SimpleDateFormat("dd/MM/yyyy");
        today = dr.getRangeStart();

        SimpleDateFormat formaterW = new SimpleDateFormat("ww");
        SimpleDateFormat formaterY = new SimpleDateFormat("yyyy");
        int sem = Integer.parseInt(formaterW.format(today));
        int ann = Integer.parseInt(formaterY.format(today));

        Date thisWeek = formaterThisWeeksMonday.parse("01/01/"+ann, new ParsePosition(0));
        SimpleDateFormat formaterDay = new SimpleDateFormat("EEE");
        while(!formaterDay.format(thisWeek).equals("lun.")) {
            thisWeek=ajouterJour(thisWeek,1);
        }
        for (int i=1;i<sem;i++) {
            thisWeek=ajouterJour(thisWeek,7);
        }
        start = thisWeek;
        end = ajouterJour(start,7);
        return new DateRange(start,end);
    }

    public DateRange initDateRangeCal() {

        Date start, end, today;
        today = new Date();
        //today = formaterThisWeeksMonday.parse("05/10/2016", new ParsePosition(0));
        SimpleDateFormat formaterThisWeeksMonday = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formaterW = new SimpleDateFormat("ww");
        SimpleDateFormat formaterY = new SimpleDateFormat("yyyy");
        int sem = Integer.parseInt(formaterW.format(today));
        int ann = Integer.parseInt(formaterY.format(today));

        Date thisWeek = formaterThisWeeksMonday.parse("01/01/"+ann, new ParsePosition(0));
        SimpleDateFormat formaterDay = new SimpleDateFormat("EEE");
        while(!formaterDay.format(thisWeek).equals("lun.")) {
            thisWeek=ajouterJour(thisWeek,1);
        }
        for (int i=1;i<sem;i++) {
            thisWeek=ajouterJour(thisWeek,7);
        }
        start = thisWeek;
        end = ajouterJour(start,7);
        return new DateRange(start,end);
    }

    public void initWeekComponent(DateRange currentWeek) {
        this.currentWeek=currentWeek;
        for (Iterator i = cal.getComponents().iterator(); i.hasNext();) {
            Component component = (Component) i.next();
            for (Iterator j = component.getProperties().iterator(); j.hasNext(); ) {
                Property property = (Property) j.next();
                if (property.getName().equals("DTSTART")) {
                    Date d = null;
                    try {
                        d = stringToDate2(property);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if (isInWeek2(d)) {
                        weekComponent.add(component);
                    }
                }
            }
        }
        sortComponentByDate();
    }

    public Date ajouterJour(Date date, int nbJour) {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(date);
        cal.add(java.util.Calendar.DATE, nbJour);
        return cal.getTime();
    }

    public void iterateOverCalendar() {
        for (Iterator i = cal.getComponents().iterator(); i.hasNext();) {
            Component component = (Component) i.next();
            System.out.println("Component [" + component.getName() + "]");

            for (Iterator j = component.getProperties().iterator(); j.hasNext();) {
                Property property = (Property) j.next();
                System.out.println("Property [" + property.getName() + ", " + property.getValue() + "]");
            }
        }

    }

    public void setParseInfo(DateRange dr) {
        for (Iterator i = cal.getComponents().iterator(); i.hasNext();) {
            Component component = (Component) i.next();
            for (Iterator j = component.getProperties().iterator(); j.hasNext();) {
                Property property = (Property) j.next();
                if (property.getName().equals("DTSTART") )
                    if (property.getName()=="LOCATION") locations.add(property.getValue());
                    if (property.getName()=="SUMMARY") classes.add(property.getValue());
                    if (property.getName()=="DESCRIPTION") groupes_profs.add(property.getValue());
            }
        }
    }

    public void updateCurrentWeekInfo(List<Component> compsInWeek) {
        for (Component c : compsInWeek) {
            weekComponent.add(c);
            for (Iterator j = c.getProperties().iterator(); j.hasNext();) {
                Property property = (Property) j.next();
                if (property.getName().equals("LOCATION")  && property.getValue()!=null) weekLocations.add(property.getValue());
                if (property.getName().equals("SUMMARY") && property.getValue()!=null) weekClasses.add(property.getValue());
                if (property.getName().equals("DESCRIPTION") && property.getValue()!=null) weekGroupes_profs.add(property.getValue());
            }
        }
    }
    public void setWeekComponents() {
        weekComponent = new ArrayList<>();
        for (Iterator i = cal.getComponents().iterator(); i.hasNext();) {
            Component component = (Component) i.next();

            for (Iterator j = component.getProperties().iterator(); j.hasNext();) {
                Property property = (Property) j.next();
                if (property.getName().equals("DTSTART")) {
                    Date d = null;
                    try {
                        d = stringToDate2(property);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if (isInWeek2(d)) {
                        weekComponent.add(component);
                    }
                }
            }
        }
        sortComponentByDate();
    }

    /*
    public ArrayList<Component> getWeekInfo() {
        ArrayList<Component> comps = new ArrayList<>();
        for (Iterator i = cal.getComponents().iterator(); i.hasNext();) {
            Component component = (Component) i.next();

            for (Iterator j = component.getProperties().iterator(); j.hasNext();) {
                Property property = (Property) j.next();
                if (property.getName().equals("DTSTART")) {
                    Date d = null;
                    try {
                        d = stringToDate2(property);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if (isInWeek2(d)) {
                        //System.out.println(d.toString());
                        comps.add(component);
                    }
                }
            }
        }
        return comps;
    }*/

    public boolean isInWeek2(Date d) {
        return currentWeek.includes(d);
    }

    public List<Component> getWeekComponent() {
        return weekComponent;
    }
    public Date stringToDate2(Property prop) throws ParseException {
        if (prop.getValue()==null) {
            System.out.println("propriété sans valeur");
            return null;
        }
        String d = prop.getValue();
        SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd'T'kkmmss'Z'");
        Date da = formater.parse(d, new ParsePosition(0));
        return formater.parse(d, new ParsePosition(0));
    }

    public void printWeek() {
        for (int i=0;i<weekClasses.size();i++) {
            System.out.println(weekClasses.get(i));
        }
        for (int i=0;i<weekGroupes_profs.size();i++) {
            System.out.println(weekGroupes_profs.get(i));
        }
        for (int i=0;i<weekLocations.size();i++) {
            System.out.println(weekLocations.get(i));
        }
    }

    public String getLocation(Component component) {
        String loc="";
        for (Iterator j = component.getProperties().iterator(); j.hasNext();) {
            Property property = (Property) j.next();
            if (property.getName().equals("LOCATION")) {
                loc=property.getValue();
            }
        }
        return loc;
    }

    public String getClasses(Component component) {
        String matiere="";
        for (Iterator j = component.getProperties().iterator(); j.hasNext();) {
            Property property = (Property) j.next();
            if (property.getName().equals("SUMMARY")) {
                matiere=property.getValue();
            }
        }
        return matiere;
    }

    public String getGroupesEtProfs(Component component) {
        String coursEtProfs="";
        for (Iterator j = component.getProperties().iterator(); j.hasNext();) {
            Property property = (Property) j.next();
            if (property.getName().equals("DESCRIPTION")) {
                coursEtProfs=property.getValue();
            }
        }
        return coursEtProfs;
    }

    public void sortComponentByDate() {
        boolean liOrdonnee = false;
        int taille = weekComponent.size();
        while(!liOrdonnee)
        {
            liOrdonnee = true;
            for(int i=0 ; i < taille-2 ; i++) {
                Date datedebnext = null;
                try {
                    datedebnext = stringToDate2(weekComponent.get(i + 1).getProperty("DTSTART"));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Date datedeb = null;
                try {
                    datedeb = stringToDate2(weekComponent.get(i).getProperty("DTSTART"));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if(datedeb.after(datedebnext)){
                    Collections.swap(weekComponent,i, i+1);
                    liOrdonnee = false;
                }
            }
            taille--;
        }
    }


    public Case buildCase(Component component) {
        return new Case(component);
    }

    public static void main(String args[]) {
        IcsParser icsp= new IcsParser("ADECal.ics");
        //icsp.iterateOverCalendar();
        icsp.setWeekComponents();
        //icsp.printWeek();
        System.out.println("************************************************\n"+icsp.getWeekComponent());
        List<Component> listWeek= new ArrayList<>();
        List<Case> lca = new ArrayList<>();
        listWeek=icsp.getWeekComponent();
        if (listWeek.size()==0) System.out.println("taille listWeek 0");
    }



}
