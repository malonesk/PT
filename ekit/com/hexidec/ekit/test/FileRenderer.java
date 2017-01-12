package com.hexidec.ekit.test;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.io.File;

/**
 * Created by Antonin on 04/01/2017.
 */
public class FileRenderer extends DefaultTreeCellRenderer {
    public FileRenderer(){
        super();
    }
    public Component getTreeCellRendererComponent(JTree tree, Object value
            , boolean selected, boolean expanded
            , boolean leaf, int row, boolean hasFocus){
        JLabel label = (JLabel)super.getTreeCellRendererComponent(tree,value,selected,expanded,leaf,row,hasFocus);
        File fichier = (File)value;
        FileSystemView sys = FileSystemView.getFileSystemView();
        label.setText(sys.getSystemDisplayName(fichier));
        label.setIcon(sys.getSystemIcon(fichier));
        return label;
    }
}