package view;

import javax.swing.JPanel;
import javax.swing.border.Border;
import java.awt.Font;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.LayoutManager;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controller.operation.*;
import controller.operation.concatenacao.GUI;

public class GridButtons extends JPanel implements ActionListener {
    private static LayoutManager layout = new GridBagLayout();
    private GridBagConstraints gbc = new GridBagConstraints();

    private AppInterface controller;
    
    private CustomButton[] buttons;
    private int numButtons;
    private int cols;
    private int rows;

    private Font font;
    private Border border;
    private Color bgColor;
    private Color textColor;
    
    public GridButtons(AppInterface frame, int numButtons, int cols) {
        super(layout);
        this.controller = frame;
        this.numButtons = numButtons;
        defineColsAndRows(numButtons, cols);
        buttons = new CustomButton[numButtons];
        CustomButton temp = new CustomButton();
        this.font = temp.getFont();
        this.border = temp.getBorder();
        this.bgColor = temp.getBackground();
        this.textColor = Color.BLACK;
    }

    private void defineColsAndRows(int numButtons, int cols) {
        if (cols >= numButtons){
            this.cols = numButtons;
            this.rows = 1;
        }else{
            this.cols = cols;
            this.rows = (int) Math.ceil((double) numButtons / cols);
        }
    }

    public void setButtonFont(Font font) {
        this.font = font;
    }

    public void setFontSize(int size) {
        font = font.deriveFont(font.getStyle(), size);
    }

    public void setFontStyle(int style) {
        font = font.deriveFont(style);
    }

    public void setBorder(Border border) {
        this.border = border;
    }

    public void setBgColor(Color color) {
        this.bgColor = color;
    }

    public void setTextColor(Color color) {
        this.textColor = color;
    }

    public void addButtons(String[] names, String[] actions) {
        int row = 0;
        int col = 0;
        for (int i=0; i<numButtons; i++){
            CustomButton button = new CustomButton(names[i]);
            button.setPadding(150, 70);
            button.setFont(font);
            button.setBorder(border);
            button.setBackground(bgColor);
            button.setForeground(textColor);
            button.setFocusPainted(false);
            gbc.insets.set(0, 0, 0, 0);
            gbc.fill =  GridBagConstraints.NONE;
            gbc.ipadx = 0;
            gbc.ipady = 0;
            gbc.weightx = 0;
            gbc.weighty = 0;
            if (col != cols-1){
                gbc.insets.right = 60;
            }
            if (row != rows-1){
                gbc.insets.bottom = 30;
            }
            gbc.gridx = col++;
            gbc.gridy = row;

            button.setActionCommand(actions[i]);
            button.addActionListener(this);
            buttons[i] = button;
            add(button, gbc);

            if (col == cols){
                col = 0;
                row++;
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        String operation = e.getActionCommand();
        ScreenAutomatons newScreen = new ScreenAutomatons(controller);

        if (operation.equals("complemento")){
            newScreen.setOperation(new Complemento(), 1);
            newScreen.setTitle("Complemento de um autômato");
            newScreen.createScreenOne();
        }else if (operation.equals("estrela")){
            newScreen.setOperation(new Estrela(), 1);
            newScreen.setTitle("Estrela de um autômato");
            newScreen.createScreenOne();
        }else if (operation.equals("gerarAFD")){
            controller.dispose();
            newScreen.setOperation(new GerarAFD(), 1);
            newScreen.setTitle("Gerar AFD equivalente a um AFN");
            newScreen.createScreenTree();
        }else if (operation.equals("uniao")){
            newScreen.setOperation(new Uniao(), 2);
            newScreen.setTitle("União de dois autômatos");
            newScreen.createScreenTwo();
        }else if (operation.equals("interseccao")){
            newScreen.setOperation(new Interseccao(), 2);
            newScreen.setTitle("Intersecção de dois autômatos");
            newScreen.createScreenTwo();
        }else if (operation.equals("concatenacao")){
            java.awt.EventQueue.invokeLater(new Runnable() {
	            public void run() {
	            	new GUI().setVisible(true);
	            }
	        });
        }
        controller.setContentPane(newScreen);
        controller.setVisible(true);
    }

}