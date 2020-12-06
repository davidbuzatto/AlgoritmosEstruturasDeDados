/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdc4.algorithms.graph.edgeweighted;

import aesd.esdc4.ds.implementations.linear.ResizingArrayList;
import aesd.esdc4.ds.implementations.nonlinear.graph.Edge;
import aesd.esdc4.ds.implementations.nonlinear.graph.EdgeWeightedGraph;
import aesd.esdc4.ds.implementations.nonlinear.uf.UF;
import aesd.esdc4.ds.interfaces.List;

/**
 *
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 4. ed.
 * Boston: Pearson Education, 2011. 955 p.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class BoruvkaMST {

    private static final double FLOATING_POINT_EPSILON = 1E-12;

    private List<Edge> mst = new ResizingArrayList<>();    // edges in MST
    private double weight;                      // weight of MST

    /**
     * Compute a minimum spanning tree (or forest) of an edge-weighted graph.
     *
     * @param G the edge-weighted graph
     */
    public BoruvkaMST( EdgeWeightedGraph G ) {
        UF uf = new UF( G.getNumberOfVertices() );

        // repeat at most log V times or until we have V-1 edges
        for ( int t = 1; t < G.getNumberOfVertices() && mst.getSize()< G.getNumberOfVertices() - 1; t = t + t ) {

            // foreach tree in forest, find closest edge
            // if edge weights are equal, ties are broken in favor of first edge in G.edges()
            Edge[] closest = new Edge[G.getNumberOfVertices()];
            for ( Edge e : G.edges() ) {
                int v = e.either(), w = e.other( v );
                int i = uf.find( v ), j = uf.find( w );
                if ( i == j ) {
                    continue;   // same tree
                }
                if ( closest[i] == null || less( e, closest[i] ) ) {
                    closest[i] = e;
                }
                if ( closest[j] == null || less( e, closest[j] ) ) {
                    closest[j] = e;
                }
            }

            // add newly discovered edges to MST
            for ( int i = 0; i < G.getNumberOfVertices(); i++ ) {
                Edge e = closest[i];
                if ( e != null ) {
                    int v = e.either(), w = e.other( v );
                    // don't add the same edge twice
                    if ( uf.find( v ) != uf.find( w ) ) {
                        mst.add( e );
                        weight += e.weight();
                        uf.union( v, w );
                    }
                }
            }
        }

        // check optimality conditions
        assert check( G );
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

    // is the weight of edge e strictly less than that of edge f?
    private static boolean less( Edge e, Edge f ) {
        return e.compareTo( f ) < 0;
    }

    // check optimality conditions (takes time proportional to E V lg* V)
    private boolean check( EdgeWeightedGraph G ) {

        // check weight
        double totalWeight = 0.0;
        for ( Edge e : edges() ) {
            totalWeight += e.weight();
        }
        if ( Math.abs( totalWeight - weight() ) > FLOATING_POINT_EPSILON ) {
            System.err.printf( "Weight of edges does not equal weight(): %f vs. %f\n", totalWeight, weight() );
            return false;
        }

        // check that it is acyclic
        UF uf = new UF( G.getNumberOfVertices() );
        for ( Edge e : edges() ) {
            int v = e.either(), w = e.other( v );
            if ( uf.find( v ) == uf.find( w ) ) {
                System.err.println( "Not a forest" );
                return false;
            }
            uf.union( v, w );
        }

        // check that it is a spanning forest
        for ( Edge e : G.edges() ) {
            int v = e.either(), w = e.other( v );
            if ( uf.find( v ) != uf.find( w ) ) {
                System.err.println( "Not a spanning forest" );
                return false;
            }
        }

        // check that it is a minimal spanning forest (cut optimality conditions)
        for ( Edge e : edges() ) {

            // all edges in MST except e
            uf = new UF( G.getNumberOfVertices() );
            for ( Edge f : mst ) {
                int x = f.either(), y = f.other( x );
                if ( f != e ) {
                    uf.union( x, y );
                }
            }

            // check that e is min weight edge in crossing cut
            for ( Edge f : G.edges() ) {
                int x = f.either(), y = f.other( x );
                if ( uf.find( x ) != uf.find( y ) ) {
                    if ( f.weight() < e.weight() ) {
                        System.err.println( "Edge " + f + " violates cut optimality conditions" );
                        return false;
                    }
                }
            }

        }

        return true;
    }

}
