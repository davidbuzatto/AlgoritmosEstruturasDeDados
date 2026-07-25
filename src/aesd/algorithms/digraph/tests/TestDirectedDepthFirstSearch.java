package aesd.algorithms.digraph.tests;

import aesd.algorithms.digraph.DirectedDepthFirstSearch;
import aesd.ds.implementations.nonlinear.graph.Digraph;

/**
 * Teste de uso da busca em profundidade (DirectedDepthFirstSearch) em um
 * digrafo.
 *
 * @author Prof. Dr. David Buzatto
 */
public class TestDirectedDepthFirstSearch {

    public static void main( String[] args ) {

        Digraph g = new Digraph( 6 );
        g.addEdge( 0, 1 );
        g.addEdge( 1, 2 );
        g.addEdge( 2, 0 );
        g.addEdge( 2, 3 );
        g.addEdge( 3, 4 );
        g.addEdge( 4, 3 );
        g.addEdge( 4, 5 );

        DirectedDepthFirstSearch dfs = new DirectedDepthFirstSearch( g, 0 );

        System.out.println( dfs );

        System.out.println( "Há caminho direcionado até 5? " + dfs.hasPathTo( 5 ) );
        System.out.println( "5 está marcado? " + dfs.isMarked( 5 ) );

        System.out.println( "\nCaminho direcionado até 5:" );
        for ( int v : dfs.pathTo( 5 ) ) {
            System.out.print( v + " " );
        }
        System.out.println();

    }

}
