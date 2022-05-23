package controller.operation.concatenacao;

import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class GUI extends javax.swing.JFrame {
	private static final long serialVersionUID = 1L;
	private String pathAutomato1;
    private String pathAutomato2;
    private String pathSave;
    
    Concatenacao concatenacao = new Concatenacao();
    
	public GUI() {
		this.pathAutomato1 = new String();
        this.pathAutomato2 = new String();
        this.pathSave = new String();
		
		JFrame frame = new JFrame();
		ImageIcon logo = new ImageIcon((System.getProperty("user.dir")+"\\logo.png"));
		frame.setIconImage(logo.getImage());
		
		ImageIcon exit = new ImageIcon((System.getProperty("user.dir")+"\\exit.png"));
		Image e1 = exit.getImage(); // transform it 
		Image e2 = e1.getScaledInstance(40, 50,  java.awt.Image.SCALE_SMOOTH);
		exit = new ImageIcon(e2);
		
		JButton b1 = new JButton("Escolher pasta destino");
		b1.setBounds(150, 200, 200, 25);
		b1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b1ActionPerformed(evt);
            }
        });
		JButton b2 = new JButton("Importar Automato 1");
		b2.setBounds(150, 230, 200, 25);
		b2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b2ActionPerformed(evt);
            }
        });
		JButton b3 = new JButton("Importar Automato 2");
		b3.setBounds(150, 260, 200, 25);
		b3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b3ActionPerformed(evt);
            }
        });
		JButton b4 = new JButton("Concatenar arquivos");
		b4.setBounds(150, 290, 200, 25);
		 b4.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                b4ActionPerformed(evt);
	            }
	        });
		JButton b5 = new JButton("Sair", exit);
		b5.setBounds(325, 350, 125, 40);
		b5.addActionListener(e -> System.exit(0));
	
		
		ImageIcon img = new ImageIcon((System.getProperty("user.dir")+"\\ufs.png"));
		JLabel ufs = new JLabel();
		ufs.setIcon(img);
		ufs.setBounds(100, -20, 300, 225);
		
		frame.setLayout(null);
		frame.getContentPane().setBackground(new Color(0x123456));
		frame.add(b1);
		frame.add(b2);
		frame.add(b3);
		frame.add(b4);
		frame.add(b5);
		frame.add(ufs);
		
		frame.setSize(500,450);
		frame.setLocationByPlatform(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Concatena��o");
		frame.setVisible(true);
		
	}
	private void b1ActionPerformed(java.awt.event.ActionEvent evt) {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = chooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            pathSave = file.getAbsolutePath();
        }}
	private void b2ActionPerformed(java.awt.event.ActionEvent evt) {
        this.pathAutomato1 = concatenacao.buscarArquivos();
    }
	private void b3ActionPerformed(java.awt.event.ActionEvent evt) {
        this.pathAutomato2 = concatenacao.buscarArquivos();
    }
	
        private void b4ActionPerformed(java.awt.event.ActionEvent evt) {
            if(pathAutomato1.isEmpty())
                JOptionPane.showMessageDialog(null,"Por favor adicione o automato numero 1", null, WIDTH);
            
            else if(pathAutomato2.isEmpty())
                JOptionPane.showMessageDialog(null,"Por favor adicione o automato numero 2", null, WIDTH);
            
            else if(pathSave.isEmpty())
                JOptionPane.showMessageDialog(null,"Por favor adicione a pasta de destino do automato Concatenado", null, WIDTH);
            
            Concatenacao concatenar = new Concatenacao();
            
			try {
				concatenar.execute(pathAutomato1, pathAutomato2, pathSave);
			} catch (ParserConfigurationException | SAXException | IOException e) {
				e.printStackTrace();
			}  
        }
}