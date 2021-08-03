/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.practico2herbas.grafos.pesados;

import excepciones.ExcepcionAristaNoExiste;
import excepciones.ExcepcionAristaYaExiste;
import excepciones.ExcepcionNroVerticesInvalido;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Nicole
 */
public class GrafoPesado {
    public List<List<AdyacenteConPeso>> listaDeAdyacencias;
    
    public GrafoPesado() {
        this.listaDeAdyacencias = new ArrayList<>();
    }
    
    public GrafoPesado(int nroInicialDeVertices) throws ExcepcionNroVerticesInvalido {
        if (nroInicialDeVertices <= 0) {
            throw new ExcepcionNroVerticesInvalido();
        }
        this.listaDeAdyacencias = new ArrayList<>();
        for (int i = 0; i < nroInicialDeVertices; i++) {
            this.insertarVertice();
        }
    }

    public void insertarVertice() {
        List<AdyacenteConPeso> adyacentesDeNuevoVertice = new ArrayList<>();
        this.listaDeAdyacencias.add(adyacentesDeNuevoVertice);        
    }
         
    public int cantidadDeVertices() {
        return listaDeAdyacencias.size();
    }
    
    public int gradoDeVertice(int posDeVertice) {
        validarVertice(posDeVertice);
        List<AdyacenteConPeso> adyacentesDelVertice = this.listaDeAdyacencias.get(posDeVertice);
        return adyacentesDelVertice.size();
    }

    public void validarVertice(int posicionDeVertice) {
        if (posicionDeVertice < 0 || 
                posicionDeVertice >= this.cantidadDeVertices()) {
            throw new IllegalArgumentException("No existe vértice en la posición " 
                    + posicionDeVertice + " en este grafo.");
        }       
    }
    
    public void insertarArista(int posVerticeOrigen, int posVerticeDestino, double peso) throws ExcepcionAristaYaExiste {
        if (this.existeAdyacencia(posVerticeOrigen, posVerticeDestino)) {
            throw new ExcepcionAristaYaExiste();
        }
        List<AdyacenteConPeso> adyacentesDelOrigen = this.listaDeAdyacencias.get(posVerticeOrigen);
        AdyacenteConPeso adyacenciaAlOrigen = new AdyacenteConPeso(posVerticeDestino, peso);
        adyacentesDelOrigen.add(adyacenciaAlOrigen);
        Collections.sort(adyacentesDelOrigen);
        //no dirigido
        if (posVerticeOrigen != posVerticeDestino) {
            List<AdyacenteConPeso> adyacentesDelDestino = this.listaDeAdyacencias.get(posVerticeDestino);
            AdyacenteConPeso adyacenciaAlDestino = new AdyacenteConPeso(posVerticeOrigen, peso);
            adyacentesDelDestino.add(adyacenciaAlDestino);
            Collections.sort(adyacentesDelDestino);
        }
    }
    
    public boolean existeAdyacencia(int posVerticeOrigen, int posVerticeDestino) {
        validarVertice(posVerticeOrigen);
        validarVertice(posVerticeDestino);
        List<AdyacenteConPeso> adyacentesDelOrigen = this.listaDeAdyacencias.get(posVerticeOrigen);
        AdyacenteConPeso adyacenciaAlOrigen = new AdyacenteConPeso(posVerticeDestino);
        return adyacentesDelOrigen.contains(adyacenciaAlOrigen);
    }
    
    public Iterable<Integer> adyacentesDeVertice(int posDeVertice) {
        validarVertice(posDeVertice);
        List<AdyacenteConPeso> adyacentesDelVertice = this.listaDeAdyacencias.get(posDeVertice);
        List<Integer> soloVertices = new ArrayList<>();
        for (AdyacenteConPeso adyacenteConPeso : adyacentesDelVertice) {
            soloVertices.add(adyacenteConPeso.getIndiceVertice());
        }
        Iterable<Integer> iterableDeAdyacentes = soloVertices;
        return iterableDeAdyacentes;
    }
    
