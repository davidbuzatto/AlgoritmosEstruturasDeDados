/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.ds.implementations.nonlinear.symtable;

import aesd.ds.implementations.linear.LinkedQueue;
import aesd.ds.implementations.linear.ResizingArrayList;
import aesd.ds.interfaces.List;
import aesd.ds.interfaces.Queue;
import aesd.ds.interfaces.SymbolTable;
import java.util.Iterator;

/**
 * Implementação de uma tabela de símbolos usando busca sequencial em uma lista
 * de pares chave/valor não ordenada.
 *
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 4. ed.
 * Boston: Pearson Education, 2011. 955 p.
 *
 * @param <Key> Tipo das chaves que serão armazenadas na tabela de símbolos.
 * @param <Value> Tipo dos valores associados às chaves armazenadas na tabela de
 * símbolos.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class SequentialSearchSymbolTable<Key, Value> implements SymbolTable<Key, Value> {

    /*
     * Classe interna privada que define os nós da tabela de síbolos.
     */
    private class Node {

        private Key key;
        private Value value;
        private Node next;

        @Override
        public String toString() {
            return key + " -> " + value;
        }

    }

    // início da lista
    private Node first;

    // tamanho da tabela de símbolos (quantidade de pares chave/valor)
    private int size;

    /**
     * Constrói uma tabela de símbolos vazia.
     */
    public SequentialSearchSymbolTable() {
        first = null;   // redundante, apenas para mostrar o que acontece
    }

    @Override
    public void put( Key key, Value value ) throws IllegalArgumentException {

        if ( key == null ) {
            throw new IllegalArgumentException( "first argument to put() is null" );
        }

        if ( value == null ) {
            delete( key );
            return;
        }

        Node current = first;
        while ( current != null ) {

            if ( key.equals( current.key ) ) {
                current.value = value;
                return;
            }

            current = current.next;

        }

        Node newNode = new Node();
        newNode.key = key;
        newNode.value = value;
        newNode.next = first;
        
        first = newNode;

        size++;

    }

    @Override
    public Value get( Key key ) throws IllegalArgumentException {

        if ( key == null ) {
            throw new IllegalArgumentException( "argument to get() is null" );
        }

        Node current = first;
        while ( current != null ) {

            if ( key.equals( current.key ) ) {
                return current.value;
            }

            current = current.next;

        }

        return null;

    }

    @Override
    public void delete( Key key ) throws IllegalArgumentException {

        if ( key == null ) {
            throw new IllegalArgumentException( "argument to delete() is null" );
        }

        first = delete( first, key );

    }

    private Node delete( Node node, Key key ) {

        if ( node == null ) {
            return null;
        }

        if ( key.equals( node.key ) ) {
            size--;
            return node.next;
        }

        node.next = delete( node.next, key );

        return node;

    }

    @Override
    public boolean contains( Key key ) throws IllegalArgumentException {

        if ( key == null ) {
            throw new IllegalArgumentException( "argument to contains() is null" );
        }

        return get( key ) != null;

    }

    @Override
    public void clear() {

        Iterable<Key> keys = getKeys();

        for ( Key k : keys ) {
            delete( k );
        }

    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public Iterable<Key> getKeys() {

        Queue<Key> queue = new LinkedQueue<>();

        Node current = first;
        while ( current != null ) {
            queue.enqueue( current.key );
            current = current.next;
        }

        return queue;

    }

    @Override
    @SuppressWarnings( "unchecked" )
    public Iterator<Entry<Key, Value>> iterator() {

        List<Entry<Key, Value>> entries = new ResizingArrayList<>();

        Node current = first;
        while ( current != null ) {
            entries.add( new Entry( current.key, current.value ) );
            current = current.next;
        }

        return entries.iterator();

    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        if ( !isEmpty() ) {

            // percorrendo o encadeamento
            Node current = first;

            while ( current != null ) {

                if ( current.key.equals( first.key ) ) {
                    sb.append( current ).append( " <- first\n" );
                } else {
                    sb.append( current ).append( "\n" );
                }

                current = current.next;

            }

        } else {
            sb.append( "empty sequential search symbol table!\n" );
        }

        return sb.toString();

    }

}
