/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdc4.ds.implementations.linear;

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
 * @param <Type> Tipo dos valores armazenados na pilha.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class ResizingArrayStack<Type> implements Stack<Type> {

    // valores armazenados na pilha
    private Type[] values;
    
    // topo da pilha
    private int top;
    
    // tamanho da pilha
    private int size;
    
    /**
     * Constrói uma pilha que suporta um valor.
     */
    @SuppressWarnings( "unchecked" )
    public ResizingArrayStack() {
        values = (Type[]) new Object[1];
        top = -1;
    }
    
    /**
     * Redimensiona o array de valores.
     * 
     * @param max Tamanho a ser redimensionado.
     */
    @SuppressWarnings( "unchecked" )
    private void resize( int max ) {
        
        // para ver a mudança de capacidade, descomente a linha abaixo.
        //System.out.println( "capacity " + values.length + " size " + size );
        
        // nova alocação
        Type[] temp = (Type[]) new Object[max];
        
        // cópia (pode-se usar o método arraycopy de System)
        for ( int i = 0; i < size; i++ ) {
            temp[i] = values[i];
        }
        
        values = temp;
        
        // para ver a mudança de capacidade, descomente a linha abaixo.
        //System.out.println( "new capacity " + values.length + " size " + size );
        
    }
    
    @Override
    public void push( Type value ) {
        
        // dobra o tamanho se chegou no limite da capacidade
        if ( size == values.length ) {
            resize( 2 * values.length );
        }
        
        top++;
        values[top] = value;
        size++;
        
    }

    @Override
    public Type peek() throws EmptyStackException {
        
        if ( !isEmpty() ) {
            return values[top];
        } else {
            throw new EmptyStackException();
        }
        
    }

    @Override
    public Type pop() throws EmptyStackException {
        
        if ( !isEmpty() ) {
            
            Type value = values[top];
            values[top] = null;      // marca como null para coleta de lixo
            top--;
            size--;
            
            // se o tamanho é igual à um quarto da capacidade
            if ( size > 0 && size == values.length / 4 ) {
                // diminui a capacidade pela metade
                resize( values.length / 2 );
            }
            
            return value;
            
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
    public Iterator<Type> iterator() {
        
        return new Iterator<Type>() {
            
            private int current = top;
            
            @Override
            public boolean hasNext() {
                return current >= 0;
            }

            @Override
            public Type next() {
                return values[current--];
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
            
            // percorrendo o array de valores
            for ( int i = top; i >= 0; i-- ) {

                if ( i == top ) {
                    sb.append( values[i] ).append( " <- top" );
                } else {
                    sb.append( "\n" );
                    sb.append( values[i] );
                }

            }
        
        } else {
            sb.append( "empty stack!" );
        }
        
        sb.append( "\n" );
        
        return sb.toString();
        
    }
    
}
