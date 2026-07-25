package aesd.algorithms.digraph.tests;

import aesd.algorithms.digraph.DirectedEulerianPath;
import aesd.ds.implementations.nonlinear.graph.Digraph;

/**
 * Teste de uso do cálculo de caminho Euleriano (DirectedEulerianPath) em um
 * digrafo.
 *
 * @author Prof. Dr. David Buzatto
 */
public class TestDirectedEulerianPath {

    public static void main( String[] args ) {

        // outdegree(0) - indegree(0) == 1 e indegree(3) - outdegree(3) == 1
        // -> possui caminho Euleriano de 0 até 3, mas não ciclo
        Digraph g = new Digraph( 4 );
        g.addEdge( 0, 1 );
        g.addEdge( 1, 2 );
        g.addEdge( 2, 3 );

        DirectedEulerianPath ep = new DirectedEulerianPath( g );

        System.out.println( "Possui caminho Euleriano? " + ep.hasEulerianPath() );
        System.out.println( "Caminho:" );
        for ( int v : ep.path() ) {
            System.out.print( v + " " );
        }
        System.out.println();

    }

}
