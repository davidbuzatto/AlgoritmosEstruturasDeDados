/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdc4.ds.implementations;

import aesd.esdc4.ds.interfaces.Deque;
import aesd.esdc4.ds.exceptions.DequeOverflowException;
import aesd.esdc4.ds.exceptions.EmptyDequeException;
import java.util.Iterator;

/**
 * Implementação de uma deque com redimensionamento de array.
 * Usar módulo nessa!
 * 
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 
 * 4. ed. Boston: Pearson Education, 2011. 955 p.
 * 
 * @param <Item> Tipo dos itens armazenados na deque.
 *
 * @author Prof. Dr. David Buzatto
 */
public class ResizingArrayDeque<Item> implements Deque<Item> {

    @Override
    public void addFirst( Item item ) throws DequeOverflowException {
    }
    
    @Override
    public void addLast( Item item ) throws DequeOverflowException {
    }

    @Override
    public Item peekFirst() throws EmptyDequeException {
        return null;
    }
    
    @Override
    public Item peekLast() throws EmptyDequeException {
        return null;
    }

    @Override
    public Item removeFirst() throws EmptyDequeException {
        return null;
    }
    
    @Override
    public Item removeLast() throws EmptyDequeException {
        return null;
    }

    @Override
    public void clear() {
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public Iterator<Item> iterator() {
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        return sb.toString();
    }
    
}
