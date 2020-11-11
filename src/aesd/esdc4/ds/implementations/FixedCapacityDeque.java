/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdc4.ds.implementations;

import aesd.esdc4.ds.interfaces.Deque;
import aesd.esdc4.ds.exceptions.DequeOverflowException;
import aesd.esdc4.ds.exceptions.EmptyDequeException;
import java.util.Iterator;

/**
 * Implementação de uma deque genérica com capacidade fixa.
 * 
 * Obs: Implementação com a marcação do início/primeiro para a esquerda e o
 * fim/último para direita (fim do array).
 * 
 * Questões a se pensar:
 *     Qual a ordem de crescimento das operações de inserção e remoção?
 *     Há como melhorar?
 *     Mudar a topologia resolve?
 *     Usar mapeamento de endereços?
 * 
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 
 * 4. ed. Boston: Pearson Education, 2011. 955 p.
 * 
 * @param <Item> Tipo dos itens armazenados na deque.
 *
 * @author Prof. Dr. David Buzatto
 */
public class FixedCapacityDeque<Item> implements Deque<Item> {

    // itens armazenados na deque
    private Item[] items;
    
    // fim da deque
    private int last;
    
    // tamanho da deque
    private int size;
    
    // tamanho máximo suportado pela deque
    private int maxSize;
    
    /**
     * Constrói uma deque vazia que suporta dez itens.
     */
    public FixedCapacityDeque() {
        this( 10 );
    }
    
    /**
     * Constrói uma deque vazia de tamanho especificado.
     * 
     * @param max Tamanho máximo da deque.
     */
    @SuppressWarnings( "unchecked" )
    public FixedCapacityDeque( int max ) {
        maxSize = max;
        items = (Item[]) new Object[maxSize];
        last = -1;
    }
    
    @Override
    public void addFirst( Item item ) throws DequeOverflowException {
        
        if ( size < maxSize ) {
            
            // realoca os valores (faz a deque andar para a direita)
            for ( int i = last; i >= 0; i-- ) {
                items[i+1] = items[i];
            }
            
            items[0] = item;
            last++;
            size++;
            
        } else {
            throw new DequeOverflowException();
        }
        
    }
    
    @Override
    public void addLast( Item item ) throws DequeOverflowException {
        
        if ( size < maxSize ) {
            last++;
            items[last] = item;
            size++;
        } else {
            throw new DequeOverflowException();
        }
        
    }

    @Override
    public Item peekFirst() throws EmptyDequeException {
        
        if ( !isEmpty() ) {
            return items[0];
        } else {
            throw new EmptyDequeException();
        }
        
    }
    
    @Override
    public Item peekLast() throws EmptyDequeException {
        
        if ( !isEmpty() ) {
            return items[last];
        } else {
            throw new EmptyDequeException();
        }
        
    }

    @Override
    public Item removeFirst() throws EmptyDequeException {
        
        if ( !isEmpty() ) {
            
            Item item = items[0];
            last--;
            size--;
            
            // realoca os valores (faz a deque andar para a esquerda)
            for ( int i = 0; i <= last; i++ ) {
                items[i] = items[i+1];
            }
            
            return item;
            
        } else {
            throw new EmptyDequeException();
        }
        
    }
    
    @Override
    public Item removeLast() throws EmptyDequeException {
        
        if ( !isEmpty() ) {
            Item item = items[last];
            items[last] = null;      // marca como null para coleta de lixo
            last--;
            size--;
            return item;
        } else {
            throw new EmptyDequeException();
        }
        
    }

    @Override
    public void clear() {
        
        while ( !isEmpty() ) {
            removeLast();
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
            
            private int current = 0;
            
            @Override
            public boolean hasNext() {
                return current <= last;
            }

            @Override
            public Item next() {
                return items[current++];
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
            
            // percorrendo o array de itens
            for ( int i = 0; i <= last; i++ ) {
                
                sb.append( items[i] );
                         
                if ( size == 1 ) {
                    sb.append( " <- first/last\n" );
                } else if ( i == 0 ) {
                    sb.append( " <- first\n" );
                } else if ( i == last ) {
                    sb.append( " <- last\n" );
                } else {
                    sb.append( "\n" );
                }

            }
            
        } else {
            sb.append( "empty deque!\n" );
        }
        
        return sb.toString();
        
    }
    
}
