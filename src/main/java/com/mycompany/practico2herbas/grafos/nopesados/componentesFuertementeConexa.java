/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.practico2herbas.grafos.nopesados;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author Nicole
 */
public class componentesFuertementeConexa {
    /**
     * 13. Para un grafo dirigido implementar un algoritmo que retorne 
     * cuántas componentes fuertemente conexas hay en dicho grafo. 
     * Definimos formalmente un componente fuertemente conectado, C, 
     * de un grafo G, como el mayor subconjunto de vértices C (que es 
     * un subconjunto de los vértices del grafo G) tal que para cada pareja 
     * de vértices v,w pertenecen a C tenemos una ruta desde v hasta 
     * w y una ruta desde w hasta v.
     */
    private int n; //numero de vertices
    private int preContador; 
    private int[] bajo; //menor numero de v
    private boolean[] visitado; //verificar si v ha sido visitado
    private DiGrafo grafo; //guardar grafo dado
    //guardar todas las componentes
    private List<List<Integer>> sccComp;
    private Stack<Integer> stack;

    public componentesFuertementeConexa() {

    }
    
    public List<List<Integer>> getSSComponentes(DiGrafo grafo){
        this.n = grafo.listaDeAdyacencias.size();
        this.grafo = grafo;
        this.bajo = new int[n];
        this.visitado = new boolean[n];
        this.stack = new Stack<>();
        this.sccComp = new ArrayList<>();
        for (int i = 0; i < this.n; i++) {
            if (!this.visitado[i]) {
                dfs(i);
            }
        }
        return sccComp;
    }

    private void dfs(int i) {
        this.bajo[i] = this.preContador++;
        this.visitado[i] = true;
        this.stack.push(i);
        int min = this.bajo[i];
        Iterable<Integer> adyacentesAlVertice = this.grafo.listaDeAdyacencias.get(i);
        for (Integer posVertice : adyacentesAlVertice) {
            if (!this.visitado[posVertice]) {
                dfs(posVertice);
            }
            if (this.bajo[posVertice] < min) {
                min = this.bajo[posVertice];
            }
        }
        if (min < this.bajo[i]) {
            this.bajo[i] = min;
            return;
        }
        List<Integer> componentes = new ArrayList<>();
        int posVertice;
        do {
            posVertice = this.stack.pop();
            componentes.add(posVertice);
            this.bajo[posVertice] = this.n;
        } while (posVertice != i);
        this.sccComp.add(componentes);                
    }
    
    public int nroDeComponentes(DiGrafo grafo) {
        List<List<Integer>> componentes = this.getSSComponentes(grafo);
        return componentes.size();
    }
   
    public List<List<Integer>> mostrarComponentes() {
        return this.sccComp;
    }
}
