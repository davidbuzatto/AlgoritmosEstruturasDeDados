package aesd.algorithms.digraph.tests;

import aesd.algorithms.digraph.DepthFirstOrder;
import aesd.ds.implementations.nonlinear.graph.Digraph;

/**
 * Teste de uso do cálculo de pré/pós-ordem (DepthFirstOrder) em um digrafo.
 *
 * @author Prof. Dr. David Buzatto
 */
public class TestDepthFirstOrder {

    public static void main( String[] args ) {

        // DAG
        Digraph g = new Digraph( 5 );
        g.addEdge( 0, 1 );
        g.addEdge( 0, 2 );
        g.addEdge( 1, 3 );
        g.addEdge( 2, 3 );
        g.addEdge( 2, 4 );
        g.addEdge( 3, 4 );

        DepthFirstOrder dfo = new DepthFirstOrder( g );

        System.out.println( "Pré-ordem de 0: " + dfo.pre( 0 ) );
        System.out.println( "Pós-ordem de 4: " + dfo.post( 4 ) );

        System.out.println( "\nVértices em pré-ordem:" );
        for ( int v : dfo.pre() ) {
            System.out.print( v + " " );
        }

        System.out.println( "\nVértices em pós-ordem:" );
        for ( int v : dfo.post() ) {
            System.out.print( v + " " );
        }

        System.out.println( "\nVértices em pós-ordem reversa (ordenação topológica):" );
        for ( int v : dfo.reversePost() ) {
            System.out.print( v + " " );
        }
        System.out.println();

    }

}
