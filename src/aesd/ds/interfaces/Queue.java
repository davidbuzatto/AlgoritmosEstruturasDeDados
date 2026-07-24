package aesd.ds.interfaces;

import aesd.ds.exceptions.EmptyQueueException;
import aesd.ds.exceptions.QueueOverflowException;

/**
 * Interface para implementação de filas.
 *
 * @param <Type> Tipo dos valores que serão armazenados na fila.
 *
 * @author Prof. Dr. David Buzatto
 */
public interface Queue<Type> extends Iterable<Type> {

    /**
     * Enfileira um valor na fila.
     *
     * @param value Valor a ser enfileirado.
     * @throws QueueOverflowException se a fila tiver capacidade fixa e
     * estiver cheia.
     */
    public void enqueue( Type value ) throws QueueOverflowException;

    /**
     * Verifica qual o valor está no início da fila, sem removê-lo.
     *
     * @return O valor do início da fila.
     * @throws EmptyQueueException se a fila estiver vazia.
     */
    public Type peek() throws EmptyQueueException;

    /**
     * Desenfileira um valor da fila.
     *
     * @return O valor do início da fila.
     * @throws EmptyQueueException se a fila estiver vazia.
     */
    public Type dequeue() throws EmptyQueueException;

    /**
     * Remove todos os elementos dessa fila.
     */
    public void clear();

    /**
     * Verifica se a fila está vazia.
     *
     * @return Verdadeiro, caso a fila esteja vazia, falso caso contrário.
     */
    public boolean isEmpty();

    /**
     * Retorna a quantidade de valores da fila.
     *
     * @return A quantidade de valores.
     */
    public int getSize();

}
