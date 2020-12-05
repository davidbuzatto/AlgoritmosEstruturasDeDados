/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdc4.ds.interfaces;

import java.util.NoSuchElementException;

/**
 * Interface para implementação de filas de prioridades.
 * 
 * @param <Key> Tipo das chaves que serão armazenadas na fila de prioridades.
 * 
 * @author Prof. Dr. David Buzatto
 */
public interface PriorityQueue<Key extends Comparable<Key>> extends Iterable<Key> {
    
    /**
     * Insere uma nova chave à essa fila de prioridades.
     *
     * @param key a nova chave a ser inserida na fila de prioridades
     */
    public void insert( Key key );
    
    /**
     * Retorna a chave que tem maior prioridade definida de acordo com a 
     * invariante da fila de prioridades implementada, sem removê-la.
     *
     * @return a chave com maior prioridade da fila de prioridades
     * @throws NoSuchElementException se essa fila de prioridades estiver vazia
     */
    public Key peek() throws NoSuchElementException;
    
    /**
     * Remove e retorna a chave que tem maior prioridade definida de acordo com a 
     * invariante da fila de prioridades implementada.
     *
     * @return a chave com maior prioridade da fila de prioridades
     * @throws NoSuchElementException se essa fila de prioridades estiver vazia
     */
    public Key delete() throws NoSuchElementException;
    
    /**
     * Remove todos os elementos dessa fila de prioridades.
     */
    public void clear();
    
    /**
     * Verifica se a fila de prioridades está vazia.
     * 
     * @return Verdadeiro, caso a fila de prioridades esteja vazia, falso caso
     * contrário.
     */
    public boolean isEmpty();
    
    /**
     * Retorna a quantidade de chaves da fila de prioridades.
     * 
     * @return A quantidade de chaves.
     */
    public int getSize();
    
}
