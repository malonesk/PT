package ekit.com.hexidec.ekit;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import java.io.File;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Antonin on 04/01/2017.
 */
public class FileTreeModel implements TreeModel, Serializable {
    private File root;
    public FileTreeModel(File file){
        root = file;
    }
    public List getFichiers(Object parent){
        File fileParent = (File)parent;
        File[] fichiers = fileParent.listFiles();
        Arrays.sort(fichiers,new Comparator<File>(){
            public int compare(File f1,File f2){
                boolean dirf1 = f1.isDirectory();
                boolean dirf2 = f2.isDirectory();
                if(dirf1&&!dirf2){return -1;}
                if(!dirf1&&dirf2){return 1;}
                return f1.getPath().compareTo(f2.getPath());
            }
        });
        return    Arrays.asList(fichiers);
    }
    public Object getRoot(){
        return root;
    }
    public Object getChild(Object parent, int index){
        return getFichiers(parent).get(index);
    }
    public int getChildCount(Object parent){
        return getFichiers(parent).size();
    }
    public int getIndexOfChild(Object parent, Object child){
        return getFichiers(parent).indexOf(child);
    }
    public boolean isLeaf(Object node){
        File f = (File)node;
        return !f.isDirectory();
    }
    public void valueForPathChanged(TreePath path, Object newValue){}
    public void addTreeModelListener(TreeModelListener l){}
    public void removeTreeModelListener(TreeModelListener l){}
}