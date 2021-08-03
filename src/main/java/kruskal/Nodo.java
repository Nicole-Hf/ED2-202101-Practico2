/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kruskal;

/**
 *
 * @author Nicole
 */
public class Nodo {
    protected int verticeOrigen;
    protected int verticeDestino;
    protected double peso;
    protected Nodo link;

    public Nodo() {
    }
    
    public Nodo(int verticeOrigen, int verticeDestino, double peso) {
        this.verticeOrigen = verticeOrigen;
        this.verticeDestino = verticeDestino;
        this.peso = peso;
        this.link = null;
    }

    public int getVerticeOrigen() {
        return verticeOrigen;
    }

    public void setVerticeOrigen(int verticeOrigen) {
        this.verticeOrigen = verticeOrigen;
    }

    public int getVerticeDestino() {
        return verticeDestino;
    }

    public void setVerticeDestino(int verticeDestino) {
        this.verticeDestino = verticeDestino;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public Nodo getLink() {
        return link;
    }

    public void setLink(Nodo link) {
        this.link = link;
    }  
}
