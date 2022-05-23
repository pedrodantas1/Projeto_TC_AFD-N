package controller.operation;

import model.AutomatoUniao;
import model.EstadoUniao;
import model.TransicaoUniao;
import java.util.ArrayList;
import java.util.List;

import model.Automato;

public class Uniao extends Operacao {

    public Uniao() {
        maxAutomaton = 2;
        qtdAutomaton = 0;
        automatons = new Automato[maxAutomaton];
    }

    public Automato makeOperation(){
        return null;
    }

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
}
