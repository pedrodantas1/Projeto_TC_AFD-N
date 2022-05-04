public class Operador {

    public Operador() {
    }

    //Realiza a operacao uniao entre dois automatos
    public void operacaoUniao(Automato aut1, Automato aut2) {
        //Codigo aqui
    }

    //Realiza a operacao intersecção entre dois automatos AFD
    public Automato operacaoInterseccaoAFD(Automato aut1, Automato aut2) {
        Interseccao operacaoAFD = new Interseccao(aut1, aut2);
        return operacaoAFD.getResultadoFinalAFD();
    }

    //Realiza a operacao intersecção entre dois automatos AFN
    public Automato operacaoInterseccaoAFN(Automato aut1, Automato aut2) {
        Interseccao operacaoAFN = new Interseccao(aut1, aut2);
        return operacaoAFN.getResutadoFinalAFN();
    }

    //Realiza a operacao concatenacao entre dois automatos
    public void operacaoConcatenacao(Automato aut1, Automato aut2) {
        //Codigo aqui
    }

    //Realiza a operacao complemento de um automato
    public Automato operacaoComplemento(Automato aut1) {
        Complemento operacaoComp = new Complemento(aut1);
        return operacaoComp.GetResultadoComp();
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