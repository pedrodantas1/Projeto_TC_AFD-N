package model;
import java.util.ArrayList;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Estado {
    private int id;
    private String nome;
    private String label;
    private boolean isInitial, isFinal;
    private ArrayList<Transicao> transicoes;

    public Estado(int id, String name,String label, Boolean initialState, Boolean finalState){
        this.id = id;
        this.nome = name;
        this.label = label;
        this.isInitial = initialState;
        this.isFinal = finalState;
    }

    //Cria um estado padrao
    public Estado(int id) {
        this.id = id;
        this.nome = String.format("q%d", id);
        this.label = null;
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
        this.isInitial = estado.getElementsByTagName("initial").item(0) != null;
        this.isFinal = estado.getElementsByTagName("final").item(0) != null;
        this.transicoes = new ArrayList<>(3);
    }

    //Printar estado completo
    public void mostrarEstado() {
        System.out.printf("Estado %s:%n%n", this.nome);
        System.out.printf("id: %d%n", this.getId());
        System.out.printf("Label: %s%n", this.getLabel());
        System.out.printf("É inicial: %b%n", this.isInicial());
        System.out.printf("É final: %b%n%n", this.isFinal());
        System.out.printf("Transições: %n");
        if (transicoes.size() == 0){
            System.out.printf("Sem transições!%n%n");
            return;
        }
        for (Transicao transicao : transicoes){
            transicao.mostrarTransicao();
        }
    }

    //Retorna id do estado
    public Integer getId() {
        return id;
    }

    /* Nao vou precisar ate o momento
    public void setId(int id) {
        this.id = id;
    }
    */

    //Retorna nome do estado
    public String getName() {
        return nome;
    }

    //Seta o nome do estado
    public void setName(String nome) {
        this.nome = nome;
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
            return transicoes.add(new Transicao(this.id, destino, valor));
        }
        return false;
    }

    //Adicionar uma transicao que foi extraida do .jff
    public boolean addTransicao(Element transition) {
        return transicoes.add(new Transicao(transition));
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
        if (transicoes.size() > 0 && transicoes.remove(transicao)){
            return true;
        }
        return false;
    }
    
    //Retorna um objeto do tipo Transicao atraves dos valores
    public Transicao getTransicao(int destino, String valor) {
        if (transicoes.size() > 0){
            for (Transicao transicao : transicoes){
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
        if (transicoes.size() > 0){
            return transicoes;
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
    //copia as trasiçoes de um estado para o outro, o cont é utulizado somente para copiar as trasiçoes de um segundo automato
    public void copyTransition(ArrayList<Transicao> trans, int cont){
        if (cont == 0){
            this.transicoes = trans;
        }
        else{
            this.transicoes = trans;
            for (Transicao transicao : trans) {
                transicao.setOrigem(transicao.getOrigem()+cont);
                transicao.setDestino(transicao.getDestino()+cont);
            }
        }
            
        
    }

    @Override
    public String toString() {
        return "Estado [finalState=" + isFinal + ", id=" + id + ", initialState=" + isInitial + ", label=" + label
                + ", name=" + nome + "]";
    }
}
