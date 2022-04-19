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

        OperacaoEstrela op = new OperacaoEstrela(aut);
        op.realizaOperacao();

        //aut.mostrarAutomato();

        ConstrutorDocumentoXML construtorXML = new ConstrutorDocumentoXML(aut);
        construtorXML.configuraDocumento();
        Document docSaida = construtorXML.getDocumentoConstruido();
        
        EscritorXML escritor = new EscritorXML();
        escritor.setDocumentXML(docSaida);
        escritor.exportaArquivoXML(diretorio, arqSaida);
    }
}