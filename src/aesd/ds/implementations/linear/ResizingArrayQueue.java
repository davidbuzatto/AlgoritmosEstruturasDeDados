package aesd.ds.implementations.linear;

import aesd.ds.exceptions.EmptyQueueException;
import aesd.ds.interfaces.Queue;
import java.util.Iterator;
import java.util.NoSuchElementException;

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
 * @param <Type> Tipo dos valores armazenados na fila.
 *
 * @author Prof. Dr. David Buzatto
 */
public class ResizingArrayQueue<Type> implements Queue<Type> {

    // valores armazenados na fila
    private Type[] values;
    
    // fim da fila
    private int end;
    
    // tamanho da fila
    private int size;
    
    /**
     * Constrói uma fila que suporta um valor.
     */
    @SuppressWarnings( "unchecked" )
    public ResizingArrayQueue() {
        // o cast é necessário pois Java não permite a criação direta de um
        // array genérico por causa do apagamento de tipos (type erasure);
        // a capacidade inicial é 1 para evidenciar, já na primeira inserção,
        // o crescimento por dobragem realizado por resize()
        values = (Type[]) new Object[1];
        end = -1;
    }

    /**
     * Redimensiona o array de valores. Dobrar a capacidade (fator 2x) é o
     * que garante o custo amortizado O(1) por inserção, apesar de cada
     * redimensionamento individual custar O(n).
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
    public void enqueue( Type value ) {
        
        // dobra o tamanho se chegou no limite da capacidade
        if ( size == values.length ) {
            resize( 2 * values.length );
        }
        
        end++;
        values[end] = value;
        size++;
        
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
            
            // se o tamanho é igual à um quarto da capacidade
            if ( size > 0 && size == values.length / 4 ) {
                // diminui a capacidade pela metade; usar 1/4 como limiar, ao
                // invés de 1/2, evita thrashing (crescer e encolher o array
                // repetidamente) quando inserções e remoções alternam perto
                // do limite de capacidade
                resize( values.length / 2 );
            }
            
            return value;
            
        } else {
            throw new EmptyQueueException();
        }
        
    }

    @Override
    public void clear() {

        // os slots são zerados manualmente, ao invés de chamar dequeue() em
        // laço, por dois motivos: manter as referências null para permitir a
        // coleta de lixo e evitar um custo O(n^2), já que aqui dequeue() é
        // O(n) por causa do deslocamento dos elementos; diferente de
        // esvaziar a fila via dequeue() elemento a elemento, este clear()
        // não encolhe a capacidade do array através de resize()
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
                if ( !hasNext() ) {
                    throw new NoSuchElementException();
                }
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
        
        if ( !isEmpty() ) {
            
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
