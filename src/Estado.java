public class Estado {
    private int id;
    private String nome;
    private String label;
    private double x, y;
    private boolean isInitial, isFinal;

    public Estado() {
    }

    public Estado(int id, double x, double y) {
        this.id = id;
        //nomear de acordo com o id (q0, q1, etc)
        this.nome = String.format("q%d", id);
        this.label = null;
        this.x = x;
        this.y = y;
        this.isInitial = false;
        this.isFinal = false;
    }

    public void mostrarEstado() {
        System.out.printf("Estado %s:%n%n", this.nome);
        System.out.printf("id: %d%n", this.getId());
        System.out.printf("x: %.1f - y: %.1f%n", this.getPosX(), this.getPosY());
        System.out.printf("É inicial: %b%n", this.isInicial());
        System.out.printf("É final: %b%n%n", this.isFinal());
    }

    public int getId() {
        return id;
    }

    /* Nao vou precisar ate o momento
    public void setId(int id) {
        this.id = id;
    }
    */

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

}