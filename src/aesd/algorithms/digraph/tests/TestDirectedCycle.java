package aesd.algorithms.digraph.tests;

import aesd.algorithms.digraph.DirectedCycle;
import aesd.ds.implementations.nonlinear.graph.Digraph;

/**
 * Teste de uso da detecção de ciclo direcionado (DirectedCycle) em um
 * digrafo.
 *
 * @author Prof. Dr. David Buzatto
 */
public class TestDirectedCycle {

    public static void main( String[] args ) {

        Digraph withCycle = new Digraph( 4 );
        withCycle.addEdge( 0, 1 );
        withCycle.addEdge( 1, 2 );
        withCycle.addEdge( 2, 0 );
        withCycle.addEdge( 2, 3 );

        DirectedCycle c1 = new DirectedCycle( withCycle );
        System.out.println( "Digrafo com ciclo -> possui ciclo? " + c1.hasCycle() );
        System.out.println( "Ciclo encontrado:" );
        for ( int v : c1.cycle() ) {
            System.out.print( v + " " );
        }
        System.out.println();

        // mesmas arestas, mas sem a que fecha o ciclo (2 -> 0) -> DAG
        Digraph dag = new Digraph( 4 );
        dag.addEdge( 0, 1 );
        dag.addEdge( 1, 2 );
        dag.addEdge( 2, 3 );

        DirectedCycle c2 = new DirectedCycle( dag );
        System.out.println( "\nDAG -> possui ciclo? " + c2.hasCycle() );

    }

}
