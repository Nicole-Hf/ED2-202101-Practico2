/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kruskal;

import com.mycompany.practico2herbas.grafos.pesados.AdyacenteConPeso;
import com.mycompany.practico2herbas.grafos.pesados.GrafoPesado;
import excepciones.ExcepcionAristaYaExiste;
import excepciones.ExcepcionNroVerticesInvalido;
import java.util.List;
import recorridos.UtilsRecorridos;

/**
 *
 * @author Nicole
 */
public class Kruskal {
    protected Lista listaOrdenada;
    protected GrafoPesado grafo;
    protected GrafoPesado grafoAuxiliar;
    protected UtilsRecorridos visitado;
    
    /**
     * 16. Para un grafo no dirigido pesado implementar el algoritmo de 
     * Kruskal que muestre cual es el grafo encontrado por el algoritmo
     */
    public Kruskal(GrafoPesado grafo) throws ExcepcionNroVerticesInvalido, ExcepcionAristaYaExiste {
        this.grafo = grafo;
        this.grafoAuxiliar = new GrafoPesado(grafo.cantidadDeVertices());
        this.listaOrdenada = new Lista();
        this.visitado = new UtilsRecorridos(grafo.cantidadDeVertices());
        ejecutarKruskal();
    }

    private void ejecutarKruskal() throws ExcepcionAristaYaExiste {
        ordenarListaPorPeso(this.grafo);
        Nodo nodoAdyacente;
        int posicion = 0;
        while (posicion != this.listaOrdenada.size) {
            nodoAdyacente = this.listaOrdenada.getNodoAdyacente(posicion);
            int verticeOrigen = nodoAdyacente.getVerticeOrigen();
            int verticeDestino = nodoAdyacente.getVerticeDestino();
            double peso = nodoAdyacente.getPeso();
            if (!hayCiclo(verticeOrigen,verticeDestino)) {
                this.grafoAuxiliar.insertarArista(verticeOrigen, verticeDestino, peso);
                this.visitado.marcarVertice(verticeDestino);
            }            
            posicion++;
        }
    }

    private void ordenarListaPorPeso(GrafoPesado grafo) {        
        int verticeOrigen, verticeDestino;
        for (int i = 0; i < grafo.cantidadDeVertices(); i++) {
            List<AdyacenteConPeso> adyacentesDelVertice = grafo.listaDeAdyacencias.get(i);
            for (AdyacenteConPeso posAdyacente : adyacentesDelVertice) {
                double peso = posAdyacente.getPeso();               
                    verticeOrigen = i;
                    verticeDestino = posAdyacente.getIndiceVertice();
                    this.listaOrdenada.add(verticeOrigen, verticeDestino, peso);
            }           
        }
    }

    private boolean hayCiclo(int verticeOrigen, int verticeDestino) {
        boolean resultado = false;
        Iterable<Integer> adyacentesDelOrigen = this.grafo.adyacentesDeVertice(verticeOrigen);
        for (Integer posAdyacente : adyacentesDelOrigen) {
            if (this.grafo.existeAdyacencia(verticeDestino, posAdyacente)) {
                return this.grafoAuxiliar.existeAdyacencia(verticeDestino, posAdyacente);
            }
        }
        return resultado;
    }
    
    public String mostrarGrafoKruskal() {
        String grafoResultante = "\n";
        for (int i = 0; i < this.grafoAuxiliar.cantidadDeVertices(); i++) {
            grafoResultante += "[" + i + "]" + "->";
            List<AdyacenteConPeso> adyacentesDelVertice = this.grafoAuxiliar.listaDeAdyacencias.get(i);
            for (AdyacenteConPeso posAdyacente: adyacentesDelVertice) {
                grafoResultante += "(" + posAdyacente.getIndiceVertice() + "," + posAdyacente.getPeso() + ")";
            }
            grafoResultante += "\n";
        }
        return grafoResultante;
    }
}
