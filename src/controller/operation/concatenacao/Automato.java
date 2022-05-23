/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.operation.concatenacao;

import java.io.IOException;
import java.util.Collection;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author clovijan
 */
public class Automato {
    private Collection<EstadosAutomato> estado;
    private Collection<TransicoesAutomato> transicoes;
        
    public Automato carregarDados(String path) throws ParserConfigurationException, SAXException, IOException{
        
        EstadosAutomato estados = new EstadosAutomato();
        TransicoesAutomato transicoesAut = new TransicoesAutomato();
        this.setEstado(estados.carregaEstados(path));
        this.setTransicoes(transicoesAut.carregaTransicoes(path));
        
        return this;
    }

    /**
     * @return the estado
     */
    public Collection<EstadosAutomato> getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(Collection<EstadosAutomato> estado) {
        this.estado = estado;
    }

    /**
     * @return the transicoes
     */
    public Collection<TransicoesAutomato> getTransicoes() {
        return transicoes;
    }

    /**
     * @param transicoes the transicoes to set
     */
    public void setTransicoes(Collection<TransicoesAutomato> transicoes) {
        this.transicoes = transicoes;
    }
}
