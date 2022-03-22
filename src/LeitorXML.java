import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import java.io.File;

public class LeitorXML {
    public String diretorio;
    public String nomeArquivo;
    public Document doc;
    private NodeList listaEstados;
    private NodeList listaTransicoes;

    public LeitorXML(String diretorio, String nomeArquivo) {
        this.diretorio = diretorio;
        this.nomeArquivo = nomeArquivo;
        carregaDocumento();
    }

    public void carregaDocumento() {
        try {
            File xml = new File(this.diretorio, this.nomeArquivo);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            this.doc = db.parse(xml);
            doc.getDocumentElement().normalize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public NodeList getEstados() {
        //Pensar em setar a nodelist definida como atributo
        return doc.getElementsByTagName("state");
    }

    public NodeList getTransicoes() {
        //Pensar em setar a nodelist definida como atributo
        return doc.getElementsByTagName("transition");
    }

}