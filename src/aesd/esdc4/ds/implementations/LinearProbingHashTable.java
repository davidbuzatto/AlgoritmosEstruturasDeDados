/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdc4.ds.implementations;

import aesd.esdc4.ds.interfaces.List;
import aesd.esdc4.ds.interfaces.Queue;
import aesd.esdc4.ds.interfaces.SymbolTable;
import java.util.Iterator;

/**
 * Implementação de uma tabela de dispersão usando endereçamento aberto com
 * sondagem linear (linear probing).
 *
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 4. ed.
 * Boston: Pearson Education, 2011. 955 p.
 *
 * @param <Key> Tipo das chaves que serão armazenadas na tabela de dispersão.
 * @param <Value> Tipo dos valores associados às chaves armazenadas na tabela de
 * dispersão.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class LinearProbingHashTable<Key, Value> implements SymbolTable<Key, Value> {

    // capacidade inicial, deve ser potência de 2
    private static final int INIT_CAPACITY = 4;

    // tamanho da tabela de dispersão (quantidade de pares chave/valor)
    private int size;

    // tamanho da tabela de sondagen linear
    private int lptSize;

    // array de chaves
    private Key[] keys;

    // array de valores
    private Value[] values;

    /**
     * Constrói uma tabela de dispersão vazia.
     */
    public LinearProbingHashTable() {
        this( INIT_CAPACITY );
    }

    /**
     * Constrói uma tabela de símbolos com a capacidade inicial fornecida.
     *
     * @param capacity A capacidade inicial.
     */
    public LinearProbingHashTable( int capacity ) {
        lptSize = capacity;
        size = 0;
        keys = (Key[]) new Object[lptSize];
        values = (Value[]) new Object[lptSize];
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

        // dobra o tamanho se estiver 50% cheia
        if ( size >= lptSize / 2 ) {
            resize( 2 * lptSize );
        }

        int i;
        
        for ( i = hash( key ); keys[i] != null; i = ( i + 1 ) % lptSize ) {
            if ( keys[i].equals( key ) ) {
                values[i] = value;
                return;
            }
        }
        
        keys[i] = key;
        values[i] = value;
        size++;
        
    }

    @Override
    public Value get( Key key ) throws IllegalArgumentException {
        
        if ( key == null ) {
            throw new IllegalArgumentException( "argument to get() is null" );
        }
        
        for ( int i = hash( key ); keys[i] != null; i = ( i + 1 ) % lptSize ) {
            if ( keys[i].equals( key ) ) {
                return values[i];
            }
        }
        
        return null;
        
    }

    @Override
    public void delete( Key key ) throws IllegalArgumentException {
        
        if ( key == null ) {
            throw new IllegalArgumentException( "argument to delete() is null" );
        }
        
        if ( !contains( key ) ) {
            return;
        }

        // calcula a posição da chave
        int i = hash( key );
        
        while ( !key.equals( keys[i] ) ) {
            i = ( i + 1 ) % lptSize;
        }

        // remove a chave e o valor associado
        keys[i] = null;
        values[i] = null;

        // realiza o rehash de todas as chaves do mesmo cluster
        i = ( i + 1 ) % lptSize;
        
        while ( keys[i] != null ) {
            
            // remove keys[i] e values[i] e reinsere
            Key keyToRehash = keys[i];
            Value valToRehash = values[i];
            
            keys[i] = null;
            values[i] = null;
            size--;
            
            put( keyToRehash, valToRehash );
            i = ( i + 1 ) % lptSize;
            
        }

        size--;

        // diminui o tamanho do array se tiver 12,5% ou menos cheio
        if ( size > 0 && size <= lptSize / 8 ) {
            resize( lptSize / 2 );
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
     * Redimensiona a tabela hash para a capacidade fornecida, realizando o
     * rehashing de todas as chaves.
     *
     * @param capacity Tamanho a ser redimensionado.
     */
    private void resize( int capacity ) {
        
        LinearProbingHashTable<Key, Value> temp = new LinearProbingHashTable<>( capacity );
        
        for ( int i = 0; i < lptSize; i++ ) {
            if ( keys[i] != null ) {
                temp.put( keys[i], values[i] );
            }
        }
        
        keys = temp.keys;
        values = temp.values;
        lptSize = temp.lptSize;
        
    }

    /**
     * Função de dispersão/espalhamento/hash para as chaves. Retorna um valor
     * entre 0 e lptSize-1, assumindo que lptSize é potência de 2.
     *
     * Cópia da implementação do Java 7, protegendo contra implementações mal
     * feitas do método hashCode()
     *
     * @param key Chave a ser calculada.
     * @return índice obtido a partir da chave.
     */
    private int hash( Key key ) {
        int h = key.hashCode();
        h ^= ( h >>> 20 ) ^ ( h >>> 12 ) ^ ( h >>> 7 ) ^ ( h >>> 4 );
        return h & ( lptSize - 1 );
    }

    @Override
    public Iterable<Key> getKeys() {

        Queue<Key> queue = new LinkedQueue<>();

        for ( int i = 0; i < lptSize; i++ ) {
            if ( keys[i] != null ) {
                queue.enqueue( keys[i] );
            }
        }

        return queue;

    }

    @Override
    public void clear() {

        Iterable<Key> keys = getKeys();

        for ( Key key : keys ) {
            delete( key );
        }

    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
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
            sb.append( "empty linear probing hash table!\n" );
        }

        return sb.toString();

    }

}
