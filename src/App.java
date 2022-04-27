import org.w3c.dom.Document;

public class App {

    public static void lerAutomato(Automato aut, String diretorio, String arquivo) {
        LeitorXML leitor = new LeitorXML();
        leitor.carregaArquivoXML(diretorio, arquivo);
        Document docEntrada = leitor.getDocumentoLido();
        aut.setEstados(docEntrada.getElementsByTagName("state"));
        aut.loadTransicoes(docEntrada.getElementsByTagName("transition"));
    }

    public static Document getDocumenXML(Automato aut) {
        ConstrutorDocumentoXML construtorXML = new ConstrutorDocumentoXML();
        construtorXML.setAutomato(aut);
        construtorXML.configuraDocumento();
        return construtorXML.getDocumentoConstruido();
    }

    public static void criarArquivoJFF(Document doc, String diretorio, String arquivo) {
        EscritorXML escritor = new EscritorXML();
        escritor.setDocumentXML(doc);
        escritor.exportaArquivoXML(diretorio, arquivo);
    }

    public static void main(String[] args) {
        String diretorio = ".\\arquivos_jff";
        String arqEntrada = "arquivo.jff";
        String arqClone = "clone.jff";
        String arqOrig = "original.jff";

        Automato aut = new Automato();
        
        //Ler automato do arquivo jff
        lerAutomato(aut, diretorio, arqEntrada);

        //Usar clone do automato original para realizar as operacoes
        Automato autClonado = new Automato(aut);
        autClonado.addEstado();
        autClonado.addEstado();
        autClonado.addEstado();
        autClonado.addEstado();
        autClonado.getEstadoPorId(3).addTransicao(3, "teste");
        aut.realizaEstrela();

        //Construir documento xml totalmente estruturado
        Document docSaidaClone = getDocumenXML(autClonado);
        Document docSaidaOrig = getDocumenXML(aut);
        
        //Criar arquivo jff final
        criarArquivoJFF(docSaidaClone, diretorio, arqClone);
        criarArquivoJFF(docSaidaOrig, diretorio, arqOrig);
    }
}