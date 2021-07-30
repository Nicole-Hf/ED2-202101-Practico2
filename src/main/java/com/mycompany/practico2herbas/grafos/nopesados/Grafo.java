/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.practico2herbas.grafos.nopesados;

import excepciones.ExcepcionAristaNoExiste;
import excepciones.ExcepcionAristaYaExiste;
import excepciones.ExcepcionNroVerticesInvalido;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import recorridos.BFS;
import recorridos.DFS;

/**
 *
 * @author Nicole
 */
public class Grafo {
    protected List<List<Integer>> listaDeAdyacencias;
    protected DFS dfs;
    protected BFS bfs;
    
    public Grafo(){
        this.listaDeAdyacencias = new ArrayList<>();
    }
    
    public Grafo(int nroInicialDeVertices) throws ExcepcionNroVerticesInvalido {
        if (nroInicialDeVertices <= 0) {
            throw new ExcepcionNroVerticesInvalido();
        }
        this.listaDeAdyacencias = new ArrayList<>();
        for (int i = 0; i < nroInicialDeVertices; i++) {
            this.insertarVertice();
        }    
    }
    
    public void insertarVertice() {
        List<Integer> adyacentesDeNuevoVertice = new ArrayList<>();
        this.listaDeAdyacencias.add(adyacentesDeNuevoVertice);
    }
    
    public int cantidadDeVertices() {
        return listaDeAdyacencias.size();
    }
    
    public int gradoDeVertice(int posDeVertice) {
        validarVertice(posDeVertice);
        List<Integer> adyacentesDelVertice = this.listaDeAdyacencias.get(posDeVertice);
        return adyacentesDelVertice.size();
    }

    public void validarVertice(int posicionDeVertice) {
        if (posicionDeVertice < 0 || 
                posicionDeVertice >= this.cantidadDeVertices()) {
            throw new IllegalArgumentException("No existe vértice en la posición " 
                    + posicionDeVertice + " en este grafo.");
        }       
    }
    
    public void insertarArista(int posVerticeOrigen, int posVerticeDestino) throws ExcepcionAristaYaExiste {
        if (this.existeAdyacencia(posVerticeOrigen, posVerticeDestino)) {
            throw new ExcepcionAristaYaExiste();
        }
        List<Integer> adyacentesDelOrigen = this.listaDeAdyacencias.get(posVerticeOrigen);
        adyacentesDelOrigen.add(posVerticeDestino);
        Collections.sort(adyacentesDelOrigen);
        //no dirigido
        if (posVerticeOrigen != posVerticeDestino) {
            List<Integer> adyacentesDelDestino = this.listaDeAdyacencias.get(posVerticeDestino);
            adyacentesDelDestino.add(posVerticeOrigen);
            Collections.sort(adyacentesDelDestino);
        }
    }
    
    public boolean existeAdyacencia(int posVerticeOrigen, int posVerticeDestino) {
        validarVertice(posVerticeOrigen);
        validarVertice(posVerticeDestino);
        List<Integer> adyacentesDelOrigen = this.listaDeAdyacencias.get(posVerticeOrigen);
        return adyacentesDelOrigen.contains(posVerticeDestino);
    }
    
    public Iterable<Integer> adyacentesDeVertice(int posDeVertice) {
        validarVertice(posDeVertice);
        List<Integer> adyacentesDelVertice = this.listaDeAdyacencias.get(posDeVertice);
        Iterable<Integer> iterableDeAdyacentes = adyacentesDelVertice;
        return iterableDeAdyacentes;
    }
    
    public int cantidadDeAristas() {
        int cantAristas = 0;
        int cantLazos = 0;
        for (int i = 0; i < this.listaDeAdyacencias.size(); i++) {
            List<Integer> adyacentesDeUnVertice = this.listaDeAdyacencias.get(i);
            for (Integer posDeAdyacente : adyacentesDeUnVertice) {
                if (i == posDeAdyacente) {
                    cantLazos++;
                } else {
                    cantAristas++;
                }
            }
        }
        return cantLazos + (cantAristas / 2);
    }
    
    public void eliminarArista(int posVerticeOrigen, int posVerticeDestino) throws ExcepcionAristaNoExiste {
        if (!this.existeAdyacencia(posVerticeOrigen, posVerticeDestino)) {
            throw new ExcepcionAristaNoExiste();
        }
        List<Integer> adyacentesDelOrigen = this.listaDeAdyacencias.get(posVerticeOrigen);
        int posicionDelDestino = adyacentesDelOrigen.indexOf(posVerticeDestino);
        adyacentesDelOrigen.remove(posicionDelDestino);
        if (posVerticeOrigen != posicionDelDestino) {
            List<Integer> adyacentesDelDestino = this.listaDeAdyacencias.get(posVerticeDestino);
            int posicionDelOrigen = adyacentesDelDestino.indexOf(posVerticeOrigen);
            adyacentesDelDestino.remove(posicionDelOrigen);
        }
    }
    
