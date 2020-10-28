/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdc4.ds.implementations;

import aesd.esdc4.ds.interfaces.Stack;
import aesd.esdc4.ds.exceptions.EmptyStackException;
import java.util.Iterator;

/**
 * Implementação de uma pilha com redimensionamento de array.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class ResizingArrayStack<Item> implements Stack<Item> {

    @Override
    public void push( Item item ) {
    }

    @Override
    public Item peek() throws EmptyStackException {
        return null;
    }

    @Override
    public Item pop() throws EmptyStackException {
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
