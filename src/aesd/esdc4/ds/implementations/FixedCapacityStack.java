/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdc4.ds.implementations;

import aesd.esdc4.ds.interfaces.Stack;
import aesd.esdc4.ds.exceptions.EmptyStackException;
import aesd.esdc4.ds.exceptions.StackOverflowException;
import java.util.Iterator;

/**
 * Implementação de uma pilha genérica com capacidade fixa.
 * 
 * Obs: Implementação com a marcação do topo para a direita (fim do array).
 * 
 * Questões a se pensar:
 *     Qual a ordem de crescimento das operações de empilhar e desempilhar?
 *     Precisa melhorar?
 * 
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 
 * 4. ed. Boston: Pearson Education, 2011. 955 p.
 * 
 * @param <Item> Tipo dos itens armazenados na pilha.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class FixedCapacityStack<Item> implements Stack<Item> {

    // itens armazenados na pilha
    private Item[] items;
    
    // topo da pilha
    private int top;
    
    // tamanho da pilha
    private int size;
    
    // tamanho máximo suportado pela pilha
    private int maxSize;
    
    /**
     * Constrói uma pilha vazia que suporta dez itens.
     */
    public FixedCapacityStack() {
        this( 10 );
    }
    
    /**
     * Constrói uma pilha vazia de tamanho especificado.
     * 
     * @param max Tamanho máximo da pilha.
     */
    @SuppressWarnings( "unchecked" )
    public FixedCapacityStack( int max ) {
        maxSize = max;
        items = (Item[]) new Object[maxSize];
        top = -1;
    }
    
    @Override
    public void push( Item item ) throws StackOverflowException {
        
        if ( size < maxSize ) {
            top++;
            items[top] = item;
            size++;
        } else {
            throw new StackOverflowException();
        }
        
    }

    @Override
    public Item peek() throws EmptyStackException {
        
        if ( !isEmpty() ) {
            return items[top];
        } else {
            throw new EmptyStackException();
        }
        
    }

    @Override
    public Item pop() throws EmptyStackException {
        
        if ( !isEmpty() ) {
            Item item = items[top];
            items[top] = null;      // marca como null para coleta de lixo
            top--;
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
            
            private int current = top;
            
            @Override
            public boolean hasNext() {
                return current >= 0;
            }

            @Override
            public Item next() {
                return items[current--];
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
            
            // percorrendo o array de itens
            for ( int i = top; i >= 0; i-- ) {

                if ( i == top ) {
                    sb.append( items[i] ).append( " <- top" );
                } else {
                    sb.append( "\n" );
                    sb.append( items[i] );
                }

            }
        
        } else {
            sb.append( "empty stack!" );
        }
        
        sb.append( "\n" );
        
        return sb.toString();
        
    }
    
}
