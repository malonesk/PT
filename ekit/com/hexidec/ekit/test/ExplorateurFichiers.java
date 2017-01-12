package ekit.com.hexidec.ekit.test;

import com.hexidec.ekit.EkitCore;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.text.BadLocationException;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.io.File;
import java.io.IOException;

/**
 * Created by Antonin on 04/01/2017.
 */
public class ExplorateurFichiers extends JPanel implements TreeSelectionListener {
    private File root;
    private TreeModel modele;
    private JTree tree;
    private File selection;
    private EkitCore ekit;
    //private JTextArea infos;
    public ExplorateurFichiers(String repertoire,EkitCore ekit){
        this.ekit=ekit;
        root = new File(repertoire);
        modele = new FileTreeModel(root);
        tree = new JTree(modele);

    }
    public JScrollPane getExplorateur(){
        tree.setCellRenderer(new FileRenderer());
        tree.addTreeSelectionListener(this);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        return (new JScrollPane(tree));

    }

    public File getSelection(){
        return selection;
    }
    public void valueChanged(TreeSelectionEvent e){
        
        TreePath path = e.getPath();
        File file = (File)path.getLastPathComponent();
        selection=file;

        String args[]={"-f"+file.getName()};
        try {
            ekit.loadDocument(file);
        } catch (IOException e1) {
        } catch (BadLocationException e1) {
            e1.printStackTrace();
        }


    }

}