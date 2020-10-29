/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdc4.ds.implementations;

import aesd.esdc4.ds.interfaces.Stack;

/**
 * Teste de uso da pilha genérica com capacidade fixa.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class TestFixedCapacityStack {
    
    public static void main( String[] args ) {
        
        Stack<Integer> pilha = new FixedCapacityStack<>( 5 );
        
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
        //pilha.push( 15 ); // <- estouro da pilha!
        
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
