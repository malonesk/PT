import com.hexidec.ekit.EkitCore;
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
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Stack;
import java.util.TreeSet;



/**
 * Created by sami on 04/01/2017.
 */
public class Resume extends JPanel{

    public enum State {RECHERCHE, LECTURE_BALISE, LECTURE_CONTENU, IGNORER_BALISE};

    private JTree tree;

    //private JTextArea infos
    public Resume(){

        tree = null;
    }

    //vérifie si une balise est prise en compte ou non.
    public boolean estPriseEnCompte(String b)
    {
        return (b.equals("b")) || (b.equals("h1"))  || (b.equals("h2"))  || (b.equals("h3"))  || (b.equals("h4"));
    }

    public void parse(ExtendedHTMLDocument d)
    {
        //La racine de l'arbre
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("ROOT");

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
        Stack<DefaultMutableTreeNode> stack = new Stack<>();
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
                        stack.pop();
                        state = State.IGNORER_BALISE;
                    }
                    //Fin de l'ouverturede balise
                    else
                    {
                        state = State.LECTURE_CONTENU;
                        contenu = "";
                    }

                    break;
                case LECTURE_CONTENU:
                    if (c != '<')
                    {
                        contenu += c;
                    }
                    else
                    {

                        if (estPriseEnCompte(nom_balise))
                        {
                            DefaultMutableTreeNode t = new DefaultMutableTreeNode(nom_balise + " : " + contenu);

                            stack.peek().add(t); //On ajoute le noeud au dernier noeud de la pile
                            stack.push(t); //Et ce noeud devient le dernier noeud de la pile.
                        }

                        //On se prépare à lire la nouvelle balise
                        state = State.LECTURE_BALISE;
                        nom_balise = "";
                    }
                    break;
            }
        }
        tree = new JTree(root);

    }

    public JScrollPane getResume(){
        /*tree.setCellRenderer(new FileRenderer());
        tree.addTreeSelectionListener(this);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);*/
        return (new JScrollPane(tree));

    }

}