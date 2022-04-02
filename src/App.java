import org.w3c.dom.Document;

public class App {
    public static String diretorio = ".\\arquivos_jff";
    public static String arqEntrada = "arquivo.jff";
    public static String arqSaida = "saida.jff";

    public static void main(String[] args) throws Exception {
        Automato aut = new Automato();
        
        LeitorXML leitor = new LeitorXML();
        leitor.carregaArquivoXML(diretorio, arqEntrada);
        Document docEntrada = leitor.getDocumentoLido();
        aut.setEstados(docEntrada.getElementsByTagName("state"));
        aut.loadTransicoes(docEntrada.getElementsByTagName("transition"));

        aut.mostrarAutomato();

        /*
        aut.addEstado().addTransicao(1, "1");
        aut.addEstado().addTransicao(3, "0");
        aut.addEstado().addTransicao(4, "1");
        aut.addEstado().addTransicao(2, "0");
        aut.addEstado().addTransicao(0, "1");
        aut.addEstado().addTransicao(5, "1");
        */

        ConstrutorDocumentoXML construtorXML = new ConstrutorDocumentoXML(aut);
        construtorXML.configuraDocumento();
        Document docSaida = construtorXML.getDocumentoConstruido();
        
        EscritorXML escritor = new EscritorXML();
        escritor.setDocumentXML(docSaida);
        escritor.exportaArquivoXML(diretorio, arqSaida);
    }
}