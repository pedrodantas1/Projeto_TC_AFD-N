package controller.operation;
import java.util.ArrayList;

import model.Automato;
import model.Estado;
import view.Dialogs;

public class Estrela extends Operacao {
    
    public Estrela() {
        maxAutomaton = 1;
        qtdAutomaton = 0;
        automatons = new Automato[maxAutomaton];
    }

    public Automato makeOperation() {
        Automato autEntrada = getAutomaton(0);
        if (autEntrada == null){
            Dialogs.showMessage("O autômato setado é inválido!");
            return null;
        }
        Automato autSaida = new Automato(autEntrada);
        ArrayList<Estado> estadosFinais = autSaida.getEstadosFinais();
        if (estadosFinais == null){
            Dialogs.showMessage("O autômato não possui estados finais!");
            return null;
        }
        Estado antigoInicial = autSaida.getEstadoInicial();
        if (antigoInicial == null){
            Dialogs.showMessage("O autômato não possui estado inicial!");
            return null;
        }
        //Criar um estado novo (inicial e final)
        antigoInicial.unsetInicial();
        Estado novoInicial = autSaida.addEstado();
        novoInicial.setInicial();
        novoInicial.setFinal();
        //Colocar epsilon desse novo estado para o antigo inicial
        novoInicial.addTransicao(antigoInicial.getId(), "lambda");
        //Colocar epsilon dos estados finais para o antigo inicial
        for (Estado estado : estadosFinais){
            estado.addTransicao(antigoInicial.getId(), "lambda");
        }

        return autSaida;
    }

}