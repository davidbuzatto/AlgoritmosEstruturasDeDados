/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdi3.ds.implementations.linear;

import aesd.esdi3.ds.interfaces.Deque;
import aesd.esdi3.ds.exceptions.EmptyDequeException;
import java.util.Iterator;

/**
 * Implementação de uma deque genérica com encadeamento.
 * 
 * Obs: Implementação com a marcação do início/primeiro para a esquerda e o
 * fim/último para direita.
 * 
 * Questões a se pensar:
 *     Qual a ordem de crescimento das operações de inserção e remoção?
 *     Há como melhorar?
 *     Mudar a topologia resolve?
 *     Caso o encadeamento mude, ou seja, ao invés de usar uma referência
 *     para o nó posterior, usar uma referência ao anterior, qual o impacto
 *     na ordem de crescimento das operações?
 *     E se o encadeamento for duplo?
 * 
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 
 * 4. ed. Boston: Pearson Education, 2011. 955 p.
 * 
 * @param <Type> Tipo dos valores armazenados na deque.
 *
 * @author Prof. Dr. David Buzatto
 */
public class LinkedDeque<Type> implements Deque<Type> {

    /*
     * Classe interna privada que define os nós da deque.
     * A referência next é direcionada à direita.
     */
    private class Node {
        Type value;
        Node next;
    }
    
    // início e fim da deque
    private Node first;
    private Node last;
    
    // tamanho da deque
    private int size;
    
    /**
     * Constrói uma deque vazia.
     */
    public LinkedDeque() {
        first = null;   // redundante, apenas para mostrar o que acontece
        last = null;    // redundante também
        size = 0;       // redundante também
    }
    
    @Override
    public void addFirst( Type value ) {
        
        Node newNode = new Node();
        newNode.value = value;
        newNode.next = null;  // redundante...
        
        if ( isEmpty()) {
            first = newNode;
            last = newNode;
        } else {
            newNode.next = first;
            first = newNode;
        }
        
        size++;
        
    }
    
    @Override
    public void addLast( Type value ) {
        
        Node newNode = new Node();
        newNode.value = value;
        newNode.next = null;  // redundante...
        
        if ( isEmpty()) {
            first = newNode;
            last = newNode;
        } else {
            last.next = newNode;
            last = newNode;
        }
        
        size++;
        
    }

    @Override
    public Type peekFirst() throws EmptyDequeException {
        
        if ( !isEmpty() ) {
            return first.value;
        } else {
            throw new EmptyDequeException();
        }
        
    }
    
    @Override
    public Type peekLast() throws EmptyDequeException {
        
        if ( !isEmpty() ) {
            return last.value;
        } else {
            throw new EmptyDequeException();
        }
        
    }

    @Override
    public Type removeFirst() throws EmptyDequeException {
        
        if ( !isEmpty()) {
            
            Type value = first.value;
            
            // início e fim apontam para o mesmo objeto
            if ( first == last ) {
                first = null;
                last = null;
            } else {
                Node temp = first;
                first = first.next;
                temp.next = null;
            }
            
            size--;
            return value;
            
        } else {
            throw new EmptyDequeException();
        }
        
    }
    
    @Override
    public Type removeLast() throws EmptyDequeException {
        
        if ( !isEmpty()) {
            
            Type value = last.value;
            
            // início e fim apontam para o mesmo objeto
            if ( first == last ) {
                first = null;
                last = null;
            } else {
                
                // percorre o encadeamento, parando no value à esquerda do último
                Node current = first;
                
                while ( current.next != last ) {
                    current = current.next;
                }
                
                current.next = null;
                last = current;
                
            }
            
            size--;
            return value;
            
        } else {
            throw new EmptyDequeException();
        }
        
    }

    @Override
    public void clear() {
        
        while ( !isEmpty() ) {
            removeFirst();
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
            
            private Node current = first;
            
            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public Type next() {
                Type value = current.value;
                current = current.next;
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
        
        if ( !isEmpty()) {
            
            // percorrendo o encadeamento
            Node current = first;
            
            while ( current != null ) {
                
                sb.append( current.value );
                
                if ( first == last ) {
                    sb.append( " <- first/last\n" );
                } else if ( current == first ) {
                    sb.append( " <- first\n" );
                } else if ( current == last ) {
                    sb.append( " <- last\n" );
                } else {
                    sb.append( "\n" );
                }
                
                current = current.next;

            }
            
        } else {
            sb.append( "empty deque!\n" );
        }
        
        return sb.toString();
        
    }
    
}
