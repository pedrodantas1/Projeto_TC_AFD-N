package controller;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import model.AutomatoUniao;
import view.Dialogs;
import view.JFLAPExecutor;

/**
 *
 * @author Janaina
 */
public class CriarXML {
   
    public void gerarXML(AutomatoUniao a1, String filePath){
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            
            Document documentXML = documentBuilder.newDocument();
            //Cria o elemento com a tag structure
            Element structure = documentXML.createElement("structure");
            documentXML.appendChild(structure);
            
            //Cria o elemento com a tag type e adiciona o texto fa dentro
            Element type = documentXML.createElement("type");
            type.appendChild(documentXML.createTextNode("fa"));

            structure.appendChild(type);
            //Cria o elemento com a tag AutomatoUniaon, que é filho do elemento structure
            Element automaton = documentXML.createElement("automaton");
            structure.appendChild(automaton);
            
            //Adiciona a lista de etados 
            for(int i=0; i<a1.getEstados().size(); i++){
                Element state = documentXML.createElement("state");
                
                Attr id = documentXML.createAttribute("id");
                Attr name = documentXML.createAttribute("name");
                
                id.setValue(Integer.toString(a1.getEstados().get(i).getId()));
                name.setValue(a1.getEstados().get(i).getNome());
                
                state.setAttributeNode(id);
                state.setAttributeNode(name);
                
                Element cordX = documentXML.createElement("x");
                Element cordY = documentXML.createElement("y");
                
                cordX.appendChild(documentXML.createTextNode(String.valueOf(a1.getEstados().get(i).getX())));
                cordY.appendChild(documentXML.createTextNode(String.valueOf(a1.getEstados().get(i).getY())));
                state.appendChild(cordX);
                state.appendChild(cordY);
                                
                if(a1.getEstados().get(i).isIsInicial()){
                    Element inicial = documentXML.createElement("initial");
                    state.appendChild(inicial);
                }
                
                if(a1.getEstados().get(i).isIsFinal()){
                    Element estFinal = documentXML.createElement("final");
                    state.appendChild(estFinal);
                }
       
                automaton.appendChild(state);
            }
            
            //Adiciona a lista de transições
            for(int j=0; j<a1.getTransicoes().size(); j++){
                Element transition = documentXML.createElement("transition");
                
                Element from = documentXML.createElement("from");
                from.appendChild(documentXML.createTextNode(Integer.toString(a1.getTransicoes().get(j).getFrom())));
                transition.appendChild(from);
                
                Element to = documentXML.createElement("to");
                to.appendChild(documentXML.createTextNode(Integer.toString(a1.getTransicoes().get(j).getTo())));
                transition.appendChild(to);
                
                Element read = documentXML.createElement("read");
                read.appendChild(documentXML.createTextNode(a1.getTransicoes().get(j).getInput()));
                transition.appendChild(read);
                
                automaton.appendChild(transition);
            }
            //Tranforma a estrutura criada até agora em um documento no disco
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            DOMSource documentoFonte = new DOMSource(documentXML);
            
            StreamResult documentoFinal = new StreamResult(new File(filePath));
            transformer.transform(documentoFonte, documentoFinal);
            Dialogs.showMessage("Arquivo exportado com sucesso!");
            JFLAPExecutor.openJFLAP();
            
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(CriarXML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(CriarXML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(CriarXML.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}