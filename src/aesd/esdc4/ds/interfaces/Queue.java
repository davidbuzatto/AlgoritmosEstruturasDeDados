/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdc4.ds.interfaces;

/**
 * Interface para implementação de filas.
 * 
 * @author Prof. Dr. David Buzatto
 * @param <Item> Tipo do itens que serão armazenados na fila.
 */
public interface Queue<Item> extends Iterable<Item> {
    
    /**
     * Enfileira um item na filha.
     * 
     * @param item Item a ser enfileirado.
     */
    public void enqueue( Item item );
    
    /**
     * Verifica qual o item está no início da fila, sem removê-lo.
     * 
     * @return O item do início da fila.
     */
    public Item peek();
    
    /**
     * Desenfileira um item da fila.
     * 
     * @return O item do início da fila.
     */
    public Item dequeue();
    
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
     * Retorna a quantidade de itens da fila.
     * 
     * @return A quantidade de itens.
     */
    public int getSize();
    
}
