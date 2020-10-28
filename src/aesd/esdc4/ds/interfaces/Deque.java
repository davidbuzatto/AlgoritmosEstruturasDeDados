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
 * @param <Item> Tipo do itens que serão armazenados na deque.
 */
public interface Deque<Item> extends Iterable<Item> {
    
    /**
     * Insere um item no início da deque.
     * 
     * @param item Item a ser inserido no início.
     */
    public void addFirst( Item item );
    
    /**
     * Insere um item no fim da deque.
     * 
     * @param item Item a ser inserido no fim.
     */
    public void addLast( Item item );
    
    /**
     * Verifica qual o item está no início da deque, sem removê-lo.
     * 
     * @return O item do início da deque.
     */
    public Item peekFirst();
    
    /**
     * Verifica qual o item está no fim da deque, sem removê-lo.
     * 
     * @return O item do fim da deque.
     */
    public Item peekLast();
    
    /**
     * Remove um item do início da deque.
     * 
     * @return O item do início da deque.
     */
    public Item removeFirst();
    
    /**
     * Remove um item do fim da deque.
     * 
     * @return O item do fim da deque.
     */
    public Item removeLast();
    
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
     * Retorna a quantidade de itens da deque.
     * 
     * @return A quantidade de itens.
     */
    public int getSize();
    
}
