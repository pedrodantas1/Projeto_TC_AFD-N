import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Automato {
    public ArrayList<Estado> estados;
    public SortedSet<Integer> idsUsados;
    public int idAtual;
    public int xAtual, yAtual;
    public boolean superior;

    public Automato() {
        this.estados = new ArrayList<>();
        this.idsUsados = new TreeSet<>();
        this.idAtual = 0;
        this.xAtual = 100;
        this.yAtual = 125;
        this.superior = true;
    }

    public void mostrarAutomato() {
        System.out.printf("%nAutômato:%n");
        System.out.printf("Estados:%n");
        for (Estado estado : this.estados){
            estado.mostrarEstado();
        }
    }

    public void setEstados(NodeList listaEstados) {
        for (int estado=0; estado<listaEstados.getLength(); estado++){
            Node node = listaEstados.item(estado);
            if (node.getNodeType() == Node.ELEMENT_NODE){
                Element elem = (Element) node;
                this.addEstado(elem);
            }
        }
        this.idAtual = getMenorId();
        this.yAtual = 360;
    }

    //Adicionar um estado padrao
    public Estado addEstado() {
        Estado estado = new Estado(idAtual, xAtual, yAtual);
        this.estados.add(estado);
        idsUsados.add(idAtual);
        this.idAtual = getMenorId();
        setPosXY();
        return estado;
    }

    //Adicionar um estado que foi lido do .jff
    public Estado addEstado(Element state) {
        Estado estado = new Estado(state);
        this.estados.add(estado);
        idsUsados.add(Integer.parseInt(state.getAttribute("id")));
        return estado;
    }

    public void setPosXY() {
        this.superior = !this.superior;
        this.xAtual += 80;
        if (this.superior){
            this.yAtual -= 120;
        }else{
            this.yAtual += 120;
        }
    }

    public int getMenorId() {
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
    
    public boolean removeEstado(Estado estado) {
        if (estados.size() > 0 && this.estados.remove(estado)){
            //Deve-se remover tambem as transicoes que estao ligadas a este estado
            idsUsados.remove(estado.getId());
            this.idAtual = getMenorId();
            return true;
        }
        return false;
    }

    public boolean removeEstado(int id) {
        if (existeEstado(id)){
            return removeEstado(getEstadoPorId(id));
        }
        return false;
    }

    public Estado getEstadoPorId(int id) {
        for (Estado estado : this.estados){
            if (estado.getId() == id){
                return estado;
            }
        }
        return null;
    }

    public Estado getEstadoInicial() {
        for (Estado estado : this.estados){
            if (estado.isInicial()){
                return estado;
            }
        }
        return null;
    }

    public ArrayList<Estado> getEstadosFinais() {
        ArrayList<Estado> estadosFinais = new ArrayList<>();
        for (Estado estado : this.estados){
            if (estado.isFinal()){
                estadosFinais.add(estado);
            }
        }
        if (estadosFinais.size() > 0){
            return estadosFinais;
        }
        return null;
    }

    public boolean existeEstado(Estado estado) {
        Estado estado_ = getEstadoPorId(estado.getId());
        if (estado_ != null){
            return true;
        }
        return false;
    }

    public boolean existeEstado(int id) {
        Estado estado = getEstadoPorId(id);
        if (estado != null){
            return true;
        }
        return false;
    }

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
        if (existeEstado(estado)){
            return getEstadoPorId(estado.getId())
                  .addTransicao(transicao.getDestino(), transicao.getValor());
        }
        return false;
    }

}
