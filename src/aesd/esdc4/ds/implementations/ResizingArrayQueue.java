/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdc4.ds.implementations;

import aesd.esdc4.ds.exceptions.EmptyQueueException;
import aesd.esdc4.ds.interfaces.Queue;
import java.util.Iterator;

/**
 * Implementação de uma fila genérica com redimensionamento de array.
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
public class ResizingArrayQueue<Item> implements Queue<Item> {

    // itens armazenados na fila
    private Item[] items;
    
    // fim da fila
    private int end;
    
    // tamanho da fila
    private int size;
    
    /**
     * Constrói uma fila que suporta um item.
     */
    @SuppressWarnings( "unchecked" )
    public ResizingArrayQueue() {
        items = (Item[]) new Object[1];
        end = -1;
    }
    
    /**
     * Redimensiona o array de itens.
     * 
     * @param max Tamanho a ser redimensionado.
     */
    private void resize( int max ) {
        
        // para ver a mudança de capacidade, descomente a linha abaixo.
        //System.out.println( "capacity " + items.length + " size " + size );
        
        // nova alocação
        Item[] temp = (Item[]) new Object[max];
        
        // cópia (pode-se usar o método arraycopy de System)
        for ( int i = 0; i < size; i++ ) {
            temp[i] = items[i];
        }
        
        items = temp;
        
        // para ver a mudança de capacidade, descomente a linha abaixo.
        //System.out.println( "new capacity " + items.length + " size " + size );
        
    }
    
    @Override
    public void enqueue( Item item ) {
        
        // dobra o tamanho se chegou no limite da capacidade
        if ( size == items.length ) {
            resize( 2 * items.length );
        }
        
        end++;
        items[end] = item;
        size++;
        
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
            
            // se o tamanho é igual à um quarto da capacidade
            if ( size > 0 && size == items.length / 4 ) {
                // diminui a capacidade pela metade
                resize( items.length / 2 );
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
