package aesd.algorithms.graph.tests;

import aesd.algorithms.graph.EulerianCycle;
import aesd.ds.implementations.nonlinear.graph.Graph;

/**
 * Teste de uso do cálculo de ciclo Euleriano (EulerianCycle) em um grafo não
 * direcionado.
 *
 * @author Prof. Dr. David Buzatto
 */
public class TestEulerianCycle {

    public static void main( String[] args ) {

        // todos os vértices com grau par -> possui ciclo Euleriano
        Graph g = new Graph( 4 );
        g.addEdge( 0, 1 );
        g.addEdge( 1, 2 );
        g.addEdge( 2, 3 );
        g.addEdge( 3, 0 );

        EulerianCycle ec = new EulerianCycle( g );

        System.out.println( "Possui ciclo Euleriano? " + ec.hasEulerianCycle() );
        System.out.println( "Ciclo:" );
        for ( int v : ec.cycle() ) {
            System.out.print( v + " " );
        }
        System.out.println();

        // vértice de grau ímpar -> não possui ciclo Euleriano
        Graph noCycle = new Graph( 3 );
        noCycle.addEdge( 0, 1 );
        noCycle.addEdge( 1, 2 );

        EulerianCycle ec2 = new EulerianCycle( noCycle );
        System.out.println( "\nGrafo com vértices de grau ímpar -> possui ciclo Euleriano? "
                + ec2.hasEulerianCycle() );

    }

}
