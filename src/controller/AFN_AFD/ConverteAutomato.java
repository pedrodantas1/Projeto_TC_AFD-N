package controller.AFN_AFD;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import controller.afnReader.WriteFile;
import model.Automato;
import model.Estado;
import model.StatesList;
import model.Transicao;

public class ConverteAutomato {
    static public Automato afn = new Automato();
    static public Automato afd = new Automato();
    static private ArrayList<StatesList> estadosAFD = new ArrayList<StatesList>();

    static private ArrayList<Estado> estados = new ArrayList<Estado>();

    public static boolean converter() {
        Estado inicial = getInicial();
        int id = 0;
        int i = 0;

        if (inicial == null)
            throw new StatesException("Nenhum Estado inicial foi encontrado");
        /*
         * Inicialmente, após verificar que o automato possui um estado inicial 
         * o método adiciona esse estado no ArrayList de estadosAFD.
         * Após adicionar os estados iniciais o programa pega todos os estados no qual o inicial vai com lambda/epislon
         */
        estados.add(inicial);
        getEstados(inicial, "lambda");
        estadosAFD.add(i, new StatesList(new ArrayList<Estado>(estados),false));
        addEstadoAFD(i,true);
        i++;
        estados.clear();

        /*
         * loop para gerar todos os estados do AFD
         * o loop só é quebrado quando todos os estados da lista
         * já foram verificados se possuem transações e nenhum novo estado foi gerado
         */
        do {
                for (String letra : afn.getAlfabeto()) {
                    
                    for (Estado e : estadosAFD.get(id).getEstados()) getEstados(e, letra);
                    
                    if(estados.size()>0){
                        ArrayList<Estado> auxArrayList = new ArrayList<Estado>(estados);

                        for (Estado e : auxArrayList) getEstados(e, "lambda");

                        estadosAFD.get(id).setRead(true);

                        auxArrayList.clear();

                        boolean contains = false;
                        for (int j = 0; j < estadosAFD.size(); j++) 
                            if (estadosAFD.get(j).getEstados().containsAll(estados) && estadosAFD.get(j).getEstados().size()==estados.size()){
                                    contains = true;
                                    addTransicaoAFD(id, j, letra);
                            } 
                           
                        if (!contains) {
                            estadosAFD.add(i, new StatesList(new ArrayList<Estado>(estados),false));
                            addEstadoAFD(i,false);
                            addTransicaoAFD(id, i, letra);
                            i++;
                        }
                        //adiciona transicao no afd
                    }
                    estados.clear();
                }     
               id++;
        }while(!allRead());

        try {
            WriteFile.save();
            afn = new Automato();
            afd = new Automato();
            estadosAFD.clear();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao salvar o arquivo", "Erro!", JOptionPane.ERROR_MESSAGE);
        }

        return true;
    }

    private static boolean allRead(){
        int size = 0;

        for (StatesList e : estadosAFD) if(e.isRead()) size++;
        
        if(size==estadosAFD.size()) return true;

        return false;
    }

    private static Estado getInicial() {

        try {

            for (Estado estado : afn.getEstados()) {
                if (estado.isInicial())
                    return estado;

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "O AFN não possui estado inicial!", "Erro", JOptionPane.ERROR_MESSAGE);
        }

        return null;
    }

    private static void getEstados(Estado inicial, String letra) {
        for (Transicao transicao : afn.getTransicoes()) {
            if (inicial.getId().equals(transicao.getFrom().getId())
                    && transicao.getValor().equals(letra) 
                    && !estados.contains(transicao.getTo())) {
                estados.add(transicao.getTo());

               if (letra.equals("lambda")) getEstados(transicao.getTo(), letra);              
            }
        }
    }

    private static void addEstadoAFD(int id,boolean initial) {
        String label ="";
        String name = "q";
        boolean initialState = false;
        boolean finalState = false;

        for (Estado estado : estadosAFD.get(id).getEstados()) {
        
            if (estado.isInicial() && initial) initialState = true;

            if (estado.isFinal()) finalState = true;

            label += "q"+estado.getId(); 

        }

        afd.addEstado(new Estado(id, name+id, label, initialState, finalState));
    }

    private static void addTransicaoAFD(int from, int to,String read) {
        Estado eFrom = null;
        Estado eTo = null;

        
        for (Estado e : afd.getEstados()) {
            if (e.getId().equals(from)) {
                eFrom = e;
            }

            if (e.getId().equals(to)) {
                eTo=e;
            }
        }
        afd.addTransicao(new Transicao(eFrom, eTo, read));
    }
}
