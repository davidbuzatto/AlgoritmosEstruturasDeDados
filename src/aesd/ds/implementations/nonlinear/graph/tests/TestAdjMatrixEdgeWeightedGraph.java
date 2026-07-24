package aesd.ds.implementations.nonlinear.graph.tests;

import aesd.ds.implementations.nonlinear.graph.AdjMatrixEdgeWeightedGraph;
import aesd.ds.implementations.nonlinear.graph.Edge;

/**
 * Teste de uso do grafo não direcionado ponderado com array de duas
 * dimensões de adjacências (AdjMatrixEdgeWeightedGraph).
 *
 * @author Prof. Dr. David Buzatto
 */
public class TestAdjMatrixEdgeWeightedGraph {

    public static void main( String[] args ) {

        AdjMatrixEdgeWeightedGraph g = new AdjMatrixEdgeWeightedGraph( 6 );

        g.addEdge( 0, 1, 0.10 );
        g.addEdge( 0, 2, 0.20 );
        g.addEdge( 1, 2, 0.15 );
        g.addEdge( 2, 3, 0.30 );
        g.addEdge( 3, 4, 0.25 );
        g.addEdge( 4, 5, 0.05 );

        System.out.println( "Vértices: " + g.getNumberOfVertices() );
        System.out.println( "Arestas:  " + g.getNumberOfEdges() );

        System.out.println( "\nArestas adjacentes a 2:" );
        for ( Edge e : g.adj( 2 ) ) {
            System.out.println( e );
        }

        System.out.println( "\nTodas as arestas do grafo:" );
        for ( Edge e : g.edges() ) {
            System.out.println( e );
        }

    }

}
