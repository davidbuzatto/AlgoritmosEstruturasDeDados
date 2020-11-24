/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdc4.ds.implementations;

import aesd.esdc4.ds.interfaces.List;
import aesd.esdc4.ds.exceptions.EmptyListException;
import aesd.esdc4.ds.exceptions.ListIndexOutOfBoundsException;
import java.util.Iterator;

/**
 * Implementação de uma lista circular genérica com encadeamento duplo.
 * 
 * Questões a se pensar:
 *     Qual a ordem de crescimento das operações?
 *     Há como melhorar?
 *     Precisa melhorar?
 * 
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 
 * 4. ed. Boston: Pearson Education, 2011. 955 p.
 * 
 * @param <Item> Tipo dos itens armazenados na lista.
 *
 * @author Prof. Dr. David Buzatto
 */
public class CircularDoublyLinkedList<Item> implements List<Item> {

    /*
     * Classe interna privada que define os nós da lista.
     * A referência next é direcionada à direita.
     * A referência previous é direcionada à esquerda.
     */
    private class Node<Item> {
        
        Item item;
        Node<Item> next;
        Node<Item> previous;

        // debug
        /*@Override
        public String toString() {
            return String.format( 
                    "%s <- %s -> %s", 
                    previous == null ? "null" : previous.item, 
                    item, 
                    next == null ? "null" : next.item );
        }*/
        
    }
    
    // início da lista circular
    private Node<Item> start;
    
    // tamanho da lista circular
    private int size;
    
    /**
     * Constrói uma lista circular vazia.
     */
    public CircularDoublyLinkedList() {
        start = null;   // redundante, apenas para mostrar o que acontece
        size = 0;       // redundante também
    }
    
    @Override
    public void add( Item item ) {
        
        Node<Item> newNode = new Node<>();
        newNode.item = item;
        newNode.next = null;      // redundante...
        newNode.previous = null;  // redundante...
        
        if ( isEmpty() ) {
            
            start = newNode;
            
            start.next = start;
            start.previous = start;
            
        } else {
            
            Node<Item> last = start.previous;
            
            newNode.next = start;
            start.previous = newNode;
            newNode.previous = last;
            last.next = newNode;
            
        }
        
        size++;
        
    }
    
    @Override
    public void add( int index, Item item )
            throws ListIndexOutOfBoundsException {
        
        if ( index < 0 || index > size ) {
            throw new ListIndexOutOfBoundsException( 
                    "index must be between 0 and " + size + ", but it's " + index );
        }
        
        Node<Item> newNode = new Node<>();
        newNode.item = item;
        newNode.next = null;      // redundante...
        newNode.previous = null;  // redundante...
        
        if ( isEmpty() ) {
            
            start = newNode;
            
            start.next = start;
            start.previous = start;
            
            // inserção no início
        } else if ( index == 0 ) {

            Node<Item> last = start.previous;
            
            newNode.next = start;
            newNode.previous = last;
            last.next = newNode;
            start.previous = newNode;
            start = newNode;
        
            // inserção no fim
        } else if ( index == size ) {
            
            Node<Item> last = start.previous;
            
            newNode.next = start;
            start.previous = newNode;
            newNode.previous = last;
            last.next = newNode;
            
            // inserção no meio
        } else {
            
            // posiciona onde será mexido (vai girar a lista em sentido horário)
            Node<Item> temp = start;
            for ( int i = 0; i < index; i++ ) {
                temp = temp.next;
            }
            
            newNode.next = temp;
            newNode.previous = temp.previous;
            
            temp.previous.next = newNode;
            temp.previous = newNode;
            
        }
        
        size++;
        
    }

    @Override
    public Item get( int index ) throws EmptyListException {
        
        if ( isEmpty() ) {
            throw new EmptyListException();
        }
        
        // mapeamento!
        index = index % size;
        
        Node<Item> current = start;
        for ( int i = 0; i < index; i++ ) {
            current = current.next;
        }
        
        return current.item;
        
    }

    @Override
    public Item remove( int index ) 
            throws ListIndexOutOfBoundsException, EmptyListException {
        
        if ( isEmpty() ) {
            throw new EmptyListException();
        }
        
        if ( index < 0 || index >= size ) {
            throw new ListIndexOutOfBoundsException( 
                    "index must be between 0 and " + size + ", but it's " + index );
        }
        
        Item item = null;
        
        // a lista tem apenas um elemento
        if ( start == start.next ) {
            
            item = start.item;
            start = null;
            
        } else {
            
            // remoção do início
            if ( index == 0 ) {
                
                item = start.item;
                
                Node<Item> last = start.previous;
                
                start = start.next;
                start.previous.next = null;
                start.previous.previous = null;
                
                start.previous = last;
                last.next = start;
                
                // remoção do fim
            } else if ( index == size - 1 ) {
                
                Node<Item> last = start.previous;
                
                item = last.item;
                
                last = last.previous;
                last.next.previous = null;
                last.next.next = null;
                
                start.previous = last;
                last.next = start;
                
                // remoção do meio
            } else {
                
                // posiciona na posição da remoção
                Node<Item> current = start;
                for ( int i = 0; i < index; i++ ) {
                    current = current.next;
                }

                item = current.item;
                
                current.next.previous = current.previous;
                current.previous.next = current.next;
                
                current.next = null;
                current.previous = null;
            
            }
            
        }
        
        size--;
        return item;
        
    }

    @Override
    public void clear() {
        
        while ( !isEmpty() ) {
            remove( 0 );
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
            
            private int index = 0;
            private Node<Item> current = start;
            
            @Override
            public boolean hasNext() {
                return index < size;
            }

            @Override
            public Item next() {
                Item item = current.item;
                current = current.next;
                index++;
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
            Node<Item> current = start;
            int index = 0;
            
            while ( index < size ) {
                
                // debug
                //sb.append( String.format( "[%d] - %s\n", index++, current ) );
                
                sb.append( String.format( "[%d] - ", index++ ) )
                        .append( current.item );
                
                if ( current == start ) {
                    sb.append( " <- start\n" );
                } else {
                    sb.append( "\n" );
                }
                
                current = current.next;

            }
            
        } else {
            sb.append( "empty list!\n" );
        }
        
        return sb.toString();
        
    }
    
}