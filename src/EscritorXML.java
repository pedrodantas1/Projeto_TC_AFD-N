import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Attr;
import java.util.ArrayList;

public class EscritorXML {
    public String diretorio;
    public String nomeArquivo;
    public Document document;
    public Automato automato;

    public EscritorXML(String diretorio, String nomeArquivo, Automato automato) {
        this.diretorio = diretorio;
        this.nomeArquivo = nomeArquivo;
        this.automato = automato;
        configuraDocumento();
    }

    public void configuraDocumento() {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            this.document = db.newDocument();
            setEstruturaDocumento();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void setEstruturaDocumento() {
        Element raiz = setElementoRaiz();
        setTipoEstrutura(raiz, "fa");
        Element tagAutomato = setTagAutomato(raiz);
        setEstadosDocumento(tagAutomato, automato.estados);
        setTransicoesDocumento(tagAutomato, automato.estados);
    }

    public Element setElementoRaiz() {
        Element raiz = document.createElement("structure");
        document.appendChild(raiz);
        return raiz;
    }

    public void setTipoEstrutura(Element noPai, String typeName) {
        Element type = document.createElement("type");
        type.appendChild(document.createTextNode(typeName));
        noPai.appendChild(type);
    }

    public Element setTagAutomato(Element noPai) {
        Element tagAutomato = document.createElement("automaton");
        noPai.appendChild(tagAutomato);
        return tagAutomato;
    }

    public void setEstadosDocumento(Element noPai, ArrayList<Estado> estados) {
        Element state, posX, posY, label, isInitial, isFinal;
        for (Estado estado : estados){
            state = document.createElement("state");
            //Atributos do estado
            Attr id = document.createAttribute("id");
            id.setValue(String.valueOf(estado.getId()));
            state.setAttributeNode(id);
            Attr name = document.createAttribute("name");
            name.setValue(estado.getNome());
            state.setAttributeNode(name);
            //Nos do estado
            posX = document.createElement("x");
            posX.appendChild(document.createTextNode(String.format("%d.0", (int)estado.getPosX())));
            state.appendChild(posX);
            posY = document.createElement("y");
            posY.appendChild(document.createTextNode(String.format("%d.0", (int)estado.getPosY())));
            state.appendChild(posY);
            if (estado.getLabel() != null){
                label = document.createElement("label");
                label.appendChild(document.createTextNode(estado.getLabel()));
                state.appendChild(label);
            }
            if (estado.isInicial()){
                isInitial = document.createElement("initial");
                state.appendChild(isInitial);
            }
            if (estado.isFinal()){
                isFinal = document.createElement("final");
                state.appendChild(isFinal);
            }
            noPai.appendChild(state);
        }
    }

    public void setTransicoesDocumento(Element noPai, ArrayList<Estado> estados) {
        Element transition, origem, destino, valor;
        for (Estado estado : estados){
            if (estado.getTransicoesAceitas() == null){
                continue;
            }
            for (Transicao transicao : estado.getTransicoesAceitas()){
                transition = document.createElement("transition");
                //Nos da transicao
                origem = document.createElement("from");
                origem.appendChild(document.createTextNode(String.valueOf(transicao.getOrigem())));
                transition.appendChild(origem);
                destino = document.createElement("to");
                destino.appendChild(document.createTextNode(String.valueOf(transicao.getDestino())));
                transition.appendChild(destino);
                valor = document.createElement("read");
                if (transicao.getValor() != "lambda"){
                    valor.appendChild(document.createTextNode(transicao.getValor()));
                }
                transition.appendChild(valor);
                noPai.appendChild(transition);
            }
        }
    }

    public boolean exportaArquivoXML() {
        try{
            File xml = new File(this.diretorio, this.nomeArquivo);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(xml);
            transformer.transform(domSource, streamResult);
            return true;
        }catch (TransformerException e) {
            e.printStackTrace();
            return false;
        }
    }
}
