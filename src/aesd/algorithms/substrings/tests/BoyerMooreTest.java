package aesd.algorithms.substrings.tests;

import aesd.algorithms.substrings.BoyerMoore;

/**
 * Teste do algoritmo Boyer-Moore.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class BoyerMooreTest {
    
    public static void main( String[] args ) {
        
        String pat = "AABAAA";
        String txt = "CAABAABAAAA";
        char[] pattern = pat.toCharArray();
        char[] text = txt.toCharArray();

        BoyerMoore boyermoore1 = new BoyerMoore( pat );
        BoyerMoore boyermoore2 = new BoyerMoore( pattern, 256 );
        int offset1 = boyermoore1.search( txt );
        int offset2 = boyermoore2.search( text );

        // imprime o resultado
        System.out.println( "text:    " + txt );

        System.out.print( "pattern: " );
        for ( int i = 0; i < offset1; i++ ) {
            System.out.print( " " );
        }
        System.out.println( pat );

        System.out.print( "pattern: " );
        for ( int i = 0; i < offset2; i++ ) {
            System.out.print( " " );
        }
        System.out.println( pat );
        
    }

}
