package model;

public class TransicaoUniao {

    private int to;
    private int from;
    private String input;

    public TransicaoUniao(int from, int to, String read) {
        this.from = from;
        this.to = to;
        this.input = read;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

}
