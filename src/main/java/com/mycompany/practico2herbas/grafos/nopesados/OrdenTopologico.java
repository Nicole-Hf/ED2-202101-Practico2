/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.practico2herbas.grafos.nopesados;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 *
 * @author Nicole
 */
public class OrdenTopologico {    
    protected DiGrafo digrafo;
    protected Queue<Integer> colaDeVertices;
    protected List<Integer> listaConOrdenTopologico;
    
    public OrdenTopologico(DiGrafo unGrafo) {
        this.digrafo = unGrafo;
        this.colaDeVertices = new LinkedList<>();
        this.listaConOrdenTopologico = new LinkedList<>();
        ejecutarOrdenT();
    }

    private void ejecutarOrdenT() {
        //Pre: DiGrafo conexo y sin ciclos
        if (!this.digrafo.hayCiclo() && this.digrafo.esDebilmenteConexo()) {       
            for (int i = 0; i < this.digrafo.listaDeAdyacencias.size(); i++) {
                int gradoDeEntrada = this.digrafo.gradoDeEntradaDelVertice(i);
                if (gradoDeEntrada == 0) {
                    this.colaDeVertices.offer(i);
                }
            }           
            int restar = 0;
            while (!this.colaDeVertices.isEmpty()) {
                int vertice = this.colaDeVertices.poll();
                this.listaConOrdenTopologico.add(vertice);
                restar++;
                meterALaCola(this.digrafo,restar);
            }                          
        } 
    }

    private void meterALaCola(DiGrafo digrafoAux, int restar) {
        for (int i = 0; i < digrafoAux.listaDeAdyacencias.size(); i++) {
            int gradoDeEntrada = digrafoAux.gradoDeEntradaDelVertice(i) - restar;
            if (gradoDeEntrada == 0) {
                this.colaDeVertices.offer(i);
            }
        }
    }
    
    /**
     * 18. Para un grafo dirigido implementar al algoritmo de 
     * ordenamiento topológico. Debe mostrar cual es el orden de 
     * los vértices según este algoritmo.
     */
    public List<Integer> mostrarListaOrdenada() {       
           return this.listaConOrdenTopologico;
    }
    
}
