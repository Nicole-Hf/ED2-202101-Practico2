/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.practico2herbas.grafos.pesados;

import excepciones.ExcepcionAristaNoExiste;
import excepciones.ExcepcionAristaYaExiste;
import excepciones.ExcepcionNroVerticesInvalido;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Nicole
 */
public class DiGrafoPesado extends GrafoPesado {
    public DiGrafoPesado() {
        super();
    }
    
    public DiGrafoPesado(int nroInicialDeVertices) throws ExcepcionNroVerticesInvalido {
        super(nroInicialDeVertices);
    }

    @Override
    //sumar todos los size de todas las listas existentes
    public int cantidadDeAristas() {
        int cantidad = 0;
        for (int i = 0; i < this.listaDeAdyacencias.size(); i++) {
            List<AdyacenteConPeso> adyacentesVertice = this.listaDeAdyacencias.get(i);
            int cantidadAdyacentes = adyacentesVertice.size();
            cantidad = cantidad + cantidadAdyacentes;
        }
        return cantidad;
    }

    @Override
    public void eliminarArista(int posVerticeOrigen, int posVerticeDestino) throws ExcepcionAristaNoExiste {
        if (!this.existeAdyacencia(posVerticeOrigen, posVerticeDestino)) {
            throw new ExcepcionAristaNoExiste();
        }
        List<AdyacenteConPeso> adyacentesDelOrigen = this.listaDeAdyacencias.get(posVerticeOrigen);
        AdyacenteConPeso adyacenciaAlOrigen = new AdyacenteConPeso(posVerticeDestino, 0);
        int posicionDelDestino = adyacentesDelOrigen.indexOf(adyacenciaAlOrigen);
        adyacentesDelOrigen.remove(posicionDelDestino);       
    }

    @Override
    public void insertarArista(int posVerticeOrigen, int posVerticeDestino, double peso) throws ExcepcionAristaYaExiste {
        if (super.existeAdyacencia(posVerticeOrigen, posVerticeDestino)) {
            throw new ExcepcionAristaYaExiste();
        }
        List<AdyacenteConPeso> adyacentesDelOrigen = this.listaDeAdyacencias.get(posVerticeOrigen);
        AdyacenteConPeso adyacenciaAlOrigen = new AdyacenteConPeso(posVerticeDestino, peso);
        adyacentesDelOrigen.add(adyacenciaAlOrigen);
        Collections.sort(adyacentesDelOrigen);        
    }

    @Override
    public int gradoDeVertice(int posDeVertice) {
        throw new UnsupportedOperationException("Método no soportado en grafos dirigidos");
    }
    
    public int gradoDeEntradaDelVertice(int posDeVertice) {
        super.validarVertice(posDeVertice);
        int entradasDeVertice = 0;
        /*for (List<Integer> adyacentesDeUnVertice : super.listaDeAdyacencias) {
            for (Integer posDeAdyacente : adyacentesDeUnVertice) {
                if (posDeAdyacente == posDeVertice) {
                    entradasDeVertice++;
                }
            }
        }*/
        for (int i = 0; i < super.listaDeAdyacencias.size(); i++) {
            Iterable<Integer> adyacentesDeUnVertice = super.adyacentesDeVertice(i);
            for (Integer posDeAdyacente : adyacentesDeUnVertice) {
                if (posDeAdyacente == posDeVertice) {
                    entradasDeVertice++;
                }
            }
        }
        
        return entradasDeVertice;
    }
    
    public int gradoDeSalidaDelVertice(int posDeVertice) {
        return super.gradoDeVertice(posDeVertice);
    }   
}
