public class Transicao {
    private int origem, destino;
    private String valor;

    public Transicao() {
    }

    public Transicao(int origem, int destino, String valor) {
        this.origem = origem;
        this.destino = destino;
        this.valor = valor;
    }

    public void mostrarTransicao() {
        System.out.printf("Origem: %d%n", this.origem);
        System.out.printf("Destino: %d%n", this.destino);
        System.out.printf("Valor: %s%n%n", this.valor);
    }

    public int getOrigem() {
        return origem;
    }

    public void setOrigem(int origem) {
        this.origem = origem;
    }

    public int getDestino() {
        return destino;
    }

    public void setDestino(int destino) {
        this.destino = destino;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    
    
}
