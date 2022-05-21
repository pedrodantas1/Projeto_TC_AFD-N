package controller.afnReader;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.AFN_AFD.ConverteAutomato;
import model.Estado;
import model.Transicao;


public class WriteFile{


public static void save() throws IOException {
    JFileChooser file = new JFileChooser();
    FileNameExtensionFilter filter = new FileNameExtensionFilter(".jff", ".jff");
    file.setFileFilter(filter);

    file.setDialogType(JFileChooser.SAVE_DIALOG);
    int j= file.showSaveDialog(null);
    if (j!=1) {
        String arquivo = file.getSelectedFile().getAbsolutePath()+".jff";//pega o nome do arquivo
        JOptionPane.showMessageDialog(null, file.getSelectedFile().getAbsolutePath());
        			
        FileWriter arq = new FileWriter(arquivo);//abre um arquivo para escrita
        PrintWriter gravarArq = new PrintWriter(arq);

        gravarArq.printf("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><!--Created with JFLAP 6.4.--><structure>\n");
            gravarArq.printf("\t<type>fa</type>\n");
            // grava estados    
            if (ConverteAutomato.afd.getEstados().size() == 0) {
                gravarArq.printf("\t<automaton/>\n");
            } else {	
                gravarArq.printf("\t<automaton>\n");
                gravarArq.printf("\t\t<!--The list of states.-->\n");
                for (Estado e : ConverteAutomato.afd.getEstados()) {
                    gravarArq.printf("\t\t<state id=\"%d\" name=\"%s\">\n", e.getId().intValue(), e.getName());
                        gravarArq.printf("\t\t\t<x></x>\n");
                        gravarArq.printf("\t\t\t<y></y>\n");
                        if (e.isInicial()) {
                            gravarArq.printf("\t\t\t<initial/>\n");
                        }
                        if (e.isFinal()) {
                            gravarArq.printf("\t\t\t<final/>\n");
                        }
                    gravarArq.printf("\t\t</state>\n");
                }
            }
            if (ConverteAutomato.afd.getEstados().size() != 0 && ConverteAutomato.afd.getTransicoes().size() == 0) {
                gravarArq.printf("\t</automaton>\n");
            } 
            // grava transicoes
            if (ConverteAutomato.afd.getTransicoes().size() == 0) {
            } else {
                gravarArq.printf("\t\t<!--The list of transitions.-->\n");
                for (Transicao t : ConverteAutomato.afd.getTransicoes()) {
                    gravarArq.printf("\t\t<transition>\n");
                        gravarArq.printf("\t\t\t<from>%d</from>\n", t.getFrom().getId().intValue());
                        gravarArq.printf("\t\t\t<to>%d</to>\n", t.getTo().getId().intValue());
                        gravarArq.printf("\t\t\t<read>%s</read>\n", t.getValor());
                    gravarArq.printf("\t\t</transition>\n");
                }
                gravarArq.printf("\t</automaton>\n");
            }
                
        gravarArq.printf("</structure>\n");
        gravarArq.close();									//fecha o arquivo
    }
}
}