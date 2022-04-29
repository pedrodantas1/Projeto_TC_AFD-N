import org.w3c.dom.Document;

public class App {

    public static void lerAutomato(Automato aut, String diretorio, String arquivo) {
        LeitorXML leitor = new LeitorXML();
        leitor.carregaArquivoXML(diretorio, arquivo);
        Document docEntrada = leitor.getDocumentoLido();
        aut.setEstados(docEntrada.getElementsByTagName("state"));
        aut.loadTransicoes(docEntrada.getElementsByTagName("transition"));
    }

    public static void criarArquivoJFF(Automato aut, String diretorio, String arquivo) {
        ConstrutorDocumentoXML construtorXML = new ConstrutorDocumentoXML();
        construtorXML.setAutomato(aut);
        construtorXML.configuraDocumento();
        EscritorXML escritor = new EscritorXML();
        escritor.setDocumentXML(construtorXML.getDocumentoConstruido());
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

        Operador operador = new Operador();
        operador.operacaoEstrela(aut);
        
        //Criar arquivo jff final
        criarArquivoJFF(autClonado, diretorio, arqClone);
        criarArquivoJFF(aut, diretorio, arqOrig);
    }

}