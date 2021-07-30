/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.practico2herbas.grafos.pesados;

import excepciones.ExcepcionAristaNoExiste;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nicole
 */
public class FloydWarshall {
    protected DiGrafoPesado grafoPesado;
    protected int[][] matrizP; //matriz de adyacencia
    protected int n;
    protected int[][] matrizPred; //matriz de predecesores
    protected static int INFINITO = 999999999;
    
    public FloydWarshall(DiGrafoPesado grafo) throws ExcepcionAristaNoExiste {
        this.grafoPesado = grafo;
        this.n = this.grafoPesado.listaDeAdyacencias.size();
        this.matrizP = new int[n][n];
        this.matrizPred = new int[n][n];
        cargarMatrizP();
        cargarMatrizPred();
        ejecutarFloyd();
    }

    private void cargarMatrizP() throws ExcepcionAristaNoExiste {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    this.matrizP[i][j] = 0;
                } else {
                    if (this.grafoPesado.existeAdyacencia(i, j)) {
                        this.matrizP[i][j] = (int) this.grafoPesado.peso(i, j);
                    } else {
                        this.matrizP[i][j] = INFINITO;
                    }
                }
            }
        }
    }

    private void cargarMatrizPred() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.matrizPred[i][j] = -1;
            }
        }
    }
    
    public void ejecutarFloyd() {
        int[][] matrizAdyacencia = this.matrizP;
        //pivote k, vertice
        for (int k = 0; k < n; k++) {
            //recorrer filas
            for (int i = 0; i < n; i++) {
                //recorrer columnas
                for (int j = 0; j < n; j++) {
                    int valor1 = matrizAdyacencia[i][j];
                    int valor2 = matrizAdyacencia[i][k] + matrizAdyacencia[k][j];
                    this.matrizP[i][j] = Math.min(valor1, valor2);
                    if (valor2 < valor1) {
                        this.matrizPred[i][j] = k;
                    }
                }
            }
            matrizAdyacencia = this.matrizP;
        }        
    }
    
    public List<Integer> costosMinimos(int vertice) {
        List<Integer> costosMin = new ArrayList<>();
        //buscar los costos en la fila del vertice
        for (int j = 0; j < n; j++) {
            int costo = this.matrizP[vertice][j];
            if (costo != INFINITO) {
                costosMin.add(costo);
            }
        }
        return costosMin;
    }
    
    public int mostrarCosto(int verticePartida, int verticeDestino) {
        return this.matrizP[verticePartida][verticeDestino];
    }
    
    public List<Integer> mostrarCaminos(int verticePartida, int verticeDestino) {
        List<Integer> camino = new ArrayList<>();
        camino.add(verticePartida);
        encontrarIntermedios(verticePartida,verticeDestino,camino);
        camino.add(verticeDestino);
        return camino;
    }

    private void encontrarIntermedios(int verticePartida, int verticeDestino, List<Integer> camino) {
        int valor = this.matrizPred[verticePartida][verticeDestino];
        if (valor != -1) {
            encontrarIntermedios(verticePartida,valor,camino);
            camino.add(valor);
            encontrarIntermedios(valor,verticeDestino,camino);
        }
    }
    
    /**
     * 12. Para un grafo dirigido usando la implementación del algoritmo de 
     * Floyd-Wharsall encontrar los caminos de costo mínimo entre un vértice a 
     * y el resto. Mostrar los costos y cuáles son los caminos.
     */
    public void mostrarCostosYCaminos(int vertice) {
        for (int j = 0; j < n; j++) {
            if (vertice != j) {
                int costo = this.mostrarCosto(vertice, j);
                if (costo != INFINITO) {
                    System.out.print("Costo Mínimo de " + vertice + " a " + j + " = " + costo);
                    List<Integer> camino = this.mostrarCaminos(vertice, j);
                    System.out.print(" | Camino -> " + camino);
                    System.out.println();
                }              
            }            
        }
        System.out.println();
    }
    
}
