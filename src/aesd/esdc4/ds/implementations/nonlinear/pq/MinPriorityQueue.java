/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdc4.ds.implementations.nonlinear.pq;

import aesd.esdc4.ds.interfaces.PriorityQueue;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Implementação de uma fila de prioridades mínima usando um heap binário mínimo.
 * 
 * Critério de ordenação:
 *     - MinHeap:
 *         * Chave do elemento pai sempre menor ou igual aos filhos
 *     – Chaves armazenadas em um array.
 *
 * Construção:
 *     – Heap binário (até dois filhos)
 *     – Completa: elementos sem filhos apenas no último nível
 *      (e anterior, quando o último nível não está completo)
 *
 * Árvore binária completa:
 *     – Armazenamento direto em array!
 *     - Raiz na posição 1
 *     - Último elemento na posição tamanho do array - 1
 *     – Manipulação dos índices:
 *          * Pai: posição do filho / 2
 *          * Filho esquerda: posição do pai * 2
 *          * Filho direita: posicao do pai * 2 + 1
 *
 *     - Para raiz na posição 0, a geração das posições seria
 *          * Pai: (posição do filho - 1) / 2
 *          * Filho esquerda: posição do pai * 2 + 1
 *          * Filho direita: posicao do pai * 2 + 2
 *
 * Elemento violando a condição heap mínimo
 *     – Chave menor que a chave do pai
 *        * O elemento precisa "subir" na árvore
 *        * Bottom-Up heapify (swim => flutuar)
 *     – Chave maior que a chave dos filhos (um ou dois)
 *        * O elemento precisa "descer" na árvore
 *        * Top-Down heapify (sink => afundar)
 *
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 4. ed.
 * Boston: Pearson Education, 2011. 955 p.
 *
 * @param <Key> Tipo das chaves que serão armazenadas na fila de prioridades.
 *
 * @author Prof. Dr. David Buzatto
 */
public class MinPriorityQueue<Key extends Comparable<Key>> implements PriorityQueue<Key> {

    // heap binário das chaves da fila de prioridades, nas posições 1 até n (size)
    private Key[] pq;
    
    // quantidade de itens na fila de prioridades
    private int n;
    
    // um comparador opcional
    private Comparator<Key> comparator;

    /**
     * Cria uma fila de prioridades mínima vazia.
     */
    public MinPriorityQueue() {
        this( 1 );
    }
    
    /**
     * Cria uma fila de prioridades mínima vazia com uma capacidade inicial.
     *
     * @param initCapacity capacidade inicial para a fila de prioridades
     */
    @SuppressWarnings( "unchecked" )
    public MinPriorityQueue( int initCapacity ) {
        pq = (Key[]) new Comparable[initCapacity + 1];
    }

    /**
     * Cria uma fila de prioridades mínima vazia com uma capacidade inicial e um
     * comparador.
     *
     * @param initCapacity capacidade inicial para a fila de prioridades
     * @param comparator a ordem em que as chaves serão comparadas
     */
    public MinPriorityQueue( int initCapacity, Comparator<Key> comparator ) {
        this( initCapacity );
        this.comparator = comparator;
    }

    /**
     * Cria uma fila de prioridades mínima vazia com um comparador.
     *
     * @param comparator a ordem em que as chaves serão comparadas
     */
    public MinPriorityQueue( Comparator<Key> comparator ) {
        this( 1, comparator );
    }

    /**
     * Cria uma fila de prioridades mínima com um array de chaves.
     * Constrói a fila de prioridades construindo subárvores, do meio até o início.
     *
     * @param keys array de chaves
     */
    @SuppressWarnings( "unchecked" )
    public MinPriorityQueue( Key[] keys ) {
        
        n = keys.length;
        
        pq = (Key[]) new Object[keys.length + 1];
        
        for ( int i = 0; i < n; i++ ) {
            pq[i+1] = keys[i];
        }
        
        for ( int k = n / 2; k >= 1; k-- ) {
            sink( k );
        }
        
    }

