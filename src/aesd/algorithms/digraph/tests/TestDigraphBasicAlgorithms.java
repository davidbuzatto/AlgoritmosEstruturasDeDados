package aesd.algorithms.digraph.tests;

import aesd.algorithms.digraph.DigraphBasicAlgorithms;
import aesd.ds.implementations.nonlinear.graph.Digraph;

/**
 * Teste de uso dos algoritmos básicos de digrafo (DigraphBasicAlgorithms).
 *
 * @author Prof. Dr. David Buzatto
 */
public class TestDigraphBasicAlgorithms {

    public static void main( String[] args ) {

        Digraph g = new Digraph( 4 );
        g.addEdge( 0, 1 );
        g.addEdge( 0, 2 );
        g.addEdge( 1, 2 );
        g.addEdge( 3, 3 ); // laço

        System.out.println( "Grau de saída de 0: " + DigraphBasicAlgorithms.outDegree( g, 0 ) );
        System.out.println( "Grau de entrada de 2: " + DigraphBasicAlgorithms.inDegree( g, 2 ) );
        System.out.println( "Maior grau de saída: " + DigraphBasicAlgorithms.maxOutDegree( g ) );
        System.out.println( "Maior grau de entrada: " + DigraphBasicAlgorithms.maxInDegree( g ) );
        System.out.println( "Grau médio: " + DigraphBasicAlgorithms.mediumDegree( g ) );
        System.out.println( "Quantidade de laços: " + DigraphBasicAlgorithms.loopQuantity( g ) );

    }

}
