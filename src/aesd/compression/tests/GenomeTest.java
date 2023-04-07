package aesd.compression.tests;

import aesd.compression.Genome;

/**
 * Teste da compressão de genomas.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class GenomeTest {
    
    public static void main( String[] args ) {
        
        if ( args[0].equals( "-" ) ) {
            Genome.compress();
        } else if ( args[0].equals( "+" ) ) {
            Genome.expand();
        } else {
            throw new IllegalArgumentException( "Illegal command line argument" );
        }
        
    }
    
}
