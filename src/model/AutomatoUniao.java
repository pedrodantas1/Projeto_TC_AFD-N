package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.io.File;
import static java.lang.Integer.parseInt;
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
 * @author Janaina
 */

public final class AutomatoUniao {

    private List<EstadoUniao> Estados = new ArrayList<>();
    private List<TransicaoUniao> transicoes = new ArrayList<>();

    public AutomatoUniao(String filePath) {
        try{
            this.getFromFile(filePath);
        }catch (ParserConfigurationException | SAXException error){
            System.out.println(error);
        }
    }

    public AutomatoUniao(List<EstadoUniao> Estados, List<TransicaoUniao> transicao) {
        this.setEstados(Estados);
        this.setTransicoes(transicao);

    }

    public List<EstadoUniao> getEstados() {
        return Estados;
    }

    public void setEstados(List<EstadoUniao> Estados) {
        this.Estados = Estados;
    }

    public List<TransicaoUniao> getTransicoes() {
        return transicoes;
    }

    public void setTransicoes(List<TransicaoUniao> transicao) {
        this.transicoes = transicao;
    }

    public void getFromFile(String filePath) throws ParserConfigurationException, SAXException {
        try {
            File file = new File(filePath);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(file);
            document.getDocumentElement().normalize();

            // Obter Estados --
            NodeList Estados = document.getElementsByTagName("state");
            // Percorrer todos os Estados e gravar no objeto
            for (int temp = 0; temp < Estados.getLength(); temp++) {
                Node nNode = Estados.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    EstadoUniao EstadoUniao = new EstadoUniao(
                            parseInt(eElement.getAttribute("id")),
                            eElement.getAttribute("name"),
                            Float.parseFloat(eElement.getElementsByTagName("x").item(0).getTextContent()),
                            Float.parseFloat(eElement.getElementsByTagName("y").item(0).getTextContent()),
                            eElement.getElementsByTagName("final").item(0) != null,
                            eElement.getElementsByTagName("initial").item(0) != null
                    );

                    this.Estados.add(EstadoUniao);

                }
            }

            // Obter Transições --
            NodeList transicoes = document.getElementsByTagName("transition");
            // Percorrer todas as transicoes e gravar no objeto
            for (int temp = 0; temp < transicoes.getLength(); temp++) {
                Node nNode = transicoes.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    TransicaoUniao transicao = new TransicaoUniao(
                            parseInt(eElement.getElementsByTagName("from").item(0).getTextContent()),
                            parseInt(eElement.getElementsByTagName("to").item(0).getTextContent()),
                            eElement.getElementsByTagName("read").item(0).getTextContent()
                    );

                    this.transicoes.add(transicao);

                }
            }
        } catch (IOException error) {
            System.out.println(error);
        }
    }

    @Override
    public String toString() {
        String message;
        message = "Estados:";
        for (int i = 0; i < this.Estados.size(); i++) {
            message = message + "\n" + Estados.get(i).getNome();
            if (Estados.get(i).isIsFinal()) {
                message = message + " - Final\n";
            }
            if (Estados.get(i).isIsInicial()) {
                message = message + " - Inicial";
            }
        }

        message = message + "\n\nTransições:";
        for (int i = 0; i < this.transicoes.size(); i++) {
            message = message
                    + "\n"
                    + transicoes.get(i).getFrom()
                    + " -> "
                    + transicoes.get(i).getTo()
                    + " com "
                    + transicoes.get(i).getInput();
        }

        message = message + "\n----------------------";
        return message;
    }

}
