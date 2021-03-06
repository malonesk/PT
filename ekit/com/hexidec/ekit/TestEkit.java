/*
GNU Lesser General Public License

Ekit - Java Swing HTML Editor & Viewer
Copyright (C) 2000 Howard Kistler

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
*/

package ekit.com.hexidec.ekit;

import com.hexidec.ekit.EkitCore;
import com.hexidec.ekit.EkitCoreSpell;
import ekit.com.hexidec.ekit.test.ExplorateurFichiers;
import ekit.com.hexidec.ekit.test.Resume;

import javax.security.auth.callback.ConfirmationCallback;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/** Ekit
 * App for editing and saving HTML in a Java text component
 *
 * @author Howard Kistler
 * @version 1.5
 *
 * REQUIREMENTS
 * Java 2 (JDK 1.5 or higher)
 * Swing Library
 */

public class TestEkit extends JFrame implements WindowListener
{
    protected final JMenu edtMenu;
    private final JMenu arboMenu, resumeMenu, configMenu, nouveauCompteMenu, travailEnLigneMenu;
    public EkitCore ekitCore;
    public ExplorateurFichiers explo;
    public JScrollPane explofich;
    private File currentFile = (File)null;
    private JButton refresh;
    private Controller controller;
    public MenuController menuController;
    public JMenuItem afficher, rafraichArboMenu, genererResume, modifierConfig, creerCompte, lierCompte, connexion;
    public Resume resume;
    public JPanel panearbo;

