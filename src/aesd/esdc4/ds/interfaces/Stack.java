/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdc4.ds.interfaces;

/**
 * Interface para implementação de pilhas.
 * 
 * @author David Buzatto
 * @param <Item> Tipo do itens que serão armazenados na pilha.
 */
public interface Stack<Item> extends Iterable<Item> {
    
    /**
     * Empilha um item na pilha.
     * 
     * @param item Item a ser empilhado.
     */
    public void push( Item item );
    
    /**
     * Verifica qual o item está no topo da pilha, sem removê-lo.
     * 
     * @return O item do topo da pilha.
     */
    public Item peek();
    
    /**
     * Desempilha um item da pilha.
     * 
     * @return O item do topo da pilha.
     */
    public Item pop();
    
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
     * Retorna a quantidade de itens da pilha.
     * 
     * @return A quantidade de itens.
     */
    public int getSize();
    
}
