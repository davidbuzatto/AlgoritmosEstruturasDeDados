package aesd.algorithms.digraph.tests;

import aesd.algorithms.digraph.TarjanSCC;
import aesd.ds.implementations.nonlinear.graph.Digraph;

/**
 * Teste de uso do cálculo de componentes fortemente conexos (TarjanSCC) em
 * um digrafo.
 *
 * @author Prof. Dr. David Buzatto
 */
public class TestTarjanSCC {

    public static void main( String[] args ) {

        // três componentes fortes: {0, 1, 2}, {3, 4} e {5}
        Digraph g = new Digraph( 6 );
        g.addEdge( 0, 1 );
        g.addEdge( 1, 2 );
        g.addEdge( 2, 0 );
        g.addEdge( 2, 3 );
        g.addEdge( 3, 4 );
        g.addEdge( 4, 3 );
        g.addEdge( 4, 5 );

        TarjanSCC scc = new TarjanSCC( g );

        System.out.println( "Quantidade de componentes fortes: " + scc.count() );

        for ( int v = 0; v < g.getNumberOfVertices(); v++ ) {
            System.out.println( "Vértice " + v + " -> componente " + scc.id( v ) );
        }

        System.out.println( "\n0 e 2 fortemente conectados? " + scc.stronglyConnected( 0, 2 ) );  // true
        System.out.println( "0 e 5 fortemente conectados? " + scc.stronglyConnected( 0, 5 ) );  // false

    }

}
