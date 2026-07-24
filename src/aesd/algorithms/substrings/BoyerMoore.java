package aesd.algorithms.substrings;

/**
 * Implementação do algoritmo de Boyer-Moore para busca de substrings.
 *
 * Diferente da busca ingênua (que compara o padrão da esquerda para a
 * direita, avançando uma posição por vez), Boyer-Moore compara o padrão
 * contra o texto da direita para a esquerda e, ao encontrar uma
 * incompatibilidade, usa a regra do "bad character": pula o padrão inteiro
 * para além da última ocorrência, no próprio padrão, do caractere do texto
 * que causou a incompatibilidade (tabela right[]). Isso permite pular
 * vários caracteres de uma vez, em vez de avançar um a um, tornando a
 * busca sublinear na prática (embora o pior caso continue sendo O(nm)).
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

    /**
     * Pré-processa o padrão pat, construindo a tabela right[] (posição da
     * ocorrência mais à direita de cada caractere no padrão, -1 se ausente).
     *
     * @param pat O padrão a ser buscado.
     */
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
    
    /**
     * Pré-processa o padrão pattern, construindo a tabela right[] (posição
     * da ocorrência mais à direita de cada caractere no padrão, -1 se
     * ausente), usando um alfabeto de tamanho R.
     *
     * @param pattern O padrão a ser buscado.
     * @param R O tamanho do alfabeto.
     */
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

    /**
     * Busca o padrão em txt, varrendo o padrão da direita para a esquerda a
     * cada tentativa e pulando via a regra do bad character quando ocorre
     * incompatibilidade.
     *
     * @param txt O texto onde o padrão será buscado.
     * @return O índice da primeira ocorrência do padrão, ou txt.length()
     * se não encontrado.
     */
    public int search( String txt ) {

        int m = pat.length();
        int n = txt.length();
        int skip;
        
        for ( int i = 0; i <= n - m; i += skip ) {
            
            skip = 0;
            
            for ( int j = m - 1; j >= 0; j-- ) {
                if ( pat.charAt( j ) != txt.charAt( i + j ) ) {

                    // caractere fora do alfabeto (R) considerado: trata como
                    // se nunca ocorresse no padrão (posição -1)
                    char c = txt.charAt( i + j );
                    int rightC = c < R ? right[c] : -1;

                    // o Math.max com 1 garante avanço mínimo de uma posição
                    // mesmo quando o caractere ocorre à direita da posição
                    // atual dentro do padrão, evitando um salto nulo/negativo
                    skip = Math.max( 1, j - rightC );
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
    
    /**
     * Busca o padrão em text, varrendo o padrão da direita para a esquerda
     * a cada tentativa e pulando via a regra do bad character quando
     * ocorre incompatibilidade.
     *
     * @param text O texto onde o padrão será buscado.
     * @return O índice da primeira ocorrência do padrão, ou text.length
     * se não encontrado.
     */
    public int search( char[] text ) {

        int m = pattern.length;
        int n = text.length;
        int skip;
        
        for ( int i = 0; i <= n - m; i += skip ) {
            
            skip = 0;
            
            for ( int j = m - 1; j >= 0; j-- ) {
                if ( pattern[j] != text[i + j] ) {

                    // caractere fora do alfabeto (R) considerado: trata como
                    // se nunca ocorresse no padrão (posição -1)
                    char c = text[i + j];
                    int rightC = c < R ? right[c] : -1;

                    // o Math.max com 1 garante avanço mínimo de uma posição
                    // mesmo quando o caractere ocorre à direita da posição
                    // atual dentro do padrão, evitando um salto nulo/negativo
                    skip = Math.max( 1, j - rightC );
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
