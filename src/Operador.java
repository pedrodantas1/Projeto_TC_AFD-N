public class Operador {

    public Operador() {
    }

    //Realiza a operacao uniao entre dois automatos
    public void operacaoUniao(Automato aut1, Automato aut2) {
        //Codigo aqui
    }

    //Realiza a operacao interseccao entre dois automatos
    public Automato operacaoInterseccao(Automato aut1, Automato aut2) {
        Interseccao operacao = new Interseccao(aut1, aut2);
        return operacao.getResultadoFinal();
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
        Estrela operacao = new Estrela(automato);
        operacao.realizaOperacao();
    }

    //Converte o afn para afd
    public void gerarAfdEquivalente(Automato automato) {
        //Codigo aqui
    }

}