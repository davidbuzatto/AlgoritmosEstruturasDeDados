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
public class DirectedEulerianPath {

    private Stack<Integer> path = null;   // Eulerian path; null if no suh path

    /**
     * Computes an Eulerian path in the specified digraph, if one exists.
     *
     * @param G the digraph
     */
    @SuppressWarnings( "unchecked" )
    public DirectedEulerianPath( Digraph G ) {

        // find vertex from which to start potential Eulerian path:
        // a vertex v with outdegree(v) > indegree(v) if it exits;
        // otherwise a vertex with outdegree(v) > 0
        int deficit = 0;
        int s = nonIsolatedVertex( G );
        for ( int v = 0; v < G.getNumberOfVertices(); v++ ) {
            if ( G.outdegree( v ) > G.indegree( v ) ) {
                deficit += ( G.outdegree( v ) - G.indegree( v ) );
                s = v;
            }
        }

        // digraph can't have an Eulerian path
        // (this condition is needed)
        if ( deficit > 1 ) {
            return;
        }

        // special case for digraph with zero edges (has a degenerate Eulerian path)
        if ( s == -1 ) {
            s = 0;
        }

        // create local view of adjacency lists, to iterate one vertex at a time
        Iterator<Integer>[] adj = new Iterator[G.getNumberOfVertices()];
        for ( int v = 0; v < G.getNumberOfVertices(); v++ ) {
            adj[v] = G.adj( v ).iterator();
        }

        // greedily add to cycle, depth-first search style
        Stack<Integer> stack = new ResizingArrayStack<>();
        stack.push( s );
        path = new ResizingArrayStack<>();
        while ( !stack.isEmpty() ) {
            int v = stack.pop();
            while ( adj[v].hasNext() ) {
                stack.push( v );
                v = adj[v].next();
            }
            // push vertex with no more available edges to path
            path.push( v );
        }

        // check if all edges have been used
        if ( path.getSize()!= G.getNumberOfEdges() + 1 ) {
            path = null;
        }
        
    }

    /**
     * Returns the sequence of vertices on an Eulerian path.
     *
     * @return the sequence of vertices on an Eulerian path; {@code null} if no
     * such path
     */
    public Iterable<Integer> path() {
        return path;
    }

    /**
     * Returns true if the digraph has an Eulerian path.
     *
     * @return {@code true} if the digraph has an Eulerian path; {@code false}
     * otherwise
     */
    public boolean hasEulerianPath() {
        return path != null;
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
