package model;
import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Automato {

    private ArrayList<Transicao> transicoes;
    private ArrayList<String> alfabeto;
    private ArrayList<Estado> estados;
    private SortedSet<Integer> idsUsados;
    private int idAtual;

    /**
     * Construtor utilizado pelo grupo de conversão AFN-AFD
     * @param estado
     * inicia o automato com um unico estado, sendo ele o estado inicial contido no Jflap
     * @param transicao
     * primeira transição lida pelo controller que será utilizada posteriormente, 
     * além de adiconar a letra que é lida na lista do alfabeto do automato
     * 
     * a lista de alfabeto é utilizada apenas pelos desenvolvedores
     * para fins de analise de códigos
     */
    public Automato(Estado estado, Transicao transicao){
        this.estados = new ArrayList<Estado>();
        this.alfabeto = new ArrayList<String>();
        this.transicoes = new ArrayList<Transicao>();
        
        this.estados.add(estado);
        this.transicoes.add(transicao);
        if (!this.alfabeto.contains(transicao.getValor()) && !transicao.getValor().equals("lambda")) this.alfabeto.add(transicao.getValor());
        
    }

    //Cria um novo automato padrao
    public Automato() {
        this.estados = new ArrayList<>();
        this.alfabeto = new ArrayList<String>();
        this.transicoes = new ArrayList<Transicao>();
        this.idsUsados = new TreeSet<>();
        this.idAtual = 0;
    }

    //Cria um clone profundo do automato
    public Automato(Automato original) {
        this.estados = new ArrayList<>();
        for (Estado estado : original.getEstados()){
            this.estados.add(new Estado(estado));
        }
        this.idsUsados = new TreeSet<>();
        for (Integer id : original.getIdsUsados()){
            this.idsUsados.add(id);
        }
        this.idAtual = original.getIdAtual();
    }

    //Printar automato completo
    public void mostrarAutomato() {
        System.out.printf("%nAutômato:%n");
        System.out.printf("Estados:%n");
        for (Estado estado : estados){
            estado.mostrarEstado();
        }
    }

    //Carregar todos os estados extraidos do .jff
    public void setEstados(NodeList listaEstados) {
        for (int estado=0; estado<listaEstados.getLength(); estado++){
            Node node = listaEstados.item(estado);
            if (node.getNodeType() == Node.ELEMENT_NODE){
                Element elem = (Element) node;
                this.addEstado(elem);
            }
        }
        idAtual = getMenorId();
    }

    /**
     * Função que adiciona um novo estado na lista de estados do automato.
     * Ao invés de retornar o endereço de um novo estado esse método 
     * adiciona um novo estado no objeto do automato
     * @param estado
     * Novo estado a ser adicionado no automato
     */
    public void addEstado(Estado estado) {
        this.estados.add(estado);  
    }

    /**
     * Função que adiciona uma nova transição na lista de transições do automato.
     * Ao invés de retornar o endereço de uma nova transição esse método 
     * adiciona uma nova transição no objeto do automato
     * @param transicao
     * Nova transição a ser adicionada no automato
     */
    public void addTransicao(Transicao transicao) {
        this.transicoes.add(transicao);
        if (!this.alfabeto.contains(transicao.getValor()) && !transicao.getValor().equals("lambda")) this.alfabeto.add(transicao.getValor());
    }
    
    //Adicionar um estado padrao
    public Estado addEstado() {
        Estado estado = new Estado(idAtual);
        estados.add(estado);
        idsUsados.add(idAtual);
        idAtual = getMenorId();
        return estado;
    }

    //Adicionar um estado que foi extraido do .jff
    public Estado addEstado(Element state) {
        Estado estado = new Estado(state);
        estados.add(estado);
        idsUsados.add(Integer.parseInt(state.getAttribute("id")));
        return estado;
    }

    //Retorna o menor id disponivel para atribuicao a um novo estado
    public int getMenorId() {
        if (idsUsados.size() == 0){
            return 0;
        }
        int menorId = idsUsados.first();
        int maiorId = idsUsados.last();
        if (menorId > 0){
            return 0;
        }
        for (int id=menorId+1; id<maiorId; id++){
            if (!idsUsados.contains(id)){
                return id;
            }
        }
        return maiorId + 1;
    }

    //Retorna a lista de ids usados no automato
    public SortedSet<Integer> getIdsUsados() {
        return idsUsados;
    }

    //Retorna o id atual
    public int getIdAtual() {
        return idAtual;
    }
    
    //Remove um estado do automato atraves do id
    public boolean removeEstado(int id) {
        if (existeEstado(id)){
            return removeEstado(getEstadoPorId(id));
        }
        return false;
    }
    
    //Remove um estado do automato passando o objeto referido
    public boolean removeEstado(Estado estado) {
        if (estados.size() > 0 && estados.remove(estado)){
            //Deve-se remover tambem as transicoes que estao ligadas a este estado
            idsUsados.remove(estado.getId());
            idAtual = getMenorId();
            return true;
        }
        return false;
    }

    //Retorna a lista de todos os estados do automato
    public ArrayList<Estado> getEstados() {
        return estados;
    }

    /**
     * Getter Padrão da lista de transições
     * @return
     * Retorna a lista de transições contidas no automato
     */
    public ArrayList<Transicao> getTransicoes() {
        return transicoes;
    }

    /**
     * Getter Padrão da lista do alfabeto da linguagem
     * @return
     * Retorna todas as "letras" que integram o alfabeto da linguagem
     */
    public ArrayList<String> getAlfabeto() {
        return alfabeto;
    }

    //Retorna um objeto do tipo Estado de acordo com id
    public Estado getEstadoPorId(int id) {
        for (Estado estado : estados){
            if (estado.getId() == id){
                return estado;
            }
        }
        return null;
    }

    //Retorna o estado inicial do automato
    public Estado getEstadoInicial() {
        for (Estado estado : estados){
            if (estado.isInicial()){
                return estado;
            }
        }
        return null;
    }

    //Retorna todos os estados de aceitacao do automato
    public ArrayList<Estado> getEstadosFinais() {
        ArrayList<Estado> estadosFinais = new ArrayList<>();
        for (Estado estado : estados){
            if (estado.isFinal()){
                estadosFinais.add(estado);
            }
        }
        if (estadosFinais.size() > 0){
            return estadosFinais;
        }
        return null;
    }

    //Verifica se existe o estado atraves do id
    public boolean existeEstado(int id) {
        Estado estado = getEstadoPorId(id);
        if (estado != null){
            return true;
        }
        return false;
    }

    //Verifica se existe o estado diretamente com objeto
    public boolean existeEstado(Estado estado) {
        return existeEstado(estado.getId());
    }

    //Carrega as transicoes extraidas do arquivo .jff
    public void loadTransicoes(NodeList listaTransicoes) {
        for (Estado estado : estados){
            estado.setTransicoes(listaTransicoes);
        }
    }

    //Adiciona transicao de forma direta com os valores
    public boolean addTransicaoAoEstado(int id, int destino, String valor) {
        if (existeEstado(id) && existeEstado(destino)){
            return getEstadoPorId(id).addTransicao(destino, valor);
        }
        return false;
    }

    //Adiciona transicao com os objetos necessarios
    public boolean addTransicaoAoEstado(Transicao transicao, Estado estado) {
        return addTransicaoAoEstado(estado.getId(), transicao.getDestino(), 
                                    transicao.getValor());
    }

    @Override
    public String toString() {
        return "Automato [alfabeto=" + alfabeto + ", estados=" + estados + ", transicoes=" + transicoes + "]";
    }

    public boolean isAFN() {
        for (Estado estado : getEstados()){
            ArrayList<Transicao> auxTrans = estado.getTransicoesAceitas();
            if (auxTrans == null){
                continue;
            }
            for (Transicao transicao : auxTrans){
                if (transicao.getValor().equals("lambda")){
                    return true;
                }
                for (Transicao trans : auxTrans){
                    if (!trans.equals(transicao) && transicao.getValor().equals(trans.getValor())){
                        return true;
                    }
                }
            }
        }

        return false;
    }

}