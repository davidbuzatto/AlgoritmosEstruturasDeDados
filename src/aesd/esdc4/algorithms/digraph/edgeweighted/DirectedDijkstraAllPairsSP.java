/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdc4.algorithms.digraph.edgeweighted;

import aesd.esdc4.ds.implementations.working.DirectedEdge;
import aesd.esdc4.ds.implementations.working.EdgeWeightedDigraph;

/**
 *
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 4. ed.
 * Boston: Pearson Education, 2011. 955 p.
 *
 * @author Prof. Dr. David Buzatto
 */
public class DirectedDijkstraAllPairsSP {

    private DirectedDijkstraSP[] all;

    /**
     * Computes a shortest paths tree from each vertex to to every other vertex
     * in the edge-weighted digraph {@code G}.
     *
     * @param G the edge-weighted digraph
     * @throws IllegalArgumentException if an edge weight is negative
     * @throws IllegalArgumentException unless {@code 0 <= s < V}
     */
    public DirectedDijkstraAllPairsSP( EdgeWeightedDigraph G ) {
        all = new DirectedDijkstraSP[G.V()];
        for ( int v = 0; v < G.V(); v++ ) {
            all[v] = new DirectedDijkstraSP( G, v );
        }
    }

    /**
     * Returns a shortest path from vertex {@code s} to vertex {@code t}.
     *
     * @param s the source vertex
     * @param t the destination vertex
     * @return a shortest path from vertex {@code s} to vertex {@code t} as an
     * iterable of edges, and {@code null} if no such path
     * @throws IllegalArgumentException unless {@code 0 <= s < V}
     * @throws IllegalArgumentException unless {@code 0 <= t < V}
     */
    public Iterable<DirectedEdge> path( int s, int t ) {
        validateVertex( s );
        validateVertex( t );
        return all[s].pathTo( t );
    }

    /**
     * Is there a path from the vertex {@code s} to vertex {@code t}?
     *
     * @param s the source vertex
     * @param t the destination vertex
     * @return {@code true} if there is a path from vertex {@code s} to vertex
     * {@code t}, and {@code false} otherwise
     * @throws IllegalArgumentException unless {@code 0 <= s < V}
     * @throws IllegalArgumentException unless {@code 0 <= t < V}
     */
    public boolean hasPath( int s, int t ) {
        validateVertex( s );
        validateVertex( t );
        return dist( s, t ) < Double.POSITIVE_INFINITY;
    }

    /**
     * Returns the length of a shortest path from vertex {@code s} to vertex
     * {@code t}.
     *
     * @param s the source vertex
     * @param t the destination vertex
     * @return the length of a shortest path from vertex {@code s} to vertex
     * {@code t}; {@code Double.POSITIVE_INFINITY} if no such path
     * @throws IllegalArgumentException unless {@code 0 <= s < V}
     * @throws IllegalArgumentException unless {@code 0 <= t < V}
     */
    public double dist( int s, int t ) {
        validateVertex( s );
        validateVertex( t );
        return all[s].distTo( t );
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex( int v ) {
        int V = all.length;
        if ( v < 0 || v >= V ) {
            throw new IllegalArgumentException( "vertex " + v + " is not between 0 and " + ( V - 1 ) );
        }
    }
}
