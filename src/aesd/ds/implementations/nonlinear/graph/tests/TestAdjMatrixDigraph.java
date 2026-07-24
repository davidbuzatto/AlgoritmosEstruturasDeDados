package aesd.ds.implementations.nonlinear.graph.tests;

import aesd.ds.implementations.nonlinear.graph.AdjMatrixDigraph;
import aesd.ds.implementations.nonlinear.graph.Edge;

/**
 * Teste de uso do digrafo com array de duas dimensões de adjacências
 * (AdjMatrixDigraph).
 *
 * @author Prof. Dr. David Buzatto
 */
public class TestAdjMatrixDigraph {

    public static void main( String[] args ) {

        AdjMatrixDigraph g = new AdjMatrixDigraph( 6 );

        g.addEdge( 0, 1 );
        g.addEdge( 0, 2 );
        g.addEdge( 1, 2 );
        g.addEdge( 2, 3 );
        g.addEdge( 3, 4 );
        g.addEdge( 4, 5 );

        System.out.println( "Vértices: " + g.getNumberOfVertices() );
        System.out.println( "Arestas:  " + g.getNumberOfEdges() );
        System.out.println( "Grau de saída de 2: " + g.outdegree( 2 ) );
        System.out.println( "Grau de entrada de 2: " + g.indegree( 2 ) );

        System.out.println( "\nArestas que saem de 2:" );
        for ( Edge e : g.adj( 2 ) ) {
            System.out.println( e );
        }

        System.out.println( "\nDigrafo inverso:" );
        System.out.println( g.reverse() );

    }

}
