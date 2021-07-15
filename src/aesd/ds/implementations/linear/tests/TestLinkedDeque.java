/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.ds.implementations.linear.tests;

import aesd.ds.implementations.linear.LinkedDeque;
import aesd.ds.interfaces.Deque;

/**
 * Teste de uso da deque genérica com encadeamento.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class TestLinkedDeque {
    
    public static void main( String[] args ) {
        
        Deque<Integer> deque = new LinkedDeque<>();
        
        deque.addFirst( 10 );
        System.out.println( deque );
        deque.addLast( 5 );
        System.out.println( deque );
        deque.addFirst( -2 );
        System.out.println( deque );
        deque.addLast( 3 );
        System.out.println( deque );
        deque.addFirst( 7 );
        System.out.println( deque );
        
        System.out.println( "Dados da deque através do iterador:" );
        for ( int i : deque ) {
            System.out.print( i );
            System.out.print( " " );
        }
        
        System.out.println( "\n" );
        
        System.out.println( "Removeu do Início: " + deque.removeFirst());
        System.out.println( deque );
        System.out.println( "Removeu do Fim: " + deque.removeLast() );
        System.out.println( deque );
        System.out.println( "Removeu do Início: " + deque.removeFirst() );
        System.out.println( deque );
        System.out.println( "Removeu do Fim: " + deque.removeLast() );
        System.out.println( deque );
        System.out.println( "Removeu do Início: " + deque.removeFirst() );
        System.out.println( deque );
        //System.out.println( "Removeu do Fim: " + deque.removeLast() ); // <- deque vazia!
        //System.out.println( "Removeu do Inicio: " + deque.removeFirst() ); // <- deque vazia!
        
    }
    
}
