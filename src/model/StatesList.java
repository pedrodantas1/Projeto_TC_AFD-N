package model;

import java.util.ArrayList;

public class StatesList {
    
    private ArrayList<Estado> estados = new ArrayList<Estado>();
    private boolean read;

    public StatesList(ArrayList<Estado> estados, boolean read) {
        this.estados = estados;
        this.read = read;
    }

    public ArrayList<Estado> getEstados() {
        return estados;
    }

    public void setEstados(Estado estados) {
        this.estados.add(estados);
    }

    public boolean isRead() {
        return read;
    }
    
    public void setRead(boolean read) {
        this.read = read;
    }

    public void imprimir() {
        for (Estado estado : estados) {
            System.out.println(estado.toString());
        }
    }

    
}
