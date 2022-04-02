import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import org.w3c.dom.Document;

public class EscritorXML {
    public Document document;

    public EscritorXML() {
    }

    public void setDocumentXML(Document document) {
        this.document = document;
    }

    public void exportaArquivoXML(String diretorio, String nomeArquivo) {
        try{
            File xml = new File(diretorio, nomeArquivo);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(xml);
            transformer.transform(domSource, streamResult);
            System.out.println("\nArquivo exportado com sucesso!\n\n");
        }catch (TransformerException e) {
            System.out.println("\nNao foi possivel exportar o arquivo!\n\n");
        }
    }
}
