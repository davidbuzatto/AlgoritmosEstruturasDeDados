package aesd.algorithms.graph.tests;

import aesd.algorithms.graph.EulerianPath;
import aesd.ds.implementations.nonlinear.graph.Graph;

/**
 * Teste de uso do cálculo de caminho Euleriano (EulerianPath) em um grafo
 * não direcionado.
 *
 * @author Prof. Dr. David Buzatto
 */
public class TestEulerianPath {

    public static void main( String[] args ) {

        // exatamente dois vértices de grau ímpar (0 e 3) -> possui caminho
        // Euleriano entre eles, mas não ciclo
        Graph g = new Graph( 4 );
        g.addEdge( 0, 1 );
        g.addEdge( 1, 2 );
        g.addEdge( 2, 3 );

        EulerianPath ep = new EulerianPath( g );

        System.out.println( "Possui caminho Euleriano? " + ep.hasEulerianPath() );
        System.out.println( "Caminho:" );
        for ( int v : ep.path() ) {
            System.out.print( v + " " );
        }
        System.out.println();

    }

}
