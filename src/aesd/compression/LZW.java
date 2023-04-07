package aesd.compression;

import aesd.utils.BinaryStdIn;
import aesd.utils.BinaryStdOut;

/**
 *
 * @author Prof. Dr. David Buzatto
 */
/**
 * The {@code LZW} class provides static methods for compressing and expanding a
 * binary input using LZW compression over the 8-bit extended ASCII alphabet
 * with 12-bit codewords.
 * <p>
 * WARNING: Starting with Oracle Java 7u6, the substring method takes time and
 * space linear in the length of the extracted substring (instead of constant
 * time an space as in earlier versions). As a result, compression takes
 * quadratic time. TODO: fix. See
 * <a href = "http://java-performance.info/changes-to-string-java-1-7-0_06/">this
 * article</a>
 * for more details.
 * <p>
 * For additional documentation, see
 * <a href="https://algs4.cs.princeton.edu/55compression">Section 5.5</a> of
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 * @author Robert Sedgewick
 * @author Kevin Wayne
 */
public class LZW {

    private static final int R = 256;        // number of input chars
    private static final int L = 4096;       // number of codewords = 2^W
    private static final int W = 12;         // codeword width

    // Do not instantiate.
    private LZW() {
    }

    /**
     * Reads a sequence of 8-bit bytes from standard input; compresses them
     * using LZW compression with 12-bit codewords; and writes the results to
     * standard output.
     */
    public static void compress() {
        String input = BinaryStdIn.readString();
        TernarySearchTree<Integer> st = new TernarySearchTree<Integer>();

        // since TST is not balanced, it would be better to insert in a different order
        for ( int i = 0; i < R; i++ ) {
            st.put( "" + (char) i, i );
        }

        int code = R + 1;  // R is codeword for EOF

        while ( input.length() > 0 ) {
            String s = st.longestPrefixOf( input );  // Find max prefix match s.
            BinaryStdOut.write( st.get( s ), W );      // Print s's encoding.
            int t = s.length();
            if ( t < input.length() && code < L ) // Add s to symbol table.
            {
                st.put( input.substring( 0, t + 1 ), code++ );
            }
            input = input.substring( t );            // Scan past s in input.
        }
        BinaryStdOut.write( R, W );
        BinaryStdOut.close();
    }

    /**
     * Reads a sequence of bit encoded using LZW compression with 12-bit
     * codewords from standard input; expands them; and writes the results to
     * standard output.
     */
    public static void expand() {
        String[] st = new String[L];
        int i; // next available codeword value

        // initialize symbol table with all 1-character strings
        for ( i = 0; i < R; i++ ) {
            st[i] = "" + (char) i;
        }
        st[i++] = "";                        // (unused) lookahead for EOF

        int codeword = BinaryStdIn.readInt( W );
        if ( codeword == R ) {
            return;           // expanded message is empty string
        }
        String val = st[codeword];

        while ( true ) {
            BinaryStdOut.write( val );
            codeword = BinaryStdIn.readInt( W );
            if ( codeword == R ) {
                break;
            }
            String s = st[codeword];
            if ( i == codeword ) {
                s = val + val.charAt( 0 );   // special case hack
            }
            if ( i < L ) {
                st[i++] = val + s.charAt( 0 );
            }
            val = s;
        }
        BinaryStdOut.close();
    }

}
