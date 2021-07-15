/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.algorithms.graph.edgeweighted.tests;

import aesd.algorithms.graph.edgeweighted.PrimMST;
import aesd.ds.implementations.nonlinear.graph.Edge;
import aesd.ds.implementations.nonlinear.graph.EdgeWeightedGraph;

/**
 * Teste de uso do algoritmo de Prim na variação eager usando a classe 
 * PrimMSTo.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class TestPrimMSTTrace {
    
    public static void main( String[] args ) {
        
        EdgeWeightedGraph g = new EdgeWeightedGraph( 8 );
        g.addEdge( 4, 5, 0.35 );
        g.addEdge( 4, 7, 0.37 );
        g.addEdge( 5, 7, 0.28 );
        g.addEdge( 0, 7, 0.16 );
        g.addEdge( 1, 5, 0.32 );
        g.addEdge( 0, 4, 0.38 );
        g.addEdge( 2, 3, 0.17 );
        g.addEdge( 1, 7, 0.19 );
        g.addEdge( 0, 2, 0.26 );
        g.addEdge( 1, 2, 0.36 );
        g.addEdge( 1, 3, 0.29 );
        g.addEdge( 2, 7, 0.34 );
        g.addEdge( 6, 2, 0.40 );
        g.addEdge( 3, 6, 0.52 );
        g.addEdge( 6, 0, 0.58 );
        g.addEdge( 6, 4, 0.93 );
        System.out.println( g );
        
        PrimMST pMST = new PrimMST( g );
        for ( Edge e : pMST.edges() ) {
            System.out.println( e );
        }
        
    }
    
}
