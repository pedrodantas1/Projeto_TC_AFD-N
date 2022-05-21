package controller.operation;

import controller.AFN_AFD.ConverteAutomato;
import model.Automato;


public class GerarAFD extends Operacao{

    
    public GerarAFD() {

    }

    @Override
    public Automato makeOperation() {
        return ConverteAutomato.afd;
    }
    
}
