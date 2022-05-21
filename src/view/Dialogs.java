package view;

import javax.swing.JOptionPane;

public class Dialogs {
    
    public static void showMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    public static void showMessage(String title ,String message) {
        JOptionPane.showMessageDialog(null, message, title, 
        JOptionPane.INFORMATION_MESSAGE);
    }

    public static int showConfirmDialog(String title ,String message) {
        return JOptionPane.showConfirmDialog(null, message, title,
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
    }

}