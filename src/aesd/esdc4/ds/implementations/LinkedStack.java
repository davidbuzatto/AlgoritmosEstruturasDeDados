/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdc4.ds.implementations;

import aesd.esdc4.ds.interfaces.Stack;
import aesd.esdc4.ds.exceptions.EmptyStackException;
import java.util.Iterator;

/**
 * Implementação de uma pilha com encadeamento simples.
 *
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 
 * 4. ed. Boston: Pearson Education, 2011. 955 p.
 * 
 * @param <Item> Tipo dos itens armazenados na pilha.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class LinkedStack<Item> implements Stack<Item> {

    /*
     * Classe interna privada que define os nós da pilha.
     */
    private class Node<Item> {
        Item item;
        Node<Item> previous;
    }
    
    // topo da pilha
    private Node<Item> top;
    
    // tamanho da pilha
    private int size;
    
    /**
     * Constrói uma pilha vazia.
     */
    public LinkedStack() {
        top = null;   // redundante, apenas para mostrar o que acontece
        size = 0;     // redundante também
    }
    
    @Override
    public void push( Item item ) {
        
        Node<Item> newNode = new Node<>();
        
        newNode.item = item;
        newNode.previous = top;
        top = newNode;
        
        size++;
        
    }

    @Override
    public Item peek() throws EmptyStackException {
        
        if ( !isEmpty() ) {
            return top.item;
        } else {
            throw new EmptyStackException();
        }
        
    }

    @Override
    public Item pop() throws EmptyStackException {
        
        if ( !isEmpty() ) {
            
            Item item = top.item;
            
            Node<Item> temp = top;
            top = top.previous;
            
            temp.previous = null;
            size--;
            
            return item;
            
        } else {
            throw new EmptyStackException();
        }
        
    }

    @Override
    public void clear() {
        
        while ( !isEmpty() ) {
            pop();
        }
        
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public Iterator<Item> iterator() {
        
        return new Iterator<Item>() {
            
            private Node<Item> current = top;
            
            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public Item next() {
                Item item = current.item;
                current = current.previous;
                return item;
            }
            
            @Override
            public void remove() {
                throw new UnsupportedOperationException( "Not supported." );
            }
            
        };
        
    }

    @Override
    public String toString() {
        
        StringBuilder sb = new StringBuilder();
        
        if ( !isEmpty() ) {
            
            // percorrendo o encadeamento
            Node<Item> current = top;
            
            while ( current != null ) {
                
                if ( current == top ) {
                    sb.append( current.item ).append( " <- top" );
                } else {
                    sb.append( "\n" );
                    sb.append( current.item );
                }
                
                current = current.previous;
                
            }
        
        } else {
            sb.append( "empty stack!" );
        }
        
        sb.append( "\n" );
        
        return sb.toString();
        
    }
    
    /**
     * Testes da pilha.
     * 
     * @param args
     */
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
