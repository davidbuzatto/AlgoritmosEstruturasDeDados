package aesd.algorithms.graph.tests;

import aesd.algorithms.graph.DepthFirstSearch;
import aesd.ds.implementations.nonlinear.graph.Graph;

/**
 * Teste de uso da busca em profundidade (DepthFirstSearch) em um grafo não
 * direcionado.
 *
 * @author Prof. Dr. David Buzatto
 */
public class TestDepthFirstSearch {

    public static void main( String[] args ) {

        Graph g = new Graph( 6 );
        g.addEdge( 0, 1 );
        g.addEdge( 0, 2 );
        g.addEdge( 1, 3 );
        g.addEdge( 2, 3 );
        g.addEdge( 3, 4 );
        g.addEdge( 4, 5 );

        DepthFirstSearch dfs = new DepthFirstSearch( g, 0 );

        System.out.println( dfs );

        System.out.println( "Há caminho até 5? " + dfs.hasPathTo( 5 ) );

        System.out.println( "\nCaminho até 5:" );
        for ( int v : dfs.pathTo( 5 ) ) {
            System.out.print( v + " " );
        }
        System.out.println();

    }

}
