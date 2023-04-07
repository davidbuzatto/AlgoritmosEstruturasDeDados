package aesd.compression.tests;

import aesd.compression.RunLength;

/**
 * Teste de codificação de comprimento de carreira (Run Length Encoding).
 *
 * @author Prof. Dr. David Buzatto
 */
public class RunLengthTest {

    public static void main( String[] args ) {
        if ( args[0].equals( "-" ) ) {
            RunLength.compress();
        } else if ( args[0].equals( "+" ) ) {
            RunLength.expand();
        } else {
            throw new IllegalArgumentException( "Illegal command line argument" );
        }
    }

}
