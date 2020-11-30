/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdc4.ds.interfaces;

/**
 * Interface para implementação de deques (double ended queues, filas de fim
 * duplo).
 * 
 * @author Prof. Dr. David Buzatto
 * @param <Type> Tipo do valores que serão armazenados na deque.
 */
public interface Deque<Type> extends Iterable<Type> {
    
    /**
     * Insere um valor no início da deque.
     * 
     * @param value Valor a ser inserido no início.
     */
    public void addFirst( Type value );
    
    /**
     * Insere um valor no fim da deque.
     * 
     * @param value Valor a ser inserido no fim.
     */
    public void addLast( Type value );
    
    /**
     * Verifica qual o valor está no início da deque, sem removê-lo.
     * 
     * @return O valor do início da deque.
     */
    public Type peekFirst();
    
    /**
     * Verifica qual o valor está no fim da deque, sem removê-lo.
     * 
     * @return O valor do fim da deque.
     */
    public Type peekLast();
    
    /**
     * Remove um valor do início da deque.
     * 
     * @return O valor do início da deque.
     */
    public Type removeFirst();
    
    /**
     * Remove um valor do fim da deque.
     * 
     * @return O valor do fim da deque.
     */
    public Type removeLast();
    
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
