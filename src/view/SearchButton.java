package view;

import javax.swing.ImageIcon;
import javax.swing.JTextField;
import java.awt.Toolkit;
import java.awt.Component;
import java.awt.Graphics;

public class SearchButton extends CustomButton {
    private JTextField textField;
    private int automatonID;

    public SearchButton(int id) {
        super();
        setIcon(createIcon("/resources/images/lupa.png"));
        this.automatonID = id;
        setFocusPainted(false);
        setActionCommand("searchFile");
    }

    public void setTextField(JTextField textField) {
        this.textField = textField;
    }

    public void setPathInTextField(String path) {
        textField.setText(path);
    }

    public int getAutomatonID() {
        return automatonID;
    }

    private ImageIcon createIcon(String path) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL == null){
            System.out.println("Não foi possível encontrar o arquivo: " + path);
            return null;
        }
        
        return new ImageIcon(Toolkit.getDefaultToolkit().getImage(imgURL)) {
            @Override
            public int getIconWidth() {
                return 17;
            }
            @Override
            public int getIconHeight() {
                return 17;
            }
    
            @Override
            public synchronized void paintIcon(Component c, Graphics g, int x, int y) {
                g.drawImage(getImage(), x, y, 17, 17, null);
            }
        };
    }

}