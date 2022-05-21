package view;

import javax.swing.*;
import java.awt.*;

public class CustomButton extends JButton {
    
    public CustomButton() {
        super();
    }

    public CustomButton(String nome) {
        super(nome);
    }

    public CustomButton(Icon icon) {
        super(icon);
    }

    public CustomButton(String nome, Icon icon) {
        super(nome, icon);
    }

    public void setPadding(int width, int height) {
        this.add(Box.createRigidArea(new Dimension(width, height)));
    }
}