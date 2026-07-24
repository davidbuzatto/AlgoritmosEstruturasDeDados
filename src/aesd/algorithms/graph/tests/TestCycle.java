package aesd.algorithms.graph.tests;

import aesd.algorithms.graph.Cycle;
import aesd.ds.implementations.nonlinear.graph.Graph;

/**
 * Teste de uso da detecção de ciclo (Cycle) em um grafo não direcionado.
 *
 * @author Prof. Dr. David Buzatto
 */
public class TestCycle {

    public static void main( String[] args ) {

        Graph withCycle = new Graph( 4 );
        withCycle.addEdge( 0, 1 );
        withCycle.addEdge( 1, 2 );
        withCycle.addEdge( 2, 0 );
        withCycle.addEdge( 2, 3 );

        Cycle c1 = new Cycle( withCycle );
        System.out.println( "Grafo com ciclo -> possui ciclo? " + c1.hasCycle() );
        System.out.println( "Ciclo encontrado:" );
        for ( int v : c1.cycle() ) {
            System.out.print( v + " " );
        }
        System.out.println();

        Graph withoutCycle = new Graph( 4 );
        withoutCycle.addEdge( 0, 1 );
        withoutCycle.addEdge( 1, 2 );
        withoutCycle.addEdge( 2, 3 );

        Cycle c2 = new Cycle( withoutCycle );
        System.out.println( "\nGrafo sem ciclo (árvore) -> possui ciclo? " + c2.hasCycle() );

    }

}
