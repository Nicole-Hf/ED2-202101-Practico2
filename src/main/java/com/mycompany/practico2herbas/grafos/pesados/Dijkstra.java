/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.practico2herbas.grafos.pesados;

import excepciones.ExcepcionAristaNoExiste;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import recorridos.UtilsRecorridos;

/**
 *
 * @author Nicole
 */
public class Dijkstra {
    protected UtilsRecorridos marcados;
    protected List<Double> costos;
    protected List<Integer> predecesores;
    protected DiGrafoPesado grafo;
    protected int n;
    protected static double INFINITO = 999999999;
    
    public Dijkstra(DiGrafoPesado grafo, int vertice) throws ExcepcionAristaNoExiste {
        this.grafo = grafo;
        this.n = grafo.listaDeAdyacencias.size();
        this.costos = new ArrayList<>();
        this.predecesores = new ArrayList<>();
        this.marcados = new UtilsRecorridos(n);
        for (int i = 0; i < n; i++) {
            this.predecesores.add(-1);
        }
        cargarCostos(vertice);
        ejecutarDijkstra(vertice);
    }
    
    private void cargarCostos(int vertice) {
        for (int i = 0; i < this.n; i++) {
            if (vertice == i) {
                this.costos.add(vertice, 0.0);
            } else {
                this.costos.add(i, INFINITO);
            }
        }
    }
    
    public void ejecutarDijkstra(int vertice) throws ExcepcionAristaNoExiste {                     
        //obtener los adyacentes del vertice 
        List<AdyacenteConPeso> adyacentesDelVertice = this.grafo.listaDeAdyacencias.get(vertice);
        for (AdyacenteConPeso A : adyacentesDelVertice) {
            int posicion = A.getIndiceVertice();             
            if (this.costos.get(posicion) > (this.costos.get(vertice) + this.grafo.peso(vertice, posicion))) {
                this.costos.set(posicion, (this.costos.get(vertice) + this.grafo.peso(vertice, posicion)));
                this.predecesores.set(posicion, vertice);
            }
        }
        int verticeNoMarcadoV = this.verticeNoMarcadoDeMenorCosto();
        if (verticeNoMarcadoV == -1) {
            return;
        }
        this.marcados.marcarVertice(verticeNoMarcadoV);
        ejecutarDijkstra(verticeNoMarcadoV);
    }

    private int verticeNoMarcadoDeMenorCosto() {
        double menorCosto = INFINITO;
        int posicion = -1;
        for (int i = 0; i < this.costos.size(); i++) {
            if (menorCosto > this.costos.get(i) && !this.marcados.estaVerticeMarcado(i)) {
                menorCosto = this.costos.get(i);                
                posicion = i;               
            }
        }
        return posicion;
    }
    
    public double costoMinimoDeAyB(int verticeDestino) {
        double costo = this.costos.get(verticeDestino);
        return costo;
    }
    
    public List<Integer> caminoCostoMinimo(int verticeDestino) {
        Stack<Integer> pilaCamino = new Stack<>();
        List<Integer> caminoMinimo = new ArrayList<>();
        pilaCamino.push(verticeDestino);      
        int valorPosicionVDestino = this.predecesores.get(verticeDestino);
        while (valorPosicionVDestino != -1) {
            pilaCamino.push(valorPosicionVDestino);
            valorPosicionVDestino = this.predecesores.get(valorPosicionVDestino);
        }             
        while (!pilaCamino.isEmpty()) {
            int vertice = pilaCamino.pop();
            caminoMinimo.add(vertice);
        }
        return caminoMinimo;       
    }
    
    /**
     * 14. Para un grafo dirigido pesado implementar el algoritmo de Dijkstra 
     * que muestre cual es el camino de costo mínimo entre un vértice a y b 
     * y cual el costo
     */
    public void mostrarCostoYCamino(int verticePartida, int verticeDestino) throws ExcepcionAristaNoExiste {
        Dijkstra nuevoVertice = new Dijkstra(this.grafo,verticePartida);
        //List<Integer> camino = new ArrayList<>();
        System.out.println("Camino de Costo Mínimo de " + verticePartida + " a " + verticeDestino + " -> " 
                + nuevoVertice.caminoCostoMinimo(verticeDestino));
        System.out.println("Costo Mínimo de " + verticePartida + " a " + verticeDestino + " -> " 
                + nuevoVertice.costoMinimoDeAyB(verticeDestino));
    }
    
    /**
     * 15. Para un grafo dirigido pesado implementar el algoritmo de Dijkstra 
     * que muestre con que vértices hay caminos de costo mínimo partiendo 
     * desde un vértice v, con qué costo y cuáles son los caminos.
     */
    public void mostrarCaminosCostos(int verticeV) throws ExcepcionAristaNoExiste {
        Dijkstra nuevoVertice = new Dijkstra(this.grafo, verticeV);
        //mostrar vertice por los que hay camino
        for (int i = 0; i < n; i++) {
            if (this.costos.get(i) != INFINITO) {
                System.out.println("Vértice " + i 
                        + " | Costo: " + this.costoMinimoDeAyB(i) 
                        + " | Camino: " + this.caminoCostoMinimo(i));
            }
        }
    }
}
