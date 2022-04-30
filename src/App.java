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
        String nomeAut1 = "automato1.jff";
        String nomeAut2 = "automato2.jff";
        String result = "interceccao.jff";

        Automato aut = new Automato();
        Automato aut2 = new Automato();
        Automato saida = new Automato();
        
        //Ler automato do arquivo jff
        lerAutomato(aut, diretorio, nomeAut1);
        lerAutomato(aut2, diretorio, nomeAut2);

        



        Operador operador = new Operador();
        saida = operador.operacaoInterseccao(aut, aut2);
        
        //Criar arquivo jff final
        criarArquivoJFF(saida, diretorio, result);
    }

}