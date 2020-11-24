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
 * Implementação de uma lista genérica com encadeamento duplo.
 * 
 * Obs: Implementação com a marcação do início/primeiro para a esquerda e o
 * fim/último para direita.
 * 
 * Questões a se pensar:
 *     Qual a ordem de crescimento das operações?
 *     Precisa melhorar?
 * 
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 
 * 4. ed. Boston: Pearson Education, 2011. 955 p.
 * 
 * @param <Item> Tipo dos itens armazenados na lista.
 *
 * @author Prof. Dr. David Buzatto
 */
public class DoubleLinkedList<Item> implements List<Item> {

    /*
     * Classe interna privada que define os nós da lista.
     * A referência next é direcionada à direita.
     * A referência previous é direcionada à esquerda.
     */
    private class Node<Item> {
        Item item;
        Node<Item> next;
        Node<Item> previous;
    }
    
    // início e fim da lista
    private Node<Item> start;
    private Node<Item> end;
    
    // tamanho da lista
    private int size;
    
    /**
     * Constrói uma lista vazia.
     */
    public DoubleLinkedList() {
        start = null;   // redundante, apenas para mostrar o que acontece
        end = null;     // redundante também
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
            end = newNode;
        } else {
            newNode.previous = end;
            end.next = newNode;
            end = newNode;
        }
        
        size++;
        
    }
    
    @Override
    public void add( int index, Item item )
            throws ListIndexOutOfBoundsException {
        
        if ( index < 0 || index >= size ) {
            throw new ListIndexOutOfBoundsException();
        }
        
        Node<Item> newNode = new Node<>();
        newNode.item = item;
        newNode.next = null;      // redundante...
        newNode.previous = null;  // redundante...
        
        if ( isEmpty() ) {
            
            start = newNode;
            end = newNode;
            
        } else if ( index == 0 ) {

            newNode.next = start;
            start.previous = newNode;
            start = newNode;
        
        } else if ( index == size - 1 ) {
            
            newNode.previous = end;
            end.next = newNode;
            end = newNode;
            
        } else {
            
            // posiciona onde será mexido (vai deslocar a lista para a direita)
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
    public Item get( int index ) 
            throws ListIndexOutOfBoundsException, EmptyListException {
        
        if ( isEmpty() ) {
            throw new EmptyListException();
        }
        
        if ( index < 0 || index >= size ) {
            throw new ListIndexOutOfBoundsException();
        }
        
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
            throw new ListIndexOutOfBoundsException();
        }
        
        Item item = null;
        
        if ( start == end ) {
            item = start.item;
            start = null;
            end = null;
        } else {
            
            Node<Item> current = start;
            for ( int i = 0; i < index; i++ ) {
                current = current.next;
            }
            
            item = current.item;
            
            if ( current.previous != null ) {
                
                Node temp = current.previous;
                temp.next = current.next;
                current.next.previous = temp;
                
                current.next = null;
                current.previous = null;
                
            } else {
                
                Node temp = current.next;
                temp.previous = current.previous;
                current.previous.next = temp;
                
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
            
            private Node<Item> current = start;
            
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
            Node<Item> current = start;
            
            while ( current != null ) {
                
                sb.append( current.item );
                
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
