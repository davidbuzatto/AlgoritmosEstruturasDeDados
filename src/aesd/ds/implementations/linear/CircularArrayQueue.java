package aesd.ds.implementations.linear;

import aesd.ds.interfaces.Queue;
import aesd.ds.exceptions.EmptyQueueException;
import aesd.ds.exceptions.QueueOverflowException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Implementação de uma fila genérica com capacidade fixa usando mapeamento
 * modular de endereços (fila circular).
 *
 * Em FixedCapacityQueue, dequeue() sempre remove do índice 0 e desloca todos
 * os elementos restantes uma posição para a esquerda, custando O(n). Aqui,
 * início (start) e fim (end) são índices lógicos que avançam sempre para
 * frente, mas são mapeados na posição física do array por aritmética modular
 * (índice % capacidade); quando um deles ultrapassa o final do array, o
 * módulo o traz de volta ao início, dando a impressão de um array circular.
 * Isso elimina a necessidade de deslocar elementos: tanto enqueue() quanto
 * dequeue() passam a ser O(1).
 *
 * Obs: como start e end são índices lógicos (não posições fixas), o conteúdo
 * do array físico pode ficar "quebrado" entre o fim e o início dele mesmo
 * (por exemplo, com end < start), sem que isso afete a fila logicamente.
 *
 * Questões a se pensar:
 *     E se for preciso enfileirar mais elementos do que a capacidade
 *     máxima suporta? Dá para redimensionar uma fila circular também?
 *
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms.
 * 4. ed. Boston: Pearson Education, 2011. 955 p.
 *
 * @param <Type> Tipo dos valores armazenados na fila.
 *
 * @author Prof. Dr. David Buzatto
 */
public class CircularArrayQueue<Type> implements Queue<Type> {

    // valores armazenados na fila
    private Type[] values;

    // início da fila (índice lógico, mapeado via módulo na posição física)
    private int start;

    // fim da fila (índice lógico, mapeado via módulo na posição física)
    private int end;

    // tamanho da fila
    private int size;

    // tamanho máximo suportado pela fila
    private int maxSize;

    /**
     * Constrói uma fila vazia que suporta dez valores.
     */
    public CircularArrayQueue() {
        this( 10 );
    }

    /**
     * Constrói uma fila vazia de tamanho especificado.
     *
     * @param max Tamanho máximo da fila.
     * @throws IllegalArgumentException se max for menor ou igual a zero.
     */
    @SuppressWarnings( "unchecked" )
    public CircularArrayQueue( int max ) throws IllegalArgumentException {

        if ( max <= 0 ) {
            throw new IllegalArgumentException( "max capacity must be greater than zero" );
        }

        maxSize = max;
        // o cast é necessário pois Java não permite a criação direta de um
        // array genérico por causa do apagamento de tipos (type erasure)
        values = (Type[]) new Object[maxSize];
        start = 0;
        end = -1;

    }

    @Override
    public void enqueue( Type value ) throws QueueOverflowException {

        if ( size < maxSize ) {
            // avança end sempre para frente e mapeia o resultado de volta
            // para dentro dos limites físicos do array via módulo
            end = ( end + 1 ) % maxSize;
            values[end] = value;
            size++;
        } else {
            throw new QueueOverflowException();
        }

    }

    @Override
    public Type peek() throws EmptyQueueException {

        if ( !isEmpty() ) {
            return values[start];
        } else {
            throw new EmptyQueueException();
        }

    }

    @Override
    public Type dequeue() throws EmptyQueueException {

        if ( !isEmpty() ) {

            Type value = values[start];
            values[start] = null;      // marca como null para coleta de lixo

            // avança start sempre para frente e mapeia o resultado de volta
            // para dentro dos limites físicos do array via módulo
            start = ( start + 1 ) % maxSize;
            size--;

            return value;

        } else {
            throw new EmptyQueueException();
        }

    }

    @Override
    public void clear() {

        // percorre apenas as posições ocupadas (size delas, a partir de
        // start, mapeadas via módulo), diferente de zerar o array inteiro
        for ( int i = 0; i < size; i++ ) {
            values[( start + i ) % maxSize] = null;
        }

        start = 0;
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

            private int count = 0;

            @Override
            public boolean hasNext() {
                return count < size;
            }

            @Override
            public Type next() {
                if ( !hasNext() ) {
                    throw new NoSuchElementException();
                }
                Type value = values[( start + count ) % maxSize];
                count++;
                return value;
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

            // percorrendo o array de valores a partir de start, mapeando
            // cada posição lógica na posição física via módulo
            for ( int i = 0; i < size; i++ ) {

                sb.append( values[( start + i ) % maxSize] );

                if ( size == 1 ) {
                    sb.append( " <- start/end\n" );
                } else if ( i == 0 ) {
                    sb.append( " <- start\n" );
                } else if ( i == size - 1 ) {
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
