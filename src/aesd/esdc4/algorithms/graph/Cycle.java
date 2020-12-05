/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdc4.algorithms.graph;

import aesd.esdc4.ds.implementations.linear.ResizingArrayStack;
import aesd.esdc4.ds.implementations.nonlinear.graph.Graph;
import aesd.esdc4.ds.interfaces.Stack;

/**
 *
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 4. ed.
 * Boston: Pearson Education, 2011. 955 p.
 *
 * @author Prof. Dr. David Buzatto
 */
public class Cycle {

    private boolean[] marked;
    private int[] edgeTo;
    private Stack<Integer> cycle;

    /**
     * Determines whether the undirected graph {@code G} has a cycle and, if so,
     * finds such a cycle.
     *
     * @param G the undirected graph
     */
    public Cycle( Graph G ) {
        if ( hasSelfLoop( G ) ) {
            return;
        }
        if ( hasParallelEdges( G ) ) {
            return;
        }
        marked = new boolean[G.getNumberOfVertices()];
        edgeTo = new int[G.getNumberOfVertices()];
        for ( int v = 0; v < G.getNumberOfVertices(); v++ ) {
            if ( !marked[v] ) {
                dfs( G, -1, v );
            }
        }
    }

    // does this graph have a self loop?
    // side effect: initialize cycle to be self loop
    private boolean hasSelfLoop( Graph G ) {
        for ( int v = 0; v < G.getNumberOfVertices(); v++ ) {
            for ( int w : G.adj( v ) ) {
                if ( v == w ) {
                    cycle = new ResizingArrayStack<>();
                    cycle.push( v );
                    cycle.push( v );
                    return true;
                }
            }
        }
        return false;
    }

    // does this graph have two parallel edges?
    // side effect: initialize cycle to be two parallel edges
    private boolean hasParallelEdges( Graph G ) {
        marked = new boolean[G.getNumberOfVertices()];

        for ( int v = 0; v < G.getNumberOfVertices(); v++ ) {

            // check for parallel edges incident to v
            for ( int w : G.adj( v ) ) {
                if ( marked[w] ) {
                    cycle = new ResizingArrayStack<>();
                    cycle.push( v );
                    cycle.push( w );
                    cycle.push( v );
                    return true;
                }
                marked[w] = true;
            }

            // reset so marked[v] = false for all v
            for ( int w : G.adj( v ) ) {
                marked[w] = false;
            }
        }
        return false;
    }

    /**
     * Returns true if the graph {@code G} has a cycle.
     *
     * @return {@code true} if the graph has a cycle; {@code false} otherwise
     */
    public boolean hasCycle() {
        return cycle != null;
    }

    /**
     * Returns a cycle in the graph {@code G}.
     *
     * @return a cycle if the graph {@code G} has a cycle, and {@code null}
     * otherwise
     */
    public Iterable<Integer> cycle() {
        return cycle;
    }

    private void dfs( Graph G, int u, int v ) {
        marked[v] = true;
        for ( int w : G.adj( v ) ) {

            // short circuit if cycle already found
            if ( cycle != null ) {
                return;
            }

            if ( !marked[w] ) {
                edgeTo[w] = v;
                dfs( G, v, w );
            } // check for cycle (but disregard reverse of edge leading to v)
            else if ( w != u ) {
                cycle = new ResizingArrayStack<>();
                for ( int x = v; x != w; x = edgeTo[x] ) {
                    cycle.push( x );
                }
                cycle.push( w );
                cycle.push( v );
            }
        }
    }

}
