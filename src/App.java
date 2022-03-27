public class App {
    public static String diretorio = ".\\arquivos_jff";
    public static String arqEntrada = "arquivo.jff";
    public static String arqSaida = "saida.jff";

    public static void main(String[] args) throws Exception {
        Automato aut = new Automato();
        
        LeitorXML leitor = new LeitorXML(diretorio, arqEntrada);
        leitor.carregaDocumento();
        aut.setEstados(leitor.getEstados());
        aut.loadTransicoes(leitor.getTransicoes());

        aut.addEstado().addTransicao(1, "1");
        aut.addEstado().addTransicao(3, "0");
        aut.addEstado().addTransicao(4, "1");
        aut.addEstado().addTransicao(2, "0");
        aut.addEstado().addTransicao(0, "1");
        aut.addEstado().addTransicao(5, "1");

        EscritorXML escritor = new EscritorXML(diretorio, arqSaida, aut);
        if (escritor.exportaArquivoXML()){
            System.out.println("\nArquivo exportado com sucesso!\n\n");
        }else{
            System.out.println("\nNao foi possivel exportar o arquivo!\n\n");
        }
    }
}