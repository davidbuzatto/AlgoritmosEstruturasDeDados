package aesd.algorithms.graph.tests;

import aesd.algorithms.graph.GraphBasicAlgorithms;
import aesd.ds.implementations.nonlinear.graph.Graph;

/**
 * Teste de uso dos algoritmos básicos de grafo (GraphBasicAlgorithms).
 *
 * @author Prof. Dr. David Buzatto
 */
public class TestGraphBasicAlgorithms {

    public static void main( String[] args ) {

        Graph g = new Graph( 5 );
        g.addEdge( 0, 1 );
        g.addEdge( 0, 2 );
        g.addEdge( 0, 3 );
        g.addEdge( 1, 2 );
        g.addEdge( 4, 4 ); // laço

        System.out.println( "Grau de 0: " + GraphBasicAlgorithms.degree( g, 0 ) );
        System.out.println( "Maior grau: " + GraphBasicAlgorithms.maxDegree( g ) );
        System.out.println( "Grau médio: " + GraphBasicAlgorithms.mediumDegree( g ) );
        System.out.println( "Quantidade de laços: " + GraphBasicAlgorithms.loopQuantity( g ) );

    }

}
