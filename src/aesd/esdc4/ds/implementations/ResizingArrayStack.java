/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdc4.ds.implementations;

import aesd.esdc4.ds.interfaces.Stack;
import aesd.esdc4.ds.exceptions.EmptyStackException;
import java.util.Iterator;

/**
 * Implementação de uma pilha genérica com redimensionamento de array.
 * 
 * Obs: Implementação com a marcação do topo para a direita (fim do array).
 * 
 * Questões a se pensar:
 *     Qual a ordem de crescimento das operações de empilhar e desempilhar?
 *     Precisa melhorar?
 * 
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 
 * 4. ed. Boston: Pearson Education, 2011. 955 p.
 * 
 * @param <Item> Tipo dos itens armazenados na pilha.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class ResizingArrayStack<Item> implements Stack<Item> {

    // itens armazenados na pilha
    private Item[] items;
    
    // topo da pilha
    private int top;
    
    // tamanho da pilha
    private int size;
    
    /**
     * Constrói uma pilha que suporta um item.
     */
    @SuppressWarnings( "unchecked" )
    public ResizingArrayStack() {
        items = (Item[]) new Object[1];
        top = -1;
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
    public void push( Item item ) {
        
        // dobra o tamanho se chegou no limite da capacidade
        if ( size == items.length ) {
            resize( 2 * items.length );
        }
        
        top++;
        items[top] = item;
        size++;
        
    }

    @Override
    public Item peek() throws EmptyStackException {
        
        if ( !isEmpty() ) {
            return items[top];
        } else {
            throw new EmptyStackException();
        }
        
    }

    @Override
    public Item pop() throws EmptyStackException {
        
        if ( !isEmpty() ) {
            
            Item item = items[top];
            items[top] = null;      // marca como null para coleta de lixo
            top--;
            size--;
            
            // se o tamanho é igual à um quarto da capacidade
            if ( size > 0 && size == items.length / 4 ) {
                // diminui a capacidade pela metade
                resize( items.length / 2 );
            }
            
            return item;
            
        } else {
            throw new EmptyStackException();
        }
        
    }

    @Override
    public void clear() {
        
        while ( !isEmpty() ) {
            pop();
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
            
            private int current = top;
            
            @Override
            public boolean hasNext() {
                return current >= 0;
            }

            @Override
            public Item next() {
                return items[current--];
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
        
        if ( !isEmpty() ) {
            
            // percorrendo o array de itens
            for ( int i = top; i >= 0; i-- ) {

                if ( i == top ) {
                    sb.append( items[i] ).append( " <- top" );
                } else {
                    sb.append( "\n" );
                    sb.append( items[i] );
                }

            }
        
        } else {
            sb.append( "empty stack!" );
        }
        
        sb.append( "\n" );
        
        return sb.toString();
        
    }
    
}
