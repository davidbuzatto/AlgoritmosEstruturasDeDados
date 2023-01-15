package aesd.ds.implementations.linear.tests;

import aesd.ds.implementations.linear.ResizingArrayStack;
import aesd.ds.interfaces.Stack;

/**
 * Teste de uso da pilha genérica com redimensionamento de array.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class TestResizingArrayStack {
    
    public static void main( String[] args ) {
        
        Stack<Integer> pilha = new ResizingArrayStack<>();
        
        pilha.push( 10 );
        System.out.println( pilha );
        pilha.push( 5 );
        System.out.println( pilha );
        pilha.push( -2 );
        System.out.println( pilha );
        pilha.push( 3 );
        System.out.println( pilha );
        pilha.push( 7 );
        System.out.println( pilha );
        
        System.out.println( "Dados da pilha através do iterador:" );
        for ( int i : pilha ) {
            System.out.print( i );
            System.out.print( " " );
        }
        
        System.out.println( "\n" );
        
        System.out.println( "Desempilhou: " + pilha.pop() );
        System.out.println( pilha );
        System.out.println( "Desempilhou: " + pilha.pop() );
        System.out.println( pilha );
        System.out.println( "Desempilhou: " + pilha.pop() );
        System.out.println( pilha );
        System.out.println( "Desempilhou: " + pilha.pop() );
        System.out.println( pilha );
        System.out.println( "Desempilhou: " + pilha.pop() );
        System.out.println( pilha );
        //System.out.println( "Desempilhou: " + pilha.pop() ); // <- pilha vazia!
        
        // verificação das mudanças de capacidade
        /*
        for ( int i = 0; i < 128; i++ ) {
            System.out.println( i );
            pilha.push( i );
        }
        pilha.push( 1000 );
        
        System.out.println( "" );
        pilha.clear();
        */
        
    }
    
}
