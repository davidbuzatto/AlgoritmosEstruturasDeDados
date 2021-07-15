/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.algorithms.digraph;

import aesd.ds.implementations.linear.ResizingArrayStack;
import aesd.ds.implementations.nonlinear.graph.Digraph;
import aesd.ds.interfaces.Stack;

/**
 * Calcula os componentes fortes do digrafo (componentes fortemente conexos).
 * 
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 4. ed.
 * Boston: Pearson Education, 2011. 955 p.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class TarjanSCC {

    // marked[v] = o vértice v foi visitado?
    private boolean[] marked;
    
    // id[v] = identificador do componente forte que contém v
    private int[] id;
    
    // low[v] = número baixo de v
    private int[] low;
    
    // contador da número da pré-ordem
    private int pre;
    
    // quantidade dos componentes fortemente conexos do grafo processado
    private int count;
    
    private Stack<Integer> stack;

    /**
     * Calcula os componentes fortes do digrafo (componentes fortemente conexos).
     *
     * @param digraph o digrafo
     */
    public TarjanSCC( Digraph digraph ) {
        
        marked = new boolean[digraph.getNumberOfVertices()];
        stack = new ResizingArrayStack<>();
        id = new int[digraph.getNumberOfVertices()];
        low = new int[digraph.getNumberOfVertices()];
        
        for ( int v = 0; v < digraph.getNumberOfVertices(); v++ ) {
            if ( !marked[v] ) {
                dfs( digraph, v );
            }
        }
        
    }

    // dfs no digrafo
    private void dfs( Digraph digraph, int v ) {
        
        marked[v] = true;
        low[v] = pre++;
        
        int min = low[v];
        
        stack.push( v );
        
        for ( int w : digraph.adj( v ) ) {
            if ( !marked[w] ) {
                dfs( digraph, w );
            }
            if ( low[w] < min ) {
                min = low[w];
            }
        }
        
        if ( min < low[v] ) {
            low[v] = min;
            return;
        }
        
        int w;
        do {
            w = stack.pop();
            id[w] = count;
            low[w] = digraph.getNumberOfVertices();
        } while ( w != v );
        
        count++;
        
    }

    /**
     * Retorna a quantidade de componentes fortes.
     *
     * @return a quantidade de componentes fortes
     */
    public int count() {
        return count;
    }

    /**
     * Os vértices v e w estão no mesmo componente forte?
     *
     * @param v um vértice
     * @param w outro vértice
     * @return verdadeiro se v e w estiverem no mesmo componente forte,
     * falso caso contrário
     * @throws IllegalArgumentException se o vértice v ou o vértice w forem
     * inválidos
     */
    public boolean stronglyConnected( int v, int w ) throws IllegalArgumentException {
        validateVertex( v );
        validateVertex( w );
        return id[v] == id[w];
    }

    /**
     * Retorna o identificador do componente forte que contém o vértice v.
     *
     * @param v o vértice
     * @return o identificador do componente forte que contém o vértice v
     * @throws IllegalArgumentException se o vértice for inválido
     */
    public int id( int v ) throws IllegalArgumentException {
        validateVertex( v );
        return id[v];
    }

    private void validateVertex( int v ) throws IllegalArgumentException {
        int length = marked.length;
        if ( v < 0 || v >= length ) {
            throw new IllegalArgumentException( "vertex " + v + " is not between 0 and " + ( length - 1 ) );
        }
    }

}
