/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdc4.algorithms.graph.edgeweighted;

import aesd.esdc4.ds.implementations.linear.LinkedQueue;
import aesd.esdc4.ds.implementations.working.Edge;
import aesd.esdc4.ds.implementations.working.EdgeWeightedGraph;
import aesd.esdc4.ds.implementations.nonlinear.pq.IndexedMinPriorityQueue;
import aesd.esdc4.ds.interfaces.Queue;

/**
 *
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 4. ed.
 * Boston: Pearson Education, 2011. 955 p.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class PrimMST {

    private static final double FLOATING_POINT_EPSILON = 1E-12;

    private Edge[] edgeTo;        // edgeTo[v] = shortest edge from tree vertex to non-tree vertex
    private double[] distTo;      // distTo[v] = weight of shortest such edge
    private boolean[] marked;     // marked[v] = true if v on tree, false otherwise
    private IndexedMinPriorityQueue<Double> pq;

    /**
     * Compute a minimum spanning tree (or forest) of an edge-weighted graph.
     *
     * @param G the edge-weighted graph
     */
    public PrimMST( EdgeWeightedGraph G ) {
        edgeTo = new Edge[G.V()];
        distTo = new double[G.V()];
        marked = new boolean[G.V()];
        pq = new IndexedMinPriorityQueue<>( G.V() );
        for ( int v = 0; v < G.V(); v++ ) {
            distTo[v] = Double.POSITIVE_INFINITY;
        }

        for ( int v = 0; v < G.V(); v++ ) // run from each vertex to find
        {
            if ( !marked[v] ) {
                prim( G, v );      // minimum spanning forest
            }
        }
    }

    // run Prim's algorithm in graph G, starting from vertex s
    private void prim( EdgeWeightedGraph G, int s ) {
        distTo[s] = 0.0;
        pq.insert( s, distTo[s] );
        while ( !pq.isEmpty() ) {
            int v = pq.delete();
            scan( G, v );
        }
    }

    // scan vertex v
    private void scan( EdgeWeightedGraph G, int v ) {
        marked[v] = true;
        for ( Edge e : G.adj( v ) ) {
            int w = e.other( v );
            if ( marked[w] ) {
                continue;         // v-w is obsolete edge
            }
            if ( e.weight() < distTo[w] ) {
                distTo[w] = e.weight();
                edgeTo[w] = e;
                if ( pq.contains( w ) ) {
                    pq.decreaseKey( w, distTo[w] );
                } else {
                    pq.insert( w, distTo[w] );
                }
            }
        }
    }

    /**
     * Returns the edges in a minimum spanning tree (or forest).
     *
     * @return the edges in a minimum spanning tree (or forest) as an iterable
     * of edges
     */
    public Iterable<Edge> edges() {
        Queue<Edge> mst = new LinkedQueue<>();
        for ( int v = 0; v < edgeTo.length; v++ ) {
            Edge e = edgeTo[v];
            if ( e != null ) {
                mst.enqueue( e );
            }
        }
        return mst;
    }

    /**
     * Returns the sum of the edge weights in a minimum spanning tree (or
     * forest).
     *
     * @return the sum of the edge weights in a minimum spanning tree (or
     * forest)
     */
    public double weight() {
        double weight = 0.0;
        for ( Edge e : edges() ) {
            weight += e.weight();
        }
        return weight;
    }

}
