/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdc4.ds.implementations.working;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 4. ed.
 * Boston: Pearson Education, 2011. 955 p.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class AdjMatrixEdgeWeightedDigraph {

    private static final String NEWLINE = System.getProperty( "line.separator" );

    private final int V;
    private int E;
    private DirectedEdge[][] adj;

    /**
     * Initializes an empty edge-weighted digraph with {@code V} vertices and 0
     * edges.
     *
     * @param V the number of vertices
     * @throws IllegalArgumentException if {@code V < 0}
     */
    public AdjMatrixEdgeWeightedDigraph( int V ) {
        if ( V < 0 ) {
            throw new IllegalArgumentException( "number of vertices must be nonnegative" );
        }
        this.V = V;
        this.E = 0;
        this.adj = new DirectedEdge[V][V];
    }

    /**
     * Returns the number of vertices in the edge-weighted digraph.
     *
     * @return the number of vertices in the edge-weighted digraph
     */
    public int V() {
        return V;
    }

    /**
     * Returns the number of edges in the edge-weighted digraph.
     *
     * @return the number of edges in the edge-weighted digraph
     */
    public int E() {
        return E;
    }

    /**
     * Adds the directed edge {@code e} to the edge-weighted digraph (if there
     * is not already an edge with the same endpoints).
     *
     * @param e the edge
     */
    public void addEdge( DirectedEdge e ) {
        int v = e.from();
        int w = e.to();
        validateVertex( v );
        validateVertex( w );
        if ( adj[v][w] == null ) {
            E++;
            adj[v][w] = e;
        }
    }

    /**
     * Returns the directed edges incident from vertex {@code v}.
     *
     * @param v the vertex
     * @return the directed edges incident from vertex {@code v} as an Iterable
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public Iterable<DirectedEdge> adj( int v ) {
        validateVertex( v );
        return new AdjIterator( v );
    }

    // support iteration over graph vertices
    private class AdjIterator implements Iterator<DirectedEdge>, Iterable<DirectedEdge> {

        private int v;
        private int w = 0;

        public AdjIterator( int v ) {
            this.v = v;
        }

        public Iterator<DirectedEdge> iterator() {
            return this;
        }

        public boolean hasNext() {
            while ( w < V ) {
                if ( adj[v][w] != null ) {
                    return true;
                }
                w++;
            }
            return false;
        }

        public DirectedEdge next() {
            if ( !hasNext() ) {
                throw new NoSuchElementException();
            }
            return adj[v][w++];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Returns a string representation of the edge-weighted digraph. This method
     * takes time proportional to <em>V</em><sup>2</sup>.
     *
     * @return the number of vertices <em>V</em>, followed by the number of
     * edges <em>E</em>, followed by the <em>V</em> adjacency lists of edges
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append( V + " " + E + NEWLINE );
        for ( int v = 0; v < V; v++ ) {
            s.append( v + ": " );
            for ( DirectedEdge e : adj( v ) ) {
                s.append( e + "  " );
            }
            s.append( NEWLINE );
        }
        return s.toString();
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex( int v ) {
        if ( v < 0 || v >= V ) {
            throw new IllegalArgumentException( "vertex " + v + " is not between 0 and " + ( V - 1 ) );
        }
    }

}
