/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdc4.algorithms.digraph;

import aesd.esdc4.ds.implementations.linear.ResizingArrayStack;
import aesd.esdc4.ds.implementations.nonlinear.graph.Digraph;
import aesd.esdc4.ds.interfaces.Stack;
import java.util.Iterator;

/**
 *
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 4. ed.
 * Boston: Pearson Education, 2011. 955 p.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class DirectedEulerianCycle {

    private Stack<Integer> cycle = null;  // Eulerian cycle; null if no such cylce

    /**
     * Computes an Eulerian cycle in the specified digraph, if one exists.
     *
     * @param G the digraph
     */
    @SuppressWarnings( "unchecked" )
    public DirectedEulerianCycle( Digraph G ) {

        // must have at least one edge
        if ( G.getNumberOfEdges() == 0 ) {
            return;
        }

        // necessary condition: indegree(v) = outdegree(v) for each vertex v
        // (without this check, DFS might return a path instead of a cycle)
        for ( int v = 0; v < G.getNumberOfVertices(); v++ ) {
            if ( G.outdegree( v ) != G.indegree( v ) ) {
                return;
            }
        }

        // create local view of adjacency lists, to iterate one vertex at a time
        Iterator<Integer>[] adj = new Iterator[G.getNumberOfVertices()];
        for ( int v = 0; v < G.getNumberOfVertices(); v++ ) {
            adj[v] = G.adj( v ).iterator();
        }

        // initialize stack with any non-isolated vertex
        int s = nonIsolatedVertex( G );
        Stack<Integer> stack = new ResizingArrayStack<>();
        stack.push( s );

        // greedily add to putative cycle, depth-first search style
        cycle = new ResizingArrayStack<>();
        while ( !stack.isEmpty() ) {
            int v = stack.pop();
            while ( adj[v].hasNext() ) {
                stack.push( v );
                v = adj[v].next();
            }
            // add vertex with no more leaving edges to cycle
            cycle.push( v );
        }

        // check if all edges have been used
        // (in case there are two or more vertex-disjoint Eulerian cycles)
        if ( cycle.getSize()!= G.getNumberOfEdges() + 1 ) {
            cycle = null;
        }
        
    }

    /**
     * Returns the sequence of vertices on an Eulerian cycle.
     *
     * @return the sequence of vertices on an Eulerian cycle; {@code null} if no
     * such cycle
     */
    public Iterable<Integer> cycle() {
        return cycle;
    }

    /**
     * Returns true if the digraph has an Eulerian cycle.
     *
     * @return {@code true} if the digraph has an Eulerian cycle; {@code false}
     * otherwise
     */
    public boolean hasEulerianCycle() {
        return cycle != null;
    }

    // returns any non-isolated vertex; -1 if no such vertex
    private static int nonIsolatedVertex( Digraph G ) {
        for ( int v = 0; v < G.getNumberOfVertices(); v++ ) {
            if ( G.outdegree( v ) > 0 ) {
                return v;
            }
        }
        return -1;
    }

}