    /** Master Constructor
     * @param sDocument         [String]  A text or HTML document to load in the editor upon startup.
     * @param sStyleSheet       [String]  A CSS stylesheet to load in the editor upon startup.
     * @param sRawDocument      [String]  A document encoded as a String to load in the editor upon startup.
     * @param urlStyleSheet     [URL]     A URL reference to the CSS style sheet.
     * @param includeToolBar    [boolean] Specifies whether the app should include the toolbar.
     * @param showViewSource    [boolean] Specifies whether or not to show the View Source window on startup.
     * @param showMenuIcons     [boolean] Specifies whether or not to show icon pictures in menus.
     * @param editModeExclusive [boolean] Specifies whether or not to use exclusive edit mode (recommended on).
     * @param sLanguage         [String]  The language portion of the Internationalization Locale to run Ekit in.
     * @param sCountry          [String]  The country portion of the Internationalization Locale to run Ekit in.
     * @param base64            [boolean] Specifies whether the raw document is Base64 encoded or not.
     * @param debugMode         [boolean] Specifies whether to show the Debug menu or not.
     * @param useSpellChecker   [boolean] Specifies whether to include the spellchecker or not.
     * @param multiBar          [boolean] Specifies whether to use multiple toolbars or one big toolbar.
     * @param enterBreak        [boolean] Specifies whether the ENTER key should insert breaks instead of paragraph tags.
     */
    public TestEkit(String sDocument, String sStyleSheet, String sRawDocument, URL urlStyleSheet, boolean includeToolBar, boolean showViewSource, boolean showMenuIcons, boolean editModeExclusive, String sLanguage, String sCountry, boolean base64, boolean debugMode, boolean useSpellChecker, boolean multiBar, boolean enterBreak)
    {
        if(useSpellChecker)
        {
            ekitCore = new EkitCoreSpell(false, sDocument, sStyleSheet, sRawDocument, null, urlStyleSheet, includeToolBar, showViewSource, showMenuIcons, editModeExclusive, sLanguage, sCountry, base64, debugMode, true, multiBar, (multiBar ? EkitCore.TOOLBAR_DEFAULT_MULTI : EkitCore.TOOLBAR_DEFAULT_SINGLE), enterBreak);
        }
        else
        {
            ekitCore = new EkitCore(false, sDocument, sStyleSheet, sRawDocument, null, urlStyleSheet, includeToolBar, showViewSource, showMenuIcons, editModeExclusive, sLanguage, sCountry, base64, debugMode, false, multiBar, (multiBar ? EkitCore.TOOLBAR_DEFAULT_MULTI : EkitCore.TOOLBAR_DEFAULT_SINGLE), enterBreak);
        }
        Configuration config = new Configuration();
        try {
            explo=new ExplorateurFichiers(config.getConfig("online.root"),ekitCore);
        } catch (Exception e) {
            e.printStackTrace();
        }
        explofich=explo.getExplorateur();
        controller=new Controller(this);

        ekitCore.setFrame(this);
        JMenuBar bar=new JMenuBar();
        JMenu ekitMenu = new JMenu("Ekit");
        ekitMenu.add(new JMenuItem("Nouveau"));


        edtMenu = new JMenu("Emploi du temps");
        afficher = new JMenuItem("Afficher");
        this.add(afficher);
        edtMenu.add(afficher);

        arboMenu = new JMenu("Arborescence");
        rafraichArboMenu = new JMenuItem("Rafraichir");
        this.add(rafraichArboMenu);
        arboMenu.add(rafraichArboMenu);

        resumeMenu = new JMenu("Resumé");
        genererResume = new JMenuItem("Générer");
        configMenu = new JMenu("Paramètres");
        nouveauCompteMenu = new JMenu("Compte en ligne");
        travailEnLigneMenu = new JMenu("Online");

        this.add(resumeMenu);
        this.add(configMenu);
        this.add(nouveauCompteMenu);
        this.add(travailEnLigneMenu);

        modifierConfig = new JMenuItem("Modifier");
        creerCompte = new JMenuItem("Creer un compte");
        lierCompte = new JMenuItem("Lier un compte");
        connexion = new JMenuItem("Connexion");

        configMenu.add(modifierConfig);
        resumeMenu.add(genererResume);
        nouveauCompteMenu.add(creerCompte);
        nouveauCompteMenu.add(lierCompte);
        travailEnLigneMenu.add(connexion);

        bar.add(ekitMenu);
        bar.add(edtMenu);
        bar.add(arboMenu);
        bar.add(resumeMenu);
        bar.add(configMenu);
        bar.add(nouveauCompteMenu);
        bar.add(travailEnLigneMenu);

        resume = new Resume();
        resume.parse(ekitCore.getExtendedHtmlDoc());
        resume.getResume();



        this.add(bar);
        JPanel paneEkit=new JPanel();
        panearbo=new JPanel();
        JPanel paneResume=new JPanel();

		/* Add the components to the app */
        if(includeToolBar)
        {
            if(multiBar)
            {
                paneEkit.setLayout(new GridBagLayout());
                panearbo.setLayout(new GridBagLayout());
                paneResume.setLayout(new GridLayout());
                GridBagConstraints gbcResume = new GridBagConstraints();
                GridBagConstraints gbcarbo = new GridBagConstraints();
                GridBagConstraints gbc = new GridBagConstraints();
                gbcarbo.fill       = GridBagConstraints.HORIZONTAL;
                gbcarbo.anchor     = GridBagConstraints.NORTH;
                gbcarbo.gridheight = 1;
                gbcarbo.gridwidth  = 1;
                gbcarbo.weightx    = 1.0;
                gbcarbo.weighty    = 0.0;
                gbcarbo.gridx      = 1;
                gbcarbo.gridy      = 1;


                gbc.fill       = GridBagConstraints.HORIZONTAL;
                gbc.anchor     = GridBagConstraints.NORTH;
                gbc.gridheight = 1;
                gbc.gridwidth  = 1;
                gbc.weightx    = 1.0;
                gbc.weighty    = 0.0;
                gbc.gridx      = 1;

                gbc.gridy      = 1;
                paneEkit.add(ekitCore.getMenuBar(), gbc);
                gbc.gridy      = 2;


                paneEkit.add(ekitCore.getToolBarMain(includeToolBar), gbc);

                gbc.gridy      = 3;
                refresh=new JButton("Rafraichir");
                paneEkit.add(ekitCore.getToolBarFormat(includeToolBar), gbc);

                gbc.gridy      = 4;
                paneEkit.add(ekitCore.getToolBarStyles(includeToolBar), gbc);

                gbc.anchor     = GridBagConstraints.SOUTH;
                gbc.fill       = GridBagConstraints.BOTH;
                gbc.weighty    = 1.0;
                gbc.gridy      = 5;

                paneEkit.add(ekitCore, gbc);
                gbcarbo.gridy=3;
                panearbo.add(refresh,gbcarbo);
                gbcarbo.gridy=4;
                gbcarbo.anchor     = GridBagConstraints.SOUTH;
                gbcarbo.fill       = GridBagConstraints.BOTH;
                gbcarbo.weighty    = 1.0;
                panearbo.add(explofich,gbcarbo);



                gbcResume.fill       = GridBagConstraints.HORIZONTAL;
                gbcResume.anchor     = GridBagConstraints.NORTH;
                gbcResume.gridheight = 1;
                gbcResume.gridwidth  = 1;
                gbcResume.weightx    = 1.0;
                gbcResume.weighty    = 0.0;
                gbcResume.gridx      = 1;
                gbcResume.gridy      = 1;

                paneResume.add(resume);




                setActionListener(controller);
                fnAddActionListener(arboMenu);
                fnAddActionListener(edtMenu);
                fnAddActionListener(resumeMenu);
                fnAddActionListener(configMenu);
                fnAddActionListener(nouveauCompteMenu);
                fnAddActionListener(travailEnLigneMenu);


                //this.getContentPane().add(refresh,gbc);
            }
            else
            {
                paneResume.setLayout(new BorderLayout());
                paneEkit.setLayout(new BorderLayout());
                panearbo.setLayout(new BorderLayout());
                paneEkit.add(ekitCore, BorderLayout.CENTER);
                panearbo.add(explofich,BorderLayout.EAST);
                paneResume.add(resume,BorderLayout.WEST);
                paneEkit.add(ekitCore.getToolBar(includeToolBar), BorderLayout.NORTH);
            }
        }
        else
        {
            paneResume.setLayout(new BorderLayout());
            paneEkit.setLayout(new BorderLayout());
            panearbo.setLayout(new BorderLayout());
            paneEkit.add(ekitCore, BorderLayout.CENTER);
            panearbo.add(explofich,BorderLayout.EAST);
            paneResume.add(resume,BorderLayout.WEST);
        }

        this.getContentPane().setLayout(new GridBagLayout());
        this.getContentPane().setLayout(new GridBagLayout());

        GridBagConstraints gbcFrame = new GridBagConstraints();
        gbcFrame.fill       = GridBagConstraints.HORIZONTAL;
        gbcFrame.anchor     = GridBagConstraints.NORTH;
        gbcFrame.gridheight = 1;
        gbcFrame.gridwidth  = 1;
        gbcFrame.weightx    = 1.0;
        gbcFrame.weighty    = 0.0;
        gbcFrame.gridx      = 1;

        gbcFrame.gridy      = 1;
        paneResume.setPreferredSize(new Dimension(200,515));
        this.add(paneResume,gbcFrame);
        gbcFrame.gridx      = 2;
        this.add(paneEkit,gbcFrame);
        gbcFrame.gridx      = 3;
        panearbo.setPreferredSize(new Dimension(200,515));
        this.add(panearbo,gbcFrame);
        this.setJMenuBar(bar); //bar de menu : files, settings etc.

        this.addWindowListener(this);
        currentFile=explo.getSelection();

        this.updateTitle();
        this.pack();
        this.setVisible(true);

    }
    public void setActionListener(ActionListener listener){
        refresh.addActionListener(listener);
        //afficher.addActionListener(listener);
        //rafraichArboMenu.addActionListener(listener);
    }

