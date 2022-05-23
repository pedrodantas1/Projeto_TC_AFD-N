/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.operation.concatenacao;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author clovijan
 */
public class TransicoesAutomato {
    private String from;
    private String to;
    private String read;

    
    public Collection<TransicoesAutomato> carregaTransicoes(String path) throws SAXException, IOException, ParserConfigurationException{
        
        File file = new File(path);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(file);
        doc.getDocumentElement().normalize();
        
        NodeList nListTransicao = doc.getElementsByTagName("transition");
        Collection<TransicoesAutomato> listaTransicoes = new ArrayList<>();

        for (int temp = 0; temp < nListTransicao.getLength(); temp++) {
            Node nTransicao = nListTransicao.item(temp);
            if (nTransicao.getNodeType() == Node.ELEMENT_NODE) {

                TransicoesAutomato transicao = new TransicoesAutomato();
                Element eElement = (Element) nTransicao;
                transicao.setFrom(eElement.getElementsByTagName("from").item(0).getTextContent());
                transicao.setTo(eElement.getElementsByTagName("to").item(0).getTextContent());
                transicao.setRead(eElement.getElementsByTagName("read").item(0).getTextContent());
                listaTransicoes.add(transicao);
            }

        }

        return listaTransicoes;
    }
    /**
     * @return the from
     */
    public String getFrom() {
        return from;
    }

    /**
     * @param from the from to set
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * @return the to
     */
    public String getTo() {
        return to;
    }

    /**
     * @param to the to to set
     */
    public void setTo(String to) {
        this.to = to;
    }

    /**
     * @return the read
     */
    public String getRead() {
        return read;
    }

    /**
     * @param read the read to set
     */
    public void setRead(String read) {
        this.read = read;
    }
}
