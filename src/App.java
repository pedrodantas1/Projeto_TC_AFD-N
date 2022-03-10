import java.util.ArrayList;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
// import java.io.FileWriter;
// import java.io.PrintWriter;

public class App {
    public static String extrairValorEncapsulado(String str, String primeiroDelim, String segundoDelim){
        int beginIndex= str.indexOf(primeiroDelim) + primeiroDelim.length() - 1;
        int endIndex = str.indexOf(segundoDelim);
        int qtdCaracteres = endIndex - beginIndex - 1;
        int pos = beginIndex + 1;
        return str.substring(pos, pos+qtdCaracteres);
    }

    public static void main(String[] args) throws Exception {
        String diretorio = ".\\arquivos_jff";
        String nomeArq = "arquivo.jff";
        File arquivo = new File(diretorio, nomeArq);
        if (!arquivo.exists()){
            System.out.println("\nO arquivo não existe!\n");
            System.exit(0);
        }

        FileReader fr = new FileReader(arquivo);
        BufferedReader br = new BufferedReader(fr);

        //Ler os estados e transicoes e salvar suas informacoes
        String linha;
        ArrayList<Integer> estados = new ArrayList<Integer>();
        ArrayList<Integer> transicoes = new ArrayList<Integer>();
        while (br.ready()){
            linha = br.readLine();
            //Estados
            if (linha.contains("<state id")){
                String id = extrairValorEncapsulado(linha, "id=\"", "\" name");
                if (!id.isEmpty()){
                    estados.add(Integer.parseInt(id));
                }
            //Transicoes
            }else if (linha.contains("<from>")){
                String valor = extrairValorEncapsulado(linha, ">", "</");
                if (!valor.isEmpty()){
                    transicoes.add(Integer.parseInt(valor));
                }
            }
        }

        fr.close();
        br.close();



        /* Escrita no arquivo
        FileWriter fw = new FileWriter(arquivo, false);
        PrintWriter pw = new PrintWriter(fw);

        fw.close();
        pw.close();
        */
    }
}