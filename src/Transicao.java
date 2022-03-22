import org.w3c.dom.Element;

public class Transicao {
    private int origem, destino;
    private String valor;

    public Transicao() {
    }

    //Cria nova transicao padrao
    public Transicao(int origem, int destino, String valor) {
        this.origem = origem;
        this.destino = destino;
        this.valor = valor;
    }

    //Cria nova transicao a partir da leitura do .jff
    public Transicao(Element transicao) {
        this.origem = Integer.parseInt(transicao.getElementsByTagName("from").item(0).getTextContent());
        this.destino = Integer.parseInt(transicao.getElementsByTagName("to").item(0).getTextContent());
        String value = transicao.getElementsByTagName("read").item(0).getTextContent();
        this.valor = (value != "") ? value : "lambda";
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
