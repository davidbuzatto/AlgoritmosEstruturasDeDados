/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdc4.ds.implementations.linear;

import aesd.esdc4.ds.interfaces.Stack;
import aesd.esdc4.ds.exceptions.EmptyStackException;
import java.util.Iterator;

/**
 * Implementação de uma pilha genérica com encadeamento simples.
 *
 * Obs: Implementação com a marcação do topo para a direita.
 * 
 * Questões a se pensar:
 *     Qual a ordem de crescimento das operações de empilhar e desempilhar?
 *     Precisa melhorar?
 *     Caso o encadeamento mude, ou seja, ao invés de usar uma referência
 *     para o nó anterior, usar uma referência ao próximo, qual o impacto
 *     na ordem de crescimento das operações?
 * 
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 
 * 4. ed. Boston: Pearson Education, 2011. 955 p.
 * 
 * @param <Type> Tipo dos valores armazenados na pilha.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class LinkedStack<Type> implements Stack<Type> {

    /*
     * Classe interna privada que define os nós da pilha.
     */
    private class Node {
        Type value;
        Node previous;
    }
    
    // topo da pilha
    private Node top;
    
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
    public void push( Type value ) {
        
        Node newNode = new Node();
        
        newNode.value = value;
        newNode.previous = top;
        top = newNode;
        
        size++;
        
    }

    @Override
    public Type peek() throws EmptyStackException {
        
        if ( !isEmpty() ) {
            return top.value;
        } else {
            throw new EmptyStackException();
        }
        
    }

    @Override
    public Type pop() throws EmptyStackException {
        
        if ( !isEmpty() ) {
            
            Type value = top.value;
            
            Node temp = top;
            top = top.previous;
            
            temp.previous = null;
            size--;
            
            return value;
            
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
    public Iterator<Type> iterator() {
        
        return new Iterator<Type>() {
            
            private Node current = top;
            
            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public Type next() {
                Type value = current.value;
                current = current.previous;
                return value;
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
            Node current = top;
            
            while ( current != null ) {
                
                if ( current == top ) {
                    sb.append( current.value ).append( " <- top" );
                } else {
                    sb.append( "\n" );
                    sb.append( current.value );
                }
                
                current = current.previous;
                
            }
        
        } else {
            sb.append( "empty stack!" );
        }
        
        sb.append( "\n" );
        
        return sb.toString();
        
    }
    
}
