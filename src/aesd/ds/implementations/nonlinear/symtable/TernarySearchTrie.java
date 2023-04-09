package aesd.ds.implementations.nonlinear.symtable;

import aesd.ds.implementations.linear.ResizingArrayList;
import aesd.ds.implementations.linear.ResizingArrayQueue;
import aesd.ds.interfaces.List;
import aesd.ds.interfaces.Queue;
import aesd.ds.interfaces.SymbolTable;
import java.util.Iterator;

/**
 * Implementação de uma Trie de Busca ternária.
 * 
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 
 * 4. ed. Boston: Pearson Education, 2011. 955 p.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class TernarySearchTrie<Value> implements SymbolTable<String, Value> {

    // tamanho
    private int n;
    
    // raiz da TernarySearchTrie
    private Node<Value> root;

    // nó da TernarySearchTrie
    private static class Node<Value> {
        
        // caractere
        private char c;
        
        // substries da esquerda, do meio e da direita
        private Node<Value> left;
        private Node<Value> mid;
        private Node<Value> right;
        
        // valor associado à String
        private Value val;
        
    }
    
    public TernarySearchTrie() {
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
    public boolean contains( String key ) {
        
        if ( key == null ) {
            throw new IllegalArgumentException( "argument to contains() is null" );
        }
        
        return get( key ) != null;
        
    }
    
    @Override
    public Value get( String key ) {
        
        if ( key == null ) {
            throw new IllegalArgumentException( "calls get() with null argument" );
        }
        
        if ( key.length() == 0 ) {
            throw new IllegalArgumentException( "key must have length >= 1" );
        }
        
        Node<Value> x = get( root, key, 0 );
        
        if ( x == null ) {
            return null;
        }
        
        return x.val;
        
    }
    
    private Node<Value> get( Node<Value> x, String key, int d ) {
        
        if ( x == null ) {
            return null;
        }
        
        if ( key.length() == 0 ) {
            throw new IllegalArgumentException( "key must have length >= 1" );
        }
        
        char c = key.charAt( d );
        
        if ( c < x.c ) {
            return get( x.left, key, d );
        } else if ( c > x.c ) {
            return get( x.right, key, d );
        } else if ( d < key.length() - 1 ) {
            return get( x.mid, key, d + 1 );
        } else {
            return x;
        }
        
    }
    
    @Override
    public void put( String key, Value val ) {
        
        if ( key == null ) {
            throw new IllegalArgumentException( "calls put() with null key" );
        }
        
        if ( !contains( key ) ) {
            n++;
        } else if ( val == null ) {
            n--;       // delete existing key
        }
        
        root = put( root, key, val, 0 );
        
    }

    private Node<Value> put( Node<Value> x, String key, Value val, int d ) {
        
        char c = key.charAt( d );
        
        if ( x == null ) {
            x = new Node<>();
            x.c = c;
        }
        
        if ( c < x.c ) {
            x.left = put( x.left, key, val, d );
        } else if ( c > x.c ) {
            x.right = put( x.right, key, val, d );
        } else if ( d < key.length() - 1 ) {
            x.mid = put( x.mid, key, val, d + 1 );
        } else {
            x.val = val;
        }
        
        return x;
        
    }
    
    public String getLongestPrefixOf( String query ) {
        
        if ( query == null ) {
            throw new IllegalArgumentException( "calls longestPrefixOf() with null argument" );
        }
        
        if ( query.length() == 0 ) {
            return null;
        }
        
        int length = 0;
        Node<Value> x = root;
        int i = 0;
        
        while ( x != null && i < query.length() ) {
            
            char c = query.charAt( i );
            
            if ( c < x.c ) {
                x = x.left;
            } else if ( c > x.c ) {
                x = x.right;
            } else {
                i++;
                if ( x.val != null ) {
                    length = i;
                }
                x = x.mid;
            }
            
        }
        
        return query.substring( 0, length );
        
    }
    
    @Override
    public Iterable<String> getKeys() {
        Queue<String> queue = new ResizingArrayQueue<>();
        collect( root, new StringBuilder(), queue );
        return queue;
    }
    
    public Iterable<String> getKeysWithPrefix( String prefix ) {
        
        if ( prefix == null ) {
            throw new IllegalArgumentException( "calls keysWithPrefix() with null argument" );
        }
        
        Queue<String> queue = new ResizingArrayQueue<>();
        Node<Value> x = get( root, prefix, 0 );
        
        if ( x == null ) {
            return queue;
        }
        
        if ( x.val != null ) {
            queue.enqueue( prefix );
        }
        
        collect( x.mid, new StringBuilder( prefix ), queue );
        return queue;
        
    }
    
    private void collect( Node<Value> x, StringBuilder prefix, Queue<String> queue ) {
        
        if ( x == null ) {
            return;
        }
        
        collect( x.left, prefix, queue );
        
        if ( x.val != null ) {
            queue.enqueue( prefix.toString() + x.c );
        }
        
        collect( x.mid, prefix.append( x.c ), queue );
        prefix.deleteCharAt( prefix.length() - 1 );
        collect( x.right, prefix, queue );
        
    }
    
    public Iterable<String> getKeysThatMatch( String pattern ) {
        Queue<String> queue = new ResizingArrayQueue<>();
        collect( root, new StringBuilder(), 0, pattern, queue );
        return queue;
    }

    private void collect( Node<Value> x, StringBuilder prefix, 
                          int i, String pattern, Queue<String> queue ) {
        
        if ( x == null ) {
            return;
        }
        
        char c = pattern.charAt( i );
        
        if ( c == '.' || c < x.c ) {
            collect( x.left, prefix, i, pattern, queue );
        }
        
        if ( c == '.' || c == x.c ) {
            
            if ( i == pattern.length() - 1 && x.val != null ) {
                queue.enqueue( prefix.toString() + x.c );
            }
            
            if ( i < pattern.length() - 1 ) {
                collect( x.mid, prefix.append( x.c ), i + 1, pattern, queue );
                prefix.deleteCharAt( prefix.length() - 1 );
            }
            
        }
        
        if ( c == '.' || c > x.c ) {
            collect( x.right, prefix, i, pattern, queue );
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
    
    @Override
    public void delete( String key ) throws IllegalArgumentException {
        throw new UnsupportedOperationException( "Not supported yet." ); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException( "Not supported yet." ); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
