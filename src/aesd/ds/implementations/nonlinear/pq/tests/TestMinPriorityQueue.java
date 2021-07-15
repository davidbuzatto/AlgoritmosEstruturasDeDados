/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.ds.implementations.nonlinear.pq.tests;

import aesd.ds.implementations.nonlinear.pq.MinPriorityQueue;
import aesd.ds.interfaces.PriorityQueue;

/**
 * Teste de uso da fila de prioridades mínima.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class TestMinPriorityQueue {
    
    public static void main( String[] args ) {
        
        PriorityQueue<Integer> minpq = new MinPriorityQueue<>();
        
        minpq.insert( 5 );
        System.out.println( minpq );
        minpq.insert( 9 );
        System.out.println( minpq );
        minpq.insert( 2 );
        System.out.println( minpq );
        minpq.insert( 8 );
        System.out.println( minpq );
        minpq.insert( 7 );
        System.out.println( minpq );
        minpq.insert( 3 );
        System.out.println( minpq );
        minpq.insert( 2 );
        System.out.println( minpq );
        
        System.out.println( "Dados da fila de prioridades mínima através do iterador:" );
        for ( int i : minpq ) {
            System.out.print( i );
            System.out.print( " " );
        }
        
        System.out.println( "\n" );
        
        System.out.println( "Removeu mínimo: " + minpq.delete() );
        System.out.println( minpq );
        System.out.println( "Removeu mínimo: " + minpq.delete() );
        System.out.println( minpq );
        System.out.println( "Removeu mínimo: " + minpq.delete() );
        System.out.println( minpq );
        System.out.println( "Removeu mínimo: " + minpq.delete() );
        System.out.println( minpq );
        System.out.println( "Removeu mínimo: " + minpq.delete() );
        System.out.println( minpq );
        System.out.println( "Removeu mínimo: " + minpq.delete() );
        System.out.println( minpq );
        System.out.println( "Removeu mínimo: " + minpq.delete() );
        System.out.println( minpq );
        
        // fila de prioridades mínima vazia vazia!
        //System.out.println( "Removeu mínimo: " + minpq.delete() ); 
        
    }
    
}
