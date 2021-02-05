/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdi3.ds.implementations.linear;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Uma Bag (Bolsa ou Multiset) genérica implementada com encadeamento simples.
 * 
 * Essa estrutura de dados serve para armazenar dados e permitir a iteração
 * sobre eles.
 * 
 * A utilização dela nesse projeto está relacionada principalmente às 
 * classes de grafos com implementação usando listas de adjacências.
 * 
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 4. ed.
 * Boston: Pearson Education, 2011. 955 p.
 *
 * @param <Type> Tipo dos valores armazenados na bag.
 *
 * @author Prof. Dr. David Buzatto
 */
public class Bag<Type> implements Iterable<Type> {

    // início da bag
    private Node<Type> first;
    
    // número de elementos na bag
    private int size;

    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
    }

    /**
     * Cria uma bag vazia.
     */
    public Bag() {
        first = null;
        size = 0;
    }

    /**
     * Insere um item na bag.
     *
     * @param value o elemento que será inserido
     */
    public void add( Type value ) {
        
        Node<Type> oldfirst = first;
        first = new Node<>();
        first.item = value;
        first.next = oldfirst;
        
        size++;
        
    }
    
    /**
     * Verifica se a bag está vazia.
     * 
     * @return Verdadeiro, caso a bag esteja vazia, falso caso contrário.
     */
    public boolean isEmpty() {
        return first == null;
    }

    /**
     * Retorna a quantidade de elementos da bag.
     * 
     * @return A quantidade de elementos.
     */
    public int getSize() {
        return size;
    }

    /**
     * Returns an iterator that iterates over the items in this bag in arbitrary
     * order.
     *
     * @return an iterator that iterates over the items in this bag in arbitrary
     * order
     */
    @Override
    public Iterator<Type> iterator() {
        return new LinkedIterator( first );
    }
    
    private class LinkedIterator implements Iterator<Type> {

        private Node<Type> current;

        public LinkedIterator( Node<Type> first ) {
            current = first;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }
        
        @Override
        public Type next() {
            if ( !hasNext() ) {
                throw new NoSuchElementException();
            }
            Type item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
        
    }

}
