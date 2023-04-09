package aesd.ds.implementations.nonlinear.symtable;

import aesd.ds.implementations.linear.ResizingArrayList;
import aesd.ds.implementations.linear.ResizingArrayQueue;
import aesd.ds.interfaces.List;
import aesd.ds.interfaces.Queue;
import aesd.ds.interfaces.SymbolTable;
import java.util.Iterator;

/**
 * Implementação de uma trie que funciona como uma tabela de símbolos para
 * Strigns.
 *
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 4. ed.
 * Boston: Pearson Education, 2011. 955 p.
 *
 * @param <Value> Tipo dos valores armazenados na trie.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class Trie<Value> implements SymbolTable<String, Value> {

    // tabela ASCII estendida
    private static final int R = 256;

    // raiz
    private Node<Value> root;
    
    // número de chaves da trie
    private int n;

    // nó R-vias (R-way)
    @SuppressWarnings( "unchecked" )
    private static class Node<Value> {
        private Value val;
        private Node<Value>[] next = new Node[R];
    }

    @Override
    public Value get( String key ) {
        
        if ( key == null ) {
            throw new IllegalArgumentException( "argument to get() is null" );
        }
        
        Node<Value> x = get( root, key, 0 );
        
        if ( x == null ) {
            return null;
        }
        
        return x.val;
        
    }

    @Override
    public boolean contains( String key ) {
        
        if ( key == null ) {
            throw new IllegalArgumentException( "argument to contains() is null" );
        }
        
        return get( key ) != null;
        
    }

    private Node<Value> get( Node<Value> x, String key, int d ) {
        
        if ( x == null ) {
            return null;
        }
        
        if ( d == key.length() ) {
            return x;
        }
        
        char c = key.charAt( d );
        return get( x.next[c], key, d + 1 );
        
    }

    @Override
    public void put( String key, Value val ) {
        
        if ( key == null ) {
            throw new IllegalArgumentException( "first argument to put() is null" );
        }
        
        if ( val == null ) {
            delete( key );
        } else {
            root = put( root, key, val, 0 );
        }
        
    }

    private Node<Value> put( Node<Value> x, String key, Value val, int d ) {
        
        if ( x == null ) {
            x = new Node<>();
        }
        
        if ( d == key.length() ) {
            if ( x.val == null ) {
                n++;
            }
            x.val = val;
            return x;
        }
        
        char c = key.charAt( d );
        x.next[c] = put( x.next[c], key, val, d + 1 );
        
        return x;
        
    }

    @Override
    public int getSize() {
        return n;
    }

    @Override
    public boolean isEmpty() {
        return getSize() == 0;
    }

    @Override
    public Iterable<String> getKeys() {
        return getKeysWithPrefix( "" );
    }

    @Override
    public void clear() {
        
        for ( String k : getKeys() ) {
            delete( k );
        }
        
    }

    @Override
    public Iterator<Entry<String, Value>> iterator() {
        
        return new Iterator<Entry<String, Value>>() {
            
            private int current = 0;
            private boolean prepared = false;
            private List<String> keys = new ResizingArrayList<>();
            
            @Override
            public boolean hasNext() {
                prepare();
                return current < keys.getSize();
            }

            @Override
            public Entry<String, Value> next() {
                prepare();
                String key = keys.get( current );
                Value val = get( key );
                current++;
                return new Entry<>( key, val );
            }
            
            private void prepare() {
                if ( !prepared ) {
                    for ( String k : getKeys() ) {
                        keys.add( k );
                    }
                    prepared = true;
                }
            }
            @Override
            public void remove() {
                throw new UnsupportedOperationException( "Not supported." );
            }
            
        };
        
    }
    
    public Iterable<String> getKeysWithPrefix( String prefix ) {
        Queue<String> results = new ResizingArrayQueue<>();
        Node<Value> x = get( root, prefix, 0 );
        collect( x, new StringBuilder( prefix ), results );
        return results;
    }

    private void collect( Node<Value> x, StringBuilder prefix, Queue<String> results ) {
        
        if ( x == null ) {
            return;
        }
        
        if ( x.val != null ) {
            results.enqueue( prefix.toString() );
        }
        
        for ( char c = 0; c < R; c++ ) {
            prefix.append( c );
            collect( x.next[c], prefix, results );
            prefix.deleteCharAt( prefix.length() - 1 );
        }
        
    }

    public Iterable<String> getKeysThatMatch( String pattern ) {
        Queue<String> results = new ResizingArrayQueue<>();
        collect( root, new StringBuilder(), pattern, results );
        return results;
    }

    private void collect( Node<Value> x, StringBuilder prefix, String pattern, Queue<String> results ) {
        
        if ( x == null ) {
            return;
        }
        
        int d = prefix.length();
        
        if ( d == pattern.length() && x.val != null ) {
            results.enqueue( prefix.toString() );
        }
        
        if ( d == pattern.length() ) {
            return;
        }
        
        char c = pattern.charAt( d );
        
        if ( c == '.' ) {
            for ( char ch = 0; ch < R; ch++ ) {
                prefix.append( ch );
                collect( x.next[ch], prefix, pattern, results );
                prefix.deleteCharAt( prefix.length() - 1 );
            }
        } else {
            prefix.append( c );
            collect( x.next[c], prefix, pattern, results );
            prefix.deleteCharAt( prefix.length() - 1 );
        }
        
    }

    public String getLongestPrefixOf( String query ) {
        
        if ( query == null ) {
            throw new IllegalArgumentException( "argument to longestPrefixOf() is null" );
        }
        
        int length = getLongestPrefixOf( root, query, 0, -1 );
        
        if ( length == -1 ) {
            return null;
        } else {
            return query.substring( 0, length );
        }
        
    }

    private int getLongestPrefixOf( Node<Value> x, String query, int d, int length ) {
        
        if ( x == null ) {
            return length;
        }
        
        if ( x.val != null ) {
            length = d;
        }
        
        if ( d == query.length() ) {
            return length;
        }
        
        char c = query.charAt( d );
        return getLongestPrefixOf( x.next[c], query, d + 1, length );
        
    }
    
    @Override
    public void delete( String key ) {
        
        if ( key == null ) {
            throw new IllegalArgumentException( "argument to delete() is null" );
        }
        
        root = delete( root, key, 0 );
        
    }

    private Node<Value> delete( Node<Value> x, String key, int d ) {
        
        if ( x == null ) {
            return null;
        }
        
        if ( d == key.length() ) {
            if ( x.val != null ) {
                n--;
            }
            x.val = null;
        } else {
            char c = key.charAt( d );
            x.next[c] = delete( x.next[c], key, d + 1 );
        }

        // remove a subtrie enraizada em x se ela estiver completamente vazia
        if ( x.val != null ) {
            return x;
        }
        
        for ( int c = 0; c < R; c++ ) {
            if ( x.next[c] != null ) {
                return x;
            }
        }
        
        return null;
        
    }
    
}