    public TestEkit()
    {
        this(null, null, null, null, true, false, true, true, null, null, false, false, false, true, false);
    }

    /* WindowListener methods */
    public void windowClosing(WindowEvent we)
    {
        this.dispose();
        System.exit(0);
    }
    public void windowOpened(WindowEvent we)      { ; }
    public void windowClosed(WindowEvent we)      { ; }
    public void windowActivated(WindowEvent we)   { ; }
    public void windowDeactivated(WindowEvent we) { ; }
    public void windowIconified(WindowEvent we)   { ; }
    public void windowDeiconified(WindowEvent we) { ; }

    /** Convenience method for updating the application title bar
     */
    private void updateTitle()
    {
        this.setTitle(ekitCore.getAppName() + (currentFile == null ? "" : " - " + currentFile.getName()));
    }

    /** Usage method
     */

    public static void usage()
    {
        System.out.println("usage: com.hexidec.ekit.Ekit [-t|t+|T] [-s|S] [-m|M] [-x|X] [-b|B] [-v|V] [-p|P] [-fFILE] [-cCSS] [-rRAW] [-uURL] [-lLANG] [-d|D] [-h|H|?]");
        System.out.println("       Each option contained in [] brackets is optional,");
        System.out.println("       and can be one of the values separated be the | pipe.");
        System.out.println("       Each option must be proceeded by a - hyphen.");
        System.out.println("       The options are:");
        System.out.println("         -t|t+|T : -t = single toolbar, -t+ = multiple toolbars, -T = no toolbar");
        System.out.println("         -s|S    : -s = show source window on startup, -S = hide source window");
        System.out.println("         -m|M    : -m = show icons on menus, -M = no menu icons");
        System.out.println("         -x|X    : -x = exclusive document/source windows, -X = use split window");
        System.out.println("         -b|B    : -b = use Base64 document encoding, -B = use regular encoding");
        System.out.println("         -v|V    : -v = include spell checker, -V = omit spell checker");
        System.out.println("         -p|P    : -p = ENTER key inserts paragraph, -P = inserts break");
        System.out.println("         -fFILE  : load HTML document on startup (replace FILE with file name)");
        System.out.println("         -cCSS   : load CSS stylesheet on startup (replace CSS with file name)");
        System.out.println("         -rRAW   : load raw document on startup (replace RAW with file name)");
        System.out.println("         -uURL   : load document at URL on startup (replace URL with file URL)");
        System.out.println("         -lLANG  : specify the starting language (defaults to your locale)");
        System.out.println("                    replace LANG with xx_XX format (e.g., US English is en_US)");
        System.out.println("         -d|D    : -d = DEBUG mode on, -D = DEBUG mode off (developers only)");
        System.out.println("         -h|H|?  : print out this help information");
        System.out.println("         ");
        System.out.println("The defaults settings are equivalent to: -t+ -S -m -x -B -V -p -D");
        System.out.println("         ");
        System.out.println("For further information, read the README file.");
    }