    public int cantidadDeAristas() {
        int cantAristas = 0;
        int cantLazos = 0;
        for (int i = 0; i < this.listaDeAdyacencias.size(); i++) {
            List<AdyacenteConPeso> adyacentesDeUnVertice = this.listaDeAdyacencias.get(i);
            for (AdyacenteConPeso posDeAdyacente : adyacentesDeUnVertice) {
                if (i == posDeAdyacente.getIndiceVertice()) {
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
        List<AdyacenteConPeso> adyacentesDelOrigen = this.listaDeAdyacencias.get(posVerticeOrigen);
        AdyacenteConPeso adyacenciaAlOrigen = new AdyacenteConPeso(posVerticeDestino, 0);
        int posicionDelDestino = adyacentesDelOrigen.indexOf(adyacenciaAlOrigen);
        adyacentesDelOrigen.remove(posicionDelDestino);
        if (posVerticeOrigen != posicionDelDestino) {
            List<AdyacenteConPeso> adyacentesDelDestino = this.listaDeAdyacencias.get(posVerticeDestino);
            AdyacenteConPeso adyacenciaAlDestino = new AdyacenteConPeso(posVerticeOrigen, 0);
            int posicionDelOrigen = adyacentesDelDestino.indexOf(adyacenciaAlDestino);
            adyacentesDelDestino.remove(posicionDelOrigen);
        }
    }
    
    public void eliminarVertice(int posDeVertice) throws ExcepcionAristaNoExiste {
        validarVertice(posDeVertice);
        List<AdyacenteConPeso> adyacentesVertice = this.listaDeAdyacencias.get(posDeVertice);
        while (adyacentesVertice.size() > 0) {         
            int primerVerticeAdyacente = adyacentesVertice.get(0).getIndiceVertice();
            this.eliminarArista(posDeVertice, primerVerticeAdyacente);
        }       
        for (int i = 0; i < this.listaDeAdyacencias.size(); i++) {
            List<AdyacenteConPeso> adyacentesAlVertice = this.listaDeAdyacencias.get(i);
            for (AdyacenteConPeso posAdyacente : adyacentesAlVertice) {
                if (posAdyacente.getIndiceVertice() > posDeVertice) {
                    int posicion = adyacentesAlVertice.indexOf(posAdyacente); 
                    int verticeACambiar = adyacentesAlVertice.get(posicion).getIndiceVertice();
                    posAdyacente.setIndiceVertice(verticeACambiar - 1);                   
                }               
            }           
        }
        this.listaDeAdyacencias.remove(posDeVertice);       
    }
    
    public double peso(int posVerticeOrigen, int posVerticeDestino) throws ExcepcionAristaNoExiste {
        validarVertice(posVerticeOrigen);
        validarVertice(posVerticeDestino);
        if (!this.existeAdyacencia(posVerticeOrigen, posVerticeDestino)) {
            throw new ExcepcionAristaNoExiste();
        }
        List<AdyacenteConPeso> adyacentesDelOrigen = this.listaDeAdyacencias.get(posVerticeOrigen);
        AdyacenteConPeso adyacenciaAlOrigen = new AdyacenteConPeso(posVerticeDestino);
        int posicionDeLaAdyacencia = adyacentesDelOrigen.indexOf(adyacenciaAlOrigen);
        return adyacentesDelOrigen.get(posicionDeLaAdyacencia).getPeso();
    }
    
    @Override
    public String toString() {
        if (this.cantidadDeVertices() == 0) {
            return "(Grafo Vacío)";
        }
        //desmarcarTodos();
        String grafo = "";       
        for (int i = 0; i < this.listaDeAdyacencias.size(); i++) {
            grafo = grafo + "[" + i + "]" + "->";
            List<AdyacenteConPeso> adyacentesDeVertice = this.listaDeAdyacencias.get(i);
            for (AdyacenteConPeso posAdyacente : adyacentesDeVertice) {
                grafo += "(" + posAdyacente.getIndiceVertice() + "," + posAdyacente.getPeso() + ")";               
            }
            grafo = grafo + "\n";
        }
        return grafo;
    }
    
}
