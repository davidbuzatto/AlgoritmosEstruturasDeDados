/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdc4.ds.implementations;

import aesd.esdc4.ds.interfaces.Queue;
import aesd.esdc4.ds.exceptions.EmptyQueueException;
import java.util.Iterator;

/**
 * Implementação de uma fila com encadeamento.
 * 
 * @author David Buzatto
 */
public class LinkedQueue<Item> implements Queue<Item> {

    @Override
    public void enqueue( Item item ) {
    }

    @Override
    public Item peek() throws EmptyQueueException {
        return null;
    }

    @Override
    public Item dequeue() throws EmptyQueueException {
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
