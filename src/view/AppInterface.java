package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import java.awt.Image;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;


/**
 * Frame principal da interface responsável pelas configurações iniciais do
 * sistema e por guardar a tela inicial.
 * 
 * 
 * @author Pedro Dantas
 */
public class AppInterface extends JFrame {
    private static AppInterface frame;
    public static JPanel homeScreen;

    public AppInterface(String titulo) {
        super(titulo);
        setResizable(false);
        setSize(900, 600);
        setLocationRelativeTo(null);
        ImageIcon logo = new ImageIcon(getClass().getResource("/resources/images/mainLogo.png"));
        Image editedLogo = logo.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        setIconImage(editedLogo);
    }

    public JPanel createHomeScreen() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        //Header da tela inicial
        JPanel header = new JPanel(new BorderLayout());
        JLabel titulo = new JLabel("Operador de autômatos finitos", JLabel.CENTER);
        Font textFont = new Font("Arial", Font.BOLD, 40);
        titulo.setFont(textFont);
        titulo.setForeground(Color.WHITE);
        titulo.setPreferredSize(new Dimension(900, 150));
        titulo.setOpaque(true);
        titulo.setBackground(Color.BLACK);
        header.add(titulo, BorderLayout.NORTH);
        
        //Body da tela inicial
        GridButtons buttonsPanel = new GridButtons(frame, 6, 3);
        String[] buttonNames = {"União", "Intersecção", "Concatenação",
                                "Complemento", "Estrela", 
                                "<html><center>Gerar AFD<br/>equivalente</center></html>"};
        String[] actions = {"uniao", "interseccao", "concatenacao",
                            "complemento", "estrela", "gerarAFD"};
        Font buttonFont = new Font("Arial", Font.BOLD, 16);
        Border border = BorderFactory.createLineBorder(Color.BLACK, 3);
        buttonsPanel.setBackground(Color.BLUE);
        buttonsPanel.setButtonFont(buttonFont);
        buttonsPanel.setBorder(border);
        buttonsPanel.setBgColor(Color.WHITE);
        buttonsPanel.setTextColor(Color.BLACK);
        buttonsPanel.addButtons(buttonNames, actions);

        //Footer da tela inicial
        JPanel footer = new JPanel(new BorderLayout());
        JLabel creditos = new JLabel("<html>Desenvolvido por alunos da UFS - DSI &copy;</html>");
        textFont = new Font("Comic Sans MS", Font.BOLD|Font.ITALIC, 14);
        creditos.setFont(textFont);
        creditos.setForeground(Color.BLUE);
        creditos.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 0));
        footer.setBackground(Color.BLACK);
        footer.setPreferredSize(new Dimension(900, 150));
        footer.add(creditos, BorderLayout.SOUTH);

        mainPanel.add(header, BorderLayout.NORTH);
        mainPanel.add(buttonsPanel, BorderLayout.CENTER);
        mainPanel.add(footer, BorderLayout.SOUTH);

        return mainPanel;
    }

    private static void createAndShowGUI() {
        frame = new AppInterface("Operador de autômatos");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Cria tela inicial e seta seu conteudo no frame
        homeScreen = frame.createHomeScreen();
        frame.setContentPane(homeScreen);
        
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
    
}