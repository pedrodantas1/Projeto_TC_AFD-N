import java.util.ArrayList;

public class Estado {
    private int id;
    private String nome;
    private String label;
    private double x, y;
    private boolean isInitial, isFinal;
    public ArrayList<Transicao> transicoes;

    public Estado() {
    }

    public Estado(int id, double x, double y) {
        this.id = id;
        //nomear de acordo com o id (q0, q1, etc)
        this.nome = String.format("q%d", id);
        this.label = null;
        this.x = x;
        this.y = y;
        this.isInitial = false;
        this.isFinal = false;
        this.transicoes = new ArrayList<>(3);
    }

    public void mostrarEstado() {
        System.out.printf("Estado %s:%n%n", this.nome);
        System.out.printf("id: %d%n", this.getId());
        System.out.printf("x: %.1f - y: %.1f%n", this.getPosX(), this.getPosY());
        System.out.printf("É inicial: %b%n", this.isInicial());
        System.out.printf("É final: %b%n%n", this.isFinal());
        System.out.printf("Transições: %n");
        if (this.transicoes.size() > 0){
            for (Transicao transicao : this.transicoes){
                transicao.mostrarTransicao();
            }
        }else{
            System.out.printf("Sem transições!%n%n");
        }
    }

    public int getId() {
        return id;
    }

    /* Nao vou precisar ate o momento
    public void setId(int id) {
        this.id = id;
    }
    */

    public boolean isInicial() {
        return isInitial;
    }
    
    public void setInicial() {
        //Se ja houver estado inicial, tira o inicial do outro e coloca nesse
        //(Pensar num metodo geral de verificacao dentro da classe Automato) 
        this.isInitial = true;
    }

    public void unsetInicial() {
        this.isInitial = false;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public void setFinal() {
        this.isFinal = true;
    }

    public void unsetFinal() {
        this.isFinal = false;
    }

    public double getPosX() {
        return x;
    }

    public void setPosX(double x) {
        this.x = x;
    }

    public double getPosY() {
        return y;
    }

    public void setPosY(double y) {
        this.y = y;
    }

    public boolean setTransicao(int destino, String valor) {
        if (!existeTransicao(destino, valor)){
            this.transicoes.add(new Transicao(this.id, destino, valor));
            return true;
        }
        return false;
    }

    public boolean removeTransicao(Transicao transicao) {
        if (this.transicoes.size() > 0 && this.transicoes.remove(transicao)){
            return true;
        }
        return false;
    }

    public boolean removeTransicao(int destino, String valor) {
        if (existeTransicao(destino, valor)){
            return removeTransicao(getTransicao(destino, valor));
        }
        return false;
    }

    public Transicao getTransicao(int destino, String valor) {
        for (Transicao transicao : this.transicoes){
            if (transicao.getOrigem() == this.id && 
                transicao.getDestino() == destino && 
                transicao.getValor() == valor){
                return transicao;
            }
        }
        return null;
    }

    public ArrayList<Transicao> getTransicoesAceitas() {
        ArrayList<Transicao> transicoesEstado = new ArrayList<>();
        for (Transicao transicao : this.transicoes){
            if (transicao.getOrigem() == this.id){
                transicoesEstado.add(transicao);
            }
        }
        return transicoesEstado;
    }

    public boolean existeTransicao(int destino, String valor) {
        Transicao transicao = getTransicao(destino, valor);
        if (transicao != null){
            return true;
        }
        return false;
    }

}