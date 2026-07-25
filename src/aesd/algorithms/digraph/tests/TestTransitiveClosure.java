package aesd.algorithms.digraph.tests;

import aesd.algorithms.digraph.TransitiveClosure;
import aesd.ds.implementations.nonlinear.graph.Digraph;

/**
 * Teste de uso do cálculo do fecho transitivo (TransitiveClosure) em um
 * digrafo.
 *
 * @author Prof. Dr. David Buzatto
 */
public class TestTransitiveClosure {

    public static void main( String[] args ) {

        // DAG
        Digraph g = new Digraph( 5 );
        g.addEdge( 0, 1 );
        g.addEdge( 0, 2 );
        g.addEdge( 1, 3 );
        g.addEdge( 2, 3 );
        g.addEdge( 3, 4 );

        TransitiveClosure tc = new TransitiveClosure( g );

        System.out.println( "0 alcança 4? " + tc.reachable( 0, 4 ) );  // true
        System.out.println( "1 alcança 4? " + tc.reachable( 1, 4 ) );  // true
        System.out.println( "4 alcança 0? " + tc.reachable( 4, 0 ) );  // false
        System.out.println( "2 alcança 1? " + tc.reachable( 2, 1 ) );  // false

    }

}
