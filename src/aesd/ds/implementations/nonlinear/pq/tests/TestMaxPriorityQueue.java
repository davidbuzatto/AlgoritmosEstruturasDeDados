/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.ds.implementations.nonlinear.pq.tests;

import aesd.ds.implementations.nonlinear.pq.MaxPriorityQueue;
import aesd.ds.interfaces.PriorityQueue;

/**
 * Teste de uso da fila de prioridades máxima.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class TestMaxPriorityQueue {
    
    public static void main( String[] args ) {
        
        PriorityQueue<Integer> maxpq = new MaxPriorityQueue<>();
        
        maxpq.insert( 5 );
        System.out.println( maxpq );
        maxpq.insert( 9 );
        System.out.println( maxpq );
        maxpq.insert( 2 );
        System.out.println( maxpq );
        maxpq.insert( 8 );
        System.out.println( maxpq );
        maxpq.insert( 7 );
        System.out.println( maxpq );
        maxpq.insert( 3 );
        System.out.println( maxpq );
        maxpq.insert( 2 );
        System.out.println( maxpq );
        
        System.out.println( "Dados da fila de prioridades máxima através do iterador:" );
        for ( int i : maxpq ) {
            System.out.print( i );
            System.out.print( " " );
        }
        
        System.out.println( "\n" );
        
        System.out.println( "Removeu máximo: " + maxpq.delete() );
        System.out.println( maxpq );
        System.out.println( "Removeu máximo: " + maxpq.delete() );
        System.out.println( maxpq );
        System.out.println( "Removeu máximo: " + maxpq.delete() );
        System.out.println( maxpq );
        System.out.println( "Removeu máximo: " + maxpq.delete() );
        System.out.println( maxpq );
        System.out.println( "Removeu máximo: " + maxpq.delete() );
        System.out.println( maxpq );
        System.out.println( "Removeu máximo: " + maxpq.delete() );
        System.out.println( maxpq );
        System.out.println( "Removeu máximo: " + maxpq.delete() );
        System.out.println( maxpq );
        
        // fila de prioridades máxima vazia vazia!
        //System.out.println( "Removeu máximo: " + maxpq.delete() ); 
        
    }
    
}
