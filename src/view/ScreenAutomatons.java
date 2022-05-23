package view;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import org.w3c.dom.Document;

import controller.CriarXML;
import controller.AFN_AFD.ConverteAutomato;
import controller.afnReader.ReadFile;
import controller.operation.*;
import controller.xmlReader.*;
import model.*;

public class ScreenAutomatons extends JPanel implements ActionListener {
    private AppInterface controller;
    private String title;
    private GridBagConstraints gbc;
    private int requiredAutomata;
    private int selectedAutomata;
    private Operacao operation;

    private Font textFont;
    private Font buttonFont;
    private Border border;

    private boolean selectedAFD;

    private JButton readyButton;
    private JButton backButton;
    private JPanel typeAutBox;
    private JTextField textField1;
    private JTextField textField2;

    private int retorno = 1;
    private String path;
    JFileChooser escolhe = new JFileChooser();

    public static JFileChooser fileChooser;

    public ScreenAutomatons(AppInterface frame) {
        super(new BorderLayout());
        this.controller = frame;
        this.title = "Título indefinido";
        this.gbc = new GridBagConstraints();
        this.requiredAutomata = 0;
        this.selectedAutomata = 0;

        buttonFont = new Font("Arial", Font.BOLD, 16);
        border = BorderFactory.createRaisedBevelBorder();
        
        createButtons();
    }

