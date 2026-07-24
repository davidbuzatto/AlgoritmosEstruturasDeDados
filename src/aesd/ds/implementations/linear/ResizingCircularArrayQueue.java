package aesd.ds.implementations.linear;

import aesd.ds.exceptions.EmptyQueueException;
import aesd.ds.interfaces.Queue;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Implementação de uma fila genérica com redimensionamento de array usando
 * mapeamento modular de endereços (fila circular).
 *
 * Combina as duas ideias já exploradas separadamente: o mapeamento modular
 * de CircularArrayQueue, que torna enqueue() e dequeue() O(1) (sem o
 * deslocamento de elementos custando O(n) que ResizingArrayQueue ainda tem),
 * com o redimensionamento por dobragem/redução de ResizingArrayQueue, que
 * elimina o limite fixo de capacidade. O único cuidado extra fica por conta
 * de resize(): como start pode estar em qualquer posição do array antigo
 * (não necessariamente 0), a cópia para o array novo precisa "desenrolar" o
 * conteúdo circular, realinhando-o a partir da posição 0.
 *
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms.
 * 4. ed. Boston: Pearson Education, 2011. 955 p.
 *
 * @param <Type> Tipo dos valores armazenados na fila.
 *
 * @author Prof. Dr. David Buzatto
 */
public class ResizingCircularArrayQueue<Type> implements Queue<Type> {

    // valores armazenados na fila
    private Type[] values;

    // início da fila (índice lógico, mapeado via módulo na posição física)
    private int start;

    // fim da fila (índice lógico, mapeado via módulo na posição física)
    private int end;

    // tamanho da fila
    private int size;

    /**
     * Constrói uma fila que suporta um valor.
     */
    @SuppressWarnings( "unchecked" )
    public ResizingCircularArrayQueue() {
        // o cast é necessário pois Java não permite a criação direta de um
        // array genérico por causa do apagamento de tipos (type erasure);
        // a capacidade inicial é 1 para evidenciar, já na primeira inserção,
        // o crescimento por dobragem realizado por resize()
        values = (Type[]) new Object[1];
        start = 0;
        end = -1;
    }

    /**
     * Redimensiona o array de valores, desenrolando o conteúdo circular do
     * array antigo (que pode começar em qualquer posição) a partir da
     * posição 0 do array novo. Dobrar a capacidade (fator 2x) é o que
     * garante o custo amortizado O(1) por inserção, apesar de cada
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

        // desenrola o conteúdo circular: copia as size posições ocupadas,
        // a partir de start, para o início do array novo
        for ( int i = 0; i < size; i++ ) {
            temp[i] = values[( start + i ) % values.length];
        }

        values = temp;
        start = 0;
        end = size - 1;

        // para ver a mudança de capacidade, descomente a linha abaixo.
        //System.out.println( "new capacity " + values.length + " size " + size );

    }

    @Override
    public void enqueue( Type value ) {

        // dobra o tamanho se chegou no limite da capacidade
        if ( size == values.length ) {
            resize( 2 * values.length );
        }

        // avança end sempre para frente e mapeia o resultado de volta para
        // dentro dos limites físicos do array via módulo
        end = ( end + 1 ) % values.length;
        values[end] = value;
        size++;

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
            start = ( start + 1 ) % values.length;
            size--;

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

        // percorre apenas as posições ocupadas (size delas, a partir de
        // start, mapeadas via módulo); assim como em ResizingArrayQueue,
        // este clear() não encolhe a capacidade do array através de resize()
        for ( int i = 0; i < size; i++ ) {
            values[( start + i ) % values.length] = null;
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
                Type value = values[( start + count ) % values.length];
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

                sb.append( values[( start + i ) % values.length] );

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
