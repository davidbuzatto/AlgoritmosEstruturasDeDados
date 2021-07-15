/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.ds.interfaces;

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
     */
    public void push( Type value );
    
    /**
     * Verifica qual o valor está no topo da pilha, sem removê-lo.
     * 
     * @return O valor do topo da pilha.
     */
    public Type peek();
    
    /**
     * Desempilha um valor da pilha.
     * 
     * @return O valor do topo da pilha.
     */
    public Type pop();
    
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
