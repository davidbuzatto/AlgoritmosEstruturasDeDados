/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.ds.implementations.linear;

import aesd.ds.interfaces.List;
import aesd.ds.exceptions.EmptyListException;
import aesd.ds.exceptions.ListIndexOutOfBoundsException;
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
 * @param <Type> Tipo dos itens armazenados na lista.
 *
 * @author Prof. Dr. David Buzatto
 */
public class CircularDoublyLinkedList<Type> implements List<Type> {

    /*
     * Classe interna privada que define os nós da lista.
     * A referência next é direcionada à direita.
     * A referência previous é direcionada à esquerda.
     */
    private class Node {
        
        Type value;
        Node next;
        Node previous;

        // debug
        /*@Override
        public String toString() {
            return String.format( 
                    "%s <- %s -> %s", 
                    previous == null ? "null" : previous.value, 
                    value, 
                    next == null ? "null" : next.value );
        }*/
        
    }
    
    // início da lista circular
    private Node start;
    
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
    public void add( Type value ) {
        
        Node newNode = new Node();
        newNode.value = value;
        newNode.next = null;      // redundante...
        newNode.previous = null;  // redundante...
        
        if ( isEmpty() ) {
            
            start = newNode;
            
            start.next = start;
            start.previous = start;
            
        } else {
            
            Node last = start.previous;
            
            newNode.next = start;
            start.previous = newNode;
            newNode.previous = last;
            last.next = newNode;
            
        }
        
        size++;
        
    }
    
    @Override
    public void add( int index, Type value )
            throws ListIndexOutOfBoundsException {
        
        if ( index < 0 || index > size ) {
            throw new ListIndexOutOfBoundsException( 
                    "index must be between 0 and " + size + ", but it's " + index );
        }
        
        Node newNode = new Node();
        newNode.value = value;
        newNode.next = null;      // redundante...
        newNode.previous = null;  // redundante...
        
        if ( isEmpty() ) {
            
            start = newNode;
            
            start.next = start;
            start.previous = start;
            
            // inserção no início
        } else if ( index == 0 ) {

            Node last = start.previous;
            
            newNode.next = start;
            newNode.previous = last;
            last.next = newNode;
            start.previous = newNode;
            start = newNode;
        
            // inserção no fim
        } else if ( index == size ) {
            
            Node last = start.previous;
            
            newNode.next = start;
            start.previous = newNode;
            newNode.previous = last;
            last.next = newNode;
            
            // inserção no meio
        } else {
            
            // posiciona onde será mexido (vai girar a lista em sentido horário)
            Node temp = start;
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
    public Type get( int index ) throws EmptyListException {
        
        if ( isEmpty() ) {
            throw new EmptyListException();
        }
        
        if ( index < 0 ) {
            throw new ListIndexOutOfBoundsException( 
                    "index must be greater or igual to 0, but it's " + index );
        }
        
        // mapeamento!
        index = index % size;
        
        Node current = start;
        for ( int i = 0; i < index; i++ ) {
            current = current.next;
        }
        
        return current.value;
        
    }

    @Override
    public void set( int index, Type value ) 
            throws EmptyListException, ListIndexOutOfBoundsException {
        
        if ( isEmpty() ) {
            throw new EmptyListException();
        }
        
        if ( index < 0 || index >= size ) {
            throw new ListIndexOutOfBoundsException( 
                    "index must be between 0 and " + size + ", but it's " + index );
        }
        
        Node current = start;
        for ( int i = 0; i < index; i++ ) {
            current = current.next;
        }
        
        current.value = value;
        
    }
    
    @Override
    public Type remove( int index ) 
            throws EmptyListException, ListIndexOutOfBoundsException {
        
        if ( isEmpty() ) {
            throw new EmptyListException();
        }
        
        if ( index < 0 || index >= size ) {
            throw new ListIndexOutOfBoundsException( 
                    "index must be between 0 and " + size + ", but it's " + index );
        }
        
        Type value = null;
        
        // a lista tem apenas um elemento
        if ( start == start.next ) {
            
            value = start.value;
            start = null;
            
        } else {
            
            // remoção do início
            if ( index == 0 ) {
                
                value = start.value;
                
                Node last = start.previous;
                
                start = start.next;
                start.previous.next = null;
                start.previous.previous = null;
                
                start.previous = last;
                last.next = start;
                
                // remoção do fim
            } else if ( index == size - 1 ) {
                
                Node last = start.previous;
                
                value = last.value;
                
                last = last.previous;
                last.next.previous = null;
                last.next.next = null;
                
                start.previous = last;
                last.next = start;
                
                // remoção do meio
            } else {
                
                // posiciona na posição da remoção
                Node current = start;
                for ( int i = 0; i < index; i++ ) {
                    current = current.next;
                }

                value = current.value;
                
                current.next.previous = current.previous;
                current.previous.next = current.next;
                
                current.next = null;
                current.previous = null;
            
            }
            
        }
        
        size--;
        return value;
        
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
    public Iterator<Type> iterator() {
        
        return new Iterator<Type>() {
            
            private int index = 0;
            private Node current = start;
            
            @Override
            public boolean hasNext() {
                return index < size;
            }

            @Override
            public Type next() {
                Type value = current.value;
                current = current.next;
                index++;
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
            Node current = start;
            int index = 0;
            
            while ( index < size ) {
                
                // debug
                //sb.append( String.format( "[%d] - %s\n", index++, current ) );
                
                sb.append( String.format( "[%d] - ", index++ ) )
                        .append( current.value );
                
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
