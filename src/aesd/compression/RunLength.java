package aesd.compression;

import aesd.utils.BinaryStdIn;
import aesd.utils.BinaryStdOut;

/**
 * codificação de comprimento de carreira (Run Length Encoding).
 * @author Prof. Dr. David Buzatto
 */
/**
 * The {@code RunLength} class provides static methods for compressing and
 * expanding a binary input using run-length coding with 8-bit run lengths.
 * <p>
 * For additional documentation, see
 * <a href="https://algs4.cs.princeton.edu/55compression">Section 5.5</a> of
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 * @author Robert Sedgewick
 * @author Kevin Wayne
 */
public class RunLength {

    private static final int R = 256;
    private static final int LG_R = 8;

    // Do not instantiate.
    private RunLength() {
    }

    /**
     * Reads a sequence of bits from standard input (that are encoded using
     * run-length encoding with 8-bit run lengths); decodes them; and writes the
     * results to standard output.
     */
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

    /**
     * Reads a sequence of bits from standard input; compresses them using
     * run-length coding with 8-bit run lengths; and writes the results to
     * standard output.
     */
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

}
