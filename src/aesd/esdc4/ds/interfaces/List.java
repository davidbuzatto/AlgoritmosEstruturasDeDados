/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdc4.ds.interfaces;

import aesd.esdc4.ds.exceptions.EmptyListException;
import aesd.esdc4.ds.exceptions.ListIndexOutOfBoundsException;

/**
 * Interface para implementação de listas.
 * 
 * @author Prof. Dr. David Buzatto
 * @param <Item> Tipo do itens que serão armazenados na lista.
 */
public interface List<Item> extends Iterable<Item> {
    
    /**
     * Insere um item no fim da lista.
     * 
     * @param item Item a ser inserido no fim.
     */
    public void add( Item item );
    
    /**
     * Insere um item em uma posição da lista.
     * 
     * @param index Índice em que o item será inserido.
     * @param item Item a ser inserido.
     */
    public void add( int index, Item item ) throws ListIndexOutOfBoundsException;
    
    /**
     * Retorna um item de uma posição específica da lista.
     * 
     * @param index Índice em que o item está.
     * @return O item da lista.
     */
    public Item get( int index ) 
            throws EmptyListException, ListIndexOutOfBoundsException;
    
    /**
     * Altera o valor de um item da lista.
     * 
     * @param index Índice do item que será alterado.
     * @return O novo valor do item da lista.
     */
    public void set( int index, Item item ) 
            throws EmptyListException, ListIndexOutOfBoundsException;
    
    /**
     * Remove um item de uma posição da lista.
     * 
     * @param index Índice do item que será removido.
     * @return O item da lista.
     */
    public Item remove( int index ) 
            throws EmptyListException, ListIndexOutOfBoundsException;
    
    /**
     * Remove todos os elementos dessa lista.
     */
    public void clear();
    
    /**
     * Verifica se a lista está vazia.
     * 
     * @return Verdadeiro, caso a lista esteja vazia, falso caso contrário.
     */
    public boolean isEmpty();
    
    /**
     * Retorna a quantidade de itens da lista.
     * 
     * @return A quantidade de itens.
     */
    public int getSize();

}
