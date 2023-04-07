package aesd.algorithms.substrings;

/**
 *
 * @author Prof. Dr. David Buzatto
 */
/**
 * The {@code KMP} class finds the first occurrence of a pattern string in a
 * text string.
 * <p>
 * This implementation uses a version of the Knuth-Morris-Pratt substring search
 * algorithm. The version takes time proportional to <em>n</em> + <em>m R</em>
 * in the worst case, where <em>n</em> is the length of the text string,
 * <em>m</em> is the length of the pattern, and <em>R</em> is the alphabet size.
 * It uses extra space proportional to <em>m R</em>.
 * <p>
 * For additional documentation, see
 * <a href="https://algs4.cs.princeton.edu/53substring">Section 5.3</a> of
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 */
public class KMP {

    private final int R;       // the radix
    private final int m;       // length of pattern
    private int[][] dfa;       // the KMP automaton

    /**
     * Preprocesses the pattern string.
     *
     * @param pat the pattern string
     */
    public KMP( String pat ) {
        this.R = 256;
        this.m = pat.length();

        // build DFA from pattern
        dfa = new int[R][m];
        dfa[pat.charAt( 0 )][0] = 1;
        for ( int x = 0, j = 1; j < m; j++ ) {
            for ( int c = 0; c < R; c++ ) {
                dfa[c][j] = dfa[c][x];     // Copy mismatch cases.
            }
            dfa[pat.charAt( j )][j] = j + 1;   // Set match case.
            x = dfa[pat.charAt( j )][x];     // Update restart state.
        }
    }

    /**
     * Preprocesses the pattern string.
     *
     * @param pattern the pattern string
     * @param R the alphabet size
     */
    public KMP( char[] pattern, int R ) {
        this.R = R;
        this.m = pattern.length;

        // build DFA from pattern
        int m = pattern.length;
        dfa = new int[R][m];
        dfa[pattern[0]][0] = 1;
        for ( int x = 0, j = 1; j < m; j++ ) {
            for ( int c = 0; c < R; c++ ) {
                dfa[c][j] = dfa[c][x];     // Copy mismatch cases.
            }
            dfa[pattern[j]][j] = j + 1;      // Set match case.
            x = dfa[pattern[j]][x];        // Update restart state.
        }
    }

    /**
     * Returns the index of the first occurrence of the pattern string in the
     * text string.
     *
     * @param txt the text string
     * @return the index of the first occurrence of the pattern string in the
     * text string; N if no such match
     */
    public int search( String txt ) {

        // simulate operation of DFA on text
        int n = txt.length();
        int i, j;
        for ( i = 0, j = 0; i < n && j < m; i++ ) {
            j = dfa[txt.charAt( i )][j];
        }
        if ( j == m ) {
            return i - m;    // found
        }
        return n;                    // not found
    }

    /**
     * Returns the index of the first occurrence of the pattern string in the
     * text string.
     *
     * @param text the text string
     * @return the index of the first occurrence of the pattern string in the
     * text string; N if no such match
     */
    public int search( char[] text ) {

        // simulate operation of DFA on text
        int n = text.length;
        int i, j;
        for ( i = 0, j = 0; i < n && j < m; i++ ) {
            j = dfa[text[i]][j];
        }
        if ( j == m ) {
            return i - m;    // found
        }
        return n;                    // not found
    }

}
