/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdi3.ds.implementations.nonlinear.symtable;

import aesd.esdi3.ds.implementations.linear.LinkedQueue;
import aesd.esdi3.ds.implementations.linear.ResizingArrayList;
import aesd.esdi3.ds.interfaces.List;
import aesd.esdi3.ds.interfaces.Queue;
import aesd.esdi3.ds.interfaces.SymbolTable;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Implementação de uma tabela de símbolo usando busca binária em um array
 * ordenado.
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
public class BinarySearchSymbolTable<Key extends Comparable<Key>, Value> implements SymbolTable<Key, Value> {

    // capacidade inicial
    private static final int INIT_CAPACITY = 2;

    // array de chaves
    private Key[] keys;

    // array de valores
    private Value[] values;

    // tamanho da tabela de símbolos (quantidade de pares chave/valor)
    private int size;

    /**
     * Constrói uma tabela de símbolos vazia.
     */
    public BinarySearchSymbolTable() {
        this( INIT_CAPACITY );
    }

    /**
     * Constrói uma tabela de símbolos com a capacidade inicial definida.
     *
     * @param capacity A nova capacidade.
     */
    @SuppressWarnings( "unchecked" )
    public BinarySearchSymbolTable( int capacity ) {
        keys = (Key[]) new Comparable[capacity];
        values = (Value[]) new Object[capacity];
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

        // computa o ranque da chave
        int i = rank( key );

        // a chave já está na tabela
        if ( i < size && keys[i].compareTo( key ) == 0 ) {
            values[i] = value;
            return;
        }

        // dobra o tamanho se chegou no limite da capacidade
        if ( size == keys.length ) {
            resize( 2 * keys.length );
        }

        // move os elementos com base no ranque para liberar
        // espaço para inserção
        for ( int j = size; j > i; j-- ) {
            keys[j] = keys[j-1];
            values[j] = values[j-1];
        }

        // insere um novo par chave/valor
        keys[i] = key;
        values[i] = value;
        size++;

    }

    @Override
    public Value get( Key key ) throws IllegalArgumentException {

        if ( key == null ) {
            throw new IllegalArgumentException( "argument to get() is null" );
        }

        if ( isEmpty() ) {
            return null;
        }

        // computa o ranque da chave
        int i = rank( key );

        if ( i < size && keys[i].compareTo( key ) == 0 ) {
            return values[i];
        }

        return null;

    }

    @Override
    public void delete( Key key ) throws IllegalArgumentException {

        if ( key == null ) {
            throw new IllegalArgumentException( "argument to delete() is null" );
        }
        if ( isEmpty() ) {
            return;
        }

        // computa o ranque da chave
        int i = rank( key );

        // a chave não está na tabela
        if ( i == size || keys[i].compareTo( key ) != 0 ) {
            return;
        }

        // move os elementos com base no ranque para ocupar a posição
        // que era ocupada
        for ( int j = i; j < size - 1; j++ ) {
            keys[j] = keys[j+1];
            values[j] = values[j+1];
        }

        size--;
        keys[size] = null;   // marca para coleta de lixo!
        values[size] = null; // idem

        // redimensiona para a metade caso o tamanho seja um quarto do total
        if ( size > 0 && size == keys.length / 4 ) {
            resize( keys.length / 2 );
        }

    }

    @Override
    public boolean contains( Key key ) throws IllegalArgumentException {

        if ( key == null ) {
            throw new IllegalArgumentException( "argument to contains() is null" );
        }

        return get( key ) != null;

    }

    /**
     * Redimensiona os arrays.
     *
     * @param capacity Tamanho a ser redimensionado.
     */
    @SuppressWarnings( "unchecked" )
    private void resize( int capacity ) {

        Key[] tempK = (Key[]) new Comparable[capacity];
        Value[] tempV = (Value[]) new Object[capacity];

        for ( int i = 0; i < size; i++ ) {
            tempK[i] = keys[i];
            tempV[i] = values[i];
        }

        values = tempV;
        keys = tempK;

    }

    // calcula o ranque da chave realizando uma busca binária
    public int rank( Key key ) throws IllegalArgumentException {

        if ( key == null ) {
            throw new IllegalArgumentException( "argument to rank() is null" );
        }

        int lower = 0;
        int higher = size - 1;

        while ( lower <= higher ) {

            int mid = lower + ( higher - lower ) / 2; // ou ( lower + higher ) / 2
            int comp = key.compareTo( keys[mid] );

            if ( comp < 0 ) {
                higher = mid - 1;
            } else if ( comp > 0 ) {
                lower = mid + 1;
            } else {
                return mid;
            }

        }

        return lower;

    }

    public Key min() throws NoSuchElementException {

        if ( isEmpty() ) {
            throw new NoSuchElementException( "called min() with empty symbol table" );
        }

        return keys[0];

    }

    public Key max() throws NoSuchElementException {

        if ( isEmpty() ) {
            throw new NoSuchElementException( "called max() with empty symbol table" );
        }

        return keys[size - 1];

    }

    @Override
    public Iterable<Key> getKeys() {
        return getKeys( min(), max() );
    }

    public Iterable<Key> getKeys( Key lower, Key higher ) throws IllegalArgumentException {

        if ( lower == null ) {
            throw new IllegalArgumentException( "first argument to keys() is null" );
        }

        if ( higher == null ) {
            throw new IllegalArgumentException( "second argument to keys() is null" );
        }

        Queue<Key> queue = new LinkedQueue<Key>();

        if ( lower.compareTo( higher ) > 0 ) {
            return queue;
        }

        for ( int i = rank( lower ); i < rank( higher ); i++ ) {
            queue.enqueue( keys[i] );
        }

        if ( contains( higher ) ) {
            queue.enqueue( keys[rank( higher )] );
        }

        return queue;

    }

    @Override
    public void clear() {

        for ( int i = 0; i < size; i++ ) {
            keys[i] = null;
            values[i] = null;
        }

        size = 0;

    }

    @Override
    public boolean isEmpty() {
        return getSize() == 0;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    @SuppressWarnings( "unchecked" )
    public Iterator<Entry<Key, Value>> iterator() {

        List<Entry<Key, Value>> entries = new ResizingArrayList<>();

        for ( Key key : getKeys() ) {
            entries.add( new Entry( key, get( key ) ) );
        }

        return entries.iterator();

    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        if ( !isEmpty() ) {

            for ( Key key : getKeys() ) {
                String v = key + " -> " + get( key );
                sb.append( v ).append( "\n" );
            }

        } else {
            sb.append( "empty binary search symbol table!\n" );
        }

        return sb.toString();

    }

}
