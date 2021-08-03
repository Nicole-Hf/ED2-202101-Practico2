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
public class Lista {
    protected Nodo nodoAdyacentePeso;
    protected int size;
    
    public Lista() {
        this.nodoAdyacentePeso = null;
        this.size = 0;
    }
    
    public void add(int verticeOrigen, int verticeDestino, double peso){ 
        Nodo nodoAnterior = null;
        Nodo nodoActual = nodoAdyacentePeso;
        //si el peso es mayor se inserta 
        while (nodoActual != null && peso >= nodoActual.getPeso()){
            if (verticeOrigen != nodoActual.getVerticeDestino() || verticeDestino != nodoActual.getVerticeOrigen()) {
                nodoAnterior = nodoActual;
                nodoActual = nodoActual.getLink();
            } else {
                return;
            }          
        }       
        Nodo nodoNuevo;
        //peso es menor a todos los que están en la Lista o nodo Adyacente es null
        if (nodoAnterior == null){   
            nodoNuevo = new Nodo(verticeOrigen,verticeDestino,peso);
            nodoNuevo.setLink(nodoAdyacentePeso);
            nodoAdyacentePeso = nodoNuevo;
            size++;
        }else {
            nodoNuevo = new Nodo(verticeOrigen, verticeDestino, peso);
            nodoAnterior.setLink(nodoNuevo);
            nodoNuevo.setLink(nodoActual);
            size++;         
        }
    }
    
    public Nodo getNodoAdyacente(int posicion) {
        Nodo nodoActual = nodoAdyacentePeso;
        int pos = 0;
        while (nodoActual != null) {
            if (pos == posicion) {
                int verticeA = nodoActual.getVerticeOrigen();
                int verticeB = nodoActual.getVerticeDestino();
                double peso = nodoActual.getPeso();
                Nodo nodoResultado = new Nodo(verticeA,verticeB,peso);
                return nodoResultado;
            }
            nodoActual = nodoActual.getLink();
            pos++;
        }
        return null;
    }
    
}
