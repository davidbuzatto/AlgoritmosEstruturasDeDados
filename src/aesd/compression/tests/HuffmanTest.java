package aesd.compression.tests;

import aesd.compression.Huffman;

/**
 * Teste de compressão usando o algoritmo de Huffman.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class HuffmanTest {
    
    public static void main( String[] args ) {
        if ( args[0].equals( "-" ) ) {
            Huffman.compress();
        } else if ( args[0].equals( "+" ) ) {
            Huffman.expand();
        } else {
            throw new IllegalArgumentException( "Illegal command line argument" );
        }
    }

}
