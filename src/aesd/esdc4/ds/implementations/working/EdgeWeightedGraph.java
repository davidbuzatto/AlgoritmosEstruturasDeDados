/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdc4.ds.implementations.working;

import aesd.esdc4.ds.implementations.linear.ResizingArrayList;
import aesd.esdc4.ds.implementations.linear.ResizingArrayStack;
import aesd.esdc4.ds.interfaces.List;
import aesd.esdc4.ds.interfaces.Stack;

/**
 *
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 4. ed.
 * Boston: Pearson Education, 2011. 955 p.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class EdgeWeightedGraph {

    private static final String NEWLINE = System.getProperty( "line.separator" );

    private final int V;
    private int E;
    private List<Edge>[] adj;

    /**
     * Initializes an empty edge-weighted graph with {@code V} vertices and 0
     * edges.
     *
     * @param V the number of vertices
     * @throws IllegalArgumentException if {@code V < 0}
     */
    @SuppressWarnings( "unchecked" )
    public EdgeWeightedGraph( int V ) {
        if ( V < 0 ) {
            throw new IllegalArgumentException( "Number of vertices must be nonnegative" );
        }
        this.V = V;
        this.E = 0;
        adj = new ResizingArrayList[V];
        for ( int v = 0; v < V; v++ ) {
            adj[v] = new ResizingArrayList<>();
        }
    }

    /**
     * Initializes a new edge-weighted graph that is a deep copy of {@code G}.
     *
     * @param G the edge-weighted graph to copy
     */
    public EdgeWeightedGraph( EdgeWeightedGraph G ) {
        this( G.V() );
        this.E = G.E();
        for ( int v = 0; v < G.V(); v++ ) {
            // reverse so that adjacency list is in same order as original
            Stack<Edge> reverse = new ResizingArrayStack<>();
            for ( Edge e : G.adj[v] ) {
                reverse.push( e );
            }
            for ( Edge e : reverse ) {
                adj[v].add( e );
            }
        }
    }

    /**
     * Returns the number of vertices in this edge-weighted graph.
     *
     * @return the number of vertices in this edge-weighted graph
     */
    public int V() {
        return V;
    }

    /**
     * Returns the number of edges in this edge-weighted graph.
     *
     * @return the number of edges in this edge-weighted graph
     */
    public int E() {
        return E;
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex( int v ) {
        if ( v < 0 || v >= V ) {
            throw new IllegalArgumentException( "vertex " + v + " is not between 0 and " + ( V - 1 ) );
        }
    }

    /**
     * Adds the undirected edge {@code e} to this edge-weighted graph.
     *
     * @param e the edge
     * @throws IllegalArgumentException unless both endpoints are between
     * {@code 0} and {@code V-1}
     */
    public void addEdge( Edge e ) {
        int v = e.either();
        int w = e.other( v );
        validateVertex( v );
        validateVertex( w );
        adj[v].add( e );
        adj[w].add( e );
        E++;
    }

    /**
     * Returns the edges incident on vertex {@code v}.
     *
     * @param v the vertex
     * @return the edges incident on vertex {@code v} as an Iterable
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public Iterable<Edge> adj( int v ) {
        validateVertex( v );
        return adj[v];
    }

    /**
     * Returns the degree of vertex {@code v}.
     *
     * @param v the vertex
     * @return the degree of vertex {@code v}
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public int degree( int v ) {
        validateVertex( v );
        return adj[v].getSize();
    }

    /**
     * Returns all edges in this edge-weighted graph. To iterate over the edges
     * in this edge-weighted graph, use foreach notation:
     * {@code for (Edge e : G.edges())}.
     *
     * @return all edges in this edge-weighted graph, as an iterable
     */
    public Iterable<Edge> edges() {
        List<Edge> list = new ResizingArrayList<>();
        for ( int v = 0; v < V; v++ ) {
            int selfLoops = 0;
            for ( Edge e : adj( v ) ) {
                if ( e.other( v ) > v ) {
                    list.add( e );
                } // add only one copy of each self loop (self loops will be consecutive)
                else if ( e.other( v ) == v ) {
                    if ( selfLoops % 2 == 0 ) {
                        list.add( e );
                    }
                    selfLoops++;
                }
            }
        }
        return list;
    }

    /**
     * Returns a string representation of the edge-weighted graph. This method
     * takes time proportional to <em>E</em> + <em>V</em>.
     *
     * @return the number of vertices <em>V</em>, followed by the number of
     * edges <em>E</em>, followed by the <em>V</em> adjacency lists of edges
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append( V + " " + E + NEWLINE );
        for ( int v = 0; v < V; v++ ) {
            s.append( v + ": " );
            for ( Edge e : adj[v] ) {
                s.append( e + "  " );
            }
            s.append( NEWLINE );
        }
        return s.toString();
    }

}
