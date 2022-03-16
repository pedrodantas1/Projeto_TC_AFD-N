import java.util.ArrayList;

public class Automato {
    public ArrayList<Estado> estados;
    public ArrayList<Transicao> transicoes;
    public int idAtual;
    public int xAtual, yAtual;

    public Automato(ArrayList<Estado> estados, ArrayList<Transicao> transicoes) {
        this.estados = estados;
        this.transicoes = transicoes;
        this.idAtual = 0;
        //Fazer logica para posicionar de forma organizada
        this.xAtual = 100;
        this.yAtual = 125;
    }

    public void addEstado() {
        Estado estado = new Estado(idAtual, xAtual, yAtual);
        this.estados.add(estado);
        this.idAtual++;
    }
    
    public void removeEstado(Estado estado) {
        if (estados.size() > 0){
            this.estados.remove(estado);
            this.idAtual--;
        }
    }

    //Ajeitar ainda
    public void addTransicao(Transicao transicao) {
        this.transicoes.add(transicao);
    }

    public void removeTransicao(Transicao transicao) {
        this.transicoes.remove(transicao);
    }

    
}
