/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdi3.ds.interfaces;

/**
 * Interface para implementação de filas.
 * 
 * @param <Type> Tipo dos valores que serão armazenados na fila.
 * 
 * @author Prof. Dr. David Buzatto
 */
public interface Queue<Type> extends Iterable<Type> {
    
    /**
     * Enfileira um valor na filha.
     * 
     * @param value Valor a ser enfileirado.
     */
    public void enqueue( Type value );
    
    /**
     * Verifica qual o valor está no início da fila, sem removê-lo.
     * 
     * @return O valor do início da fila.
     */
    public Type peek();
    
    /**
     * Desenfileira um valor da fila.
     * 
     * @return O valor do início da fila.
     */
    public Type dequeue();
    
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
