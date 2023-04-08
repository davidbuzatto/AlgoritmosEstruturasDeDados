package aesd.compression;

import aesd.utils.BinaryStdIn;
import aesd.utils.BinaryStdOut;

/**
 * Implementação da codificação de comprimento de carreira (Run Length Encoding). 
 * 
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 
 * 4. ed. Boston: Pearson Education, 2011. 955 p.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class RunLength {

    private static final int R = 256;
    private static final int LG_R = 8;
    
    public static void compress() {
        
        char run = 0;
        boolean old = false;
        
        while ( !BinaryStdIn.isEmpty() ) {
            
            boolean b = BinaryStdIn.readBoolean();
            
            if ( b != old ) {
                BinaryStdOut.write( run, LG_R );
                run = 1;
                old = !old;
            } else {
                if ( run == R - 1 ) {
                    BinaryStdOut.write( run, LG_R );
                    run = 0;
                    BinaryStdOut.write( run, LG_R );
                }
                run++;
            }
            
        }
        
        BinaryStdOut.write( run, LG_R );
        BinaryStdOut.close();
        
    }
    
    public static void expand() {
        
        boolean b = false;
        
        while ( !BinaryStdIn.isEmpty() ) {
            
            int run = BinaryStdIn.readInt( LG_R );
            
            for ( int i = 0; i < run; i++ ) {
                BinaryStdOut.write( b );
            }
            
            b = !b;
            
        }
        
        BinaryStdOut.close();
        
    }

}
