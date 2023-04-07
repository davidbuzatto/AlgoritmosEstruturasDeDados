package aesd.compression.tests;

import aesd.compression.LZW;

/**
 * Teste de compressão usando o algoritmo LZW.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class LZWTest {
    
    public static void main( String[] args ) {
        if ( args[0].equals( "-" ) ) {
            LZW.compress();
        } else if ( args[0].equals( "+" ) ) {
            LZW.expand();
        } else {
            throw new IllegalArgumentException( "Illegal command line argument" );
        }
    }

}
