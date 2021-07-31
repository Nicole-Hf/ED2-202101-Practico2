/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.practico2herbas.grafos.pesados;

import excepciones.ExcepcionAristaYaExiste;
import excepciones.ExcepcionNroVerticesInvalido;
import java.util.ArrayList;
import java.util.List;
import recorridos.UtilsRecorridos;

/**
 *
 * @author Nicole
 */
public class Prim {
    /**
     * 17. Para un grafo no dirigido pesado implementar el algoritmo de Prim 
     * que muestre cual es el grafo encontrado por el algoritmo
     */
    protected GrafoPesado grafo, grafoW;
    protected UtilsRecorridos marcados;
    
    public Prim(GrafoPesado grafo, int verticeInicial) throws ExcepcionNroVerticesInvalido, ExcepcionAristaYaExiste {
        this.grafo = grafo;
        this.grafoW = new GrafoPesado(grafo.cantidadDeVertices());
        this.marcados = new UtilsRecorridos(grafo.listaDeAdyacencias.size());
        ejecutarPrim(verticeInicial);
    }

    private void ejecutarPrim(int verticeInicial) throws ExcepcionAristaYaExiste {       
        List<Integer> verticesGrafoW = new ArrayList<>();       
        int verticeMenorPeso = verticeInicial;
        this.marcados.marcarVertice(verticeInicial);
        while (!this.marcados.estanTodosMarcados()) {
            double menorPeso = Double.MAX_VALUE;
            int verticeZ = 0;
            verticesGrafoW.add(verticeMenorPeso);            
            //obtener los adyacentes de los vertices de la lista
            for (int i = 0; i < verticesGrafoW.size(); i++) {
                int verticeU = verticesGrafoW.get(i);
                List<AdyacenteConPeso> adyacentesDelVertice = this.grafo.listaDeAdyacencias.get(verticeU);
                //buscar la arista con menor costo
                for (AdyacenteConPeso posVertice : adyacentesDelVertice) {
                    double peso = posVertice.getPeso();
                    if (!this.marcados.estaVerticeMarcado(posVertice.getIndiceVertice()) 
                        && !this.grafoW.existeAdyacencia(verticeU, posVertice.getIndiceVertice())) {
                        if (peso < menorPeso) {
                            menorPeso = peso;
                            verticeZ = posVertice.getIndiceVertice();
                            verticeMenorPeso = verticeU;
                        }
                    }
                }
            }
            //añadir arista con menor costo al grafoW        
            this.grafoW.insertarArista(verticeMenorPeso, verticeZ, menorPeso); 
            verticeMenorPeso = verticeZ;
            this.marcados.marcarVertice(verticeMenorPeso);
        }                            
    }
    
    public String mostrarGrafoPrim() {
        String grafoResultado = "\n";
        for (int i = 0; i < this.grafoW.cantidadDeVertices(); i++) {
            grafoResultado = grafoResultado + "[" + i + "]" + "->";
            List<AdyacenteConPeso> adyacentesDeVertice = this.grafoW.listaDeAdyacencias.get(i);
            for (AdyacenteConPeso posAdyacente : adyacentesDeVertice) {
                grafoResultado += "(" + posAdyacente.getIndiceVertice() + "," + posAdyacente.getPeso() + ")";               
            }
            grafoResultado = grafoResultado + "\n";
        }
        return grafoResultado;
    }
    
    
}
