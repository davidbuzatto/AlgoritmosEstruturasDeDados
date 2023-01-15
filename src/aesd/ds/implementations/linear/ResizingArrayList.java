package aesd.ds.implementations.linear;

import aesd.ds.interfaces.List;
import aesd.ds.exceptions.EmptyListException;
import aesd.ds.exceptions.ListIndexOutOfBoundsException;
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
 * @param <Type> Tipo dos valores armazenados na lista.
 *
 * @author Prof. Dr. David Buzatto
 */
public class ResizingArrayList<Type> implements List<Type> {

    // valores armazenados na lista
    private Type[] values;
    
    // fim da lista
    private int end;
    
    // tamanho da lista
    private int size;
    
    /**
     * Constrói uma lista que suporta um valor.
     */
    @SuppressWarnings( "unchecked" )
    public ResizingArrayList() {
        values = (Type[]) new Object[1];
        end = -1;
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
    public void add( Type value ) {
        
        // dobra o tamanho se chegou no limite da capacidade
        if ( size == values.length ) {
            resize( 2 * values.length );
        }
        
        end++;
        values[end] = value;
        size++;
        
    }
    
    @Override
    public void add( int index, Type value ) 
            throws ListIndexOutOfBoundsException {
        
        if ( index < 0 || index > size ) {
            throw new ListIndexOutOfBoundsException( 
                    "index must be between 0 and " + size + ", but it's " + index );
        }
        
        // inserção no fim por índice ou se vazia
        if ( isEmpty() || index == size ) {
            
            add( value );
            
            // inserção no início ou no meio
        } else { 
            
            // dobra o tamanho se chegou no limite da capacidade
            if ( size == values.length ) {
                resize( 2 * values.length );
            }
            
            // realoca os valores (faz a lista andar para a direita)
            for ( int i = end; i >= index; i-- ) {
                values[i+1] = values[i];
            }

            values[index] = value;
            end++;
            size++;
            
        }
        
    }

    @Override
    public Type get( int index ) 
            throws ListIndexOutOfBoundsException, EmptyListException {
        
        if ( isEmpty() ) {
            throw new EmptyListException();
        }
        
        if ( index < 0 || index >= size ) {
            throw new ListIndexOutOfBoundsException( 
                    "index must be between 0 and " + size + ", but it's " + index );
        }
        
        return values[index];
        
    }

    @Override
    public void set( int index, Type value ) 
            throws EmptyListException, ListIndexOutOfBoundsException {
        
        if ( isEmpty() ) {
            throw new EmptyListException();
        }
        
        if ( index < 0 || index >= size ) {
            throw new ListIndexOutOfBoundsException( 
                    "index must be between 0 and " + size + ", but it's " + index );
        }
        
        values[index] = value;
        
    }
    
    @Override
    public Type remove( int index ) 
            throws ListIndexOutOfBoundsException, EmptyListException {
        
        if ( isEmpty() ) {
            throw new EmptyListException();
        }
        
        if ( index < 0 || index >= size ) {
            throw new ListIndexOutOfBoundsException( 
                    "index must be between 0 and " + size + ", but it's " + index );
        }
        
        Type value = values[index];
        end--;
        size--;
        
        // realoca os valores (faz a lista andar para a esquerda)
        for ( int i = index; i <= end; i++ ) {
            values[i] = values[i+1];
        }
        
        values[end+1] = null;      // marca como null para coleta de lixo

        // se o tamanho é igual à um quarto da capacidade
        if ( size > 0 && size == values.length / 4 ) {
            // diminui a capacidade pela metade
            resize( values.length / 2 );
        }
        
        return value;
        
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
                
                sb.append( String.format( "[%d] - ", i ) )
                        .append( values[i] );
                         
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
