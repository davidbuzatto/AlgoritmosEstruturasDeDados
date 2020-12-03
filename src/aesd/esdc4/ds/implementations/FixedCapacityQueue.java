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
 * @param <Type> Tipo dos valores armazenados na fila.
 *
 * @author Prof. Dr. David Buzatto
 */
public class FixedCapacityQueue<Type> implements Queue<Type> {

    // valores armazenados na fila
    private Type[] values;
    
    // fim da fila
    private int end;
    
    // tamanho da fila
    private int size;
    
    // tamanho máximo suportado pela fila
    private int maxSize;
    
    /**
     * Constrói uma fila vazia que suporta dez valores.
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
        values = (Type[]) new Object[maxSize];
        end = -1;
    }
    
    @Override
    public void enqueue( Type value ) throws QueueOverflowException {
        
        if ( size < maxSize ) {
            end++;
            values[end] = value;
            size++;
        } else {
            throw new QueueOverflowException();
        }
        
    }

    @Override
    public Type peek() throws EmptyQueueException {
        
        if ( !isEmpty() ) {
            return values[0];
        } else {
            throw new EmptyQueueException();
        }
        
    }

    @Override
    public Type dequeue() throws EmptyQueueException {
        
        if ( !isEmpty() ) {
            
            Type value = values[0];
            end--;
            size--;
            
            // realoca os valores (faz a fila andar para a esquerda)
            for ( int i = 0; i <= end; i++ ) {
                values[i] = values[i+1];
            }
            
            values[end+1] = null;      // marca como null para coleta de lixo
            
            return value;
            
        } else {
            throw new EmptyQueueException();
        }
        
    }

    @Override
    public void clear() {
        
        for ( int i = 0; i <= end; i++ ) {
            values[i] = null;
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
    public Iterator<Type> iterator() {
        
        return new Iterator<Type>() {
            
            private int current = 0;
            
            @Override
            public boolean hasNext() {
                return current <= end;
            }

            @Override
            public Type next() {
                return values[current++];
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
            
            // percorrendo o array de valores
            for ( int i = 0; i <= end; i++ ) {
                
                sb.append( values[i] );
                         
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
