package aesd.algorithms.graph.edgeweighted.tests;

import aesd.algorithms.graph.edgeweighted.BoruvkaMST;
import aesd.ds.implementations.nonlinear.graph.Edge;
import aesd.ds.implementations.nonlinear.graph.EdgeWeightedGraph;

/**
 * Teste de uso do algoritmo de Boruvka usando a classe BoruvkaMST.
 *
 * @author Prof. Dr. David Buzatto
 */
public class TestBoruvkaMST {

    public static void main( String[] args ) {

        // mesmo grafo (tinyEWG) usado em TestKruskalMSTTrace/
        // TestLazyPrimMSTTrace/TestPrimMSTTrace, para permitir comparar o
        // peso final da MST (1.81) entre os quatro algoritmos
        EdgeWeightedGraph g = new EdgeWeightedGraph( 8 );
        g.addEdge( 4, 5, 0.35 );
        g.addEdge( 4, 7, 0.37 );
        g.addEdge( 5, 7, 0.28 );
        g.addEdge( 0, 7, 0.16 );
        g.addEdge( 1, 5, 0.32 );
        g.addEdge( 0, 4, 0.38 );
        g.addEdge( 2, 3, 0.17 );
        g.addEdge( 1, 7, 0.19 );
        g.addEdge( 0, 2, 0.26 );
        g.addEdge( 1, 2, 0.36 );
        g.addEdge( 1, 3, 0.29 );
        g.addEdge( 2, 7, 0.34 );
        g.addEdge( 6, 2, 0.40 );
        g.addEdge( 3, 6, 0.52 );
        g.addEdge( 6, 0, 0.58 );
        g.addEdge( 6, 4, 0.93 );
        System.out.println( g );

        BoruvkaMST mst = new BoruvkaMST( g );
        for ( Edge e : mst.edges() ) {
            System.out.println( e );
        }
        System.out.println( "Peso total da MST: " + mst.weight() );

    }

}
