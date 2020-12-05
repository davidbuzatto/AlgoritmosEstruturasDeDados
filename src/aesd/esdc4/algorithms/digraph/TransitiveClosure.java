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
public class TransitiveClosure {

    private DirectedDepthFirstSearch[] tc;  // tc[v] = reachable from v

    /**
     * Computes the transitive closure of the digraph {@code G}.
     *
     * @param G the digraph
     */
    public TransitiveClosure( Digraph G ) {
        tc = new DirectedDepthFirstSearch[G.getNumberOfVertices()];
        for ( int v = 0; v < G.getNumberOfVertices(); v++ ) {
            tc[v] = new DirectedDepthFirstSearch( G, v );
        }
    }

    /**
     * Is there a directed path from vertex {@code v} to vertex {@code w} in the
     * digraph?
     *
     * @param v the source vertex
     * @param w the target vertex
     * @return {@code true} if there is a directed path from {@code v} to
     * {@code w}, {@code false} otherwise
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     * @throws IllegalArgumentException unless {@code 0 <= w < V}
     */
    public boolean reachable( int v, int w ) {
        validateVertex( v );
        validateVertex( w );
        return tc[v].isMarked( w );
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex( int v ) {
        int V = tc.length;
        if ( v < 0 || v >= V ) {
            throw new IllegalArgumentException( "vertex " + v + " is not between 0 and " + ( V - 1 ) );
        }
    }

}
