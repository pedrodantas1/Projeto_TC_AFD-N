import org.w3c.dom.Document;

import controller.Operador;
import controller.xmlReader.ConstrutorDocumentoXML;
import controller.xmlReader.EscritorXML;
import controller.xmlReader.LeitorXML;
import model.Automato;
import view.BuildMenu;

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
      /*  String diretorio = ".\\arquivos_jff";
        String nomeAut1 = "automato1.jff";
        String nomeAut2 = "automato2.jff";
        String result = "intersecçãoAFN.jff";

        Automato aut1 = new Automato();
        Automato aut2 = new Automato();
        Automato saida = new Automato();
        
        //Ler automato do arquivo jff
        lerAutomato(aut1, diretorio, nomeAut1);
        lerAutomato(aut2, diretorio, nomeAut2);

        


        //Operação para criar uma intersecção entre dois AFD
        Operador operador = new Operador();
        //saida = operador.operacaoInterseccaoAFD(aut1, aut2);

        //Operação para criar uma intersecção entre dois AFN
        saida = operador.operacaoComplemento(aut1);
        
        //Criar arquivo jff final
        criarArquivoJFF(saida, diretorio, result); */


        BuildMenu teste = new BuildMenu();
    }

}