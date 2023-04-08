package aesd.algorithms.substrings;

/**
 * Implementação do algoritmo de Boyer-Moore para busca de substrings.
 * 
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 
 * 4. ed. Boston: Pearson Education, 2011. 955 p.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class BoyerMoore {

    // a raiz/base
    private final int R;
    
    // o array de caracteres ruins para pular
    private int[] right;

    // o padrão armazenado como array
    private char[] pattern;
    
    // o padrão armazenado como String
    private String pat;

    public BoyerMoore( String pat ) {
        
        this.R = 256;
        this.pat = pat;

        // posição da ocorrência de c mais à direita no padrão
        right = new int[R];
        
        for ( int c = 0; c < R; c++ ) {
            right[c] = -1;
        }
        
        for ( int j = 0; j < pat.length(); j++ ) {
            right[pat.charAt( j )] = j;
        }
        
    }
    
    public BoyerMoore( char[] pattern, int R ) {
        
        this.R = R;
        this.pattern = new char[pattern.length];
        
        for ( int j = 0; j < pattern.length; j++ ) {
            this.pattern[j] = pattern[j];
        }

        // posição da ocorrência de c mais à direita no padrão
        right = new int[R];
        
        for ( int c = 0; c < R; c++ ) {
            right[c] = -1;
        }
        
        for ( int j = 0; j < pattern.length; j++ ) {
            right[pattern[j]] = j;
        }
        
    }

    public int search( String txt ) {
        
        int m = pat.length();
        int n = txt.length();
        int skip;
        
        for ( int i = 0; i <= n - m; i += skip ) {
            
            skip = 0;
            
            for ( int j = m - 1; j >= 0; j-- ) {
                if ( pat.charAt( j ) != txt.charAt( i + j ) ) {
                    skip = Math.max( 1, j - right[txt.charAt( i + j )] );
                    break;
                }
            }
            
            // encontrou
            if ( skip == 0 ) {
                return i;
            }
            
        }
        
        // não encontrou
        return n;
        
    }
    
    public int search( char[] text ) {
        
        int m = pattern.length;
        int n = text.length;
        int skip;
        
        for ( int i = 0; i <= n - m; i += skip ) {
            
            skip = 0;
            
            for ( int j = m - 1; j >= 0; j-- ) {
                if ( pattern[j] != text[i + j] ) {
                    skip = Math.max( 1, j - right[text[i + j]] );
                    break;
                }
            }
        
            // encontrou
            if ( skip == 0 ) {
                return i;
            }
            
        }
        
        // não encontrou
        return n;
        
    }

}
