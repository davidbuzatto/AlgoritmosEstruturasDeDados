package aesd.algorithms.digraph.edgeweighted.tests;

import aesd.algorithms.digraph.edgeweighted.BellmanFordSP;
import aesd.ds.implementations.nonlinear.graph.Edge;
import aesd.ds.implementations.nonlinear.graph.EdgeWeightedDigraph;

/**
 * Teste de uso do algoritmo de Bellman-Ford (BellmanFordSP) para menor
 * caminho em um digrafo ponderado que pode ter arestas de peso negativo.
 *
 * @author Prof. Dr. David Buzatto
 */
public class TestBellmanFordSP {

    public static void main( String[] args ) {

        // digrafo com aresta de peso negativo, mas sem ciclo negativo
        EdgeWeightedDigraph g = new EdgeWeightedDigraph( 5 );
        g.addEdge( 0, 1, 1.0 );
        g.addEdge( 0, 2, 3.0 );
        g.addEdge( 1, 2, -2.0 );
        g.addEdge( 2, 3, 2.0 );
        g.addEdge( 3, 4, 1.0 );

        BellmanFordSP sp = new BellmanFordSP( g, 0 );

        System.out.println( "Possui ciclo negativo alcançável? " + sp.hasNegativeCycle() );

        for ( int v = 0; v < g.getNumberOfVertices(); v++ ) {

            System.out.print( "0 -> " + v + " (" + sp.distTo( v ) + "): " );

            if ( sp.hasPathTo( v ) ) {
                for ( Edge e : sp.pathTo( v ) ) {
                    System.out.print( e + "   " );
                }
            }

            System.out.println();

        }

        // digrafo com ciclo negativo alcançável a partir da fonte
        EdgeWeightedDigraph withNegativeCycle = new EdgeWeightedDigraph( 3 );
        withNegativeCycle.addEdge( 0, 1, 1.0 );
        withNegativeCycle.addEdge( 1, 2, -3.0 );
        withNegativeCycle.addEdge( 2, 0, 1.0 );

        BellmanFordSP spNeg = new BellmanFordSP( withNegativeCycle, 0 );
        System.out.println( "\nDigrafo com ciclo negativo -> possui ciclo negativo alcançável? "
                + spNeg.hasNegativeCycle() );
        System.out.println( "Ciclo negativo encontrado:" );
        for ( Edge e : spNeg.negativeCycle() ) {
            System.out.println( e );
        }

    }

}