    @Override
    public String toString() {
        if (this.cantidadDeVertices() == 0) {
            return "(Grafo Vacío)";
        }
        //desmarcarTodos();
        String grafo = "";       
        for (int i = 0; i < this.listaDeAdyacencias.size(); i++) {
            grafo = grafo + "(" + i + ")" + "->";
            List<Integer> adyacentesDeVertice = this.listaDeAdyacencias.get(i);
            for (Integer posAdyacente : adyacentesDeVertice) {
                grafo += "{" + i + "," + posAdyacente + "}";               
            }
            grafo = grafo + "\n";
        }
        return grafo;
    }
    
    /**
     * 1. Para un grafo no dirigido implementar el método de eliminar vértice.
     */
    public void eliminarVertice(int posDeVertice) throws ExcepcionAristaNoExiste {
        validarVertice(posDeVertice);
        List<Integer> adyacentesVertice = this.listaDeAdyacencias.get(posDeVertice);
        while (adyacentesVertice.size() > 0) {
            int posVerticeDestino = adyacentesVertice.get(0);
            this.eliminarArista(posDeVertice, posVerticeDestino);
        }       
        for (int i = 0; i < this.listaDeAdyacencias.size(); i++) {
            List<Integer> adyacentesAlVertice = this.listaDeAdyacencias.get(i);
            for (Integer posAdyacente : adyacentesAlVertice) {
                if (posAdyacente > posDeVertice) {
                    int posicion = adyacentesAlVertice.indexOf(posAdyacente);
                    adyacentesAlVertice.set(posicion, posAdyacente - 1);
                }               
            }           
        }
        this.listaDeAdyacencias.remove(posDeVertice);       
    }   
    
    /**
     * 8. Para un grafo no dirigido implementar un algoritmo 
     * para encontrar que en que vértices del grafo hay ciclos
     */
    public String pregunta8() throws ExcepcionNroVerticesInvalido, ExcepcionAristaYaExiste {      
        String resultado = "\n";  
        List<Integer> listaVerticesCiclos = verticesConCiclos();       
        for (int i = 0; i < listaVerticesCiclos.size(); i++) {
            int verticeActual = listaVerticesCiclos.get(i);            
            resultado = resultado + "(" + verticeActual + ")" + "->";           
            List<Integer> adyacentesDeVertice = this.listaDeAdyacencias.get(verticeActual);
            for (Integer posAdyacente : adyacentesDeVertice) {
                resultado += posAdyacente + ",";                  
            }
            resultado = resultado + "\n";           
        }
        return resultado;
    }
    
    public List<Integer> verticesConCiclos() throws ExcepcionNroVerticesInvalido, ExcepcionAristaYaExiste {
        Grafo grafoAuxiliar = new Grafo(this.listaDeAdyacencias.size());
        bfs = new BFS(this);
        Queue<Integer> cola = new LinkedList<>();
        List<Integer> verticesConCiclos = new ArrayList<>();
        cola.offer(0);
        bfs.controlMarcados.marcarVertice(0);
        do {
            int posVerticeEnTurno;
            if (!bfs.controlMarcados.estanTodosMarcados() && cola.isEmpty()) {
                posVerticeEnTurno = bfs.getNoMarcado();
                bfs.controlMarcados.marcarVertice(posVerticeEnTurno);
            } else {
                if (!cola.isEmpty())
                    posVerticeEnTurno = cola.poll();
                else
                    return verticesConCiclos;
            }
            Iterable<Integer> adyacentesDeVerticeEnTurno = this.adyacentesDeVertice(posVerticeEnTurno);
            for (Integer posVerticeAdyacente : adyacentesDeVerticeEnTurno) {
                if (!bfs.controlMarcados.estaVerticeMarcado(posVerticeAdyacente)) {
                    cola.offer(posVerticeAdyacente);
                    bfs.controlMarcados.marcarVertice(posVerticeAdyacente);
                    grafoAuxiliar.insertarArista(posVerticeEnTurno, posVerticeAdyacente);
                } else {
                    if (!grafoAuxiliar.existeAdyacencia(posVerticeEnTurno, posVerticeAdyacente)) {
                        verticesConCiclos.add(posVerticeEnTurno);
                        Iterable<Integer> adyacentesVerticeAuxiliar = grafoAuxiliar.adyacentesDeVertice(posVerticeEnTurno);
                        for (Integer posAdyacenteAux : adyacentesVerticeAuxiliar) {
                            if (!verticesConCiclos.contains(posAdyacenteAux)) {
                                verticesConCiclos.add(posAdyacenteAux);
                            }
                        }
                    }
                }
            }
        } while (!cola.isEmpty() || !bfs.controlMarcados.estanTodosMarcados());
        return verticesConCiclos;
    }
    
    /**
     * 9. Para un grafo no dirigido implementar un algoritmo para 
     * encontrar el número de islas que hay en el grafo
     */
    public int nroDeIslas() {
        int contador = 0;
        dfs = new DFS(this);
        for (int i = 0; i < this.listaDeAdyacencias.size(); i++) {
            if (!dfs.controlMarcados.estaVerticeMarcado(i)) {
                dfs.procesarDFS(i);
                contador++;
            }
        }
        return contador;
    }
}
