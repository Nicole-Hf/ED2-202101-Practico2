/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.practico2herbas.grafos.pesados;

import excepciones.ExcepcionNroVerticesInvalido;
import java.util.ArrayList;
import java.util.List;
import recorridos.UtilsRecorridos;

/**
 *
 * @author Nicole
 */
public class Kruskal {
    /**
     * 16. Para un grafo no dirigido pesado implementar el algoritmo de Kruskal 
     * que muestre cual es el grafo encontrado por el algoritmo
     */
    protected GrafoPesado grafo;
    protected GrafoPesado grafoAuxiliar;
    protected List<Integer> listaDeAristas;
    
    public Kruskal(GrafoPesado grafo) throws ExcepcionNroVerticesInvalido {
        this.grafo = grafo;
        this.grafoAuxiliar = new GrafoPesado(grafo.listaDeAdyacencias.size());
        this.listaDeAristas = new ArrayList<>();
        ordenarAristasPorPeso();
        ejecutarKruskal();
    }

    private void ejecutarKruskal() {
        
    }

    private void ordenarAristasPorPeso() {
        UtilsRecorridos visitado = new UtilsRecorridos(this.grafo.cantidadDeVertices());
        while (!visitado.estanTodosMarcados()) {
            int verticePartida = 0;
            int verticeDestino = 0;
            double peso = Double.MAX_VALUE;
            for (int i = 0; i < this.grafo.cantidadDeVertices(); i++) {
                List<AdyacenteConPeso> adyacentesDelVertice = this.grafo.listaDeAdyacencias.get(i);
                for (AdyacenteConPeso posAdyacente : adyacentesDelVertice) {
                    if (!visitado.estaVerticeMarcado(i) || !visitado.estaVerticeMarcado(posAdyacente.getIndiceVertice())) {
                        double pesoAdyacente = posAdyacente.getPeso();
                        if (pesoAdyacente < peso) {
                            peso = pesoAdyacente;
                            verticePartida = i;
                            verticeDestino = posAdyacente.getIndiceVertice();
                        }
                    }                    
                }
            }
            
        }
        
        
    }
   
}
