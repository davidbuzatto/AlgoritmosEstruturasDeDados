package aesd.ds.implementations.nonlinear.symtable;

import aesd.ds.implementations.linear.LinkedQueue;
import aesd.ds.implementations.linear.ResizingArrayList;
import aesd.ds.interfaces.List;
import aesd.ds.interfaces.Queue;
import aesd.ds.interfaces.SymbolTable;
import java.util.Iterator;

/**
 * Implementação de uma tabela de dispersão com encadeamento.
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
public class SeparateChainingHashTable<Key, Value> implements SymbolTable<Key, Value> {

    // capacidade inicial, deve ser potência de 2
    private static final int INIT_CAPACITY = 4;

    // tamanho da tabela de dispersão (quantidade de pares chave/valor)
    private int size;

    // tamanho da tabela de dispersão (quantidade de encadeamentos)
    private int htSize;

    // array de tabelas de símbolos com encadeamento
    private SequentialSearchSymbolTable<Key, Value>[] st;

    /**
     * Constrói uma tabela de dispersão vazia.
     */
    public SeparateChainingHashTable() {
        this( INIT_CAPACITY );
    }

    /**
     * Constrói uma tabela de símbolos com a capacidade inicial de encadeamentos
     * fornecida.
     *
     * @param numberOfChains Quantidade de encadeamentos.
     */
    @SuppressWarnings( "unchecked" )
    public SeparateChainingHashTable( int numberOfChains ) {

        this.htSize = numberOfChains;

        st = new SequentialSearchSymbolTable[numberOfChains];

        for ( int i = 0; i < numberOfChains; i++ ) {
            st[i] = new SequentialSearchSymbolTable<>();
        }

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

        // dobra o tamanho da tabela caso a média do comprimento da lista seja
        // maior ou igual à 10
        if ( size >= 10 * htSize ) {
            resize( 2 * htSize );
        }

        int i = hash( key );

        if ( !st[i].contains( key ) ) {
            size++;
        }

        st[i].put( key, value );

    }

    @Override
    public Value get( Key key ) throws IllegalArgumentException {

        if ( key == null ) {
            throw new IllegalArgumentException( "argument to get() is null" );
        }

        int i = hash( key );

        return st[i].get( key );

    }

    @Override
    public void delete( Key key ) throws IllegalArgumentException {

        if ( key == null ) {
            throw new IllegalArgumentException( "argument to delete() is null" );
        }

        int i = hash( key );

        if ( st[i].contains( key ) ) {
            size--;
        }

        st[i].delete( key );

        // se o tamanho médio da lista é menor ou igual a 2,
        // diminui a capacidade pela metade
        if ( htSize > INIT_CAPACITY && size <= 2 * htSize ) {
            resize( htSize / 2 );
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
     * Redimensiona a tabela hash para a quantidade de encadeamentos fornecido,
     * realizando o rehashing de todas as chaves.
     *
     * @param chains Quantidade de cadeias a ser utilizado.
     */
    private void resize( int chains ) {

        SeparateChainingHashTable<Key, Value> temp = new SeparateChainingHashTable<>( chains );

        for ( int i = 0; i < htSize; i++ ) {
            for ( Key key : st[i].getKeys() ) {
                temp.put( key, st[i].get( key ) );
            }
        }

        this.htSize = temp.htSize;
        this.size = temp.size;
        this.st = temp.st;

    }
    
    private int hash( Key key ) {
        return ( key.hashCode() & 0x7fffffff ) % htSize;
    }

    @Override
    public Iterable<Key> getKeys() {

        Queue<Key> queue = new LinkedQueue<>();

        for ( int i = 0; i < htSize; i++ ) {
            for ( Key key : st[i].getKeys() ) {
                queue.enqueue( key );
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
            sb.append( "empty separate chaining hash table!\n" );
        }

        return sb.toString();

    }

}
