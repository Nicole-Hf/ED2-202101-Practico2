/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recorridos;

import com.mycompany.practico2herbas.grafos.nopesados.Grafo;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nicole
 */
public class DFS {
    public UtilsRecorridos controlMarcados;
    private Grafo grafo;
    private List<Integer> recorrido;
    
    public DFS(Grafo unGrafo) {
        this.grafo = unGrafo;
        recorrido = new ArrayList<>();
        controlMarcados = new UtilsRecorridos(this.grafo.cantidadDeVertices());
    }
    
    public DFS(Grafo unGrafo, int posVerticePartida) {
        this.grafo = unGrafo;
        grafo.validarVertice(posVerticePartida);
        recorrido = new ArrayList<>();
        //ya esta todo desmarcado
        controlMarcados = new UtilsRecorridos(this.grafo.cantidadDeVertices());
        procesarDFS(posVerticePartida);
    }

    public void procesarDFS(int posVertice) {
        controlMarcados.marcarVertice(posVertice);
        recorrido.add(posVertice);
        Iterable<Integer> adyacentesDeVerticeEnTurno = grafo.adyacentesDeVertice(posVertice);
        for (Integer posVerticeAdyacente : adyacentesDeVerticeEnTurno) {
            if (!controlMarcados.estaVerticeMarcado(posVerticeAdyacente)) {
                procesarDFS(posVerticeAdyacente);
            }
        }
    }
    
    public boolean hayCaminoAVertice(int posVertice) {
        grafo.validarVertice(posVertice);
        return controlMarcados.estaVerticeMarcado(posVertice);
    }
    
    public Iterable<Integer> elRecorrido() {
        return this.recorrido;
    }
    
    public boolean hayCaminosATodos() {
        return controlMarcados.estanTodosMarcados();
    }   
    
    public int getNoMarcado() {
        for (int i = 0; i < this.grafo.cantidadDeVertices(); i++) {
            if (!controlMarcados.estaVerticeMarcado(i)) {
                return i;
            }
        }
        return -1;
    }
    
    public Iterable<Integer> recorridoDesde(int vertice) {
        List<Integer> parteDelRecorrido = new ArrayList<>();
        for (int i = 0; i < this.recorrido.size(); i++) {
            int posVertice = posicion(vertice);
            if (i >= posVertice) {
                parteDelRecorrido.add(this.recorrido.get(i));
            }
        }
        return parteDelRecorrido;
    }
    
    public int posicion(int vertice) {
        for (int i = 0; i < this.recorrido.size(); i++) {
            int valorVertice = this.recorrido.get(i);
            if (valorVertice == vertice) {
                return i;
            }
        }
        //el vertice no existe en el recorrido
        return -1;
    }
}
