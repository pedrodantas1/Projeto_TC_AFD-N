public class Estado {
    private int id;
    private String nome;
    private String label;
    private double x, y;
    private boolean isInitial, isFinal;

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

    public boolean isInicial() {
        return isInitial;
    }
    
    public void setInicial() {
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