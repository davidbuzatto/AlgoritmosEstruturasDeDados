/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdi3.ds.implementations.linear;

import aesd.esdi3.ds.interfaces.List;
import aesd.esdi3.ds.exceptions.EmptyListException;
import aesd.esdi3.ds.exceptions.ListIndexOutOfBoundsException;
import java.util.Iterator;

/**
 * Implementação de uma lista genérica com encadeamento duplo.
 * 
 * Obs: Implementação com a marcação do início/primeiro para a esquerda e o
 * fim/último para direita.
 * 
 * Questões a se pensar:
 *     Qual a ordem de crescimento das operações?
 *     Há como melhorar?
 *     Precisa melhorar?
 * 
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 
 * 4. ed. Boston: Pearson Education, 2011. 955 p.
 * 
 * @param <Type> Tipo dos valores armazenados na lista.
 *
 * @author Prof. Dr. David Buzatto
 */
public class DoublyLinkedList<Type> implements List<Type> {

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
    
    // início e fim da lista
    private Node start;
    private Node end;
    
    // tamanho da lista
    private int size;
    
    /**
     * Constrói uma lista vazia.
     */
    public DoublyLinkedList() {
        start = null;   // redundante, apenas para mostrar o que acontece
        end = null;     // redundante também
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
            end = newNode;
        } else {
            newNode.previous = end;
            end.next = newNode;
            end = newNode;
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
            end = newNode;
            
            // inserção no início
        } else if ( index == 0 ) {

            newNode.next = start;
            start.previous = newNode;
            start = newNode;
        
            // inserção no fim
        } else if ( index == size ) {
            
            newNode.previous = end;
            end.next = newNode;
            end = newNode;
            
            // inserção no meio
        } else {
            
            // posiciona onde será mexido (vai deslocar a lista para a direita)
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
    public Type get( int index ) 
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
        if ( start == end ) {
            
            value = start.value;
            start = null;
            end = null;
            
        } else {
            
            // remoção do início
            if ( index == 0 ) {
                
                value = start.value;
                
                start = start.next;
                start.previous.next = null;
                start.previous = null;
                
                // remoção do fim
            } else if ( index == size - 1 ) {
                
                value = end.value;
                
                end = end.previous;
                end.next.previous = null;
                end.next = null;
                
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
            
            private Node current = start;
            
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
            Node current = start;
            int index = 0;
            
            while ( current != null ) {
                
                // debug
                //sb.append( String.format( "[%d] - %s\n", index++, current ) );
                
                sb.append( String.format( "[%d] - ", index++ ) )
                        .append( current.value );
                
                if ( start == end ) {
                    sb.append( " <- start/end\n" );
                } else if ( current == start ) {
                    sb.append( " <- start\n" );
                } else if ( current == end ) {
                    sb.append( " <- end\n" );
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
