import java.util.ArrayList;
public class Automato {
    public ArrayList<Estado> estados;
    public ArrayList<Transicao> transicoes;
    public int idAtual;
    public int xAtual, yAtual;

    public Automato() {
        this.estados = new ArrayList<Estado>();
        this.transicoes = new ArrayList<Transicao>();
        this.idAtual = 0;
        //Fazer logica para posicionar de forma organizada
        this.xAtual = 100;
        this.yAtual = 125;
    }

    public void mostrarAutomato() {
        System.out.printf("%nAutômato:%n%n");
        System.out.printf("Estados:%n");
        for (Estado estado : this.estados) {
            estado.mostrarEstado();
            //Colocar transicoes quando estiverem configuradas
        }
    }

    public void addEstado() {
        Estado estado = new Estado(idAtual, xAtual, yAtual);
        this.estados.add(estado);
        this.idAtual++;
    }
    
    public void removeEstado(Estado estado) {
        if (estados.size() > 0){
            this.estados.remove(estado);
            //Deve-se remover tambem as transicoes que estao ligadas a este estado
        }
    }

    public Estado getEstadoPorId(int id) {
        for (Estado estado : this.estados) {
            if (estado.getId() == id){
                return estado;
            }
        }
        return null;
    }

    public Estado getEstadoInicial() {
        for (Estado estado : this.estados) {
            if (estado.isInicial()){
                return estado;
            }
        }
        return null;
    }

    public ArrayList<Estado> getEstadosFinais() {
        ArrayList<Estado> estadosFinais = new ArrayList<Estado>();
        for (Estado estado : this.estados) {
            if (estado.isFinal()){
                estadosFinais.add(estado);
            }
        }
        if (estadosFinais.size() > 0){
            return estadosFinais;
        }
        return null;
    }

    //Ajeitar ainda
    public void addTransicao(Transicao transicao) {
        this.transicoes.add(transicao);
    }

    public void removeTransicao(Transicao transicao) {
        this.transicoes.remove(transicao);
    }

    
}