    /** Main method
     */
    public static void main(String[] args)
    {
        String sDocument = null;
        String sStyleSheet = null;
        String sRawDocument = null;
        URL urlStyleSheet = null;
        boolean includeToolBar = true;
        boolean multibar = true;
        boolean includeViewSource = false;
        boolean includeMenuIcons = true;
        boolean modeExclusive = true;
        String sLang = null;
        String sCtry = null;
        boolean base64 = false;
        boolean debugOn = false;
        boolean spellCheck = false;
        boolean enterBreak = false;
        for(int i = 0; i < args.length; i++)
        {
            if     (args[i].equals("-h") ||
                    args[i].equals("-H") ||
                    args[i].equals("-?"))     { usage(); }
            else if(args[i].equals("-t"))     { includeToolBar = true; multibar = false; }
            else if(args[i].equals("-t+"))    { includeToolBar = true; multibar = true; }
            else if(args[i].equals("-T"))     { includeToolBar = false; multibar = false; }
            else if(args[i].equals("-s"))     { includeViewSource = true; }
            else if(args[i].equals("-S"))     { includeViewSource = false; }
            else if(args[i].equals("-m"))     { includeMenuIcons = true; }
            else if(args[i].equals("-M"))     { includeMenuIcons = false; }
            else if(args[i].equals("-x"))     { modeExclusive = true; }
            else if(args[i].equals("-X"))     { modeExclusive = false; }
            else if(args[i].equals("-b"))     { base64 = true; }
            else if(args[i].equals("-B"))     { base64 = false; }
            else if(args[i].startsWith("-f")) { sDocument = args[i].substring(2, args[i].length()); }
            else if(args[i].startsWith("-c")) { sStyleSheet = args[i].substring(2, args[i].length()); }
            else if(args[i].startsWith("-r")) { sRawDocument = args[i].substring(2, args[i].length()); }
            else if(args[i].equals("-v"))     { spellCheck = true; }
            else if(args[i].equals("-V"))     { spellCheck = false; }
            else if(args[i].equals("-p"))     { enterBreak = false; }
            else if(args[i].equals("-P"))     { enterBreak = true; }
            else if(args[i].startsWith("-u"))
            {
                try
                {
                    urlStyleSheet = new URL(args[i].substring(2, args[i].length()));
                }
                catch(MalformedURLException murle)
                {
                    murle.printStackTrace(System.err);
                }
            }
            else if(args[i].startsWith("-l"))
            {
                if(args[i].indexOf('_') == 4 && args[i].length() >= 7)
                {
                    sLang = args[i].substring(2, args[i].indexOf('_'));
                    sCtry = args[i].substring(args[i].indexOf('_') + 1, args[i].length());
                }
            }
            else if(args[i].equals("-d"))     { debugOn = true; }
            else if(args[i].equals("-D"))     { debugOn = false; }
        }
        TestEkit ekit = new TestEkit(sDocument, sStyleSheet, sRawDocument, urlStyleSheet, includeToolBar, includeViewSource, includeMenuIcons, modeExclusive, sLang, sCtry, base64, debugOn, spellCheck, multibar, enterBreak);
    }

