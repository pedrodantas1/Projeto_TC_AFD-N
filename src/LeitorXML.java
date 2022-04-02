import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import java.io.File;
import java.io.IOException;

public class LeitorXML {
    private Document document;

    public LeitorXML() {
    }

    public boolean carregaArquivoXML(String diretorio, String nomeArquivo) {
        try{
            File xml = new File(diretorio, nomeArquivo);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            this.document = db.parse(xml);
            document.getDocumentElement().normalize();
            return true;
        }catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Document getDocumentoLido() {
        return this.document;
    }

}