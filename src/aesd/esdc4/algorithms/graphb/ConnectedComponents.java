/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aesd.esdc4.algorithms.graphb;

import aesd.esdc4.ds.implementations.working.BasicGraph;

/**
 * Algoritmo para contagem de componentes conexos.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class ConnectedComponents {
    
    private boolean[] marcado;
    private int[] id; // identificadores dos componentes
    private int cont; // quantidade de componentes
    
    public ConnectedComponents( BasicGraph g ) {
        
        marcado = new boolean[g.v()];
        id = new int[g.v()];
        
        // realiza a contagem usando dfs
        for ( int v = 0; v < g.v(); v++ ) {
            if ( !marcado[v] ) {
                dfs( g, v );
                cont++;
            }
        }
        
    }

    /**
     * Verifica se dois vértices estão conectados.
     * 
     * @param v Um dos vértices
     * @param w Outro vértice
     * @return Se os dois vértices estão conectados.
     */
    public boolean conectado( int v, int w ) {
        return id[v] == id[w];
    }
    
    /**
     * Retorna a getQuantidade de componentes conexos do grafo.
     * 
     * @return Quantidade de componentes conexos.
     */
    public int getQuantidade() {
        return cont;
    }
    
    /**
     * Obtém o identificador do componente de um vértice
     * 
     * @param v vértice que se deseja descobrir o componente.
     * @return O identificador do componente do vértice desejado.
     */
    public int id( int v ) {
        return id[v];
    }

    /*
     * Busca em profundidade para encontrar os componentes.
     */
    private void dfs( BasicGraph g, int v ) {
        
        marcado[v] = true;
        
        // todos os vertices descobertos na mesma chamada de dfs têm o mesmo id
        id[v] = cont;
        
        for ( int w : g.adj( v ) ) {
            if ( !marcado[w] ) {
                dfs( g, w );
            }
        }
        
    }
    
}
