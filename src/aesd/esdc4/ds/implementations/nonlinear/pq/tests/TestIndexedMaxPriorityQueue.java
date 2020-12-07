/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdc4.ds.implementations.nonlinear.pq.tests;

import aesd.esdc4.ds.implementations.nonlinear.pq.IndexedMaxPriorityQueue;
import aesd.esdc4.ds.interfaces.IndexedPriorityQueue;

/**
 * Teste de uso da fila de prioridades máxima indexada.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class TestIndexedMaxPriorityQueue {
    
    public static void main( String[] args ) {
        
        IndexedPriorityQueue<String> indMaxPq = new IndexedMaxPriorityQueue<>( 100 );
        
        indMaxPq.insert( 1, "Maria" );
        System.out.println( indMaxPq );
        indMaxPq.insert( 2, "Aurora" );
        System.out.println( indMaxPq );
        indMaxPq.insert( 3, "Fernanda" );
        System.out.println( indMaxPq );
        indMaxPq.insert( 4, "David" );
        System.out.println( indMaxPq );
        indMaxPq.insert( 5, "Joaquina" );
        System.out.println( indMaxPq );
        indMaxPq.insert( 6, "Roberto" );
        System.out.println( indMaxPq );
        
        System.out.println( "Dados da fila de prioridades máxima indexada através do iterador:" );
        for ( int i : indMaxPq ) {
            System.out.print( i );
            System.out.print( " " );
        }
        
        System.out.println( "\n" );
        
        System.out.println( "Removeu máximo: " + indMaxPq.delete() );
        System.out.println( indMaxPq );
        System.out.println( "Removeu máximo: " + indMaxPq.delete() );
        System.out.println( indMaxPq );
        System.out.println( "Removeu máximo: " + indMaxPq.delete() );
        System.out.println( indMaxPq );
        System.out.println( "Removeu máximo: " + indMaxPq.delete() );
        System.out.println( indMaxPq );
        System.out.println( "Removeu máximo: " + indMaxPq.delete() );
        System.out.println( indMaxPq );
        System.out.println( "Removeu máximo: " + indMaxPq.delete() );
        System.out.println( indMaxPq );
        
        // fila de prioridades máxima indexada vazia vazia!
        //System.out.println( "Removeu máximo: " + maxpq.delete() ); 
        
    }
    
}
