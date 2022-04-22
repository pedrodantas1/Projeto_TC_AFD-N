import org.w3c.dom.Document;

public class App {
    public static String diretorio = ".\\arquivos_jff";
    public static String arqEntrada = "arquivo.jff";
    public static String arqClone = "clone.jff";
    public static String arqOrig = "original.jff";

    public static void main(String[] args) {
        Automato aut = new Automato();
        
        //Funcao do app (retorna automato ja pronto)
        LeitorXML leitor = new LeitorXML();
        leitor.carregaArquivoXML(diretorio, arqEntrada);
        Document docEntrada = leitor.getDocumentoLido();
        aut.setEstados(docEntrada.getElementsByTagName("state"));
        aut.loadTransicoes(docEntrada.getElementsByTagName("transition"));

        //Usar clone do automato original para realizar as operacoes
        Automato autClonado = new Automato(aut);
        autClonado.addEstado();
        autClonado.addEstado();
        autClonado.addEstado();
        autClonado.addEstado();
        autClonado.getEstadoPorId(3).addTransicao(3, "teste");
        aut.realizaEstrela();

        //Funcao do app (retorna doc pronto)
        ConstrutorDocumentoXML construtorXML = new ConstrutorDocumentoXML();
        construtorXML.setAutomato(autClonado);
        construtorXML.configuraDocumento();
        Document docSaidaClone = construtorXML.getDocumentoConstruido();
        construtorXML.setAutomato(aut);
        construtorXML.configuraDocumento();
        Document docSaidaOrig = construtorXML.getDocumentoConstruido();
        
        //Funcao do app
        EscritorXML escritor = new EscritorXML();
        escritor.setDocumentXML(docSaidaClone);
        escritor.exportaArquivoXML(diretorio, arqClone);
        escritor.setDocumentXML(docSaidaOrig);
        escritor.exportaArquivoXML(diretorio, arqOrig);
    }
}