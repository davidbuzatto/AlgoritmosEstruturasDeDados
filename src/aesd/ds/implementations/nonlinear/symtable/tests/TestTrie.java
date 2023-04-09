package aesd.ds.implementations.nonlinear.symtable.tests;

import aesd.ds.implementations.nonlinear.symtable.Trie;
import aesd.ds.interfaces.SymbolTable;

/**
 * Teste de uso de uma Trie.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class TestTrie {
    
    public static void main( String[] args ) {

        // constrói a trie
        Trie<Integer> st = new Trie<>();
        st.put( "she", 0 );
        st.put( "sells", 1 );
        st.put( "sea", 2 );
        st.put( "shells", 3 );
        st.put( "by", 4 );
        st.put( "the", 5 );
        st.put( "sea", 6 );
        st.put( "shore", 7 );
        
        // imprime
        if ( st.getSize() < 100 ) {
            System.out.println( "keys(\"\"):" );
            for ( String key : st.getKeys() ) {
                System.out.println( key + " " + st.get( key ) );
            }
            System.out.println();
        }
        
        // imprime usando iterador
        for ( SymbolTable.Entry<String, Integer> e : st ) {
            System.out.println( e.getKey() + " " + e.getValue() );
        }
        System.out.println();
        
        System.out.println( "longestPrefixOf(\"shellsort\"):" );
        System.out.println( st.getLongestPrefixOf( "shellsort" ) );
        System.out.println();

        System.out.println( "longestPrefixOf(\"quicksort\"):" );
        System.out.println( st.getLongestPrefixOf( "quicksort" ) );
        System.out.println();

        System.out.println( "keysWithPrefix(\"shor\"):" );
        for ( String s : st.getKeysWithPrefix( "shor" ) ) {
            System.out.println( s );
        }
        System.out.println();

        System.out.println( "keysThatMatch(\".he.l.\"):" );
        for ( String s : st.getKeysThatMatch( ".he.l." ) ) {
            System.out.println( s );
        }
        
    }
    
}
