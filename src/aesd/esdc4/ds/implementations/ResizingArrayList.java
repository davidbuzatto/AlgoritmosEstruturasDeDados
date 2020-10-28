/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdc4.ds.implementations;

import aesd.esdc4.ds.interfaces.List;
import aesd.esdc4.ds.exceptions.EmptyListException;
import java.util.Iterator;

/**
 * Implementação de uma lista com redimensionamento de array.
 * Usar módulo nessa!
 * 
 * @author Prof. Dr. David Buzatto
 */
public class ResizingArrayList<Item> implements List<Item> {

    @Override
    public void add( Item item ) {
    }
    
    @Override
    public void add( int index, Item item ) {
    }

    @Override
    public Item get( int index ) throws EmptyListException {
        return null;
    }

    @Override
    public Item remove( int index ) throws EmptyListException {
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
