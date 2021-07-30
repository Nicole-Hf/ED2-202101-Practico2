/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.practico2herbas.grafos.pesados;

import excepciones.ExcepcionAristaNoExiste;
import java.util.LinkedList;
import java.util.List;
import recorridos.UtilsRecorridos;

/**
 *
 * @author Nicole
 */
public class FordFulkerson {
    /**
     * 19. Para un grafo dirigido pesado implementar el algoritmo 
     * de Ford-Fulkerson
     */
    protected DiGrafoPesado grafo;
    protected int n;
    
    public FordFulkerson(DiGrafoPesado grafo) {
        this.grafo = grafo;  
        this.n = grafo.cantidadDeVertices();
    }
    
    private int[][] matrizAdyacencia(DiGrafoPesado grafo) throws ExcepcionAristaNoExiste {
        int[][] matrizGrafo = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.grafo.existeAdyacencia(i, j)) {
                    matrizGrafo[i][j] = (int) this.grafo.peso(i, j);
                }
            }
        }
        return matrizGrafo;
    }
    
    public boolean bfs(int[][] grafo, int fuente, int sumidero, int path[]) {
        boolean[] visitado = new boolean[n];
        for (int i = 0; i < n; i++) {
            visitado[i] = false;
        }         
        LinkedList<Integer> cola = new LinkedList<>();
        cola.add(fuente);
        visitado[fuente] = true;
        path[fuente] = -1;
        while (!cola.isEmpty()) {
            int verticeU = cola.poll();
            for (int verticeV = 0; verticeV < n; verticeV++) {               
                if (visitado[verticeV] == false && grafo[verticeU][verticeV] > 0) {
                    cola.add(verticeV);
                    path[verticeV] = verticeU;
                    visitado[verticeV] = true;
                }
            }           
        }
        return visitado[sumidero];
    }
    
    public int fordFulkerson(DiGrafoPesado grafo, int fuente, int sumidero) throws ExcepcionAristaNoExiste {
        int[][] grafoAuxiliar = matrizAdyacencia(grafo);
        int verticeU, verticeV;
        int path[] = new int[grafo.cantidadDeVertices()];
        int maximoFlujo = 0;
        while (bfs(grafoAuxiliar,fuente,sumidero,path)) {
            double flujoCamino = Integer.MAX_VALUE;
            for (verticeV = sumidero; verticeV != fuente; verticeV = path[verticeV]) {
                verticeU = path[verticeV];
                flujoCamino = Math.min(flujoCamino, grafoAuxiliar[verticeU][verticeV]);
            }
            
            for (verticeV = sumidero; verticeV != fuente; verticeV = path[verticeV]) {
                verticeU = path[verticeV];      
                grafoAuxiliar[verticeU][verticeV] -= flujoCamino;
                grafoAuxiliar[verticeV][verticeU] += flujoCamino;
            }
            maximoFlujo += flujoCamino;
        }
        return maximoFlujo;
    }
}
