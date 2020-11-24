/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdc4.ds.implementations;

import aesd.esdc4.ds.interfaces.Deque;
import aesd.esdc4.ds.exceptions.EmptyDequeException;
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
 * @param <Item> Tipo dos itens armazenados na deque.
 *
 * @author Prof. Dr. David Buzatto
 */
public class LinkedDeque<Item> implements Deque<Item> {

    /*
     * Classe interna privada que define os nós da deque.
     * A referência next é direcionada à direita.
     */
    private class Node<Item> {
        Item item;
        Node<Item> next;
    }
    
    // início e fim da deque
    private Node<Item> first;
    private Node<Item> last;
    
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
    public void addFirst( Item item ) {
        
        Node<Item> newNode = new Node<>();
        newNode.item = item;
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
    public void addLast( Item item ) {
        
        Node<Item> newNode = new Node<>();
        newNode.item = item;
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
    public Item peekFirst() throws EmptyDequeException {
        
        if ( !isEmpty() ) {
            return first.item;
        } else {
            throw new EmptyDequeException();
        }
        
    }
    
    @Override
    public Item peekLast() throws EmptyDequeException {
        
        if ( !isEmpty() ) {
            return last.item;
        } else {
            throw new EmptyDequeException();
        }
        
    }

    @Override
    public Item removeFirst() throws EmptyDequeException {
        
        if ( !isEmpty()) {
            
            Item item = first.item;
            
            // início e fim apontam para o mesmo objeto
            if ( first == last ) {
                first = null;
                last = null;
            } else {
                Node<Item> temp = first;
                first = first.next;
                temp.next = null;
            }
            
            size--;
            return item;
            
        } else {
            throw new EmptyDequeException();
        }
        
    }
    
    @Override
    public Item removeLast() throws EmptyDequeException {
        
        if ( !isEmpty()) {
            
            Item item = last.item;
            
            // início e fim apontam para o mesmo objeto
            if ( first == last ) {
                first = null;
                last = null;
            } else {
                
                // percorre o encadeamento, parando no item à esquerda do último
                Node<Item> current = first;
                
                while ( current.next != last ) {
                    current = current.next;
                }
                
                current.next = null;
                last = current;
                
            }
            
            size--;
            return item;
            
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
    public Iterator<Item> iterator() {
        
        return new Iterator<Item>() {
            
            private Node<Item> current = first;
            
            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public Item next() {
                Item item = current.item;
                current = current.next;
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
        
        if ( !isEmpty()) {
            
            // percorrendo o encadeamento
            Node<Item> current = first;
            
            while ( current != null ) {
                
                sb.append( current.item );
                
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
