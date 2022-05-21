package controller.xmlReader;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import java.io.File;
import java.io.IOException;

import view.Dialogs;
public class LeitorXML {
    private Document document;

    public LeitorXML() {
    }

    public void carregaArquivoXML(File file) {
        try{
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            this.document = db.parse(file);
            document.getDocumentElement().normalize();
        }catch (ParserConfigurationException | SAXException | IOException e) {
            Dialogs.showMessage("Erro ao carregar arquivo", 
            "Ocorreu algo de errado ao tentar carregar o arquivo!");
        }
    }

    public Document getDocumentoLido() {
        return this.document;
    }

}