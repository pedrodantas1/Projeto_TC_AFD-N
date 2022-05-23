package controller.operation.concatenacao;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
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
public class EstadosAutomato {
    String id;
    private String name;
    private boolean EstadoInitial;
    private boolean EstadoFinal;

    
    public Collection<EstadosAutomato> carregaEstados(String path) throws ParserConfigurationException, IOException, SAXException {
        
        File file = new File(path);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(file);
        doc.getDocumentElement().normalize();
        
        NodeList nListEstado = doc.getElementsByTagName("state"); //Criando lista de Nó de estado
        Collection<EstadosAutomato> estados = new ArrayList<>();
        
        for(int i=0; i<nListEstado.getLength(); i++){
            Node nEstado = nListEstado.item(i);
            NodeList tags = nListEstado.item(i).getChildNodes();
            EstadosAutomato estadoAutomato = new EstadosAutomato();
            if (nEstado.getNodeType() == Node.ELEMENT_NODE) {
                
                Element eElement = (Element) nEstado;
                
                estadoAutomato.setId(eElement.getAttribute("id"));
                estadoAutomato.setName(eElement.getAttribute("name"));
                
               for (int j = 0; j < tags.getLength(); j++) {
                    Node noFilho = tags.item(j);
                    if (noFilho.getNodeType() == Node.ELEMENT_NODE) {
                        if (Objects.equals("initial", noFilho.getNodeName()))
                            estadoAutomato.setEstadoInitial(true);
                        if (Objects.equals("final", noFilho.getNodeName()))
                            estadoAutomato.setEstadoFinal(true);
                    }
               }
               
               estados.add(estadoAutomato);
            }
        }    
        return estados;  
    }
    
    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the EstadoInitial
     */
    public boolean isEstadoInitial() {
        return EstadoInitial;
    }

    /**
     * @param EstadoInitial the EstadoInitial to set
     */
    public void setEstadoInitial(boolean EstadoInitial) {
        this.EstadoInitial = EstadoInitial;
    }

    /**
     * @return the EstadoFinal
     */
    public boolean isEstadoFinal() {
        return EstadoFinal;
    }

    /**
     * @param EstadoFinal the EstadoFinal to set
     */
    public void setEstadoFinal(boolean EstadoFinal) {
        this.EstadoFinal = EstadoFinal;
    }

   
}
