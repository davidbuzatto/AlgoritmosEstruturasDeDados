package aesd.ds.interfaces;

import aesd.ds.exceptions.EmptyStackException;
import aesd.ds.exceptions.StackOverflowException;

/**
 * Interface para implementação de pilhas.
 *
 * @param <Type> Tipo dos valores que serão armazenados na pilha.
 *
 * @author Prof. Dr. David Buzatto
 */
public interface Stack<Type> extends Iterable<Type> {

    /**
     * Empilha um valor na pilha.
     *
     * @param value Valor a ser empilhado.
     * @throws StackOverflowException se a pilha tiver capacidade fixa e
     * estiver cheia.
     */
    public void push( Type value ) throws StackOverflowException;

    /**
     * Verifica qual o valor está no topo da pilha, sem removê-lo.
     *
     * @return O valor do topo da pilha.
     * @throws EmptyStackException se a pilha estiver vazia.
     */
    public Type peek() throws EmptyStackException;

    /**
     * Desempilha um valor da pilha.
     *
     * @return O valor do topo da pilha.
     * @throws EmptyStackException se a pilha estiver vazia.
     */
    public Type pop() throws EmptyStackException;

    /**
     * Remove todos os elementos dessa pilha.
     */
    public void clear();

    /**
     * Verifica se a pilha está vazia.
     *
     * @return Verdadeiro, caso a pilha esteja vazia, falso caso contrário.
     */
    public boolean isEmpty();

    /**
     * Retorna a quantidade de valores da pilha.
     *
     * @return A quantidade de valores.
     */
    public int getSize();

}
