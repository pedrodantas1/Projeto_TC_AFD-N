package controller.operation;

import model.Automato;


/**
 * Classe abstrata a ser herdada pelas operações padrões do sistema.
 * 
 * 
 * @author Pedro Dantas
 */
public abstract class Operacao {
    public Automato[] automatons;
    public int maxAutomaton;
    public int qtdAutomaton;

    public void setAutomaton(Automato aut, int id) {
        if (id < 0 || id > maxAutomaton-1){
            System.out.println("Índice inválido!");
            return;
        }
        if (qtdAutomaton < maxAutomaton && automatons[id] == null){
            qtdAutomaton++;
        }
        automatons[id] = aut;
    }

    public Automato getAutomaton(int id) {
        if (id < 0 || id > maxAutomaton-1){
            System.out.println("Índice inválido!");
            return null;
        }
        return automatons[id];
    }
    
    public abstract Automato makeOperation();

}