package model;

/**
 *
 * @author Janaina
 */
public class EstadoUniao {

    private String nome;
    private int id;
    private float x;
    private float y;
    private boolean isFinal;
    private boolean isInicial;

    public EstadoUniao(int id, String nome, float x, float y, boolean isFinal, boolean isInicial) {
        this.id = id;
        this.nome = nome;
        this.x = x;
        this.y = y;
        this.isInicial = isInicial;
        this.isFinal = isFinal;
    }

    public boolean isIsFinal() {
        return isFinal;
    }

    public void setIsFinal(boolean isFinal) {
        this.isFinal = isFinal;
    }

    public boolean isIsInicial() {
        return isInicial;
    }

    public void setIsInicial(boolean isInicial) {
        this.isInicial = isInicial;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getX() {
        return this.x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getY() {
        return this.y;
    }

}