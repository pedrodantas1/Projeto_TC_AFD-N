import java.util.ArrayList;

public class Estrela {
    private Automato automato;
    
    public Estrela(Automato automato) {
        this.automato = automato;
    }

    public void realizaOperacao() {
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

}