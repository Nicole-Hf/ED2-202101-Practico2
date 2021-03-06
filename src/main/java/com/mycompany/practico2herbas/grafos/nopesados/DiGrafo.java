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
import java.util.List;
import recorridos.BFS;
import recorridos.DFS;

/**
 *
 * @author Nicole
 */
public class DiGrafo extends Grafo {
    protected DFS dfs;
    protected BFS bfs;
    
    public DiGrafo() {
        super();
    }
    
    public DiGrafo(int nroInicialDeVertices) throws ExcepcionNroVerticesInvalido {
        super(nroInicialDeVertices);
    }
    
    @Override
    //sumar todos los size de todas las listas existentes
    public int cantidadDeAristas() {
        int cantidad = 0;
        for (int i = 0; i < this.listaDeAdyacencias.size(); i++) {
            List<Integer> adyacentesVertice = this.listaDeAdyacencias.get(i);
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
        List<Integer> adyacentesDelOrigen = this.listaDeAdyacencias.get(posVerticeOrigen);
        int posicionDelDestino = adyacentesDelOrigen.indexOf(posVerticeDestino);
        adyacentesDelOrigen.remove(posicionDelDestino);
    }
    
    @Override
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

    @Override
    public void insertarArista(int posVerticeOrigen, int posVerticeDestino) throws ExcepcionAristaYaExiste {
        if (super.existeAdyacencia(posVerticeOrigen, posVerticeDestino)) {
            throw new ExcepcionAristaYaExiste();
        }
        List<Integer> adyacentesDelOrigen = this.listaDeAdyacencias.get(posVerticeOrigen);
        adyacentesDelOrigen.add(posVerticeDestino);
        Collections.sort(adyacentesDelOrigen);        
    }

    @Override
    public int gradoDeVertice(int posDeVertice) {
        throw new UnsupportedOperationException("M??todo no soportado en grafos dirigidos");
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
    
    /**
     * 2. Para un grafo dirigido implementar m??todo o clase para 
     * encontrar si hay ciclos sin usar matriz de caminos.
     */
    public boolean hayCiclo() {
        for (int i = 0; i < this.listaDeAdyacencias.size(); i++) {
            Iterable<Integer> adyacenteAlVertice = this.adyacentesDeVertice(i);
            for (Integer posAdyacente : adyacenteAlVertice) {
                if (i > posAdyacente && esAdyacente(i,posAdyacente)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean esAdyacente(int posVertice, int posVerticeAdyacente) {
        Iterable<Integer> adyacenteAlVertice = this.listaDeAdyacencias.get(posVerticeAdyacente);
        for (Integer posAdyacente : adyacenteAlVertice) {
            Iterable<Integer> adyacenteAlVerticeAdyacente = this.listaDeAdyacencias.get(posAdyacente);
            for (Integer posAdyacenteDelAdyacente : adyacenteAlVerticeAdyacente) {
                if (posAdyacenteDelAdyacente == posVertice) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * 3. Para un grafo dirigido implementar un m??todo o clase 
     * que sea capas de retornar los componentes de las islas que 
     * existen en dicho digrafo
     */
    public void componentesDeLasIslas() {
        dfs = new DFS(this);
        int posVerticeNoMarcado = 0;
        dfs.procesarDFS(posVerticeNoMarcado);
        int contador = 1;            
        while (!dfs.controlMarcados.estanTodosMarcados()) {
            int posVerticeConAdyacenteMarcado = verticeConAdyacenteMarcado();
            if (posVerticeConAdyacenteMarcado != -1) {
                dfs.procesarDFS(posVerticeConAdyacenteMarcado);                                
            } else {
                System.out.println("Isla " + contador + "->" + dfs.recorridoDesde(posVerticeNoMarcado));
                contador++;
                posVerticeNoMarcado = dfs.getNoMarcado();
                dfs.procesarDFS(posVerticeNoMarcado);             
            }
        }
        System.out.println("Isla " + contador + "->" + dfs.recorridoDesde(posVerticeNoMarcado));
    }

    private int verticeConAdyacenteMarcado() {
        for (int i = 0; i < this.listaDeAdyacencias.size(); i++) {
            if (!dfs.controlMarcados.estaVerticeMarcado(i)) {
                Iterable<Integer> adyacentesDeUnVertice = super.adyacentesDeVertice(i);
                for (Integer posVerticeAdyacente : adyacentesDeUnVertice) {
                    if (dfs.controlMarcados.estaVerticeMarcado(posVerticeAdyacente)) {
                        return i;
                    }
                }
            }
        }
        //No encontr?? vertices con Adyacentes marcados
        return -1;
    }
    
    /**
     * 4. Para un grafo dirigido implementar un m??todo o clase para 
     * determinar desde que v??rtices se puede llegar a un v??rtice, pero 
     * sin ejecutar m??s de una vez un recorrido.
     */
    public List<Integer> pregunta4(int verticeDestino) {
        List<Integer> caminoAlVertice = new ArrayList<>();
        if (this.gradoDeEntradaDelVertice(verticeDestino) > 0) {
            
        }
        return caminoAlVertice;
    }
    
    /**
     * 5. Para un grafo dirigido solo usando como base la l??gica de un 
     * recorrido (dfs o bfs) encuentre desde que v??rtices se puede llegar 
     * a un v??rtice a, sin importar las veces que ejecute el
     * recorrido elegido.
     */
    public List<Iterable<Integer>> pregunta5(int verticeDestino) {       
        Iterable<Integer> recorrido;
        List<Iterable<Integer>> caminosAlVertice = new ArrayList<>();
        for (int i = 0; i < this.listaDeAdyacencias.size(); i++) {
            dfs = new DFS(this);
            if (i != verticeDestino) {
                dfs.procesarDFS(i);
                if (dfs.controlMarcados.estaVerticeMarcado(verticeDestino)) {
                    recorrido = dfs.elRecorrido();
                    caminosAlVertice.add(recorrido);
                }
            }           
        }
        return caminosAlVertice;
    } 
    
    public void mostrarPregunta5(int verticeDestino) {
        List<Iterable<Integer>> caminosVertice = pregunta5(verticeDestino);
        for (int i = 0; i < caminosVertice.size(); i++) {
            System.out.println("Camino a " + verticeDestino 
                    + " desde " + i + "->" + caminosVertice.get(i));
        }
    }
    
    /**
     * 6. Para un grafo dirigido implementar un algoritmo para 
     * encontrar si el grafo dirigido tiene ciclos
     */
    public boolean tieneCiclos() {
        for (int i = 0; i < this.listaDeAdyacencias.size(); i++) {
            Iterable<Integer> adyacenteAlVertice = this.adyacentesDeVertice(i);
            for (Integer posAdyacente : adyacenteAlVertice) {
                if (i > posAdyacente && esAdyacente(i,posAdyacente)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * 7. Para un grafo dirigido implementar un algoritmo para 
     * encontrar si es d??bilmente conexo
     */
    public boolean esDebilmenteConexo() {
        dfs = new DFS(this);
        dfs.procesarDFS(0);
        while (!dfs.controlMarcados.estanTodosMarcados()) {
            int posVerticeConAdyacenteMarcado = verticeAdyacenteMarcado();
            if (posVerticeConAdyacenteMarcado != -1) {
                dfs.procesarDFS(posVerticeConAdyacenteMarcado);
            } else {
                return false;
            }
        }
        return true;
    }

    private int verticeAdyacenteMarcado() {
        for (int i = 0; i < this.listaDeAdyacencias.size(); i++) {
            if (!dfs.controlMarcados.estaVerticeMarcado(i)) {
                Iterable<Integer> adyacentesDeUnVertice = super.adyacentesDeVertice(i);
                for (Integer posAdyacente : adyacentesDeUnVertice) {
                    if (dfs.controlMarcados.estaVerticeMarcado(posAdyacente)) {
                        return i;
                    }
                }
            }
        }
        //No se encontraron vertices con adyacentes marcados
        return -1;
    }
    
    /**
     * 10. Para un grafo dirigido implementar un algoritmo para 
     * encontrar el n??mero de islas que hay en el grafo
     */
    public int cantidadDeIslas() {
        dfs = new DFS(this);
        dfs.procesarDFS(0);
        int contador = 1;
        while (!dfs.controlMarcados.estanTodosMarcados()) {
            int posVerticeConAdyacenteMarcado = verticeConAdyacenteMarcado();
            if (posVerticeConAdyacenteMarcado != -1) {
                dfs.procesarDFS(posVerticeConAdyacenteMarcado);
            } else {
                contador++;
                //buscar si hay vertices sin marcar
                int posVerticeNoMarcado = dfs.getNoMarcado();
                if (posVerticeNoMarcado != -1)
                    dfs.procesarDFS(posVerticeNoMarcado);
            }
        }
        return contador;
    }
    
}
