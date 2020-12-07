/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdc4.ds.implementations.nonlinear.pq.tests;

import aesd.esdc4.ds.implementations.nonlinear.pq.IndexedMinPriorityQueue;
import aesd.esdc4.ds.interfaces.IndexedPriorityQueue;

/**
 * Teste de uso da fila de prioridades mínima indexada.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class TestIndexedMinPriorityQueue {
    
    public static void main( String[] args ) {
        
        IndexedPriorityQueue<String> indMinPq = new IndexedMinPriorityQueue<>( 100 );
        
        indMinPq.insert( 1, "Maria" );
        System.out.println( indMinPq );
        indMinPq.insert( 2, "Aurora" );
        System.out.println( indMinPq );
        indMinPq.insert( 3, "Fernanda" );
        System.out.println( indMinPq );
        indMinPq.insert( 4, "David" );
        System.out.println( indMinPq );
        indMinPq.insert( 5, "Joaquina" );
        System.out.println( indMinPq );
        indMinPq.insert( 6, "Roberto" );
        System.out.println( indMinPq );
        
        System.out.println( "Dados da fila de prioridades máxima indexada através do iterador:" );
        for ( int i : indMinPq ) {
            System.out.print( i );
            System.out.print( " " );
        }
        
        System.out.println( "\n" );
        
        System.out.println( "Removeu máximo: " + indMinPq.delete() );
        System.out.println( indMinPq );
        System.out.println( "Removeu máximo: " + indMinPq.delete() );
        System.out.println( indMinPq );
        System.out.println( "Removeu máximo: " + indMinPq.delete() );
        System.out.println( indMinPq );
        System.out.println( "Removeu máximo: " + indMinPq.delete() );
        System.out.println( indMinPq );
        System.out.println( "Removeu máximo: " + indMinPq.delete() );
        System.out.println( indMinPq );
        System.out.println( "Removeu máximo: " + indMinPq.delete() );
        System.out.println( indMinPq );
        
        // fila de prioridades máxima indexada vazia vazia!
        //System.out.println( "Removeu máximo: " + maxpq.delete() ); 
        
    }
    
}
