package controller.operation.concatenacao;

import java.awt.Component;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author clovijan
 */
public class Concatenacao {

    private Component rootPane;
    
    public void execute(String pathAutomato1, String pathAutomato2, String pathSave) throws ParserConfigurationException, SAXException, IOException {

        Automato automato1 = new Automato().carregarDados(pathAutomato1);
        Automato automato2 = new Automato().carregarDados(pathAutomato2);
        Automato automatoFinal = new Automato();
        ArrayList<EstadosAutomato> estadosConcat = new ArrayList<>();

        for (EstadosAutomato estado : automato1.getEstado()) {
            EstadosAutomato estadoAutomato1 = new EstadosAutomato();
            estadoAutomato1.setId(estado.getId());
            estadoAutomato1.setName(estado.getName());
            estadoAutomato1.setEstadoInitial(estado.isEstadoInitial());
            if (estado.isEstadoFinal()) {
               estadoAutomato1.setEstadoFinal(false);
            }else{
               estadoAutomato1.setEstadoFinal(estado.isEstadoFinal()); 
            }
            estadosConcat.add(estadoAutomato1);
        }
        
        automatoFinal.setEstado(estadosConcat);
        
        for (EstadosAutomato estado : automato2.getEstado()) {
            EstadosAutomato estadoAutomato2 = new EstadosAutomato();
            int id = automato1.getEstado().size() + Integer.parseInt(estado.getId());
            estadoAutomato2.setId(Integer.toString(id));
            estadoAutomato2.setName("q" + id);
            if (estado.isEstadoInitial()) {
                estadoAutomato2.setEstadoInitial(false);
            }else{
                estadoAutomato2.setEstadoInitial(estado.isEstadoInitial());
            }
            estadoAutomato2.setEstadoFinal(estado.isEstadoFinal());
            
            automatoFinal.getEstado().add(estadoAutomato2);
        }

        automatoFinal.setTransicoes(automato1.getTransicoes());

        for (TransicoesAutomato transicao : automato2.getTransicoes()) {
            TransicoesAutomato transicaoAutomato2 = new TransicoesAutomato();
           
            int from = automato1.getEstado().size() + Integer.parseInt(transicao.getFrom());
            int to = automato1.getEstado().size() + Integer.parseInt(transicao.getTo());
            transicaoAutomato2.setFrom(Integer.toString(from));
            transicaoAutomato2.setTo(Integer.toString(to));
            transicaoAutomato2.setRead(transicao.getRead());
            
            automatoFinal.getTransicoes().add(transicaoAutomato2);
        }

        automatoFinal.getTransicoes().addAll(concatenandoAutomatos(automato1, automato2));
        try(FileWriter fileAutomato = new FileWriter(new File(pathSave + "\\" + "AutomatoConcatenado" + ".jff"))){
           fileAutomato.write(criarTxtAutomato(automatoFinal));
           fileAutomato.close();
        }        
    }
    
    /**
     * Cria a transicao entre o automato 1 e o automato 2 
     * 
     * @param automato1
     * @param automato2
     * @return 
     */
    private Collection<TransicoesAutomato> concatenandoAutomatos(Automato automato1, Automato automato2) {

        Collection<TransicoesAutomato> transicoesConcat = new ArrayList<>();

        for (String idEstadoFinal : estadosFinais(automato1.getEstado())) {
            TransicoesAutomato novaTransicao = new TransicoesAutomato();
            novaTransicao.setFrom(idEstadoFinal);
            int to = Integer.parseInt(estadoInicial(automato2.getEstado())) + automato1.getEstado().size();
            novaTransicao.setTo(Integer.toString(to));
            novaTransicao.setRead("read");
            
            transicoesConcat.add(novaTransicao);
        }

        return transicoesConcat;
    }

    /**
     * Retorna ID do estado inicial
     *
     * @param estados
     * @return id
     */
    private String estadoInicial(Collection<EstadosAutomato> estados) {
        for (EstadosAutomato estado : estados) {
            if (estado.isEstadoInitial()) {
                return estado.id;
            }
        }
        return null;
    }

    /**
     * Returna o ID de todos os estados Finais
     *
     * @param estados
     * @return
     */
    private Collection<String> estadosFinais(Collection<EstadosAutomato> estados) {
        Collection<String> ids = new ArrayList<>();
        for (EstadosAutomato estado : estados) {
            if (estado.isEstadoFinal()) {
                ids.add(estado.getId());
            }
        }

        return ids;
    }
    
    /**
     * Método utilizado na interface
     * 
     * @return 
     */
    public String buscarArquivos() {

        JFileChooser chooser = new JFileChooser();
        String path = "";
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Selecione apenas arquivos com a extensão jff", "jff");
        chooser.addChoosableFileFilter(filter);
        chooser.setAcceptAllFileFilterUsed(false);
        int retorno = chooser.showOpenDialog(rootPane);
        if (retorno == JFileChooser.APPROVE_OPTION) {
            path = chooser.getSelectedFile().getAbsolutePath();
        }

        return path;
    }
    
    private String criarTxtAutomato(Automato automatoFinal){
        StringBuilder txtAutomato = new StringBuilder();
        String tab = "      ";
        
        txtAutomato.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><!--Created with JFLAP 6.4.--><structure>&#13;\n");
        txtAutomato.append(tab).append("<type>fa</type>&#13;\n");
        txtAutomato.append(tab).append("<automaton>&#13;\n");
        txtAutomato.append(tab).append(tab).append("<!--The list of states.-->&#13;\n");
        int i = 0;
        for(EstadosAutomato estado : automatoFinal.getEstado()){
            i+=5;
            txtAutomato.append(tab).append(tab).append("<state id=\"").append(estado.getId()).append("\" name=\"").append(estado.getName()).append("\">&#13;\n");
            txtAutomato.append(tab).append(tab).append(tab).append("<x>").append(i).append(".0").append("</x>&#13;\n");
            txtAutomato.append(tab).append(tab).append(tab).append("<y>").append(i).append(".0").append("</y>&#13;\n");
            if(estado.isEstadoInitial())
                txtAutomato.append(tab).append(tab).append(tab).append("<initial/>&#13;\n");
            if(estado.isEstadoFinal())
                txtAutomato.append(tab).append(tab).append(tab).append("<final/>&#13;\n");
            txtAutomato.append(tab).append(tab).append("</state>&#13;\n");
        }
        txtAutomato.append(tab).append(tab).append("<!--The list of transitions.-->&#13;\n");
        for(TransicoesAutomato transicao : automatoFinal.getTransicoes()){
            txtAutomato.append(tab).append(tab).append("<transition>&#13;\n");
            txtAutomato.append(tab).append(tab).append(tab).append("<from>").append(transicao.getFrom()).append("</from>&#13;\n");
            txtAutomato.append(tab).append(tab).append(tab).append("<to>").append(transicao.getTo()).append("</to>&#13;\n");
            if(transicao.getRead().equals("read")){
                txtAutomato.append(tab).append(tab).append(tab).append("<read/>&#13;\n");
            }else{
                txtAutomato.append(tab).append(tab).append(tab).append("<read>").append(transicao.getRead()).append("</read>&#13;\n");  
            }            
            txtAutomato.append(tab).append(tab).append("</transition>&#13;\n");
        }
        txtAutomato.append(tab).append("</automaton>&#13;\n");
        txtAutomato.append("</structure>");

        return txtAutomato.toString();
    }

}
