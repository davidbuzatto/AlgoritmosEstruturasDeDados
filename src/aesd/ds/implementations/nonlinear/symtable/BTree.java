/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.ds.implementations.nonlinear.symtable;

import java.util.Iterator;
import aesd.ds.interfaces.SymbolTable;

/**
 * Implementação de uma árvore b (B-Tree)).
 *
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 
 * 4. ed. Boston: Pearson Education, 2011. 955 p.
 * 
 * @param <Key> Tipo das chaves que serão armazenadas na árvore.
 * @param <Value> Tipo dos valores associados às chaves armazenadas na árvore.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class BTree<Key extends Comparable<Key>, Value> implements SymbolTable<Key, Value> {

    // máximo de filhos por nó = M-1
    // precisa ser par e maior que 2
    private static final int M = 4;

    /*
     * Classe interna estática que define os nós da árvore b.
     */
    @SuppressWarnings( "unchecked" )
    private static final class BNode<Key extends Comparable<Key>, Value> {
        
        // quantidade de filhos
        private int m;
        
        // array de filhos
        private Entry<Key, Value>[] children = (Entry<Key, Value>[]) new Object[M];

        // cria um nó com m filhos
        private BNode( int m ) {
            this.m = m;
        }
        
    }

    /*
     * Classe interna estática que define as entradas dos nós da árvore b.
     * Nós internos usam apenas key e next (chave e próximo)
     * Nós externos usam apenas key e value (chave e valor)
     */
    private static final class Entry<Key extends Comparable<Key>, Value> {
        
        private Key key;
        private Value value;
        private BNode next;
        
        public Entry( Key key, Value value, BNode<Key, Value> next ) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
        
    }
    
    // raiz da árvore
    private BNode<Key, Value> root;
    
    // altura da árvore
    private int height;
    
    // quantidade de pares chave-valor contidos na árvore
    private int n;

    /**
     * Constrói uma Árvore b vazia.
     */
    public BTree() {
        root = new BNode<>( 0 );
    }

    @Override
    public boolean isEmpty() {
        return getSize() == 0;
    }

    @Override
    public int getSize() {
        return n;
    }
    
    public int getHeight() {
        return height;
    }

    @Override
    public Value get(Key key) {
        
        if ( key == null ) {
            throw new IllegalArgumentException( "argument to get() is null" );
        }
        
        return search( root, key, height );
        
    }

    @SuppressWarnings( "unchecked" )
    private Value search( BNode<Key, Value> x, Key key, int ht ) {
        
        Entry<Key, Value>[] children = x.children;

        // nó externo
        if ( ht == 0 ) {
            for ( int j = 0; j < x.m; j++ ) {
                if ( eq( key, children[j].key ) ) {
                    return children[j].value;
                }
            }
        } else { // nó interno
            for ( int j = 0; j < x.m; j++ ) {
                if ( j+1 == x.m || less( key, (Key) children[j+1].key ) ) {
                    return (Value) search( children[j].next, key, ht-1 );
                }
            }
        }
        
        return null;
        
    }

    @Override
    public void put( Key key, Value val ) {
        
        if ( key == null ) {
            throw new IllegalArgumentException( "argument key to put() is null" );
        }
        
        BNode<Key, Value> u = insert( root, key, val, height );
        n++;
        
        if ( u == null ) {
            return;
        }

        // precisa dividir a raiz
        BNode<Key, Value> t = new BNode<>( 2 );
        t.children[0] = new Entry<>( root.children[0].key, null, root );
        t.children[1] = new Entry<>( u.children[0].key, null, u );
        
        root = t;
        height++;
        
    }

    @SuppressWarnings( "unchecked" )
    private BNode<Key, Value> insert( BNode<Key, Value> h, Key key, Value val, int ht ) {
        
        int j;
        Entry<Key, Value> t = new Entry<>( key, val, null );

        // nó externo
        if ( ht == 0 ) {
            for ( j = 0; j < h.m; j++ ) {
                if ( less( key, h.children[j].key ) ) {
                    break;
                }
            }
        } else { // nó interno
            for ( j = 0; j < h.m; j++ ) {
                if ( ( j+1 == h.m ) || less( key, h.children[j+1].key ) ) {
                    
                    BNode<Key, Value> u = insert( h.children[j++].next, key, val, ht-1 );
                    
                    if ( u == null ) {
                        return null;
                    }
                    
                    t.key = u.children[0].key;
                    t.value = null;
                    t.next = u;
                    
                    break;
                    
                }
            }
        }

        for ( int i = h.m; i > j; i-- ) {
            h.children[i] = h.children[i-1];
        }
        
        h.children[j] = t;
        h.m++;
        
        if ( h.m < M ) {
            return null;
        } else {
            return split(h);
        }
        
    }

    // divide o nó na metade
    private BNode<Key, Value> split( BNode<Key, Value> h ) {
        
        BNode<Key, Value> t = new BNode<>( M / 2 );
        h.m = M / 2;
        
        for ( int j = 0; j < M/2; j++ ) {
            t.children[j] = h.children[M/2+j];
        }
        
        return t;
        
    }

    @Override
    public String toString() {
        return toString( root, height, "" ) + "\n";
    }

    @SuppressWarnings( "unchecked" )
    private String toString( BNode<Key, Value> h, int ht, String indent ) {
        
        StringBuilder s = new StringBuilder();
        Entry<Key, Value>[] children = h.children;

        if ( ht == 0 ) {
            for (int j = 0; j < h.m; j++) {
                s.append( indent )
                        .append( children[j].key )
                        .append( " " )
                        .append( children[j].value )
                        .append("\n");
            }
        } else {
            for (int j = 0; j < h.m; j++) {
                if ( j > 0 ) {
                    s.append( indent )
                            .append( "(" )
                            .append( children[j].key )
                            .append( ")\n" );
                }
                s.append( toString( children[j].next, ht-1, indent + "     " ) );
            }
        }
        
        return s.toString();
        
    }


    private boolean less( Key k1, Key k2 ) {
        return k1.compareTo(k2) < 0;
    }

    private boolean eq( Key k1, Key k2 ) {
        return k1.compareTo(k2) == 0;
    }

    @Override
    public void delete( Key key ) throws IllegalArgumentException {
        throw new UnsupportedOperationException( "Not supported yet." );
    }

    @Override
    public boolean contains( Key key ) throws IllegalArgumentException {
        throw new UnsupportedOperationException( "Not supported yet." );
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException( "Not supported yet." );
    }

    @Override
    public Iterable<Key> getKeys() {
        throw new UnsupportedOperationException( "Not supported yet." );
    }
    
    @Override
    public Iterator<SymbolTable.Entry<Key, Value>> iterator() {
        throw new UnsupportedOperationException( "Not supported yet." );
    }
    
}