    public void fnAddActionListener(JMenu mnu) {
        if (mnu.getItemCount() != 0) {
            for (int iCount = 0; iCount < mnu.getItemCount(); iCount++) {
                (mnu.getItem(iCount)).addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        fnMenuItemAction(e);
                        System.out.println("ok");
                    }
                });
            }
        }
    }

    //menu item action event
    public void fnMenuItemAction(ActionEvent e) {
        if (e.getSource().equals(afficher)) {
            System.out.println("ok");
            EdtDisplayer edtd = new EdtDisplayer("ADECal.ics");
        } else if (e.getSource().equals(genererResume)) {
            resume.parse(ekitCore.getExtendedHtmlDoc());
            resume.getResume();
            getContentPane().repaint();
            //ekit.getContentPane().add(ekit.resume);
            resume.revalidate();
            try {
                resume.ecrire("salut.txt");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        else if (e.getSource().equals(rafraichArboMenu)) {
            Configuration config = new Configuration();
            try {
                System.out.println(config.getConfig("online.root"));
                explo=new ExplorateurFichiers(config.getConfig("online.root"),ekitCore);
                explo.getTree().updateUI();
                explofich = new JScrollPane(explo.getExplorateur());
                explo.getTree().revalidate();
                panearbo.revalidate();
                SwingUtilities.updateComponentTreeUI(panearbo);


            } catch (Exception e1) {
                e1.printStackTrace();
            }
            SwingUtilities.updateComponentTreeUI(this);
        }
        else if (e.getSource().equals(modifierConfig)) {
            ConfigDisplayer cfdisp = new ConfigDisplayer();
        }
        else if (e.getSource().equals(creerCompte)) {
            NouveauCompteDisplayer newcomptedisp = new NouveauCompteDisplayer();
        }
        else if (e.getSource().equals(lierCompte)) {
            LierCompteDisplayer liercdisp = new LierCompteDisplayer();
        }
        else if (e.getSource().equals(connexion)) {
            TravailGroupeDisplayer tgdisp = new TravailGroupeDisplayer();
        }
    }


}