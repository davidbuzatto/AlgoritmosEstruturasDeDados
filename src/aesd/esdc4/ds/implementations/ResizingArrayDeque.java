/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdc4.ds.implementations;

import aesd.esdc4.ds.interfaces.Deque;
import aesd.esdc4.ds.exceptions.EmptyDequeException;
import java.util.Iterator;

/**
 * Implementação de uma deque genérica com redimensionamento de array.
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
 * @param <Type> Tipo dos valores armazenados na deque.
 *
 * @author Prof. Dr. David Buzatto
 */
public class ResizingArrayDeque<Type> implements Deque<Type> {

    // valores armazenados na deque
    private Type[] values;
    
    // fim da deque
    private int last;
    
    // tamanho da deque
    private int size;
    
    /**
     * Constrói uma deque que suporta um valor.
     */
    @SuppressWarnings( "unchecked" )
    public ResizingArrayDeque() {
        values = (Type[]) new Object[1];
        last = -1;
    }

    /**
     * Redimensiona o array de valores.
     * 
     * @param max Tamanho a ser redimensionado.
     */
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
    public void addFirst( Type value ) {
        
        // dobra o tamanho se chegou no limite da capacidade
        if ( size == values.length ) {
            resize( 2 * values.length );
        }
            
        // realoca os valores (faz a deque andar para a direita)
        for ( int i = last; i >= 0; i-- ) {
            values[i+1] = values[i];
        }

        values[0] = value;
        last++;
        size++;
        
    }
    
    @Override
    public void addLast( Type value ) {
        
        // dobra o tamanho se chegou no limite da capacidade
        if ( size == values.length ) {
            resize( 2 * values.length );
        }
        
        last++;
        values[last] = value;
        size++;
        
    }

    @Override
    public Type peekFirst() throws EmptyDequeException {
        
        if ( !isEmpty() ) {
            return values[0];
        } else {
            throw new EmptyDequeException();
        }
        
    }
    
    @Override
    public Type peekLast() throws EmptyDequeException {
        
        if ( !isEmpty() ) {
            return values[last];
        } else {
            throw new EmptyDequeException();
        }
        
    }

    @Override
    public Type removeFirst() throws EmptyDequeException {
        
        if ( !isEmpty() ) {
            
            Type value = values[0];
            last--;
            size--;
            
            // realoca os valores (faz a deque andar para a esquerda)
            for ( int i = 0; i <= last; i++ ) {
                values[i] = values[i+1];
            }
            
            values[last+1] = null;      // marca como null para coleta de lixo
            
            // se o tamanho é igual à um quarto da capacidade
            if ( size > 0 && size == values.length / 4 ) {
                // diminui a capacidade pela metade
                resize( values.length / 2 );
            }
            
            return value;
            
        } else {
            throw new EmptyDequeException();
        }
        
    }
    
    @Override
    public Type removeLast() throws EmptyDequeException {
        
        if ( !isEmpty() ) {
            
            Type value = values[last];
            values[last] = null;      // marca como null para coleta de lixo
            last--;
            size--;
            
            // se o tamanho é igual à um quarto da capacidade
            if ( size > 0 && size == values.length / 4 ) {
                // diminui a capacidade pela metade
                resize( values.length / 2 );
            }
            
            return value;
            
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
    public Iterator<Type> iterator() {
        
        return new Iterator<Type>() {
            
            private int current = 0;
            
            @Override
            public boolean hasNext() {
                return current <= last;
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
            for ( int i = 0; i <= last; i++ ) {
                
                sb.append( values[i] );
                         
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
