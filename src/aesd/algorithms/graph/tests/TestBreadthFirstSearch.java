package aesd.algorithms.graph.tests;

import aesd.algorithms.graph.BreadthFirstSearch;
import aesd.ds.implementations.nonlinear.graph.Graph;

/**
 * Teste de uso da busca em largura (BreadthFirstSearch) em um grafo não
 * direcionado.
 *
 * @author Prof. Dr. David Buzatto
 */
public class TestBreadthFirstSearch {

    public static void main( String[] args ) {

        Graph g = new Graph( 6 );
        g.addEdge( 0, 1 );
        g.addEdge( 0, 2 );
        g.addEdge( 1, 3 );
        g.addEdge( 2, 3 );
        g.addEdge( 3, 4 );
        g.addEdge( 4, 5 );

        BreadthFirstSearch bfs = new BreadthFirstSearch( g, 0 );

        System.out.println( bfs );

        System.out.println( "Há caminho até 5? " + bfs.hasPathTo( 5 ) );
        System.out.println( "Distância até 5: " + bfs.distTo( 5 ) );

        System.out.println( "\nMenor caminho até 5:" );
        for ( int v : bfs.pathTo( 5 ) ) {
            System.out.print( v + " " );
        }
        System.out.println();

    }

}
