package aesd.algorithms.digraph.edgeweighted.tests;

import aesd.algorithms.digraph.edgeweighted.EdgeWeightedDirectedCycle;
import aesd.ds.implementations.nonlinear.graph.Edge;
import aesd.ds.implementations.nonlinear.graph.EdgeWeightedDigraph;

/**
 * Teste de uso da detecção de ciclo direcionado (EdgeWeightedDirectedCycle)
 * em um digrafo ponderado.
 *
 * @author Prof. Dr. David Buzatto
 */
public class TestEdgeWeightedDirectedCycle {

    public static void main( String[] args ) {

        EdgeWeightedDigraph withCycle = new EdgeWeightedDigraph( 4 );
        withCycle.addEdge( 0, 1, 0.10 );
        withCycle.addEdge( 1, 2, 0.20 );
        withCycle.addEdge( 2, 0, 0.30 );
        withCycle.addEdge( 2, 3, 0.15 );

        EdgeWeightedDirectedCycle c1 = new EdgeWeightedDirectedCycle( withCycle );
        System.out.println( "Digrafo ponderado com ciclo -> possui ciclo? " + c1.hasCycle() );
        System.out.println( "Ciclo encontrado:" );
        for ( Edge e : c1.cycle() ) {
            System.out.println( e );
        }

        EdgeWeightedDigraph dag = new EdgeWeightedDigraph( 4 );
        dag.addEdge( 0, 1, 0.10 );
        dag.addEdge( 1, 2, 0.20 );
        dag.addEdge( 2, 3, 0.15 );

        EdgeWeightedDirectedCycle c2 = new EdgeWeightedDirectedCycle( dag );
        System.out.println( "\nDAG ponderado -> possui ciclo? " + c2.hasCycle() );

    }

}
