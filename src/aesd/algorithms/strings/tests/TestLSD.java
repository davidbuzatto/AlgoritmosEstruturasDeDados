package aesd.algorithms.strings.tests;

import aesd.algorithms.strings.LSD;

/**
 * Testes do algoritmo LSD.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class TestLSD {
    
    public static void main( String[] args ) {
        
        // as strings precisam ter o mesmo comprimento
        String[] strings = {
            "aba", 
            "bac", 
            "caa", 
            "acb", 
            "bab", 
            "cca", 
            "aac", 
            "bba"
        };

        // ordena as strings
        LSD.sort( strings, strings[0].length() );

        // mostra o resultado
        for ( String s : strings ) {
            System.out.println( s );
        }
        
    }
    
}
