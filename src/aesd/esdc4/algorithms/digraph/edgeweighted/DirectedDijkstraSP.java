/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdc4.algorithms.digraph.edgeweighted;

import aesd.esdc4.ds.implementations.linear.ResizingArrayStack;
import aesd.esdc4.ds.implementations.working.DirectedEdge;
import aesd.esdc4.ds.implementations.working.EdgeWeightedDigraph;
import aesd.esdc4.ds.implementations.nonlinear.pq.IndexedMinPriorityQueue;
import aesd.esdc4.ds.interfaces.Stack;

/**
 *
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 4. ed.
 * Boston: Pearson Education, 2011. 955 p.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class DirectedDijkstraSP {

    private double[] distTo;          // distTo[v] = distance  of shortest s->v path
    private DirectedEdge[] edgeTo;    // edgeTo[v] = last edge on shortest s->v path
    private IndexedMinPriorityQueue<Double> pq;    // priority queue of vertices

    /**
     * Computes a shortest-paths tree from the source vertex {@code s} to every
     * other vertex in the edge-weighted digraph {@code G}.
     *
     * @param G the edge-weighted digraph
     * @param s the source vertex
     * @throws IllegalArgumentException if an edge weight is negative
     * @throws IllegalArgumentException unless {@code 0 <= s < V}
     */
    public DirectedDijkstraSP( EdgeWeightedDigraph G, int s ) {
        for ( DirectedEdge e : G.edges() ) {
            if ( e.weight() < 0 ) {
                throw new IllegalArgumentException( "edge " + e + " has negative weight" );
            }
        }

        distTo = new double[G.V()];
        edgeTo = new DirectedEdge[G.V()];

        validateVertex( s );

        for ( int v = 0; v < G.V(); v++ ) {
            distTo[v] = Double.POSITIVE_INFINITY;
        }
        distTo[s] = 0.0;

        // relax vertices in order of distance from s
        pq = new IndexedMinPriorityQueue<Double>( G.V() );
        pq.insert( s, distTo[s] );
        while ( !pq.isEmpty() ) {
            int v = pq.delete();
            for ( DirectedEdge e : G.adj( v ) ) {
                relax( e );
            }
        }
        
    }

    // relax edge e and update pq if changed
    private void relax( DirectedEdge e ) {
        int v = e.from(), w = e.to();
        if ( distTo[w] > distTo[v] + e.weight() ) {
            distTo[w] = distTo[v] + e.weight();
            edgeTo[w] = e;
            if ( pq.contains( w ) ) {
                pq.decreaseKey( w, distTo[w] );
            } else {
                pq.insert( w, distTo[w] );
            }
        }
    }

    /**
     * Returns the length of a shortest path from the source vertex {@code s} to
     * vertex {@code v}.
     *
     * @param v the destination vertex
     * @return the length of a shortest path from the source vertex {@code s} to
     * vertex {@code v}; {@code Double.POSITIVE_INFINITY} if no such path
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public double distTo( int v ) {
        validateVertex( v );
        return distTo[v];
    }

    /**
     * Returns true if there is a path from the source vertex {@code s} to
     * vertex {@code v}.
     *
     * @param v the destination vertex
     * @return {@code true} if there is a path from the source vertex {@code s}
     * to vertex {@code v}; {@code false} otherwise
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public boolean hasPathTo( int v ) {
        validateVertex( v );
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    /**
     * Returns a shortest path from the source vertex {@code s} to vertex
     * {@code v}.
     *
     * @param v the destination vertex
     * @return a shortest path from the source vertex {@code s} to vertex
     * {@code v} as an iterable of edges, and {@code null} if no such path
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public Iterable<DirectedEdge> pathTo( int v ) {
        validateVertex( v );
        if ( !hasPathTo( v ) ) {
            return null;
        }
        Stack<DirectedEdge> path = new ResizingArrayStack<DirectedEdge>();
        for ( DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()] ) {
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
