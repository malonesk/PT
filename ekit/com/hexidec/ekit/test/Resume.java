package ekit.com.hexidec.ekit.test;

import com.hexidec.ekit.component.ExtendedHTMLDocument;
import sun.reflect.generics.tree.Tree;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Stack;
import java.util.TreeSet;



/**
 * Created by sami on 04/01/2017.
 */
public class Resume extends JPanel{

    public enum State {RECHERCHE, LECTURE_BALISE, LECTURE_CONTENU, IGNORER_BALISE, BALISE_FERMANTE, IGNORER_BALISE_DANS_CONTENU,
                        VALIDATION_CONTENU, LECTURE_BALISE_DANS_CONTENU};
    public ElementsResume root = new ElementsResume("h1", "ROOT");

    //private JTextArea infos
    public Resume()
    {
        root.level = 0;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setMaximumSize(getSize());
        setBackground(new Color(220, 220, 220));

    }

    //vérifie si une balise est prise en compte ou non.
    public boolean estPriseEnCompte(String b)
    {
        return (b.equals("b")) || (b.equals("h1"))  || (b.equals("h2"))  || (b.equals("h3"))  || (b.equals("h4") || b.equals("h5") || b.equals("h6"));
    }

    public static int getLevel(String b)
    {
        if (b.equals("h0"))
            return 0;
        if (b.equals("h1"))
            return 1;
        if (b.equals("h2"))
            return 2;
        if (b.equals("h3"))
            return 3;
        if (b.equals("h4"))
            return 4;
        if (b.equals("h5"))
            return 5;
        if (b.equals("h6"))
            return 6;
        if (b.equals("b"))
            return 7;

        return 9;
    }

    public void parse(ExtendedHTMLDocument d)
    {
        //La racine de l'arbre
        root = new ElementsResume("h0", "ROOT");

        //debug
        System.out.println("PARSE");


        //Cela sert à écrire le code HTML dans un String
        HTMLEditorKit kit = new HTMLEditorKit();

        StringWriter writer = new StringWriter();
        try {
            kit.write(writer, d, 0, d.getLength());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        String s = writer.toString();

        //La pile des noeuds, on ajoute les nouveaux noeuds au dernier de la pile.
        Stack<ElementsResume> stack = new Stack<>();
        stack.add(root);

        State state = State.RECHERCHE;

        String nom_balise = null;
        String contenu = null;


        //La parsing à proprement parler
        for(int i = 0; i < s.length(); i++)
        {
            char c = s.charAt(i);

            switch(state)
            {
                case RECHERCHE:
                    if(c == '<')
                    {
                        state = State.LECTURE_BALISE;
                        nom_balise = "";
                    }
                    break;
                case IGNORER_BALISE:
                    if (c == '>')
                        state = State.RECHERCHE;
                    break;
                case LECTURE_BALISE:
                    //Ouverture de balise
                    if(c != '>' && c != '/')
                    {
                        nom_balise += c;
                    }
                    //Fermeture de balise
                    else if (nom_balise.equals("") && c == '/')
                    {
                        //On n'a pas besoin de lire le nom de la balise fermée : ce sera la dernière ouverte.
                        //stack.pop();
                        //state = State.IGNORER_BALISE;
                        nom_balise = "";
                        state = State.IGNORER_BALISE;
                    }
                    //Fin de l'ouverturede balise
                    else
                    {
                        state = State.LECTURE_CONTENU;
                        contenu = "";
                    }

                    break;
                case BALISE_FERMANTE:
                    if (c != '>')
                    {
                        nom_balise += c;
                    }
                    else
                    {
                        nom_balise = "";
                        state = State.RECHERCHE;
                    }
                    break;
                case IGNORER_BALISE_DANS_CONTENU:
                    if (c == '>')
                    {
                        state = State.LECTURE_CONTENU;
                    }
                    break;
                case LECTURE_CONTENU:
                    if (c != '<')
                    {
                        contenu += c;
                    }
                    // Fin de contenu car nouvelle balise
                    else
                    {

                        if (estPriseEnCompte(nom_balise))
                        {
                            ElementsResume t = new ElementsResume(nom_balise, contenu);

                            while ((stack.size() > 1) && (getLevel(nom_balise) <= getLevel(stack.peek().balise)))
                                stack.pop();

                            stack.peek().add(t); //On ajoute le noeud au dernier noeud de la pile

                            stack.push(t);
                        }
                        else
                        {
                            state = State.IGNORER_BALISE_DANS_CONTENU;
                        }

                        //On se prépare à lire la nouvelle balise
                        state = State.LECTURE_BALISE;
                        nom_balise = "";
                    }
                    break;
                case VALIDATION_CONTENU:

                    break;
            }
        }
        System.out.println(root);

    }

    public void ecrire(String filepath) throws IOException
    {
        FileWriter f = new FileWriter(filepath);
        String s = root.toString();
        f.write(s);
        f.flush();
        f.close();

    }

    public void getResume(){

        this.removeAll();
        root.arborescenceRecurJPanel(this, root, 0, 0);

    }

}