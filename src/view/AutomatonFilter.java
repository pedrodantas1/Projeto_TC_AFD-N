package view;

import java.io.File;
import javax.swing.filechooser.FileFilter;

public class AutomatonFilter extends FileFilter {

    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }
        
        String extension = getExtension(f);
        if (extension != null){
            if (extension.equals("jff")){
                return true;
            }
        }
        
        return false;
    }

    public String getDescription() {
        return "JFF files";
    }

    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1){
            ext = s.substring(i+1).toLowerCase();
        }
        
        return ext;
    }
    
}