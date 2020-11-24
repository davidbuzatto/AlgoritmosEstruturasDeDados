/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aesd.esdc4.algorithms.digraph;

import aesd.esdc4.ds.implementations.working.Digraph;
import java.util.HashMap;
import java.util.Map;

/**
 * Algoritmo de Kosaraju.
 * Algoritmo para contagem de componentes fortes.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class StrongComponents<Tipo> {
    
    private Map<Tipo, Boolean> marcado;
    private Map<Tipo, Integer> id;
    private int cont; // quantidade de componentes
    
    public StrongComponents( Digraph<Tipo> g ) {
        
        marcado = new HashMap<>();
        id = new HashMap<>();
        
        for ( Tipo v : g.getVertices() ) {
            marcado.put( v, false );
            id.put( v, 0 );
        }
        
        // realiza a contagem usando dfs
        for ( Tipo v : g.getVertices() ) {
            if ( !marcado.get( v ) ) {
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
    public boolean conectado( Tipo v, Tipo w ) {
        return id.get( v ) == id.get( w );
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
    public int id( Tipo v ) {
        return id.get( v );
    }

    /*
     * Busca em profundidade para encontrar os componentes.
     */
    private void dfs( Digraph<Tipo> g, Tipo v ) {
        
        marcado.put( v , true );
        
        // todos os vertices descobertos na mesma chamada de dfs têm o mesmo id
        id.put( v , cont );
        
        for ( Tipo w : g.getAdjacentes( v ) ) {
            
            if ( !marcado.get( w ) ) {
                dfs( g, w );
            }
            
        }
        
    }
    
}
