/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdi3.ds.implementations.linear.tests;

import aesd.esdi3.ds.implementations.linear.ResizingArrayQueue;
import aesd.esdi3.ds.interfaces.Queue;

/**
 * Teste de uso da fila genérica com redimensionamento de array.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class TestResizingArrayQueue {
    
    public static void main( String[] args ) {
        
        Queue<Integer> fila = new ResizingArrayQueue<>();
        
        fila.enqueue( 10 );
        System.out.println( fila );
        fila.enqueue( 5 );
        System.out.println( fila );
        fila.enqueue( -2 );
        System.out.println( fila );
        fila.enqueue( 3 );
        System.out.println( fila );
        fila.enqueue( 7 );
        System.out.println( fila );
        
        System.out.println( "Dados da fila através do iterador:" );
        for ( int i : fila ) {
            System.out.print( i );
            System.out.print( " " );
        }
        
        System.out.println( "\n" );
        
        System.out.println( "Desenfileirou: " + fila.dequeue() );
        System.out.println( fila );
        System.out.println( "Desenfileirou: " + fila.dequeue() );
        System.out.println( fila );
        System.out.println( "Desenfileirou: " + fila.dequeue() );
        System.out.println( fila );
        System.out.println( "Desenfileirou: " + fila.dequeue() );
        System.out.println( fila );
        System.out.println( "Desenfileirou: " + fila.dequeue() );
        System.out.println( fila );
        //System.out.println( "Desenfileirou: " + fila.dequeue() ); // <- fila vazia!
        
        // verificação das mudanças de capacidade
        /*
        for ( int i = 0; i < 128; i++ ) {
            System.out.println( i );
            fila.enqueue( i );
        }
        fila.enqueue( 1000 );
        
        System.out.println( "" );
        
        while ( !fila.isEmpty() ) {
            fila.dequeue();
        }
        */
        
    }
    
}
