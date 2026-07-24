package aesd.ds.interfaces;

import aesd.ds.exceptions.DequeOverflowException;
import aesd.ds.exceptions.EmptyDequeException;

/**
 * Interface para implementação de deques (double ended queues, filas de fim
 * duplo).
 *
 * @param <Type> Tipo dos valores que serão armazenados na deque.
 *
 * @author Prof. Dr. David Buzatto
 */
public interface Deque<Type> extends Iterable<Type> {

    /**
     * Insere um valor no início da deque.
     *
     * @param value Valor a ser inserido no início.
     * @throws DequeOverflowException se a deque tiver capacidade fixa e
     * estiver cheia.
     */
    public void addFirst( Type value ) throws DequeOverflowException;

    /**
     * Insere um valor no fim da deque.
     *
     * @param value Valor a ser inserido no fim.
     * @throws DequeOverflowException se a deque tiver capacidade fixa e
     * estiver cheia.
     */
    public void addLast( Type value ) throws DequeOverflowException;

    /**
     * Verifica qual o valor está no início da deque, sem removê-lo.
     *
     * @return O valor do início da deque.
     * @throws EmptyDequeException se a deque estiver vazia.
     */
    public Type peekFirst() throws EmptyDequeException;

    /**
     * Verifica qual o valor está no fim da deque, sem removê-lo.
     *
     * @return O valor do fim da deque.
     * @throws EmptyDequeException se a deque estiver vazia.
     */
    public Type peekLast() throws EmptyDequeException;

    /**
     * Remove um valor do início da deque.
     *
     * @return O valor do início da deque.
     * @throws EmptyDequeException se a deque estiver vazia.
     */
    public Type removeFirst() throws EmptyDequeException;

    /**
     * Remove um valor do fim da deque.
     *
     * @return O valor do fim da deque.
     * @throws EmptyDequeException se a deque estiver vazia.
     */
    public Type removeLast() throws EmptyDequeException;

    /**
     * Remove todos os elementos dessa deque.
     */
    public void clear();

    /**
     * Verifica se a deque está vazia.
     *
     * @return Verdadeiro, caso a deque esteja vazia, falso caso contrário.
     */
    public boolean isEmpty();

    /**
     * Retorna a quantidade de valores da deque.
     *
     * @return A quantidade de valores.
     */
    public int getSize();

}
