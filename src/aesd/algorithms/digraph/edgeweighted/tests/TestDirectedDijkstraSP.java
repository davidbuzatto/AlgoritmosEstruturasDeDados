package aesd.algorithms.digraph.edgeweighted.tests;

import aesd.algorithms.digraph.edgeweighted.DirectedDijkstraSP;
import aesd.ds.implementations.nonlinear.graph.Edge;
import aesd.ds.implementations.nonlinear.graph.EdgeWeightedDigraph;

/**
 * Teste de uso do algoritmo de Dijkstra (digrafo ponderado) usando a classe
 * DirectedDijkstraSP.
 *
 * @author Prof. Dr. David Buzatto
 */
public class TestDirectedDijkstraSP {

    public static void main( String[] args ) {

        // digrafo ponderado clássico (tinyEWD)
        EdgeWeightedDigraph g = new EdgeWeightedDigraph( 8 );
        g.addEdge( 4, 5, 0.35 );
        g.addEdge( 5, 4, 0.35 );
        g.addEdge( 4, 7, 0.37 );
        g.addEdge( 5, 7, 0.28 );
        g.addEdge( 7, 5, 0.28 );
        g.addEdge( 5, 1, 0.32 );
        g.addEdge( 0, 4, 0.38 );
        g.addEdge( 0, 2, 0.26 );
        g.addEdge( 7, 3, 0.39 );
        g.addEdge( 1, 3, 0.29 );
        g.addEdge( 2, 7, 0.34 );
        g.addEdge( 6, 2, 0.40 );
        g.addEdge( 3, 6, 0.52 );
        g.addEdge( 6, 0, 0.58 );
        g.addEdge( 6, 4, 0.93 );

        DirectedDijkstraSP sp = new DirectedDijkstraSP( g, 0 );

        for ( int v = 0; v < g.getNumberOfVertices(); v++ ) {

            System.out.print( "0 -> " + v + " (" + sp.distTo( v ) + "): " );

            if ( sp.hasPathTo( v ) ) {
                for ( Edge e : sp.pathTo( v ) ) {
                    System.out.print( e + "   " );
                }
            }

            System.out.println();

        }

    }

}
