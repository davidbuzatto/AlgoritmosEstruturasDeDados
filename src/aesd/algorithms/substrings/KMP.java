package aesd.algorithms.substrings;

/**
 * Implementação do algoritmo de Knuth-Morris-Pratt para busca de substrings.
 *
 * A ideia central é pré-computar um autômato finito determinístico (DFA) a
 * partir do padrão, de modo que a busca no texto nunca precise retroceder:
 * o índice i do texto avança sempre, um caractere por vez, e é o estado j
 * do autômato (não i) que "sabe" quanto do padrão já foi reconhecido até
 * aqui — inclusive reaproveitando informação de correspondências parciais
 * anteriores. Isso garante O(n) no texto, contra o pior caso O(nm) da
 * busca ingênua.
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

    /**
     * Constrói o autômato KMP (dfa) a partir do padrão pat.
     *
     * @param pat O padrão a ser buscado.
     */
    public KMP( String pat ) {

        this.R = 256;
        this.m = pat.length();

        // constroi o DFA a partir do padrão
        dfa = new int[R][m];
        dfa[pat.charAt( 0 )][0] = 1;

        for ( int x = 0, j = 1; j < m; j++ ) {

            // copia os casos de não casamento/incompatibilidade (mismatch):
            // x é o estado que o autômato atingiria vendo apenas o sufixo
            // do prefixo já reconhecido, simulando o reinício sem
            // reprocessar o texto já lido
            for ( int c = 0; c < R; c++ ) {
                dfa[c][j] = dfa[c][x];
            }

            // configura o caso de casamento/correspondência (match)
            dfa[pat.charAt( j )][j] = j + 1;

            // atualiza o estado de reinício
            x = dfa[pat.charAt( j )][x];

        }

    }

    /**
     * Constrói o autômato KMP (dfa) a partir do padrão pattern, usando um
     * alfabeto de tamanho R.
     *
     * @param pattern O padrão a ser buscado.
     * @param R O tamanho do alfabeto.
     */
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
    
    /**
     * Busca o padrão em txt simulando o autômato KMP, sem retroceder no
     * texto.
     *
     * @param txt O texto onde o padrão será buscado.
     * @return O índice da primeira ocorrência do padrão, ou txt.length()
     * se não encontrado.
     */
    public int search( String txt ) {

        // simula a operação do DFA no texto
        int n = txt.length();
        int i;
        int j;
        
        for ( i = 0, j = 0; i < n && j < m; i++ ) {

            // caractere fora do alfabeto (R) considerado: não pertence ao
            // padrão, então o autômato reinicia (estado 0)
            char c = txt.charAt( i );
            j = c < R ? dfa[c][j] : 0;

        }
        
        // encontrou
        if ( j == m ) {
            return i - m;
        }
        
        // não encontrou
        return n;
        
    }
    
    /**
     * Busca o padrão em text simulando o autômato KMP, sem retroceder no
     * texto.
     *
     * @param text O texto onde o padrão será buscado.
     * @return O índice da primeira ocorrência do padrão, ou text.length
     * se não encontrado.
     */
    public int search( char[] text ) {

        // simula a operação do DFA no texto
        int n = text.length;
        int i;
        int j;
        
        for ( i = 0, j = 0; i < n && j < m; i++ ) {

            // caractere fora do alfabeto (R) considerado: não pertence ao
            // padrão, então o autômato reinicia (estado 0)
            char c = text[i];
            j = c < R ? dfa[c][j] : 0;

        }
        
        // encontrou
        if ( j == m ) {
            return i - m;
        }
        
        // não encontrou
        return n;
        
    }

}
