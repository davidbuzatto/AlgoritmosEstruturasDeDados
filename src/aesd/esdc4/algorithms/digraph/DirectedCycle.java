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
public class DirectedCycle {

    private boolean[] marked;        // marked[v] = has vertex v been marked?
    private int[] edgeTo;            // edgeTo[v] = previous vertex on path to v
    private boolean[] onStack;       // onStack[v] = is vertex on the stack?
    private Stack<Integer> cycle;    // directed cycle (or null if no such cycle)

    /**
     * Determines whether the digraph {@code G} has a directed cycle and, if so,
     * finds such a cycle.
     *
     * @param G the digraph
     */
    public DirectedCycle( Digraph G ) {
        marked = new boolean[G.getNumberOfVertices()];
        onStack = new boolean[G.getNumberOfVertices()];
        edgeTo = new int[G.getNumberOfVertices()];
        for ( int v = 0; v < G.getNumberOfVertices(); v++ ) {
            if ( !marked[v] && cycle == null ) {
                dfs( G, v );
            }
        }
    }

    // run DFS and find a directed cycle (if one exists)
    private void dfs( Digraph G, int v ) {
        onStack[v] = true;
        marked[v] = true;
        for ( int w : G.adj( v ) ) {

            // short circuit if directed cycle found
            if ( cycle != null ) {
                return;
            } // found new vertex, so recur
            else if ( !marked[w] ) {
                edgeTo[w] = v;
                dfs( G, w );
            } // trace back directed cycle
            else if ( onStack[w] ) {
                cycle = new ResizingArrayStack<>();
                for ( int x = v; x != w; x = edgeTo[x] ) {
                    cycle.push( x );
                }
                cycle.push( w );
                cycle.push( v );
            }
        }
        onStack[v] = false;
    }

    /**
     * Does the digraph have a directed cycle?
     *
     * @return {@code true} if the digraph has a directed cycle, {@code false}
     * otherwise
     */
    public boolean hasCycle() {
        return cycle != null;
    }

    /**
     * Returns a directed cycle if the digraph has a directed cycle, and
     * {@code null} otherwise.
     *
     * @return a directed cycle (as an iterable) if the digraph has a directed
     * cycle, and {@code null} otherwise
     */
    public Iterable<Integer> cycle() {
        return cycle;
    }

}
