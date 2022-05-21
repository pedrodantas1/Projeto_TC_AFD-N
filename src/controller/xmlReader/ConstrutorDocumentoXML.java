package controller.xmlReader;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Attr;
import java.util.ArrayList;

import model.Automato;
import model.Estado;
import model.Transicao;
import view.Dialogs;

public class ConstrutorDocumentoXML {
    private Automato automato;
    private Document document;

    public ConstrutorDocumentoXML() {
    }

    public void setAutomato(Automato automato) {
        this.automato = automato;
    }

    public void configuraDocumento() {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            this.document = db.newDocument();
            setEstruturaDocumento();
        } catch (ParserConfigurationException e) {
            Dialogs.showMessage("Erro ao exportar arquivo", 
            "Não foi possível estruturar arquivo xml!");
        }
    }

    public void setEstruturaDocumento() {
        Element raiz = setElementoRaiz();
        setTipoEstrutura(raiz, "fa");
        Element tagAutomato = setTagAutomato(raiz);
        setEstadosDocumento(tagAutomato, automato.getEstados());
        setTransicoesDocumento(tagAutomato, automato.getEstados());
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
        Element state, label, isInitial, isFinal;
        for (Estado estado : estados){
            state = document.createElement("state");
            //Atributos do estado
            Attr id = document.createAttribute("id");
            id.setValue(String.valueOf(estado.getId()));
            state.setAttributeNode(id);
            Attr name = document.createAttribute("name");
            name.setValue(estado.getName());
            state.setAttributeNode(name);
            //Nos do estado
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

    public Document getDocumentoConstruido() {
        return this.document;
    }

}