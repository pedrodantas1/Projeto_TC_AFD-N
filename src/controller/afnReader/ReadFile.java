package controller.afnReader;

import java.io.BufferedReader;
import java.io.FileReader;

import controller.AFN_AFD.ConverteAutomato;
import controller.AFN_AFD.StatesException;
import model.Estado;
import model.Transicao;

public class ReadFile {
    private String filePath;
    
    public ReadFile(String filePath) {
        this.filePath = filePath;
    }

    public boolean read(){
        String line;

        try(BufferedReader bf = new BufferedReader(new FileReader(filePath))) {

            //Lê o arquivo até o final do mesmo
            while((line=bf.readLine())!=null){

                //condição para ler os estados
                if (line.contains("state id") && !line.contains("</state>&#13;")) {

                    /*
                        atributos do objeto estado que serão 
                        alterados a medida que o arquivo for lido
                    */
                    int id = 0;
                    String name = "none";
                    String label = "none";
                    Boolean initialState = false;
                    Boolean finalState = false;
                    //-----------------------------

                    //procura pelo id do estado e altera a variavel id
                    if (line.contains("id=")) id = Integer.parseInt(getStateSubString(line, "id="));

                    //procura pelo nome do estado e altera a variavel nome
                    if (line.contains("name=")) name = getStateSubString(line, "name=");

                    //procura pelo label do estado e altera a variavel label
                    if (line.contains("label=")) label = getStateSubString(line, "label=");
        
                    /*
                        Após o programa verificar que existe um estado o loop a seguir
                        vai verificar se esse estado é final/inicial ou as duas condições
                    */
                    while ((line=bf.readLine())!=null && !line.contains("</state>&#13;")) {
                        if(line.contains("<initial/>&#13;")) initialState=true;

                        if(line.contains("<final/>&#13;")) finalState=true;
                    }

                    /*
                        ao encontrar a tag de fechamento o metodo 
                        cria um novo estado e adiciona na lista de estado do automato
                        Posteriormente, se algum valor foi lido incorretamente será tratado
                    */
                    if (line.contains("</state>&#13;")) {
                        Estado estado = new Estado(id,name,label,initialState,finalState);
                        ConverteAutomato.afn.addEstado(estado);
                    }
                }
                
                if(line.contains("<transition>&#13;") && !line.contains("</transition>&#13;")){

                   /*
                        atributos do objeto transição que serão 
                        alterados a medida que o arquivo for lido
                    */
                    Integer from = null;
                    Integer to = null;
                    String read = "lambda";
                    //----------------------------

                    /*
                        outro loop para continuar lendo o arquivo 
                        dentro da condição e salvar os dados na lista statica
                    */
                    while ((line=bf.readLine())!=null && !line.contains("</transition>&#13;")) {

                        //se o arquivo contem a tag from ele chama a função para procurar o valor dentro da string
                        if(line.contains("<from>")) from = Integer.parseInt(getTransitionSubString(line, "<from>"));

                        //se o arquivo contem a tag to ele chama a função para procurar o valor dentro da string
                        if(line.contains("<to>")) to = Integer.parseInt(getTransitionSubString(line, "<to>"));

                        //se o arquivo contem a tag read ele chama a função para procurar o valor dentro da string
                       if(line.contains("<read>")) read = getTransitionSubString(line, "<read>");
                    }

                     /*
                        ao encontrar a tag de fechamento o metodo 
                        cria uma nova transição e adiciona na lista de transições do automato
                        Posteriormente, se algum valor foi lido incorretamente será tratado
                    */
                    if (line.contains("</transition>&#13;")) {

                        Estado de = null, para = null;
                        for (Estado estado : ConverteAutomato.afn.getEstados()) {
                            if(estado.getId()==from) de = estado;

                            if(estado.getId()==to) para = estado;
                        }

                        if (de == null || para == null) throw new StatesException("O Automato está incompleto");
                        
                        Transicao transicao = new Transicao(de,para,read);
                        ConverteAutomato.afn.addTransicao(transicao);
                    }
                }
            }
            return true;

        } catch (Exception e) {
            e.getMessage();
            return false;
        }
    }

    private String getStateSubString(String line,String lookFor) {
        int initial = line.indexOf(lookFor)+lookFor.length();
        Integer end = null;

        for (int i = initial+1; i < line.length(); i++) {
            if (line.charAt(i)=='\"') {
                end = i;
                break;
            }
        }

        //verifica se a busca deu certo, caso contrário ele define que o id possui apenas 1 caractere
        if(end==null) end = initial+2;

        return line.substring(initial+1, end);
    }


    private String getTransitionSubString(String line,String lookFor) {
        int initial = line.indexOf(lookFor) + lookFor.length();
        Integer end = null;

        for (int i = initial+1; i < line.length(); i++) {
            if (line.charAt(i)=='<') {
                end = i;
                break;
            }
        }

        //verifica se a busca deu certo, caso contrário ele define que o id possui apenas 1 caractere
        if(end==null) end = initial+2;
        return line.substring(initial, end);
    }

}
