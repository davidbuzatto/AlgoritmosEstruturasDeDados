/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdc4.ds.implementations.nonlinear.pq;

import aesd.esdc4.ds.interfaces.IndexedPriorityQueue;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Implementação de uma fila de prioridades mínima indexada usando um heap
 * binário mínimo.
 * 
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 4. ed.
 * Boston: Pearson Education, 2011. 955 p.
 *
 * @param <Key> Tipo das chaves que serão armazenadas na fila de prioridades
 * indexada.
 *
 * @author Prof. Dr. David Buzatto
 */
public class IndexedMinPriorityQueue<Key extends Comparable<Key>> implements IndexedPriorityQueue<Key> {

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
     * Cria uma fila de prioridade mínima indexada com índices entre 0 e maxN - 1.
     *
     * @param maxN a quantidade de chaves permitida
     * @throws IllegalArgumentException se a quantidade for menor que 0
     */
    @SuppressWarnings( "unchecked" )
    public IndexedMinPriorityQueue( int maxN ) {
        
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
    public void insert( int i, Key key ) {
        
        validateIndex( i );
        
        if ( contains( i ) ) {
            throw new IllegalArgumentException( "index is already in the priority queue" );
        }
        
        n++;
        qp[i] = n;
        pq[n] = i;
        keys[i] = key;
        
        swim( n );
        
    }

    @Override
    public int peekIndex() {
        
        if ( n == 0 ) {
            throw new NoSuchElementException( "Priority queue underflow" );
        }
        
        return pq[1];
        
    }
    
    @Override
    public Key peekKey() {
        
        if ( n == 0 ) {
            throw new NoSuchElementException( "Priority queue underflow" );
        }
        
        return keys[pq[1]];
        
    }
    
    @Override
    public int delete() {
        
        if ( n == 0 ) {
            throw new NoSuchElementException( "Priority queue underflow" );
        }
        
        int min = pq[1];
        exchange( 1, n-- );
        sink( 1 );
        
        qp[min] = -1;          // exclui
        keys[min] = null;      // marca como null para coleta de lixo
        pq[n + 1] = -1;        // desnecessário
        
        return min;
        
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
    public Key keyOf( int i ) {
        
        validateIndex( i );
        
        if ( !contains( i ) ) {
            throw new NoSuchElementException( "index is not in the priority queue" );
        } else {
            return keys[i];
        }
        
    }

    @Override
    public void changeKey( int i, Key key ) {
        
        validateIndex( i );
        
        if ( !contains( i ) ) {
            throw new NoSuchElementException( "index is not in the priority queue" );
        }
        
        keys[i] = key;
        
        swim( qp[i] );
        sink( qp[i] );
        
    }

    @Override
    public void decreaseKey( int i, Key key ) {
        
        validateIndex( i );
        
        if ( !contains( i ) ) {
            throw new NoSuchElementException( "index is not in the priority queue" );
        }
        
        if ( keys[i].compareTo( key ) == 0 ) {
            throw new IllegalArgumentException( "Calling decreaseKey() with a key equal to the key in the priority queue" );
        }
        
        if ( keys[i].compareTo( key ) < 0 ) {
            throw new IllegalArgumentException( "Calling decreaseKey() with a key strictly greater than the key in the priority queue" );
        }
        
        keys[i] = key;
        swim( qp[i] );
        
    }

    @Override
    public void increaseKey( int i, Key key ) {
        
        validateIndex( i );
        
        if ( !contains( i ) ) {
            throw new NoSuchElementException( "index is not in the priority queue" );
        }
        
        if ( keys[i].compareTo( key ) == 0 ) {
            throw new IllegalArgumentException( "Calling increaseKey() with a key equal to the key in the priority queue" );
        }
        
        if ( keys[i].compareTo( key ) > 0 ) {
            throw new IllegalArgumentException( "Calling increaseKey() with a key strictly less than the key in the priority queue" );
        }
        
        keys[i] = key;
        sink( qp[i] );
        
    }

    private void validateIndex( int index ) {
        
        if ( index < 0 ) {
            throw new IllegalArgumentException( "index is negative: " + index );
        }
        
        if ( index >= maxN ) {
            throw new IllegalArgumentException( "index >= capacity: " + index );
        }
        
    }
    
    private boolean greater( int i, int j ) {
        return keys[pq[i]].compareTo( keys[pq[j]] ) > 0;
    }

    private void exchange( int i, int j ) {
        
        int temp = pq[i];
        
        pq[i] = pq[j];
        pq[j] = temp;
        
        qp[pq[i]] = i;
        qp[pq[j]] = j;
        
    }

    private void swim( int k ) {
        
        while ( k > 1 && greater( k / 2, k ) ) {
            exchange( k, k / 2 );
            k = k / 2;
        }
        
    }

    private void sink( int k ) {
        
        while ( 2 * k <= n ) {
            
            int j = 2 * k;
            
            if ( j < n && greater( j, j + 1 ) ) {
                j++;
            }
            
            if ( !greater( k, j ) ) {
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

        private IndexedMinPriorityQueue<Key> copy;
        
        public HeapIterator() {
            copy = new IndexedMinPriorityQueue<Key>( pq.length - 1 );
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
    
    @Override
    public String toString() {
        
        StringBuilder sb = new StringBuilder();
        
        if ( !isEmpty()) {
            
            preOrder( 1, "", sb );
            
        } else {
            sb.append( "empty indexed min priority queue!\n" );
        }
        
        return sb.toString();
        
    }
    
    private void preOrder( int i, String ident, StringBuilder sb ) {
        
        if ( i <= n ) {
            
            String rootIdent = "";
            String leafIdent = "";
            
            if ( i != 1 ) {
                rootIdent = ident + "|--";
                leafIdent = ident + "|  ";
            }
            
            sb.append( rootIdent );
            sb.append( "(" ).append( pq[i] ).append( ") " ).append( keys[pq[i]] );
            
            if ( i == 1 ) {
                sb.append(  " <- min (root)" );
            }
            sb.append( "\n" );
            
            preOrder( i * 2, leafIdent, sb );
            preOrder( i * 2 + 1, leafIdent, sb );
            
        }
        
    }

}
