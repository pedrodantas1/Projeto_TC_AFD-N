package model;
import org.w3c.dom.Element;

public class Transicao {
    private int origem, destino;
    private Estado from, to;
    private String valor;

    public Transicao() {
    }

    public Transicao(Estado from, Estado to,String read){
        this.from = from;
        this.to = to;
        this.valor = read;
    }

    //Cria uma transicao padrao
    public Transicao(int origem, int destino, String valor) {
        this.origem = origem;
        this.destino = destino;
        this.valor = valor;
    }

    //Cria uma transicao extraida do .jff
    public Transicao(Element transicao) {
        this.origem = Integer.parseInt(transicao.getElementsByTagName("from").item(0).getTextContent());
        this.destino = Integer.parseInt(transicao.getElementsByTagName("to").item(0).getTextContent());
        String value = transicao.getElementsByTagName("read").item(0).getTextContent();
        this.valor = (value != "") ? value : "lambda";
    }

    //Printar transicao completa
    public void mostrarTransicao() {
        System.out.printf("Origem: %d%n", this.origem);
        System.out.printf("Destino: %d%n", this.destino);
        System.out.printf("Valor: %s%n%n", this.valor);
    }

    //Retorna origem da transicao
    public int getOrigem() {
        return origem;
    }

    //Seta origem da transicao
    public void setOrigem(int origem) {
        this.origem = origem;
    }

    //Retorna destino da transicao
    public int getDestino() {
        return destino;
    }

    //Seta destino da transicao
    public void setDestino(int destino) {
        this.destino = destino;
    }

    //Retorna valor da transicao
    public String getValor() {
        return valor;
    }
/**
 * 
 * @return
 */
    public Estado getFrom() {
        return from;
    }
/**
 * 
 * @return
 */
    public Estado getTo() {
        return to;
    }

    //Seta valor da transicao
    public void setValor(String valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "Transicao [from=" + this.from.getId() + ", read=" + this.valor + ", to=" + this.to.getId() + "]";
    }
}
