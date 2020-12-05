/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdc4.algorithms.digraph.edgeweighted;

import aesd.esdc4.ds.implementations.linear.ResizingArrayStack;
import aesd.esdc4.ds.implementations.working.DirectedEdge;
import aesd.esdc4.ds.implementations.working.EdgeWeightedDigraph;
import aesd.esdc4.ds.interfaces.Stack;

/**
 *
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 4. ed.
 * Boston: Pearson Education, 2011. 955 p.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class EdgeWeightedDirectedCycle {

    private boolean[] marked;             // marked[v] = has vertex v been marked?
    private DirectedEdge[] edgeTo;        // edgeTo[v] = previous edge on path to v
    private boolean[] onStack;            // onStack[v] = is vertex on the stack?
    private Stack<DirectedEdge> cycle;    // directed cycle (or null if no such cycle)

    /**
     * Determines whether the edge-weighted digraph {@code G} has a directed
     * cycle and, if so, finds such a cycle.
     *
     * @param G the edge-weighted digraph
     */
    public EdgeWeightedDirectedCycle( EdgeWeightedDigraph G ) {
        marked = new boolean[G.V()];
        onStack = new boolean[G.V()];
        edgeTo = new DirectedEdge[G.V()];
        for ( int v = 0; v < G.V(); v++ ) {
            if ( !marked[v] ) {
                dfs( G, v );
            }
        }
    }

    // check that algorithm computes either the topological order or finds a directed cycle
    private void dfs( EdgeWeightedDigraph G, int v ) {
        onStack[v] = true;
        marked[v] = true;
        for ( DirectedEdge e : G.adj( v ) ) {
            int w = e.to();

            // short circuit if directed cycle found
            if ( cycle != null ) {
                return;
            } // found new vertex, so recur
            else if ( !marked[w] ) {
                edgeTo[w] = e;
                dfs( G, w );
            } // trace back directed cycle
            else if ( onStack[w] ) {
                cycle = new ResizingArrayStack<DirectedEdge>();

                DirectedEdge f = e;
                while ( f.from() != w ) {
                    cycle.push( f );
                    f = edgeTo[f.from()];
                }
                cycle.push( f );

                return;
            }
        }

        onStack[v] = false;
    }

    /**
     * Does the edge-weighted digraph have a directed cycle?
     *
     * @return {@code true} if the edge-weighted digraph has a directed cycle,
     * {@code false} otherwise
     */
    public boolean hasCycle() {
        return cycle != null;
    }

    /**
     * Returns a directed cycle if the edge-weighted digraph has a directed
     * cycle, and {@code null} otherwise.
     *
     * @return a directed cycle (as an iterable) if the edge-weighted digraph
     * has a directed cycle, and {@code null} otherwise
     */
    public Iterable<DirectedEdge> cycle() {
        return cycle;
    }

}
