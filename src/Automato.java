import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Automato {
    private ArrayList<Estado> estados;
    private SortedSet<Integer> idsUsados;
    private int idAtual;
    private int xAtual, yAtual;
    private boolean superior;

    //Cria um novo automato padrao
    public Automato() {
        this.estados = new ArrayList<>();
        this.idsUsados = new TreeSet<>();
        this.idAtual = 0;
        this.xAtual = 100;
        this.yAtual = 125;
        this.superior = true;
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
        this.xAtual = original.getXAtual();
        this.yAtual = original.getYAtual();
        this.superior = original.getSuperior();
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
        yAtual = 360;
    }

    //Adicionar um estado padrao
    public Estado addEstado() {
        Estado estado = new Estado(idAtual, xAtual, yAtual);
        estados.add(estado);
        idsUsados.add(idAtual);
        idAtual = getMenorId();
        setPosXY();
        return estado;
    }

    //Adicionar um estado que foi extraido do .jff
    public Estado addEstado(Element state) {
        Estado estado = new Estado(state);
        estados.add(estado);
        idsUsados.add(Integer.parseInt(state.getAttribute("id")));
        return estado;
    }

    //Seta nova posicao x e y para novo estado (paliativo)
    public void setPosXY() {
        superior = !superior;
        xAtual += 80;
        if (superior){
            yAtual -= 120;
        }else{
            yAtual += 120;
        }
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

    //Retorna o xAtual
    public int getXAtual() {
        return xAtual;
    }

    //Retorna o yAtual
    public int getYAtual() {
        return yAtual;
    }

    //Retorna a posicao do proximo estado a ser adicionado (superior)
    public boolean getSuperior() {
        return superior;
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

}