    public void setOperation(Operacao operation, int numAutomatos) {
        this.operation = operation;
        this.requiredAutomata = numAutomatos;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private void createButtons() {
        //Botao de realizar operacao
        readyButton = new JButton("<html><center>Realizar<br/>operação</center></html>");
        readyButton.setFont(buttonFont);
        readyButton.setForeground(Color.BLACK);
        readyButton.setBorder(border);
        readyButton.setFocusPainted(false);
        readyButton.setActionCommand("makeOperation");
        readyButton.addActionListener(this);

        //Botao de voltar a tela inicial
        backButton = new JButton("Voltar");
        backButton.setFont(buttonFont);
        backButton.setBorder(border);
        backButton.setForeground(Color.BLACK);
        backButton.setFocusPainted(false);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.setContentPane(AppInterface.homeScreen);
                controller.setVisible(true);
            }
        });

        //Criar botoes de escolha entre AFD e AFN
        textFont = new Font("Arial", Font.BOLD|Font.ITALIC, 18);
        JLabel typeAut = new JLabel("Tipo do autômato de saída:");
        typeAut.setFont(textFont);
        typeAut.setForeground(Color.BLACK);

        Font radioFont = new Font("Comic Sans MS", Font.BOLD|Font.ITALIC, 15);
        JRadioButton afdButton = new JRadioButton("AFD");
        afdButton.setFocusPainted(false);
        afdButton.setFont(radioFont);
        afdButton.setBackground(Color.GREEN);
        afdButton.setForeground(Color.BLACK);
        afdButton.setActionCommand("typeAFD");
        afdButton.setSelected(true);
        selectedAFD = true;
        afdButton.addActionListener(this);

        JRadioButton afnButton = new JRadioButton("AFN");
        afnButton.setFocusPainted(false);
        afnButton.setFont(radioFont);
        afnButton.setBackground(Color.GREEN);
        afnButton.setForeground(Color.BLACK);
        afnButton.setActionCommand("typeAFN");
        afnButton.addActionListener(this);

        //Agrupar botoes
        ButtonGroup typeGroup = new ButtonGroup();
        typeGroup.add(afdButton);
        typeGroup.add(afnButton);

        //Organizar box completa
        typeAutBox = new JPanel();
        typeAutBox.setBackground(Color.GREEN);
        typeAutBox.setLayout(new BoxLayout(typeAutBox, BoxLayout.LINE_AXIS));
        typeAutBox.add(Box.createRigidArea(new Dimension(10, 40)));
        typeAutBox.add(typeAut);
        typeAutBox.add(Box.createRigidArea(new Dimension(20, 0)));
        typeAutBox.add(afdButton);
        typeAutBox.add(Box.createRigidArea(new Dimension(40, 0)));
        typeAutBox.add(afnButton);
        typeAutBox.add(Box.createRigidArea(new Dimension(10, 0)));
        typeAutBox.setBorder(BorderFactory.createCompoundBorder(
                             BorderFactory.createRaisedBevelBorder(),
                             BorderFactory.createLoweredBevelBorder()));
    }

    public void createScreenOne() {
        createHeader();
        createBodyOneAut();
        createFooter();
    }

    public void createScreenTwo() {
        createHeader();
        createBodyTwoAut();
        createFooter();
    }

    public void createScreenTree(){
        createHeader();
        createBodyScreen();
        createFooter();
    }

    private void createHeader() {
        JPanel main = new JPanel(new BorderLayout());
        JLabel titulo = new JLabel(title, JLabel.CENTER);
        Font textFont = new Font("Arial", Font.BOLD, 40);
        titulo.setFont(textFont);
        titulo.setOpaque(true);
        titulo.setForeground(Color.WHITE);
        titulo.setBackground(Color.BLACK);
        titulo.setPreferredSize(new Dimension(900, 150));
        main.add(titulo, BorderLayout.CENTER);

        add(main, BorderLayout.NORTH);
    }

    private void createBodyOneAut() {
        JPanel operationPanel = new JPanel(new GridBagLayout());
        operationPanel.setBackground(Color.BLUE);
        operationPanel.setBorder(BorderFactory.createEmptyBorder(0, 125, 0, 125));

        //Label
        textFont = new Font("Arial", Font.BOLD, 18);
        JLabel automatoText = new JLabel("Autômato:");
        automatoText.setFont(textFont);
        automatoText.setForeground(Color.WHITE);
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 10);
        operationPanel.add(automatoText, gbc);

        //TextField
        JTextField textField = new JTextField(40);
        textField.setEditable(false);
        gbc.fill =  GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.ipady = 5;
        gbc.weightx = 1;
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets.set(0, 0, 0, 10);
        operationPanel.add(textField, gbc);

        //Botao de pesquisar arquivo (FileChooser)
        SearchButton searchButton;
        searchButton = new SearchButton(0);
        searchButton.setTextField(textField);
        searchButton.addActionListener(this);
        gbc.fill =  GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.FIRST_LINE_END;
        gbc.ipadx = -25;
        gbc.ipady = -2;
        gbc.weightx = 0;
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.insets.set(0, 0, 0, 0);
        operationPanel.add(searchButton, gbc);

        //Config do readyButton
        gbc.fill =  GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.ipadx = 75;
        gbc.ipady = 30;
        gbc.weightx = 0;
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets.set(50, 0, 0, 80);
        operationPanel.add(readyButton, gbc);

        //Config do backButton
        gbc.fill =  GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.ipadx = 40;
        gbc.ipady = 20;
        gbc.weightx = 0;
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.insets.set(20, 0, 0, 80);
        operationPanel.add(backButton, gbc);

        add(operationPanel, BorderLayout.CENTER);
    }

    private void createBodyTwoAut() {
        JPanel operationPanel = new JPanel(new GridBagLayout());
        operationPanel.setBackground(Color.BLUE);
        operationPanel.setBorder(BorderFactory.createEmptyBorder(0, 125, 0, 125));

        //Config do typeAutBox
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 20, 0);
        operationPanel.add(typeAutBox, gbc);

        //Label 1
        textFont = new Font("Arial", Font.BOLD, 18);
        JLabel automatoText = new JLabel("1° autômato:");
        automatoText.setFont(textFont);
        automatoText.setForeground(Color.WHITE);
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets .set(0, 0, 0, 10);
        operationPanel.add(automatoText, gbc);

        //TextField 1
        textField1 = new JTextField(40);
        textField1.setEditable(false);
        gbc.fill =  GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.ipady = 5;
        gbc.weightx = 1;
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets.set(0, 0, 0, 10);
        operationPanel.add(textField1, gbc);

        //Botao de pesquisar arquivo 1 (FileChooser) 
        SearchButton searchButton;
        searchButton = new SearchButton(0);
        searchButton.setTextField(textField1);
        searchButton.addActionListener(this);
        gbc.fill =  GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.ipadx = -25;
        gbc.ipady = -2;
        gbc.weightx = 0;
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.insets.set(0, 0, 0, 0);
        operationPanel.add(searchButton, gbc);

        //Label 2
        automatoText = new JLabel("2° autômato:");
        automatoText.setFont(textFont);
        automatoText.setForeground(Color.WHITE);
        gbc.fill =  GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets.set(20, 0, 0, 10);
        operationPanel.add(automatoText, gbc);

        //TextField 2
        textField2 = new JTextField(40);
        textField2.setEditable(false);
        gbc.fill =  GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.ipady = 5;
        gbc.weightx = 1;
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.insets.set(20, 0, 0, 10);
        operationPanel.add(textField2, gbc);

        //Botao de pesquisar arquivo 2 (FileChooser)
        searchButton = new SearchButton(1);
        searchButton.setTextField(textField2);
        searchButton.addActionListener(this);
        gbc.fill =  GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.ipadx = -25;
        gbc.ipady = -2;
        gbc.weightx = 0;
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.insets.set(20, 0, 0, 0);
        operationPanel.add(searchButton, gbc);

        //Config do readyButton
        gbc.fill =  GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.ipadx = 75;
        gbc.ipady = 30;
        gbc.weightx = 0;
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.insets.set(30, 0, 0, 90);
        operationPanel.add(readyButton, gbc);

        //Config do backButton
        gbc.fill =  GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.ipadx = 40;
        gbc.ipady = 20;
        gbc.weightx = 0;
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.insets.set(20, 0, 0, 90);
        operationPanel.add(backButton, gbc);

        add(operationPanel, BorderLayout.CENTER);
    }

    private void createBodyScreen(){
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Selecione apenas arquivos jff", "jff");
        JPanel operationPanel = new JPanel(new GridBagLayout());
        operationPanel.setBackground(Color.BLUE);
        operationPanel.setBorder(BorderFactory.createEmptyBorder(0, 125, 0, 125));
        JButton operacao = new JButton("<html><center>Realizar<br/>operação</center></html>");// selecionar arquivo


        operacao.setFont(buttonFont);
        operacao.setForeground(Color.BLACK);
        operacao.setBorder(border);
        operacao.setFocusPainted(false);
        operacao.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                
                escolhe.setFileFilter(filter);
                if (retorno == 0)
                JOptionPane.showMessageDialog(null, "Arquivo já convertido!");

            if (retorno == JFileChooser.SAVE_DIALOG){
                retorno = extracted();
                JOptionPane.showMessageDialog(null, escolhe.getSelectedFile().getAbsolutePath());
                path = escolhe.getSelectedFile().getAbsolutePath();
                ReadFile rf = new ReadFile(path);
                rf.read();

                if (retorno == 0)
                    ConverteAutomato.converter();
                else
                    JOptionPane.showMessageDialog(null, "É necessário informar o diretório do arquivo .jff");

                retorno = 1;
            }        
        }
    });

        gbc.fill =  GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.ipadx = 75;
        gbc.ipady = 30;
        gbc.weightx = 0;
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets.set(50, 0, 0, 0);
        
        operationPanel.add(operacao,gbc);


        gbc.fill =  GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.ipadx = 40;
        gbc.ipady = 20;
        gbc.weightx = 0;
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.insets.set(20, 0, 0, 0);
        operationPanel.add(backButton, gbc);

        add(operationPanel, BorderLayout.CENTER);
    }

    private int extracted() {
        return escolhe.showOpenDialog(null);   
    }

    private void createFooter() {
        JPanel footer = new JPanel(new BorderLayout());
        JLabel creditos = new JLabel("<html>Desenvolvido por alunos da UFS - DSI &copy;</html>");
        textFont = new Font("Comic Sans MS", Font.BOLD|Font.ITALIC, 14);
        creditos.setFont(textFont);
        creditos.setForeground(Color.BLUE);
        footer.setBackground(Color.BLACK);
        footer.setBorder(BorderFactory.createEmptyBorder(50, 5, 5, 0));
        footer.add(creditos, BorderLayout.WEST);
        
        add(footer, BorderLayout.SOUTH);
    }

    private boolean areBothAFD() {
        boolean firstIsAFN = operation.getAutomaton(0).isAFN();
        boolean secondIsAFN = operation.getAutomaton(1).isAFN();

        return !firstIsAFN && !secondIsAFN;
    }

    private int verifySelectedAut() {
        int quant = 1;
        if (requiredAutomata == 2){
            if (selectedAutomata != 0){
                quant = 2;
            }
        }

        return quant;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        
        if (action.equals("searchFile")){
            if (fileChooser == null){
                fileChooser = new JFileChooser(System.getProperty("user.dir"));
                fileChooser.addChoosableFileFilter(new AutomatonFilter());
                fileChooser.setAcceptAllFileFilterUsed(false);
            }
            
            SearchButton source = (SearchButton) e.getSource();
            int returnVal = fileChooser.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION){
                File file = fileChooser.getSelectedFile();
                String path = file.getAbsolutePath();
                source.setPathInTextField(path);
                selectedAutomata = verifySelectedAut();
                operation.setAutomaton(readAutomaton(file), source.getAutomatonID());
            }

            return;
        }

        if (action.equals("typeAFD")){
            selectedAFD = true;
            return;
        }
        if (action.equals("typeAFN")){
            selectedAFD = false;
            return;
        }
        
        if (action.equals("makeOperation")){
            //Para as operações que exigem apenas 1 autômato
            if (requiredAutomata == 1 && selectedAutomata == 1){
                createOutputFile(operation.makeOperation());
            //Para as operações que exigem 2 autômatos
            }else if (requiredAutomata == 2 && selectedAutomata == 2){
                //Adaptações para a operação de união
                if (operation instanceof Uniao){
                    makeUniao();
                //Adaptações para a operação de intersecção
                }else if (operation instanceof Interseccao){
                    if (selectedAFD){
                        makeInterseccaoAFD();
                    }else{
                        if (areBothAFD()){
                            createOutputFile(operation.makeOperation());
                        }else{
                            Dialogs.showMessage("Ambos os autômatos precisam ser AFD!");
                        }
                    }
                //Adaptações para a operação de gerar AFD
                }else if (operation instanceof GerarAFD){
                    System.out.println("passou");
                    operation.makeOperation();
                //Para demais operações (exceto concatenação)
                }else {
                    createOutputFile(operation.makeOperation());
                }
            //Caso não haja os autômatos necessários selecionados
            }else{
                Dialogs.showMessage("Autômatos insuficientes",
                "Por favor, selecione os autômatos necessários!");
            }
        }
    }

    private Automato readAutomaton(File file) {
        LeitorXML leitor = new LeitorXML();
        leitor.carregaArquivoXML(file);
        Document docEntrada = leitor.getDocumentoLido();
        Automato aut = new Automato();
        aut.setEstados(docEntrada.getElementsByTagName("state"));
        aut.loadTransicoes(docEntrada.getElementsByTagName("transition"));

        return aut;
    }

    private void createOutputFile(Automato aut) {
        try{
            ConstrutorDocumentoXML construtorXML = new ConstrutorDocumentoXML();
            construtorXML.setAutomato(aut);
            construtorXML.configuraDocumento();
            EscritorXML escritor = new EscritorXML();
            escritor.setDocumentXML(construtorXML.getDocumentoConstruido());

            int returnVal = fileChooser.showSaveDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION){
                File file = fileChooser.getSelectedFile();
                String path = getPathOutputFile(file);
                escritor.exportaArquivoXML(path);
                JFLAPExecutor.openJFLAP();
            }
        }catch (NullPointerException e){
        }
    }

    private String getPathOutputFile(File file) {
        String extension = AutomatonFilter.getExtension(file);
        if (extension != null){
            if (extension.equals("jff")){
                return file.getAbsolutePath();
            }
        }

        return file.getAbsolutePath() + ".jff";
    }

    private void makeUniao() {
        AutomatoUniao automato = new AutomatoUniao(textField1.getText());
        AutomatoUniao automato2 = new AutomatoUniao(textField2.getText());
        Uniao uniao = new Uniao();
        CriarXML criar = new CriarXML();
        int returnVal = fileChooser.showSaveDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION){
            File file = fileChooser.getSelectedFile();
            String filePath = getPathOutputFile(file);
            if (selectedAFD){
                criar.gerarXML(uniao.unirAFD(automato, automato2), filePath);
            }else{
                criar.gerarXML(uniao.unirAFN(automato, automato2), filePath);
            }
        }
    }

    private void makeInterseccaoAFD() {
        String aut1 = textField1.getText();
        String aut2 = textField2.getText();
        int returnVal = fileChooser.showSaveDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION){
            File file = fileChooser.getSelectedFile();
            String autSaida = getPathOutputFile(file);
            try {
                InterseccaoAFD inter = new InterseccaoAFD(aut1, aut2, autSaida);
                inter.juntarAFD();
                Dialogs.showMessage("Arquivo exportado com sucesso!");
                JFLAPExecutor.openJFLAP();
            }catch (Exception e){
                Dialogs.showMessage("Ambos os autômatos precisam ser AFD!");
            }
        }
    }

}