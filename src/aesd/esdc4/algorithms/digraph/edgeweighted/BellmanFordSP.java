/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdc4.algorithms.digraph.edgeweighted;

import aesd.esdc4.ds.implementations.linear.LinkedQueue;
import aesd.esdc4.ds.implementations.linear.ResizingArrayStack;
import aesd.esdc4.ds.implementations.nonlinear.graph.Edge;
import aesd.esdc4.ds.implementations.nonlinear.graph.EdgeWeightedDigraph;
import aesd.esdc4.ds.interfaces.Queue;
import aesd.esdc4.ds.interfaces.Stack;

/**
 *
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 4. ed.
 * Boston: Pearson Education, 2011. 955 p.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class BellmanFordSP {

    // for floating-point precision issues
    private static final double EPSILON = 1E-14;

    private double[] distTo;               // distTo[v] = distance  of shortest s->v path
    private Edge[] edgeTo;         // edgeTo[v] = last edge on shortest s->v path
    private boolean[] onQueue;             // onQueue[v] = is v currently on the queue?
    private Queue<Integer> queue;          // queue of vertices to relax
    private int cost;                      // number of calls to relax()
    private Iterable<Edge> cycle;  // negative cycle (or null if no such cycle)

    /**
     * Computes a shortest paths tree from {@code s} to every other vertex in
     * the edge-weighted digraph {@code G}.
     *
     * @param G the acyclic digraph
     * @param s the source vertex
     * @throws IllegalArgumentException unless {@code 0 <= s < V}
     */
    public BellmanFordSP( EdgeWeightedDigraph G, int s ) {
        distTo = new double[G.getNumberOfVertices()];
        edgeTo = new Edge[G.getNumberOfVertices()];
        onQueue = new boolean[G.getNumberOfVertices()];
        for ( int v = 0; v < G.getNumberOfVertices(); v++ ) {
            distTo[v] = Double.POSITIVE_INFINITY;
        }
        distTo[s] = 0.0;

        // Bellman-Ford algorithm
        queue = new LinkedQueue<>();
        queue.enqueue( s );
        onQueue[s] = true;
        while ( !queue.isEmpty() && !hasNegativeCycle() ) {
            int v = queue.dequeue();
            onQueue[v] = false;
            relax( G, v );
        }
        
    }

    // relax vertex v and put other endpoints on queue if changed
    private void relax( EdgeWeightedDigraph G, int v ) {
        for ( Edge e : G.adj( v ) ) {
            int w = e.to();
            if ( distTo[w] > distTo[v] + e.weight() + EPSILON ) {
                distTo[w] = distTo[v] + e.weight();
                edgeTo[w] = e;
                if ( !onQueue[w] ) {
                    queue.enqueue( w );
                    onQueue[w] = true;
                }
            }
            if ( ++cost % G.getNumberOfVertices() == 0 ) {
                findNegativeCycle();
                if ( hasNegativeCycle() ) {
                    return;  // found a negative cycle
                }
            }
        }
    }

    /**
     * Is there a negative cycle reachable from the source vertex {@code s}?
     *
     * @return {@code true} if there is a negative cycle reachable from the
     * source vertex {@code s}, and {@code false} otherwise
     */
    public boolean hasNegativeCycle() {
        return cycle != null;
    }

    /**
     * Returns a negative cycle reachable from the source vertex {@code s}, or
     * {@code null} if there is no such cycle.
     *
     * @return a negative cycle reachable from the soruce vertex {@code s} as an
     * iterable of edges, and {@code null} if there is no such cycle
     */
    public Iterable<Edge> negativeCycle() {
        return cycle;
    }

    // by finding a cycle in predecessor graph
    private void findNegativeCycle() {
        int V = edgeTo.length;
        EdgeWeightedDigraph spt = new EdgeWeightedDigraph( V );
        for ( int v = 0; v < V; v++ ) {
            if ( edgeTo[v] != null ) {
                spt.addEdge( edgeTo[v].from(), edgeTo[v].to(), edgeTo[v].weight() );
            }
        }

        EdgeWeightedDirectedCycle finder = new EdgeWeightedDirectedCycle( spt );
        cycle = finder.cycle();
    }

    /**
     * Returns the length of a shortest path from the source vertex {@code s} to
     * vertex {@code v}.
     *
     * @param v the destination vertex
     * @return the length of a shortest path from the source vertex {@code s} to
     * vertex {@code v}; {@code Double.POSITIVE_INFINITY} if no such path
     * @throws UnsupportedOperationException if there is a negative cost cycle
     * reachable from the source vertex {@code s}
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public double distTo( int v ) {
        validateVertex( v );
        if ( hasNegativeCycle() ) {
            throw new UnsupportedOperationException( "Negative cost cycle exists" );
        }
        return distTo[v];
    }

    /**
     * Is there a path from the source {@code s} to vertex {@code v}?
     *
     * @param v the destination vertex
     * @return {@code true} if there is a path from the source vertex {@code s}
     * to vertex {@code v}, and {@code false} otherwise
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public boolean hasPathTo( int v ) {
        validateVertex( v );
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    /**
     * Returns a shortest path from the source {@code s} to vertex {@code v}.
     *
     * @param v the destination vertex
     * @return a shortest path from the source {@code s} to vertex {@code v} as
     * an iterable of edges, and {@code null} if no such path
     * @throws UnsupportedOperationException if there is a negative cost cycle
     * reachable from the source vertex {@code s}
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public Iterable<Edge> pathTo( int v ) {
        validateVertex( v );
        if ( hasNegativeCycle() ) {
            throw new UnsupportedOperationException( "Negative cost cycle exists" );
        }
        if ( !hasPathTo( v ) ) {
            return null;
        }
        Stack<Edge> path = new ResizingArrayStack<Edge>();
        for ( Edge e = edgeTo[v]; e != null; e = edgeTo[e.from()] ) {
            path.push( e );
        }
        return path;
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex( int v ) {
        int V = distTo.length;
        if ( v < 0 || v >= V ) {
            throw new IllegalArgumentException( "vertex " + v + " is not between 0 and " + ( V - 1 ) );
        }
    }

}
