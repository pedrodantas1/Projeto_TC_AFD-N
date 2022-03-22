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
        LeitorXML leitor = new LeitorXML(".\\arquivos_jff", "arquivo.jff");
        leitor.carregaDocumento();
        aut.setEstados(leitor.getEstados());
        aut.loadTransicoes(leitor.getTransicoes());

        aut.mostrarAutomato();



        /*
        aut.addEstado().setInicial();
        aut.addEstado();
        aut.addEstado().setFinal();
        aut.addEstado();
        aut.addEstado().setFinal();
        aut.addEstado().setFinal();

        aut.addTransicaoAoEstado(0, 1, "0");
        aut.addTransicaoAoEstado(1, 3, "1");
        aut.addTransicaoAoEstado(2, 1, "1");
        aut.addTransicaoAoEstado(4, 2, "0");
        aut.addTransicaoAoEstado(4, 1, "0");
        aut.addTransicaoAoEstado(4, 3, "1");

        aut.removeEstado(121);

        aut.mostrarAutomato();
        */
    }
}