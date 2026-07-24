package aesd.ds.implementations.nonlinear.graph.tests;

import aesd.ds.implementations.nonlinear.graph.AdjMatrixGraph;

/**
 * Teste de uso do grafo não direcionado com array de duas dimensões de
 * adjacências (AdjMatrixGraph).
 *
 * @author Prof. Dr. David Buzatto
 */
public class TestAdjMatrixGraph {

    public static void main( String[] args ) {

        AdjMatrixGraph g = new AdjMatrixGraph( 6 );

        g.addEdge( 0, 1 );
        g.addEdge( 0, 2 );
        g.addEdge( 1, 2 );
        g.addEdge( 2, 3 );
        g.addEdge( 3, 4 );
        g.addEdge( 4, 5 );

        System.out.println( "Vértices: " + g.getNumberOfVertices() );
        System.out.println( "Arestas:  " + g.getNumberOfEdges() );
        System.out.println( "Grau de 2: " + g.degree( 2 ) );

        System.out.println( "\nAdjacentes a 2:" );
        for ( int v : g.adj( 2 ) ) {
            System.out.print( v + " " );
        }
        System.out.println();

    }

}
