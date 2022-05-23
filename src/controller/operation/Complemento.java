package controller.operation;
import model.Automato;
import model.Estado;

public class Complemento extends Operacao {
    
    public Complemento() {
        maxAutomaton = 1;
        qtdAutomaton = 0;
        automatons = new Automato[maxAutomaton];
    }

    public Automato makeOperation() {
        Automato automato = getAutomaton(0);

        for (Estado estado : automato.getEstados()) {

            //Pega o estado final e transforma em estado normal

            if(estado.isFinal()){

                estado.unsetFinal();

            }

            //Pega o estado que não é final e transforma em final

            else{

                estado.setFinal();

            }
            
        }

        return automato;

    }
    
}