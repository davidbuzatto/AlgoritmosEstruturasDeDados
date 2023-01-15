package aesd.ds.implementations.linear.tests;

import aesd.ds.implementations.linear.FixedCapacityQueue;
import aesd.ds.interfaces.Queue;

/**
 * Teste de uso da fila genérica com capacidade fixa.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class TestFixedCapacityQueue {
    
    public static void main( String[] args ) {
        
        Queue<Integer> fila = new FixedCapacityQueue<>( 5 );
        
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
        //fila.enqueue( 15 ); // <- estouro da fila!
        
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
        
    }
    
}
