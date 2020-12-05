/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdc4.ds.implementations.nonlinear.pq;

import java.util.Iterator;
import java.util.NoSuchElementException;
import aesd.esdc4.ds.interfaces.IndexedPriorityQueue;

/**
 * Implementação de uma fila de prioridades máxima indexada usando um heap
 * binário máximo.
 * 
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 4. ed.
 * Boston: Pearson Education, 2011. 955 p.
 *
 * @param <Key> Tipo das chaves que serão armazenadas na fila de prioridades
 * indexada.
 *
 * @author Prof. Dr. David Buzatto
 */
public class IndexedMaxPriorityQueue<Key extends Comparable<Key>> implements IndexedPriorityQueue<Key> {

    // quantidade máxima de elementos na fila de prioridades
    private int maxN;
    
    // quantidade de itens na fila de prioridades
    private int n;
    
    // heap binário dos índices da fila de prioridades, nas posições 1 até n (size)
    private int[] pq;
    
    // inverso de pq - qp[pq[i]] = pq[qp[i]] = i
    private int[] qp;
    
    // keys[i] = prioridade de i
    private Key[] keys;

    /**
     * Cria uma fila de prioridade máxima indexada com índices entre 0 e maxN - 1.
     *
     * @param maxN a quantidade de chaves permitida
     * @throws IllegalArgumentException se a quantidade for menor que 0
     */
    @SuppressWarnings( "unchecked" )
    public IndexedMaxPriorityQueue( int maxN ) throws IllegalArgumentException {
        
        if ( maxN < 0 ) {
            throw new IllegalArgumentException();
        }
        
        this.maxN = maxN;
        
        n = 0;
        keys = (Key[]) new Comparable[maxN + 1];
        pq = new int[maxN + 1];
        qp = new int[maxN + 1];
        
        for ( int i = 0; i <= maxN; i++ ) {
            qp[i] = -1;
        }
        
    }

    @Override
    public void insert( int index, Key key ) throws IllegalArgumentException {
        
        validateIndex( index );
        
        if ( contains( index ) ) {
            throw new IllegalArgumentException( "index is already in the priority queue" );
        }
        
        n++;
        qp[index] = n;
        pq[n] = index;
        keys[index] = key;
        
        swim( n );
        
    }

    @Override
    public int peekIndex() throws NoSuchElementException {
        
        if ( n == 0 ) {
            throw new NoSuchElementException( "Priority queue underflow" );
        }
        
        return pq[1];
        
    }

    @Override
    public Key peekKey() throws NoSuchElementException {
        
        if ( n == 0 ) {
            throw new NoSuchElementException( "Priority queue underflow" );
        }
        
        return keys[pq[1]];
        
    }

    @Override
    public int delete() throws NoSuchElementException {
        
        if ( n == 0 ) {
            throw new NoSuchElementException( "Priority queue underflow" );
        }
        
        int max = pq[1];
        exchange( 1, n-- );
        sink( 1 );

        qp[max] = -1;          // exclui
        keys[max] = null;      // marca como null para coleta de lixo
        pq[n + 1] = -1;        // desnecessário
        
        return max;
        
    }
    
    @Override
    public void delete( int index ) {
        
        validateIndex( index );
        
        if ( !contains( index ) ) {
            throw new NoSuchElementException( "index is not in the priority queue" );
        }
        
        int tempIndex = qp[index];
        
        exchange( tempIndex, n-- );
        swim( tempIndex );
        sink( tempIndex );
        
        keys[index] = null;
        qp[index] = -1;
        
    }
    
    @Override
    public boolean contains( int index ) throws IllegalArgumentException {
        validateIndex( index );
        return qp[index] != -1;
    }

    @Override
    public Key keyOf( int index ) throws NoSuchElementException {
        
        validateIndex( index );
        
        if ( !contains( index ) ) {
            throw new NoSuchElementException( "index is not in the priority queue" );
        } else {
            return keys[index];
        }
        
    }

    @Override
    public void changeKey( int index, Key key ) {
        
        validateIndex( index );
        
        if ( !contains( index ) ) {
            throw new NoSuchElementException( "index is not in the priority queue" );
        }
        
        keys[index] = key;
        
        swim( qp[index] );
        sink( qp[index] );
        
    }

    @Override
    public void increaseKey( int index, Key key ) {
        
        validateIndex( index );
        
        if ( !contains( index ) ) {
            throw new NoSuchElementException( "index is not in the priority queue" );
        }
        
        if ( keys[index].compareTo( key ) == 0 ) {
            throw new IllegalArgumentException( "Calling increaseKey() with a key equal to the key in the priority queue" );
        }
        
        if ( keys[index].compareTo( key ) > 0 ) {
            throw new IllegalArgumentException( "Calling increaseKey() with a key that is strictly less than the key in the priority queue" );
        }

        keys[index] = key;
        swim( qp[index] );
        
    }
    
    @Override
    public void decreaseKey( int index, Key key ) {
        
        validateIndex( index );
        
        if ( !contains( index ) ) {
            throw new NoSuchElementException( "index is not in the priority queue" );
        }
        
        if ( keys[index].compareTo( key ) == 0 ) {
            throw new IllegalArgumentException( "Calling decreaseKey() with a key equal to the key in the priority queue" );
        }
        
        if ( keys[index].compareTo( key ) < 0 ) {
            throw new IllegalArgumentException( "Calling decreaseKey() with a key that is strictly greater than the key in the priority queue" );
        }
        
        keys[index] = key;
        sink( qp[index] );
        
    }

    private void validateIndex( int index ) {
        
        if ( index < 0 ) {
            throw new IllegalArgumentException( "index is negative: " + index );
        }
        
        if ( index >= maxN ) {
            throw new IllegalArgumentException( "index >= capacity: " + index );
        }
        
    }

    private boolean less( int i, int j ) {
        return keys[pq[i]].compareTo( keys[pq[j]] ) < 0;
    }

    private void exchange( int i, int j ) {
        
        int temp = pq[i];
        
        pq[i] = pq[j];
        pq[j] = temp;
        
        qp[pq[i]] = i;
        qp[pq[j]] = j;
        
    }

    private void swim( int k ) {
        
        while ( k > 1 && less( k / 2, k ) ) {
            exchange( k, k / 2 );
            k = k / 2;
        }
        
    }

    private void sink( int k ) {
        
        while ( 2 * k <= n ) {
            
            int j = 2 * k;
            
            if ( j < n && less( j, j + 1 ) ) {
                j++;
            }
            
            if ( !less( k, j ) ) {
                break;
            }
            
            exchange( k, j );
            k = j;
            
        }
        
    }

    @Override
    public void clear() {
        
        for ( int i = 0; i <= maxN; i++ ) {
            keys[i] = null;
            pq[i] = 0;
            qp[i] = -1;
        }
        
        n = 0;
        
    }
    
    @Override
    public boolean isEmpty() {
        return n == 0;
    }
    
    @Override
    public int getSize() {
        return n;
    }
    
    public Iterator<Integer> iterator() {
        return new HeapIterator();
    }

    private class HeapIterator implements Iterator<Integer> {

        private IndexedMaxPriorityQueue<Key> copy;

        public HeapIterator() {
            copy = new IndexedMaxPriorityQueue<Key>( pq.length - 1 );
            for ( int i = 1; i <= n; i++ ) {
                copy.insert( pq[i], keys[pq[i]] );
            }
        }

        public boolean hasNext() {
            return !copy.isEmpty();
        }
        
        public Integer next() {
            if ( !hasNext() ) {
                throw new NoSuchElementException();
            }
            return copy.delete();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
        
    }

}
