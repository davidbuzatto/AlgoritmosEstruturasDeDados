package aesd.algorithms.digraph.tests;

import aesd.algorithms.digraph.DirectedEulerianCycle;
import aesd.ds.implementations.nonlinear.graph.Digraph;

/**
 * Teste de uso do cálculo de ciclo Euleriano (DirectedEulerianCycle) em um
 * digrafo.
 *
 * @author Prof. Dr. David Buzatto
 */
public class TestDirectedEulerianCycle {

    public static void main( String[] args ) {

        // todo vértice com indegree == outdegree -> possui ciclo Euleriano
        Digraph g = new Digraph( 4 );
        g.addEdge( 0, 1 );
        g.addEdge( 1, 2 );
        g.addEdge( 2, 3 );
        g.addEdge( 3, 0 );

        DirectedEulerianCycle ec = new DirectedEulerianCycle( g );

        System.out.println( "Possui ciclo Euleriano? " + ec.hasEulerianCycle() );
        System.out.println( "Ciclo:" );
        for ( int v : ec.cycle() ) {
            System.out.print( v + " " );
        }
        System.out.println();

        // indegree(3) != outdegree(3) -> não possui ciclo Euleriano
        Digraph noCycle = new Digraph( 4 );
        noCycle.addEdge( 0, 1 );
        noCycle.addEdge( 1, 2 );
        noCycle.addEdge( 2, 3 );

        DirectedEulerianCycle ec2 = new DirectedEulerianCycle( noCycle );
        System.out.println( "\nDigrafo em caminho -> possui ciclo Euleriano? " + ec2.hasEulerianCycle() );

    }

}