    /**
     * Redimensiona o array de chaves.
     * 
     * @param max Tamanho a ser redimensionado.
     */
    @SuppressWarnings( "unchecked" )
    private void resize( int capacity ) {
        
        Key[] temp = (Key[]) new Comparable[capacity];
        
        for ( int i = 1; i <= n; i++ ) {
            temp[i] = pq[i];
        }
        
        pq = temp;
        
    }
    
    @Override
    public void insert( Key key ) {
        
        // dobra o tamanho se chegou no limite da capacidade
        if ( n == pq.length - 1 ) {
            resize( 2 * pq.length );
        }

        // insere a chave e a flutua, mantendo a invariante do heap mínimo
        pq[++n] = key;
        swim( n );
        
    }

    /**
     * Retorna a menor chave nessa fila de prioridades, sem remover.
     *
     * @return a menor chave da fila de prioridades
     * @throws NoSuchElementException se essa fila de prioridades estiver vazia
     */
    @Override
    public Key peek() throws NoSuchElementException {
        
        if ( isEmpty() ) {
            throw new NoSuchElementException( "Priority queue underflow" );
        }
        
        return pq[1];
        
    }
    
    /**
     * Remove e retorna a menor chave nessa fila de prioridades
     *
     * @return a menor chave nessa fila de prioridades
     * @throws NoSuchElementException se essa fila de prioridades estiver vazia
     */
    @Override
    public Key delete() throws NoSuchElementException {
        
        if ( isEmpty() ) {
            throw new NoSuchElementException( "Priority queue underflow" );
        }
        
        Key min = pq[1];
        exchange( 1, n-- );
        sink( 1 );
        
        pq[n + 1] = null;     // marca como null para coleta de lixo
        
        // se o tamanho é igual à um quarto da capacidade
        if ( ( n > 0 ) && ( n == ( pq.length - 1 ) / 4 ) ) {
            // diminui a capacidade pela metade
            resize( pq.length / 2 );
        }
        
        return min;
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

    private boolean greater( int i, int j ) {
        if ( comparator == null ) {
            return pq[i].compareTo( pq[j] ) > 0;
        } else {
            return comparator.compare( pq[i], pq[j] ) > 0;
        }
    }

    private void exchange( int i, int j ) {
        Key temp = pq[i];
        pq[i] = pq[j];
        pq[j] = temp;
    }

    @Override
    public void clear() {
        for ( int i = 0; i < pq.length; i++ ) {
            pq[i] = null;
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
    
    @Override
    public Iterator<Key> iterator() {
        return new HeapIterator();
    }

    private class HeapIterator implements Iterator<Key> {

        private MinPriorityQueue<Key> copy;

        public HeapIterator() {
            if ( comparator == null ) {
                copy = new MinPriorityQueue<>( getSize() );
            } else {
                copy = new MinPriorityQueue<>( getSize(), comparator );
            }
            for ( int i = 1; i <= n; i++ ) {
                copy.insert( pq[i] );
            }
        }

        public boolean hasNext() {
            return !copy.isEmpty();
        }
        
        public Key next() {
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
            sb.append( "empty min priority queue!\n" );
        }
        
        return sb.toString();
        
    }
    
    private void preOrder( int i, String ident, StringBuilder sb ) {
        
        if ( i <= n && pq[i] != null ) {
            
            String rootIdent = "";
            String leafIdent = "";
            
            if ( i != 1 ) {
                rootIdent = ident + "|--";
                leafIdent = ident + "|  ";
            }
            
            sb.append( rootIdent );
            sb.append( pq[i] );
            if ( i == 1 ) {
                sb.append(  " <- min (root)" );
            }
            sb.append( "\n" );
            
            preOrder( i * 2, leafIdent, sb );
            preOrder( i * 2 + 1, leafIdent, sb );
            
        }
        
    }

}
