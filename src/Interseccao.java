import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

public class Interseccao {
    private Automato aut1;
    private Automato aut2;
    private Automato autFinal;
    private SortedSet<String> alfabeto;

    public Interseccao(Automato aut1, Automato aut2) {
        this.aut1 = aut1;
        this.aut2 = aut2;
        this.alfabeto = new TreeSet<>();
    }

    
    public Automato getResutadoFinalAFN(){
        autFinal = new Automato();
        int cont = 0;
        int numEstado = 0;
        Estado novoEstado;
        Estado antigoEstadoInicial1, antigoEstadoInicial2, novoEstadoInicial, novoEstadoFinal;
        ArrayList<Estado> antigoFinal;

        //Criação de estados do automato 1
        for (Estado estado1 : aut1.getEstados()) {
            ArrayList<Transicao> trans = estado1.getTransicoesAceitas();
            novoEstado = autFinal.addEstado();
            novoEstado.setNome("q" + numEstado);
            novoEstado.copyTransition(trans, 0);
            
            if (estado1.isInicial()){
                novoEstado.setInicial();
            }

            if (estado1.isFinal()){
                novoEstado.setFinal();
            }
            cont++;
            numEstado++;
        }

        //Pega o antigo inicial do autômato 1
        antigoEstadoInicial1 = autFinal.getEstadoInicial();
        antigoEstadoInicial1.unsetInicial();

        //Criação de estados do automato 2
        for (Estado estado2 : aut2.getEstados()) {
            ArrayList<Transicao> trans = estado2.getTransicoesAceitas();
            novoEstado = autFinal.addEstado();
            novoEstado.setNome("q" + numEstado);
            novoEstado.copyTransition(trans, cont);

            if (estado2.isInicial()){
                novoEstado.setInicial();
            }

            if (estado2.isFinal()){
                novoEstado.setFinal();
            }
            numEstado++;
        }

        //Pega o antigo inicial do autômato 2 e cria um novo estado final
        antigoFinal = autFinal.getEstadosFinais();
        novoEstadoFinal = autFinal.addEstado();
        novoEstadoFinal.setFinal();

        //Retira os estados finais anteriores e adiciona uma transição para o novo final
        for (Estado estado : antigoFinal) {
            estado.unsetFinal();
            estado.addTransicao(novoEstadoFinal.getId(), "lambda");
        }

        antigoEstadoInicial2 = autFinal.getEstadoInicial();
        antigoEstadoInicial2.unsetInicial();

        novoEstadoInicial = autFinal.addEstado();
        novoEstadoInicial.setInicial();
        
        novoEstadoInicial.addTransicao(antigoEstadoInicial1.getId(), "lambda");
        novoEstadoInicial.addTransicao(antigoEstadoInicial2.getId(), "lambda");

        return autFinal;
    } 

    public Automato getResultadoFinalAFD() {
        autFinal = new Automato();
        Estado novoEstado;
        verificaAlfabeto(aut1);
        
        //Junção de estados
        for (Estado estado : aut1.getEstados()) {
            for (Estado estado2 : aut2.getEstados()) {
                novoEstado = autFinal.addEstado();
                novoEstado.setNome(estado.getNome() + estado2.getNome());

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
        for (Estado estado : aut1.getEstados()) {
            ArrayList<Transicao> trans1 = estado.getTransicoesAceitas();
            for (Estado estado2 : aut2.getEstados()) {
                ArrayList<Transicao> trans2 = estado2.getTransicoesAceitas();
                for (String simbolo : alfabeto) {
                    if(verificaTransicao(simbolo, estado, estado2)){

                        destino1 = getTransicao(simbolo, trans1).getDestino();
                        destino2 = getTransicao(simbolo, trans2).getDestino();
                        String nomeDestino = getEstadoPorId(destino1, aut1).getNome() + getEstadoPorId(destino2, aut2).getNome();
                        int idOrigem = achaId(estado.getNome()+estado2.getNome());
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

        for (Estado estado: autFinal.getEstados()) {
            if(nome.equals(estado.getNome())){
                return estado.getId();
            }
        }
        return -1;
    }

    private boolean verificaTransicao (String simbolo, Estado estado1, Estado estado2){

        boolean existeTrans1= false;
        boolean existeTrans2= false;

        for (Transicao transicao : estado1.getTransicoesAceitas()) {
            if (transicao.getValor().equals(simbolo)) {
                existeTrans1 = true;
            }
        }

        for (Transicao transicao : estado2.getTransicoesAceitas()) {
            if (transicao.getValor().equals(simbolo)) {
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

        for (Estado estado : aut.getEstados()) {
            ArrayList<Transicao> trans = estado.getTransicoesAceitas();
            if(trans == null){
                continue;
            }

            for (Transicao transicao : trans) {
                alfabeto.add(transicao.getValor());
            }
        }
    }
}