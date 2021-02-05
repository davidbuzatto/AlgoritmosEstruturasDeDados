/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdi3.algorithms.digraph;

import aesd.esdi3.ds.implementations.nonlinear.graph.Digraph;

/**
 * Calcula os componentes fortes do digrafo (componentes fortemente conexos).
 * 
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 4. ed.
 * Boston: Pearson Education, 2011. 955 p.
 *
 * @author Prof. Dr. David Buzatto
 */
public class KosarajuSharirSCC {

    // marked[v] = o vértice v foi visitado?
    private boolean[] marked;
    
    // id[v] = identificador do componente forte que contém v
    private int[] id;
    
    // quantidade dos componentes fortemente conexos do grafo processado
    private int count;

    /**
     * Calcula os componentes fortes do digrafo (componentes fortemente conexos).
     *
     * @param digraph o digrafo
     */
    public KosarajuSharirSCC( Digraph digraph ) {

        // computa a pós-ordem reversa do reverso do grafo
        DepthFirstOrder dfs = new DepthFirstOrder( digraph.reverse() );

        // executa a DFS no digrafo, usando a pós-ordeme reversa para guiar o
        // cálculo
        marked = new boolean[digraph.getNumberOfVertices()];
        id = new int[digraph.getNumberOfVertices()];
        
        for ( int v : dfs.reversePost() ) {
            if ( !marked[v] ) {
                dfs( digraph, v );
                count++;
            }
        }

    }

    // dfs no digrafo
    private void dfs( Digraph digraph, int v ) {
        
        marked[v] = true;
        id[v] = count;
        
        for ( int w : digraph.adj( v ) ) {
            if ( !marked[w] ) {
                dfs( digraph, w );
            }
        }
        
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
