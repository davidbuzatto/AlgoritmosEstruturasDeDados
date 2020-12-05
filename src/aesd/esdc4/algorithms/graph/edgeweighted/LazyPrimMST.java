/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdc4.algorithms.graph.edgeweighted;

import aesd.esdc4.ds.implementations.linear.LinkedQueue;
import aesd.esdc4.ds.implementations.nonlinear.pq.MinPriorityQueue;
import aesd.esdc4.ds.implementations.working.Edge;
import aesd.esdc4.ds.implementations.working.EdgeWeightedGraph;
import aesd.esdc4.ds.interfaces.Queue;

/**
 *
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 4. ed.
 * Boston: Pearson Education, 2011. 955 p.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class LazyPrimMST {

    private static final double FLOATING_POINT_EPSILON = 1E-12;

    private double weight;       // total weight of MST
    private Queue<Edge> mst;     // edges in the MST
    private boolean[] marked;    // marked[v] = true iff v on tree
    private MinPriorityQueue<Edge> pq;      // edges with one endpoint in tree

    /**
     * Compute a minimum spanning tree (or forest) of an edge-weighted graph.
     *
     * @param G the edge-weighted graph
     */
    public LazyPrimMST( EdgeWeightedGraph G ) {
        mst = new LinkedQueue<Edge>();
        pq = new MinPriorityQueue<Edge>();
        marked = new boolean[G.V()];
        for ( int v = 0; v < G.V(); v++ ) // run Prim from all vertices to
        {
            if ( !marked[v] ) {
                prim( G, v );     // get a minimum spanning forest
            }
        }
    }

    // run Prim's algorithm
    private void prim( EdgeWeightedGraph G, int s ) {
        scan( G, s );
        while ( !pq.isEmpty() ) {                        // better to stop when mst has V-1 edges
            Edge e = pq.delete();                      // smallest edge on pq
            int v = e.either(), w = e.other( v );        // two endpoints
            assert marked[v] || marked[w];
            if ( marked[v] && marked[w] ) {
                continue;      // lazy, both v and w already scanned
            }
            mst.enqueue( e );                            // add e to MST
            weight += e.weight();
            if ( !marked[v] ) {
                scan( G, v );               // v becomes part of tree
            }
            if ( !marked[w] ) {
                scan( G, w );               // w becomes part of tree
            }
        }
    }

    // add all edges e incident to v onto pq if the other endpoint has not yet been scanned
    private void scan( EdgeWeightedGraph G, int v ) {
        assert !marked[v];
        marked[v] = true;
        for ( Edge e : G.adj( v ) ) {
            if ( !marked[e.other( v )] ) {
                pq.insert( e );
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
        return weight;
    }
}
