import java.util.ArrayList;

public class Operador {
    //private Automato automato;

    public Operador() {
        //this.automato = automato;
    }

    //Realiza a operacao uniao entre dois automatos
    public void operacaoUniao(Automato aut1, Automato aut2) {
        //Codigo aqui
    }

    //Realiza a operacao interseccao entre dois automatos
    public void operacaoInterseccao(Automato aut1, Automato aut2) {
        //Codigo aqui
    }

    //Realiza a operacao concatenacao entre dois automatos
    public void operacaoConcatenacao(Automato aut1, Automato aut2) {
        //Codigo aqui
    }

    //Realiza a operacao complemento de um automato
    public void operacaoComplemento(Automato automato) {
        //Codigo aqui
    }

    //Realiza a operacao estrela no automato desejado
    public void operacaoEstrela(Automato automato) {
        ArrayList<Estado> estadosFinais = automato.getEstadosFinais();
        if (estadosFinais == null){
            System.out.println("Não existem estados finais.");
            return;
        }
        Estado antigoInicial = automato.getEstadoInicial();
        if (antigoInicial == null){
            System.out.println("Não existe estado inicial.");
            return;
        }
        //Criar um estado novo (inicial e final)
        antigoInicial.unsetInicial();
        Estado novoInicial = automato.addEstado();
        novoInicial.setInicial();
        novoInicial.setFinal();
        //Colocar epsilon desse novo estado para o antigo inicial
        novoInicial.addTransicao(antigoInicial.getId(), "lambda");
        //Colocar epsilon dos estados finais para o antigo inicial
        for (Estado estado : estadosFinais){
            estado.addTransicao(antigoInicial.getId(), "lambda");
        }
    }

    //Converte o afn para afd
    public void gerarAfdEquivalente(Automato automato) {
        //Codigo aqui
    }

}