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
 * Implementação de uma lista genérica com redimensionamento de array.
 *
 * Obs: Implementação com a marcação do início/primeiro para a esquerda e o
 * fim/último para direita (fim do array).
 * 
 * Questões a se pensar:
 *     Qual a ordem de crescimento das operações?
 *     Há como melhorar?
 *     Mudar a topologia resolve?
 *     Usar mapeamento de endereços?
 * 
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 
 * 4. ed. Boston: Pearson Education, 2011. 955 p.
 * 
 * @param <Item> Tipo dos itens armazenados na lista.
 *
 * @author Prof. Dr. David Buzatto
 */
public class ResizingArrayList<Item> implements List<Item> {

    // itens armazenados na lista
    private Item[] items;
    
    // fim da lista
    private int end;
    
    // tamanho da lista
    private int size;
    
    /**
     * Constrói uma lista que suporta um item.
     */
    @SuppressWarnings( "unchecked" )
    public ResizingArrayList() {
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
    public void add( Item item ) {
        
        // dobra o tamanho se chegou no limite da capacidade
        if ( size == items.length ) {
            resize( 2 * items.length );
        }
        
        end++;
        items[end] = item;
        size++;
        
    }
    
    @Override
    public void add( int index, Item item ) 
            throws ListIndexOutOfBoundsException {
        
        if ( index < 0 || index > size ) {
            throw new ListIndexOutOfBoundsException( 
                    "index must be between 0 and " + size + ", but it's " + index );
        }
        
        // inserção no fim por índice ou se vazia
        if ( isEmpty() || index == size ) {
            
            add( item );
            
            // inserção no início ou no meio
        } else { 
            
            // dobra o tamanho se chegou no limite da capacidade
            if ( size == items.length ) {
                resize( 2 * items.length );
            }
            
            // realoca os valores (faz a lista andar para a direita)
            for ( int i = end; i >= index; i-- ) {
                items[i+1] = items[i];
            }

            items[index] = item;
            end++;
            size++;
            
        }
        
    }

    @Override
    public Item get( int index ) 
            throws ListIndexOutOfBoundsException, EmptyListException {
        
        if ( isEmpty() ) {
            throw new EmptyListException();
        }
        
        if ( index < 0 || index >= size ) {
            throw new ListIndexOutOfBoundsException( 
                    "index must be between 0 and " + size + ", but it's " + index );
        }
        
        return items[index];
        
    }

    @Override
    public void set( int index, Item item ) 
            throws EmptyListException, ListIndexOutOfBoundsException {
        
        if ( isEmpty() ) {
            throw new EmptyListException();
        }
        
        if ( index < 0 || index >= size ) {
            throw new ListIndexOutOfBoundsException( 
                    "index must be between 0 and " + size + ", but it's " + index );
        }
        
        items[index] = item;
        
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
        
        Item item = items[index];
        end--;
        size--;
        
        // realoca os valores (faz a lista andar para a esquerda)
        for ( int i = index; i <= end; i++ ) {
            items[i] = items[i+1];
        }

        // se o tamanho é igual à um quarto da capacidade
        if ( size > 0 && size == items.length / 4 ) {
            // diminui a capacidade pela metade
            resize( items.length / 2 );
        }
        
        return item;
        
    }

    @Override
    public void clear() {
        
        while ( !isEmpty() ) {
            remove( size - 1 );
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
                
                sb.append( String.format( "[%d] - ", i ) )
                        .append( items[i] );
                         
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
            sb.append( "empty list!\n" );
        }
        
        return sb.toString();
        
    }
    
}
