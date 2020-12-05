/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdc4.algorithms.digraph;

import aesd.esdc4.ds.implementations.nonlinear.graph.Digraph;

/**
 *
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 4. ed.
 * Boston: Pearson Education, 2011. 955 p.
 *
 * @author Prof. Dr. David Buzatto
 */
public class KosarajuSharirSCC {

    private boolean[] marked;     // marked[v] = has vertex v been visited?
    private int[] id;             // id[v] = id of strong component containing v
    private int count;            // number of strongly-connected components

    /**
     * Computes the strong components of the digraph {@code G}.
     *
     * @param G the digraph
     */
    public KosarajuSharirSCC( Digraph G ) {

        // compute reverse postorder of reverse graph
        DepthFirstOrder dfs = new DepthFirstOrder( G.reverse() );

        // run DFS on G, using reverse postorder to guide calculation
        marked = new boolean[G.getNumberOfVertices()];
        id = new int[G.getNumberOfVertices()];
        for ( int v : dfs.reversePost() ) {
            if ( !marked[v] ) {
                dfs( G, v );
                count++;
            }
        }

    }

    // DFS on graph G
    private void dfs( Digraph G, int v ) {
        marked[v] = true;
        id[v] = count;
        for ( int w : G.adj( v ) ) {
            if ( !marked[w] ) {
                dfs( G, w );
            }
        }
    }

    /**
     * Returns the number of strong components.
     *
     * @return the number of strong components
     */
    public int count() {
        return count;
    }

    /**
     * Are vertices {@code v} and {@code w} in the same strong component?
     *
     * @param v one vertex
     * @param w the other vertex
     * @return {@code true} if vertices {@code v} and {@code w} are in the same
     * strong component, and {@code false} otherwise
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     * @throws IllegalArgumentException unless {@code 0 <= w < V}
     */
    public boolean stronglyConnected( int v, int w ) {
        validateVertex( v );
        validateVertex( w );
        return id[v] == id[w];
    }

    /**
     * Returns the component id of the strong component containing vertex
     * {@code v}.
     *
     * @param v the vertex
     * @return the component id of the strong component containing vertex
     * {@code v}
     * @throws IllegalArgumentException unless {@code 0 <= s < V}
     */
    public int id( int v ) {
        validateVertex( v );
        return id[v];
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex( int v ) {
        int V = marked.length;
        if ( v < 0 || v >= V ) {
            throw new IllegalArgumentException( "vertex " + v + " is not between 0 and " + ( V - 1 ) );
        }
    }

}
