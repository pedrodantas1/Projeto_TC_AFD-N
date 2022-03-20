// import javax.xml.parsers.DocumentBuilderFactory;
// import javax.xml.parsers.DocumentBuilder;
// import org.w3c.dom.Document;
// import org.w3c.dom.Element;
// import org.w3c.dom.Node;
// import org.w3c.dom.NodeList;
// import java.io.File;
// import java.util.ArrayList;

public class App {
    public static void main(String[] args) throws Exception {
        Automato aut = new Automato();
        aut.addEstado();
        aut.addEstado();
        aut.addEstado();
        aut.addEstado();
        aut.addEstado();
        aut.addEstado();

        aut.addTransicao(0, 1, "0");
        aut.addTransicao(1, 3, "1");
        aut.addTransicao(2, 1, "1");
        aut.addTransicao(4, 2, "0");

        aut.estados.get(0).setInicial();
        aut.estados.get(2).setFinal();
        aut.estados.get(4).setFinal();
        aut.estados.get(5).setFinal();
        aut.mostrarAutomato();

        //System.out.println("\nisInicial: "+ aut.estados.get(0).isInicial() +"\n\n");




        /*
        try{   
            File file = new File(".\\atividades\\arquivos jff\\arquivo.jff");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();

            System.out.println("\nRoot element: " + doc.getDocumentElement().getNodeName());

            NodeList stateList = doc.getElementsByTagName("state");
            for (int itr = 0; itr < stateList.getLength(); itr++){
                Node node = stateList.item(itr);
                System.out.println("\nNode Name: " + node.getNodeName());
                if (node.getNodeType() == Node.ELEMENT_NODE){
                    Element eElement = (Element) node;
                    System.out.println("id: "+ eElement.getAttribute("id"));
                    System.out.println("nome: "+ eElement.getAttribute("name"));
                    System.out.println("x: "+ eElement.getElementsByTagName("x").item(0).getTextContent());
                    System.out.println("y: "+ eElement.getElementsByTagName("y").item(0).getTextContent());
                    System.out.printf("isInitial: %b%n", eElement.getElementsByTagName("initial").item(0) != null);
                }
            }

            NodeList transitionList = doc.getElementsByTagName("transition");
            for (int itr = 0; itr < transitionList.getLength(); itr++){
                Node node = transitionList.item(itr);
                System.out.println("\nNode Name: " + node.getNodeName());
                if (node.getNodeType() == Node.ELEMENT_NODE){
                    Element eElement = (Element) node
                    System.out.println("origem: "+ eElement.getElementsByTagName("from").item(0).getTextContent());
                    System.out.println("destino: "+ eElement.getElementsByTagName("to").item(0).getTextContent());
                    String valor;
                    System.out.printf("valor: %s%n",
                                     (valor = eElement.getElementsByTagName("read").item(0).getTextContent()) != ""
                                     ? valor  : "epsilon");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        */
    }
}