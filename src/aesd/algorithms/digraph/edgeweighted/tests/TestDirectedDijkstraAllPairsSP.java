package aesd.algorithms.digraph.edgeweighted.tests;

import aesd.algorithms.digraph.edgeweighted.DirectedDijkstraAllPairsSP;
import aesd.ds.implementations.nonlinear.graph.Edge;
import aesd.ds.implementations.nonlinear.graph.EdgeWeightedDigraph;

/**
 * Teste de uso do cálculo de menores caminhos para todos os pares
 * (DirectedDijkstraAllPairsSP) em um digrafo ponderado.
 *
 * @author Prof. Dr. David Buzatto
 */
public class TestDirectedDijkstraAllPairsSP {

    public static void main( String[] args ) {

        EdgeWeightedDigraph g = new EdgeWeightedDigraph( 4 );
        g.addEdge( 0, 1, 0.10 );
        g.addEdge( 1, 2, 0.20 );
        g.addEdge( 2, 3, 0.15 );
        g.addEdge( 0, 3, 1.00 );

        DirectedDijkstraAllPairsSP allPairs = new DirectedDijkstraAllPairsSP( g );

        System.out.println( "0 -> 3, distância: " + allPairs.dist( 0, 3 ) );
        System.out.println( "Caminho de 0 a 3:" );
        for ( Edge e : allPairs.path( 0, 3 ) ) {
            System.out.println( e );
        }

        System.out.println( "\n3 alcança 0? " + allPairs.hasPath( 3, 0 ) );

    }

}
