/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdc4.algorithms.digraph;

import aesd.esdc4.ds.implementations.linear.ResizingArrayStack;
import aesd.esdc4.ds.implementations.nonlinear.graph.Digraph;
import aesd.esdc4.ds.interfaces.Stack;

/**
 *
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 4. ed.
 * Boston: Pearson Education, 2011. 955 p.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class DirectedDepthFirstSearch {

    private boolean[] marked;  // marked[v] = true iff v is reachable from s
    private int[] edgeTo;      // edgeTo[v] = last edge on path from s to v
    private final int s;       // source vertex

    /**
     * Computes a directed path from {@code s} to every other vertex in digraph
     * {@code G}.
     *
     * @param G the digraph
     * @param s the source vertex
     * @throws IllegalArgumentException unless {@code 0 <= s < V}
     */
    public DirectedDepthFirstSearch( Digraph G, int s ) {
        marked = new boolean[G.getNumberOfVertices()];
        edgeTo = new int[G.getNumberOfVertices()];
        this.s = s;
        validateVertex( s );
        dfs( G, s );
    }

    private void dfs( Digraph G, int v ) {
        marked[v] = true;
        for ( int w : G.adj( v ) ) {
            if ( !marked[w] ) {
                dfs( G, w );
                edgeTo[w] = v;
            }
        }
    }

    /**
     * Is there a directed path from the source vertex {@code s} to vertex
     * {@code v}?
     *
     * @param v the vertex
     * @return {@code true} if there is a directed path from the source vertex
     * {@code s} to vertex {@code v}, {@code false} otherwise
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public boolean hasPathTo( int v ) {
        validateVertex( v );
        return marked[v];
    }

    /**
     * Returns a directed path from the source vertex {@code s} to vertex
     * {@code v}, or {@code null} if no such path.
     *
     * @param v the vertex
     * @return the sequence of vertices on a directed path from the source
     * vertex {@code s} to vertex {@code v}, as an Iterable
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public Iterable<Integer> pathTo( int v ) {
        validateVertex( v );
        if ( !hasPathTo( v ) ) {
            return null;
        }
        Stack<Integer> path = new ResizingArrayStack<>();
        for ( int x = v; x != s; x = edgeTo[x] ) {
            path.push( x );
        }
        path.push( s );
        return path;
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex( int v ) {
        int V = marked.length;
        if ( v < 0 || v >= V ) {
            throw new IllegalArgumentException( "vertex " + v + " is not between 0 and " + ( V - 1 ) );
        }
    }
    
    public boolean isMarked( int w ) {
        return marked[w];
    }

}
