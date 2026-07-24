package aesd.algorithms.graph.tests;

import aesd.algorithms.graph.BipartiteBFS;
import aesd.ds.implementations.nonlinear.graph.Graph;

/**
 * Teste de uso da detecção de bipartição (BipartiteBFS) em um grafo não
 * direcionado.
 *
 * @author Prof. Dr. David Buzatto
 */
public class TestBipartiteBFS {

    public static void main( String[] args ) {

        // ciclo par: bipartido
        Graph bipartite = new Graph( 4 );
        bipartite.addEdge( 0, 1 );
        bipartite.addEdge( 1, 2 );
        bipartite.addEdge( 2, 3 );
        bipartite.addEdge( 3, 0 );

        BipartiteBFS b1 = new BipartiteBFS( bipartite );
        System.out.println( "Ciclo par -> é bipartido? " + b1.isBipartite() );
        System.out.println( "Cor de 0: " + b1.color( 0 ) + " | Cor de 1: " + b1.color( 1 ) );

        // ciclo ímpar: não bipartido
        Graph notBipartite = new Graph( 3 );
        notBipartite.addEdge( 0, 1 );
        notBipartite.addEdge( 1, 2 );
        notBipartite.addEdge( 2, 0 );

        BipartiteBFS b2 = new BipartiteBFS( notBipartite );
        System.out.println( "\nCiclo ímpar -> é bipartido? " + b2.isBipartite() );
        System.out.println( "Ciclo ímpar encontrado:" );
        for ( int v : b2.oddCycle() ) {
            System.out.print( v + " " );
        }
        System.out.println();

    }

}
