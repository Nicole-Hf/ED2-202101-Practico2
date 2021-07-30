/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recorridos;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nicole
 */
public class UtilsRecorridos {
    private List<Boolean> marcados;
    
    public UtilsRecorridos(int numVertices) {
        marcados = new ArrayList<>();
        for (int i = 0; i < numVertices; i++) {
            marcados.add(Boolean.FALSE);
        }
    }
    
    public void marcarVertice(int posVertice) {
        //pre : la posicion es valida
        marcados.set(posVertice, Boolean.TRUE);
    }
    
    public boolean estaVerticeMarcado(int posVertice) {
        return marcados.get(posVertice);
    }
    
    public void desmarcarTodos() {
        for (int i = 0; i < marcados.size(); i++) {
            marcados.set(i, Boolean.FALSE);
        }
    }
    
    public boolean estanTodosMarcados() {
        for (Boolean marcado : marcados) {
            if (!marcado) {
                return false;
            }
        }
        return true;
    }
    
}
