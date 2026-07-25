package aesd.algorithms.digraph.edgeweighted.tests;

import aesd.algorithms.digraph.edgeweighted.FloydWarshall;
import aesd.ds.implementations.nonlinear.graph.AdjMatrixEdgeWeightedDigraph;
import aesd.ds.implementations.nonlinear.graph.Edge;

/**
 * Teste de uso do algoritmo de Floyd-Warshall (FloydWarshall) para todos os
 * pares de menores caminhos em um digrafo ponderado.
 *
 * @author Prof. Dr. David Buzatto
 */
public class TestFloydWarshall {

    public static void main( String[] args ) {

        AdjMatrixEdgeWeightedDigraph g = new AdjMatrixEdgeWeightedDigraph( 4 );
        g.addEdge( 0, 1, 0.10 );
        g.addEdge( 1, 2, 0.20 );
        g.addEdge( 2, 3, 0.15 );
        g.addEdge( 0, 3, 1.00 );

        FloydWarshall fw = new FloydWarshall( g );

        System.out.println( "Possui ciclo negativo? " + fw.hasNegativeCycle() );

        System.out.println( "\nMenores caminhos a partir de 0:" );
        for ( int v = 0; v < g.getNumberOfVertices(); v++ ) {

            System.out.print( "0 -> " + v + " (" + fw.dist( 0, v ) + "): " );

            if ( fw.hasPath( 0, v ) ) {
                for ( Edge e : fw.path( 0, v ) ) {
                    System.out.print( e + "   " );
                }
            }

            System.out.println();

        }

        // digrafo com ciclo negativo
        AdjMatrixEdgeWeightedDigraph withNegativeCycle = new AdjMatrixEdgeWeightedDigraph( 3 );
        withNegativeCycle.addEdge( 0, 1, 1.0 );
        withNegativeCycle.addEdge( 1, 2, -3.0 );
        withNegativeCycle.addEdge( 2, 0, 1.0 );

        FloydWarshall fwNeg = new FloydWarshall( withNegativeCycle );
        System.out.println( "\nDigrafo com ciclo negativo -> possui ciclo negativo? " + fwNeg.hasNegativeCycle() );
        System.out.println( "Ciclo negativo encontrado:" );
        for ( Edge e : fwNeg.negativeCycle() ) {
            System.out.println( e );
        }

    }

}
