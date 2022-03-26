import java.util.ArrayList;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Estado {
    private int id;
    private String nome;
    private String label;
    private double x, y;
    private boolean isInitial, isFinal;
    private ArrayList<Transicao> transicoes;

    public Estado() {
    }

    //Cria novo estado padrao
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

    //Cria novo estado a partir da leitura do .jff
    public Estado(Element estado) {
        this.id = Integer.parseInt(estado.getAttribute("id"));
        this.nome = estado.getAttribute("name");
        Node label = estado.getElementsByTagName("label").item(0);
        this.label = (label != null) ? label.getTextContent() : null;
        this.x = Double.parseDouble(estado.getElementsByTagName("x").item(0).getTextContent());
        this.y = Double.parseDouble(estado.getElementsByTagName("y").item(0).getTextContent());
        this.isInitial = estado.getElementsByTagName("initial").item(0) != null;
        this.isFinal = estado.getElementsByTagName("final").item(0) != null;
        this.transicoes = new ArrayList<>(3);
    }

    public void mostrarEstado() {
        System.out.printf("Estado %s:%n%n", this.nome);
        System.out.printf("id: %d%n", this.getId());
        System.out.printf("Label: %s%n", this.getLabel());
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

    public String getNome() {
        return nome;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

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

    public void setTransicoes(NodeList listaTransicoes) {
        for (int transicao=0; transicao<listaTransicoes.getLength(); transicao++){
            Node node = listaTransicoes.item(transicao);
            if (node.getNodeType() == Node.ELEMENT_NODE){
                Element elem = (Element) node;
                if (this.id == Integer.parseInt(elem.getElementsByTagName("from").item(0).getTextContent())){
                    this.addTransicao(elem);
                }
            }
        }
    }

    //Adicionar transicao diretamente por parametros
    public boolean addTransicao(int destino, String valor) {
        if (!existeTransicao(destino, valor)){
            return this.transicoes.add(new Transicao(this.id, destino, valor));
        }
        return false;
    }

    //Adicionar transicao atraves da leitura do .jff
    public boolean addTransicao(Element transition) {
        return this.transicoes.add(new Transicao(transition));
    }

    //Remover transicao atraves dos valores
    public boolean removeTransicao(int destino, String valor) {
        if (existeTransicao(destino, valor)){
            return removeTransicao(getTransicao(destino, valor));
        }
        return false;
    }

    //Remover transicao atraves do objeto
    public boolean removeTransicao(Transicao transicao) {
        if (this.transicoes.size() > 0 && this.transicoes.remove(transicao)){
            return true;
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
        if (this.transicoes.size() > 0){
            return this.transicoes;
        }
        return null;
    }

    public boolean existeTransicao(int destino, String valor) {
        Transicao transicao = getTransicao(destino, valor);
        if (transicao != null){
            return true;
        }
        return false;
    }

}