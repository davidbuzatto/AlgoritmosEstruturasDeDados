package aesd.algorithms.substrings.tests;

import aesd.algorithms.substrings.RabinKarp;

/**
 * Teste do algoritmo Rabin-Karp.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class RabinKarpTest {
    
    public static void main( String[] args ) {
        
        String pat = "AABAAA";
        String txt = "CAABAABAAAA";

        RabinKarp searcher = new RabinKarp( pat );
        int offset = searcher.search( txt );

        // imprime o resultado
        System.out.println( "text:    " + txt );

        // resultado do primeiro método de busca do Rabin-Karp
        System.out.print( "pattern: " );
        for ( int i = 0; i < offset; i++ ) {
            System.out.print( " " );
        }
        System.out.println( pat );
        
    }

}
