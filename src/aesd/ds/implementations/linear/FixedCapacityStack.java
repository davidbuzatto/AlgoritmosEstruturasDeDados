/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.ds.implementations.linear;

import aesd.ds.interfaces.Stack;
import aesd.ds.exceptions.EmptyStackException;
import aesd.ds.exceptions.StackOverflowException;
import java.util.Iterator;

/**
 * Implementação de uma pilha genérica com capacidade fixa.
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
public class FixedCapacityStack<Type> implements Stack<Type> {

    // valores armazenados na pilha
    private Type[] values;
    
    // topo da pilha
    private int top;
    
    // tamanho da pilha
    private int size;
    
    // tamanho máximo suportado pela pilha
    private int maxSize;
    
    /**
     * Constrói uma pilha vazia que suporta dez valores.
     */
    public FixedCapacityStack() {
        this( 10 );
    }
    
    /**
     * Constrói uma pilha vazia de tamanho especificado.
     * 
     * @param max Tamanho máximo da pilha.
     */
    @SuppressWarnings( "unchecked" )
    public FixedCapacityStack( int max ) throws IllegalArgumentException {
        
        if ( max <= 0 ) {
            throw new IllegalArgumentException( "max capacity must be greater than zero" );
        }
        
        maxSize = max;
        values = (Type[]) new Object[maxSize];
        top = -1;
        
    }
    
    @Override
    public void push( Type value ) throws StackOverflowException {
        
        if ( size < maxSize ) {
            top++;
            values[top] = value;
            size++;
        } else {
            throw new StackOverflowException();
        }
        
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
