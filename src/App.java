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

        aut.mostrarAutomato();

        EscritorXML escritor = new EscritorXML(diretorio, arqSaida, aut);
        if (escritor.exportaArquivoXML()){
            System.out.println("\nArquivo exportado com sucesso!\n\n");
        }else{
            System.out.println("\nNao foi possivel exportar o arquivo!\n\n");
        }


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