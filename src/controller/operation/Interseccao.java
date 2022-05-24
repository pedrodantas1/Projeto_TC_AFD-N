package controller.operation;

import java.util.ArrayList;

import model.Automato;
import model.Estado;
import model.Transicao;

public class Interseccao extends Operacao {
    Automato autFinal;

    public Interseccao() {
        maxAutomaton = 2;
        qtdAutomaton = 0;
        automatons = new Automato[maxAutomaton];
    }
    
    public Automato makeOperation(){
        autFinal = new Automato();
        Estado novoEstado;
        verificaAlfabeto(getAutomaton(0));
        
        //Junção de estados
        for (Estado estado : getAutomaton(0).getEstados()) {
            for (Estado estado2 : getAutomaton(1).getEstados()) {
                novoEstado = autFinal.addEstado();
                novoEstado.setName(estado.getName() + estado2.getName());

                if(estado.isInicial() && estado2.isInicial()){
                    novoEstado.setInicial();
                }

                if(estado.isFinal() && estado2.isFinal()){
                    novoEstado.setFinal();
                }
            }
        }

        //Criação de transições
        int destino1, destino2;
        for (Estado estado : getAutomaton(0).getEstados()){
            ArrayList<Transicao> trans1 = estado.getTransicoesAceitas();
            for (Estado estado2 : getAutomaton(1).getEstados()) {
                ArrayList<Transicao> trans2 = estado2.getTransicoesAceitas();
                for (String simbolo : getAutomaton(0).getAlfabeto()){
                    if(verificaTransicao(simbolo, estado, estado2)){
                        destino1 = getTransicao(simbolo, trans1).getDestino();
                        destino2 = getTransicao(simbolo, trans2).getDestino();
                        String nomeDestino = getEstadoPorId(destino1, getAutomaton(0)).getName() 
                                            + getEstadoPorId(destino2, getAutomaton(1)).getName();
                        int idOrigem = achaId(estado.getName()+estado2.getName());
                        int idDestino = achaId(nomeDestino);
                        Estado estadoOrigem = getEstadoPorId(idOrigem, autFinal);
                        estadoOrigem.addTransicao(idDestino, simbolo);
                    }
                }
            }
        }

        return autFinal;
    }
    
    private int achaId (String nome) {
        for (Estado estado: autFinal.getEstados()){
            if(nome.equals(estado.getName())){
                return estado.getId();
            }
        }
        return -1;
    }
    

    private boolean verificaTransicao (String simbolo, Estado estado1, Estado estado2) {
        boolean existeTrans1 = false;
        boolean existeTrans2 = false;

        for (Transicao transicao : estado1.getTransicoesAceitas()) {
            if (transicao.getValor().equals(simbolo)){
                existeTrans1 = true;
            }
        }

        for (Transicao transicao : estado2.getTransicoesAceitas()) {
            if (transicao.getValor().equals(simbolo)){
                existeTrans2 = true;
            }
        }

        return existeTrans1 && existeTrans2;
    }

    public Transicao getTransicao(String simbolo, ArrayList<Transicao> transicoes) {
        for (Transicao transicao : transicoes){
            if (transicao.getValor().equals(simbolo)){
                return transicao;
            }
        }

        return null;
    }

    public Estado getEstadoPorId(int id, Automato aut) {
        for (Estado estado : aut.getEstados()){
            if (estado.getId() == id){
                return estado;
            }
        }

        return null;
    }

    public void verificaAlfabeto (Automato aut){
        for (Estado estado : aut.getEstados()){
            ArrayList<Transicao> trans = estado.getTransicoesAceitas();
            if(trans == null){
                continue;
            }
            for (Transicao transicao : trans){
                aut.getAlfabeto().add(transicao.getValor());
            }
        }
    }

}