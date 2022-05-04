public class Complemento {

private Automato automato;
    
    public Complemento(Automato automato) {
        this.automato = automato;
    }

    public Automato GetResultadoComp() {

        for (Estado estado : this.automato.getEstados()) {

            //Pega o estado final e transforma em estado normal

            if(estado.isFinal()){

                estado.unsetFinal();

            }

            //Pega o estado que não é final e transforma em final

            else{

                estado.setFinal();

            }
            
        }

        return this.automato;

    }
}