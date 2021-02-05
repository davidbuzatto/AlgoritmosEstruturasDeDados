/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdi3.ds.implementations.linear.tests;

import aesd.esdi3.ds.implementations.linear.LinkedStack;
import aesd.esdi3.ds.interfaces.Stack;

/**
 * Teste de uso da pilha genérica com encadeamento.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class TestLinkedStack {
    
    public static void main( String[] args ) {
        
        Stack<Integer> pilha = new LinkedStack<>();
        
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
        
    }
    
}
