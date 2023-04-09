package aesd.algorithms.substrings;

/**
 * Implementação do algoritmo de Knut-Morris-Pratt para busca de substrings.
 * 
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 
 * 4. ed. Boston: Pearson Education, 2011. 955 p.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class KMP {

    // a raiz/base
    private final int R;
    
    // comprimento do padrão
    private final int m;
    
    // o autômato KMP
    private int[][] dfa;

    
    public KMP( String pat ) {
        
        this.R = 256;
        this.m = pat.length();

        // constroi o DFA a partir do padrão
        dfa = new int[R][m];
        dfa[pat.charAt( 0 )][0] = 1;
        
        for ( int x = 0, j = 1; j < m; j++ ) {
            
            // copia os casos de não casamento/incompatibilidade (mismatch)
            for ( int c = 0; c < R; c++ ) {
                dfa[c][j] = dfa[c][x];
            }
            
            // configura o caso de casamento/correspondência (match)
            dfa[pat.charAt( j )][j] = j + 1;
            
            // atualiza o estado de reinício
            x = dfa[pat.charAt( j )][x];
            
        }
        
    }
    
    public KMP( char[] pattern, int R ) {
        
        this.R = R;
        this.m = pattern.length;

        // constroi o DFA a partir do padrão
        int m = pattern.length;
        dfa = new int[R][m];
        dfa[pattern[0]][0] = 1;
        
        for ( int x = 0, j = 1; j < m; j++ ) {
            
            // copia os casos de não casamento (mismatch)
            for ( int c = 0; c < R; c++ ) {
                dfa[c][j] = dfa[c][x];
            }
            
            // configura o caso de casamento (match)
            dfa[pattern[j]][j] = j + 1;
            
            // atualiza o estado de reinício
            x = dfa[pattern[j]][x];
            
        }
    }
    
    public int search( String txt ) {

        // simula a operação do DFA no texto
        int n = txt.length();
        int i;
        int j;
        
        for ( i = 0, j = 0; i < n && j < m; i++ ) {
            j = dfa[txt.charAt( i )][j];
        }
        
        // encontrou
        if ( j == m ) {
            return i - m;
        }
        
        // não encontrou
        return n;
        
    }
    
    public int search( char[] text ) {

        // simula a operação do DFA no texto
        int n = text.length;
        int i;
        int j;
        
        for ( i = 0, j = 0; i < n && j < m; i++ ) {
            j = dfa[text[i]][j];
        }
        
        // encontrou
        if ( j == m ) {
            return i - m;
        }
        
        // não encontrou
        return n;
        
    }

}
