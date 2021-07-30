/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.practico2herbas.grafos.nopesados;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nicole
 */

public class AlgoritmoWarshall {
    /**
     * 11. Para un grafo dirigido implementar el algoritmo de Wharsall, 
     * que luego muestre entre que vértices hay camino
     */
    protected DiGrafo digrafo;
    protected int[][] matrizWarshall;
    protected int n;
    protected List<List<Integer>> listaDeCaminos;
    
    public AlgoritmoWarshall(int[][] unaMatriz) {
        this.n = unaMatriz.length;
        this.matrizWarshall = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (unaMatriz[i][j] == 1) {
                    this.matrizWarshall[i][j] = 1;
                }else {
                    this.matrizWarshall[i][j] = 0;
                }
            }
        }
        ejecutarWarshall();
    }
    
    public AlgoritmoWarshall(DiGrafo unGrafo) {
        this.digrafo = unGrafo;
        this.n = this.digrafo.listaDeAdyacencias.size();
        this.listaDeCaminos = new ArrayList<>();
        this.matrizWarshall = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.digrafo.existeAdyacencia(i, j)) {
                    this.matrizWarshall[i][j] = 1;
                } else {
                    this.matrizWarshall[i][j] = 0;
                }
            }
        }
        ejecutarWarshall();
        caminoAlVertice();
    }

    private void ejecutarWarshall() {
        //k recorre los vertices
        for (int k = 0; k < n; k++) {
            //i recorre las filas
            for (int i = 0; i < n; i++) {
                //j recorre las colulmnas
                for (int j = 0; j < n; j++) {
                    int posicionIJ = this.matrizWarshall[i][j];
                    int posicionIK = this.matrizWarshall[i][k];
                    int posicionKJ = this.matrizWarshall[k][j];
                    this.matrizWarshall[i][j] = posicionIJ | (posicionIK & posicionKJ);                   
                }
            }
        }
    }
    
    public void caminoAlVertice() {            
        for (int i = 0; i < n; i++) {
            List<Integer> caminosDelVertice = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                int valor = this.matrizWarshall[i][j];
                if (valor == 1) {
                    caminosDelVertice.add(j);
                }
            }
            this.listaDeCaminos.add(caminosDelVertice);
        }
    }

    @Override
    public String toString() {
        if (this.listaDeCaminos.isEmpty()) {
            return "No hay camino";
        }
        String caminos = "";
        for (int i = 0; i < this.listaDeCaminos.size(); i++) {
            caminos += "Camino al vertice " + i + "->";
            List<Integer> caminosAlVertice = this.listaDeCaminos.get(i);
            for (int j = 0; j < caminosAlVertice.size(); j++) {
                caminos += caminosAlVertice.get(j) + ",";
            }
            caminos += "\n";
        }
        return caminos;
    }      
}
