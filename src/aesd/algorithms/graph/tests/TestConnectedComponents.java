package aesd.algorithms.graph.tests;

import aesd.algorithms.graph.ConnectedComponents;
import aesd.ds.implementations.nonlinear.graph.Graph;

/**
 * Teste de uso do cálculo de componentes conexos (ConnectedComponents) em um
 * grafo não direcionado.
 *
 * @author Prof. Dr. David Buzatto
 */
public class TestConnectedComponents {

    public static void main( String[] args ) {

        // dois componentes: {0, 1, 2} e {3, 4, 5}
        Graph g = new Graph( 6 );
        g.addEdge( 0, 1 );
        g.addEdge( 1, 2 );
        g.addEdge( 3, 4 );
        g.addEdge( 4, 5 );

        ConnectedComponents cc = new ConnectedComponents( g );

        System.out.println( "Quantidade de componentes: " + cc.count() );

        for ( int v = 0; v < g.getNumberOfVertices(); v++ ) {
            System.out.println( "Vértice " + v + " -> componente " + cc.id( v ) );
        }

        System.out.println( "\n0 e 2 conectados? " + cc.connected( 0, 2 ) );  // true
        System.out.println( "0 e 3 conectados? " + cc.connected( 0, 3 ) );  // false
        System.out.println( "Tamanho do componente de 4: " + cc.size( 4 ) );

    }

}
