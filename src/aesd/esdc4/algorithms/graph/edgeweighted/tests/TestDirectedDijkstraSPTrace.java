/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdc4.algorithms.graph.edgeweighted.tests;

import aesd.esdc4.algorithms.digraph.edgeweighted.DirectedDijkstraSP;
import aesd.esdc4.ds.implementations.nonlinear.graph.Edge;
import aesd.esdc4.ds.implementations.nonlinear.graph.EdgeWeightedDigraph;

/**
 * Teste de uso do algoritmo de Dijkstra da classe DirectedDijkstraSP.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class TestDirectedDijkstraSPTrace {
    
    public static void main( String[] args ) {
        
        EdgeWeightedDigraph g = new EdgeWeightedDigraph( 8 );
        g.addEdge( 4, 5, 0.35 );
        g.addEdge( 5, 4, 0.35 );
        g.addEdge( 4, 7, 0.37 );
        g.addEdge( 5, 7, 0.28 );
        g.addEdge( 7, 5, 0.28 );
        g.addEdge( 5, 1, 0.32 );
        g.addEdge( 0, 4, 0.38 );
        g.addEdge( 0, 2, 0.26 );
        g.addEdge( 7, 3, 0.39 );
        g.addEdge( 1, 3, 0.29 );
        g.addEdge( 2, 7, 0.34 );
        g.addEdge( 6, 2, 0.40 );
        g.addEdge( 3, 6, 0.52 );
        g.addEdge( 6, 0, 0.58 );
        g.addEdge( 6, 4, 0.93 );
        System.out.println( g );
        
        DirectedDijkstraSP dSP = new DirectedDijkstraSP( g, 0 );
        for ( Edge e : dSP.pathTo( 0 ) ) {
            System.out.println( e );
        }
        
    }
    
}
