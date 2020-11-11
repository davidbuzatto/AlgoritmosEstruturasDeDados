/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdc4.ds.implementations;

import aesd.esdc4.ds.interfaces.Queue;
import aesd.esdc4.ds.exceptions.EmptyQueueException;
import aesd.esdc4.ds.exceptions.QueueOverflowException;
import java.util.Iterator;

/**
 * Implementação de uma fila genérica com capacidade fixa.
 * 
 * Obs: Implementação com a marcação do início/cabeça para a esquerda e o
 * fim/cauda para direita (fim do array).
 * 
 * Questões a se pensar:
 *     Qual a ordem de crescimento das operações de enfileirar e desenfileirar?
 *     Há como melhorar?
 *     Mudar a topologia resolve?
 *     Usar mapeamento de endereços?
 * 
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 
 * 4. ed. Boston: Pearson Education, 2011. 955 p.
 * 
 * @param <Item> Tipo dos itens armazenados na fila.
 *
 * @author Prof. Dr. David Buzatto
 */
public class FixedCapacityQueue<Item> implements Queue<Item> {

    // itens armazenados na fila
    private Item[] items;
    
    // fim da fila
    private int end;
    
    // tamanho da fila
    private int size;
    
    // tamanho máximo suportado pela fila
    private int maxSize;
    
    /**
     * Constrói uma fila vazia que suporta dez itens.
     */
    public FixedCapacityQueue() {
        this( 10 );
    }
    
    /**
     * Constrói uma fila vazia de tamanho especificado.
     * 
     * @param max Tamanho máximo da fila.
     */
    @SuppressWarnings( "unchecked" )
    public FixedCapacityQueue( int max ) {
        maxSize = max;
        items = (Item[]) new Object[maxSize];
        end = -1;
    }
    
    @Override
    public void enqueue( Item item ) throws QueueOverflowException {
        
        if ( size < maxSize ) {
            end++;
            items[end] = item;
            size++;
        } else {
            throw new QueueOverflowException();
        }
        
    }

    @Override
    public Item peek() throws EmptyQueueException {
        
        if ( !isEmpty() ) {
            return items[0];
        } else {
            throw new EmptyQueueException();
        }
        
    }

    @Override
    public Item dequeue() throws EmptyQueueException {
        
        if ( !isEmpty() ) {
            
            Item item = items[0];
            end--;
            size--;
            
            // realoca os valores (faz a fila andar para a esquerda)
            for ( int i = 0; i <= end; i++ ) {
                items[i] = items[i+1];
            }
            
            return item;
            
        } else {
            throw new EmptyQueueException();
        }
        
    }

    @Override
    public void clear() {
        
        for ( int i = 0; i <= end; i++ ) {
            items[i] = null;
        }
        
        end = -1;
        size = 0;
        
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
                return current <= end;
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
            for ( int i = 0; i <= end; i++ ) {
                
                sb.append( items[i] );
                         
                if ( size == 1 ) {
                    sb.append( " <- start/end\n" );
                } else if ( i == 0 ) {
                    sb.append( " <- start\n" );
                } else if ( i == end ) {
                    sb.append( " <- end\n" );
                } else {
                    sb.append( "\n" );
                }

            }
            
        } else {
            sb.append( "empty queue!\n" );
        }
        
        return sb.toString();
        
    }
    
}
