package controller.operation;

import model.AutomatoUniao;
import model.EstadoUniao;
import model.TransicaoUniao;
import java.util.ArrayList;
import java.util.List;

public class Uniao {
    public AutomatoUniao unir(AutomatoUniao a1, AutomatoUniao a2){
        List<EstadoUniao> EstadosUniao = new ArrayList<>();
        List<TransicaoUniao> transicaoUniao = new ArrayList<>();
        
        int maiorID = 0;
        
        for(int i=0; i<a1.getEstados().size(); i++){
            //adiciona os Estados de a1 a lista de Estados do novo automato
            EstadosUniao.add(a1.getEstados().get(i));
            //pega o maior id do automato 1 para os ids do automato 2 começar a partir dele
            if(a1.getEstados().get(i).getId() > maiorID)
                maiorID = a1.getEstados().get(i).getId();
        }
        //System.out.println("Maior id:" + maiorID);
        //altera os ids do automato 2
        for(int i=0; i<a2.getEstados().size(); i++){
            a2.getEstados().get(i).setId(a2.getEstados().get(i).getId()+maiorID+1);  //Mais 1 pq o próximo começa com 0
            //altera o nome do estado
            String nome= "a"+i;
            a2.getEstados().get(i).setNome(nome);
            //adiciona os Estados de a2 a lista de Estados do novo automato
            EstadosUniao.add(a2.getEstados().get(i));
        }
        //adiciona as transições de a1 a lista de transições do novo automato
        for(int j=0; j<a1.getTransicoes().size(); j++){
            transicaoUniao.add(a1.getTransicoes().get(j));
        }
        //altera o id das transições do automato 2
        for(int j=0; j<a2.getTransicoes().size(); j++){
            a2.getTransicoes().get(j).setFrom(a2.getTransicoes().get(j).getFrom()+maiorID+1);
            a2.getTransicoes().get(j).setTo(a2.getTransicoes().get(j).getTo()+maiorID+1);
            //adiciona as transições de a2 a lista de transições do novo automato
            transicaoUniao.add(a2.getTransicoes().get(j));
        }
        
        //Verifica qual maior id do novo automato
        maiorID = 0;
        for(int j=0; j<EstadosUniao.size(); j++){
            if(EstadosUniao.get(j).getId() > maiorID)
                maiorID = EstadosUniao.get(j).getId();
        }
        
        //Cria o novo EstadoUniao inicial
        EstadoUniao EstadoUniaoInicial = new EstadoUniao(maiorID+1, "iniUniao", 0, 0, false, true);
        
        EstadosUniao.add(EstadoUniaoInicial);
        //Pegamos até o tamanho-1 para não incluir o EstadoUniao inicial que acabamos de adicionar
        for(int j=0; j<EstadosUniao.size()-1; j++){
            if(EstadosUniao.get(j).isIsInicial()){
                EstadosUniao.get(j).setIsInicial(false);
                TransicaoUniao t1 = new TransicaoUniao(maiorID+1, EstadosUniao.get(j).getId(), "");
                transicaoUniao.add(t1);
            }
        }
        
        AutomatoUniao autUniao = new AutomatoUniao(EstadosUniao, transicaoUniao);
        
        return autUniao;
    }

    public AutomatoUniao unirAFD(AutomatoUniao a1, AutomatoUniao a2){

        List<EstadoUniao> estados = new ArrayList<>();
        List<TransicaoUniao> transicoes = new ArrayList<>();
        List<String> alfabeto = new ArrayList<>();
        int id=0, idTrans;    
        String nome;

        //Pegando o alfabeto
        for(int i=0; i<a1.getTransicoes().size(); i++){
            if(!alfabeto.contains(a1.getTransicoes().get(i).getInput())){
                alfabeto.add(a1.getTransicoes().get(i).getInput());
                System.out.println(a1.getTransicoes().get(i).getInput());
            }

        }
        //Cria os estados do novo automato combinando os estados dos automatos originais
        for(int i=0; i<a1.getEstados().size(); i++){
            for(int j=0; j<a2.getEstados().size(); j++){
                EstadoUniao e = new EstadoUniao(id, a1.getEstados().get(i).getNome()+a2.getEstados().get(j).getNome(), a1.getEstados().get(i).getX(), a1.getEstados().get(i).getY(), false, false);
                //Verifica se os dois estados são inciais
                if(a1.getEstados().get(i).isIsInicial()){
                    if(a2.getEstados().get(j).isIsInicial()){
                        e.setIsInicial(true);
                    }
                }
                //Verifica se um dos dois estados é final
                if((a1.getEstados().get(i).isIsFinal()) || (a2.getEstados().get(j).isIsFinal())){
                    e.setIsFinal(true);
                }
                estados.add(e);
                id++;
            }
        }

        //A partir de dois estados busca as suas duas transições
        id=0;
        for (int i=0; i<a1.getEstados().size(); i++) {
            for (int j=0; j<a2.getEstados().size(); j++) {
                for(int x=0; x<alfabeto.size(); x++){
                    idTrans = buscarTrans(a1, a1.getEstados().get(i).getId(), alfabeto.get(x));
                    nome = buscarNome(a1, idTrans);
                    idTrans = buscarTrans(a2, a2.getEstados().get(j).getId(), alfabeto.get(x));
                    nome = nome + buscarNome(a2, idTrans);
                    TransicaoUniao t = new TransicaoUniao(id, buscarID(estados, nome), alfabeto.get(x));
                    transicoes.add(t);
                }
                id++;
                
                
            }
        }

        AutomatoUniao aUniao = new AutomatoUniao(estados, transicoes);
        return aUniao;
    }

    //Busca o id da estado destino
    public int buscarTrans(AutomatoUniao a, int id, String read){
        for(int i=0; i<a.getTransicoes().size(); i++){
            //Verifica se é o id e o simbolo que estamos procurando e retorna o id do destino
            if((a.getTransicoes().get(i).getFrom() == id) && (a.getTransicoes().get(i).getInput()).equals(read)){
                return a.getTransicoes().get(i).getTo();
            }
        }
        return 0;
    }

    //Recebe o id de um estado, se encontrar na lista, retorna o nome do estado
    public String buscarNome(AutomatoUniao a, int id){
        for(int i=0; i<a.getEstados().size(); i++){
            if(id == a.getEstados().get(i).getId()){
                return a.getEstados().get(i).getNome();
            }
        }
        return "";
    }

    //Recebe o nome de um estado, se encontrar na lista, retorna o id do estado
    public int buscarID(List<EstadoUniao> a, String nome){
        for(int i=0; i<a.size(); i++){
            if(nome.equals(a.get(i).getNome())){
                return a.get(i).getId();
            }
        }
        return 0;
    }

}
