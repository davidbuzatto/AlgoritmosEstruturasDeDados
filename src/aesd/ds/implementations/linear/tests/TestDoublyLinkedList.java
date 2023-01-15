package aesd.ds.implementations.linear.tests;

import aesd.ds.implementations.linear.DoublyLinkedList;
import aesd.ds.interfaces.List;

/**
 * Teste de uso da lista genérica com encadeamento duplo.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class TestDoublyLinkedList {
    
    public static void main( String[] args ) {
        
        List<Integer> lista = new DoublyLinkedList<>();
        
        lista.add( 10 );                    // inserção no fim
        System.out.println( lista );
        lista.add( 2 );                     // inserção no fim
        System.out.println( lista );
        lista.add( 0, 5 );                  // inserção no início
        System.out.println( lista );
        lista.add( 0, 15 );                 // inserção no início
        System.out.println( lista );
        lista.add( lista.getSize(), 7 );    // inserção no fim
        System.out.println( lista );
        lista.add( lista.getSize(), 8 );    // inserção no fim
        System.out.println( lista );
        lista.add( 2, 32 );                 // inserção no meio
        System.out.println( lista );
        lista.add( 5, 21 );                 // inserção no meio
        System.out.println( lista );
        
        
        /*lista.add( -1, -9 );                  // inserção em posição inválida
        System.out.println( lista );*/
        
        /*lista.add( lista.getSize() + 1, 3 );  // inserção em posição inválida
        System.out.println( lista );*/
        
        System.out.println( "Dados da lista através do iterador:" );
        for ( int i : lista ) {
            System.out.print( i );
            System.out.print( " " );
        }
        
        System.out.println( "\n" );
        
        System.out.println( "Removeu do Início: " + lista.remove( 0 ) );
        System.out.println( lista );
        System.out.println( "Removeu do Fim: " + lista.remove( lista.getSize() - 1 ) );
        System.out.println( lista );
        System.out.println( "Removeu da posição 4: " + lista.remove( 4 ) );
        System.out.println( lista );
        System.out.println( "Removeu da posição 1: " + lista.remove( 1 ) );
        System.out.println( lista );
        System.out.println( "Removeu da posição 1: " + lista.remove( 1 ) );
        System.out.println( lista );
        System.out.println( "Removeu do Início: " + lista.remove( 0 ) );
        System.out.println( lista );
        System.out.println( "Removeu do Fim: " + lista.remove( lista.getSize() - 1 ) );
        System.out.println( lista );
        System.out.println( "Removeu do Fim: " + lista.remove( lista.getSize() - 1 ) );
        System.out.println( lista );
        
        //System.out.println( "Removeu do Fim: " + lista.remove( lista.getSize() - 1 ) ); // <- lista vazia!
        //System.out.println( "Removeu do Inicio: " + lista.remove( 0 ) );                // <- lista vazia!
        //System.out.println( "Removeu da posição 1: " + lista.remove( 1 ) );             // <- lista vazia!
        
    }
    
}
