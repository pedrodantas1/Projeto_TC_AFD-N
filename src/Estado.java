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

    //Cria um estado padrao
    public Estado(int id, double x, double y) {
        this.id = id;
        this.nome = String.format("q%d", id);
        this.label = null;
        this.x = x;
        this.y = y;
        this.isInitial = false;
        this.isFinal = false;
        this.transicoes = new ArrayList<>(3);
    }

    //Cria um estado extraido do .jff
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

    //Printar estado completo
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

    //Retorna id do estado
    public int getId() {
        return id;
    }

    /* Nao vou precisar ate o momento
    public void setId(int id) {
        this.id = id;
    }
    */

    //Retorna nome do estado
    public String getNome() {
        return nome;
    }

    //Retorna label do estado
    public String getLabel() {
        return label;
    }

    //Seta label do estado
    public void setLabel(String label) {
        this.label = label;
    }

    //Verifica se este estado eh inicial
    public boolean isInicial() {
        return isInitial;
    }
    
    //Seta este estado como inicial
    public void setInicial() {
        //Se ja houver estado inicial, tira o inicial do outro e coloca nesse
        //(Pensar num metodo geral de verificacao dentro da classe Automato) 
        this.isInitial = true;
    }

    //Unseta estado estado como inicial
    public void unsetInicial() {
        this.isInitial = false;
    }

    //Verifica se este estado eh final
    public boolean isFinal() {
        return isFinal;
    }

    //Seta este estado como final
    public void setFinal() {
        this.isFinal = true;
    }

    //Unseta este estado como final
    public void unsetFinal() {
        this.isFinal = false;
    }

    //Retorna a posicao x deste estado
    public double getPosX() {
        return x;
    }

    //Seta a posicao x deste estado
    public void setPosX(double x) {
        this.x = x;
    }

    //Retorna a posicao y deste estado
    public double getPosY() {
        return y;
    }

    //Seta a posicao y deste estado
    public void setPosY(double y) {
        this.y = y;
    }

    //Carregar todas as transicoes extraidos do .jff
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

    //Adicionar uma transicao que foi extraida do .jff
    public boolean addTransicao(Element transition) {
        return this.transicoes.add(new Transicao(transition));
    }

    //Remover uma transicao do automato atraves dos valores
    public boolean removeTransicao(int destino, String valor) {
        if (existeTransicao(destino, valor)){
            return removeTransicao(getTransicao(destino, valor));
        }
        return false;
    }

    //Remover uma transicao do automato passando o objeto referido
    public boolean removeTransicao(Transicao transicao) {
        if (this.transicoes.size() > 0 && this.transicoes.remove(transicao)){
            return true;
        }
        return false;
    }
    
    //Retorna um objeto do tipo Transicao atraves dos valores
    public Transicao getTransicao(int destino, String valor) {
        if (this.transicoes.size() > 0){
            for (Transicao transicao : this.transicoes){
                if (transicao.getOrigem() == this.id && 
                    transicao.getDestino() == destino && 
                    transicao.getValor() == valor){
                    return transicao;
                }
            }
        }
        return null;
    }

    //Retorna as transicoes aceitas neste estado
    public ArrayList<Transicao> getTransicoesAceitas() {
        if (this.transicoes.size() > 0){
            return this.transicoes;
        }
        return null;
    }

    //Verifica se a transicao existe atraves dos valores
    public boolean existeTransicao(int destino, String valor) {
        Transicao transicao = getTransicao(destino, valor);
        if (transicao != null){
            return true;
        }
        return false;
    }

    //Verifica se existe a transicao diretamente com objeto
    public boolean existeTransicao(Transicao transicao) {
        return existeTransicao(transicao.getDestino(), transicao.getValor());
    }

}