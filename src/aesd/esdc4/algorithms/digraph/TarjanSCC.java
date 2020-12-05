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
public class TarjanSCC {

    private boolean[] marked;        // marked[v] = has v been visited?
    private int[] id;                // id[v] = id of strong component containing v
    private int[] low;               // low[v] = low number of v
    private int pre;                 // preorder number counter
    private int count;               // number of strongly-connected components
    private Stack<Integer> stack;

    /**
     * Computes the strong components of the digraph {@code G}.
     *
     * @param G the digraph
     */
    public TarjanSCC( Digraph G ) {
        marked = new boolean[G.getNumberOfVertices()];
        stack = new ResizingArrayStack<>();
        id = new int[G.getNumberOfVertices()];
        low = new int[G.getNumberOfVertices()];
        for ( int v = 0; v < G.getNumberOfVertices(); v++ ) {
            if ( !marked[v] ) {
                dfs( G, v );
            }
        }
    }

    private void dfs( Digraph G, int v ) {
        marked[v] = true;
        low[v] = pre++;
        int min = low[v];
        stack.push( v );
        for ( int w : G.adj( v ) ) {
            if ( !marked[w] ) {
                dfs( G, w );
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
            low[w] = G.getNumberOfVertices();
        } while ( w != v );
        count++;
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
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public int id( int v ) {
        validateVertex( v );
        return id[v];
    }

    // does the id[] array contain the strongly connected components?
    private boolean check( Digraph G ) {
        TransitiveClosure tc = new TransitiveClosure( G );
        for ( int v = 0; v < G.getNumberOfVertices(); v++ ) {
            for ( int w = 0; w < G.getNumberOfVertices(); w++ ) {
                if ( stronglyConnected( v, w ) != ( tc.reachable( v, w ) && tc.reachable( w, v ) ) ) {
                    return false;
                }
            }
        }
        return true;
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex( int v ) {
        int V = marked.length;
        if ( v < 0 || v >= V ) {
            throw new IllegalArgumentException( "vertex " + v + " is not between 0 and " + ( V - 1 ) );
        }
    }

}
