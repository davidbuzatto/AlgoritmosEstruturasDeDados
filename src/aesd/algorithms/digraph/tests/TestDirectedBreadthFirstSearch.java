package aesd.algorithms.digraph.tests;

import aesd.algorithms.digraph.DirectedBreadthFirstSearch;
import aesd.ds.implementations.nonlinear.graph.Digraph;

/**
 * Teste de uso da busca em largura (DirectedBreadthFirstSearch) em um
 * digrafo.
 *
 * @author Prof. Dr. David Buzatto
 */
public class TestDirectedBreadthFirstSearch {

    public static void main( String[] args ) {

        Digraph g = new Digraph( 6 );
        g.addEdge( 0, 1 );
        g.addEdge( 1, 2 );
        g.addEdge( 2, 0 );
        g.addEdge( 2, 3 );
        g.addEdge( 3, 4 );
        g.addEdge( 4, 3 );
        g.addEdge( 4, 5 );

        DirectedBreadthFirstSearch bfs = new DirectedBreadthFirstSearch( g, 0 );

        System.out.println( bfs );

        System.out.println( "Há caminho direcionado até 5? " + bfs.hasPathTo( 5 ) );
        System.out.println( "Distância até 5: " + bfs.distTo( 5 ) );

        System.out.println( "\nMenor caminho direcionado até 5:" );
        for ( int v : bfs.pathTo( 5 ) ) {
            System.out.print( v + " " );
        }
        System.out.println();

    }

}
