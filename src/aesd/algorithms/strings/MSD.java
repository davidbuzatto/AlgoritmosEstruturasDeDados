package aesd.algorithms.strings;

/**
 * Implementação do algoritmo Most Significant Digit (MSD) para ordenação de
 * Strings
 *
 * Ordena da esquerda para a direita: particiona o array pelo primeiro
 * caractere (o mais significativo) usando counting sort, e então ordena
 * recursivamente cada partição (cada grupo de Strings que compartilha aquele
 * caractere) pelo caractere seguinte. Diferente do LSD, não exige que as
 * Strings tenham o mesmo comprimento — Strings mais curtas que já se
 * esgotaram usam um caractere sentinela (-1, menor que qualquer caractere
 * válido) para terminar antes das demais. Faz a troca para insertion sort
 * (mais eficiente em partições pequenas) quando a partição cai abaixo de
 * CUTOFF.
 *
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms.
 * 4. ed. Boston: Pearson Education, 2011. 955 p.
 *
 * @author Prof. Dr. David Buzatto
 */
public class MSD {

    // tamanho do alfabeto ASCII estendido
    private static final int R = 256;

    // tamanho para o emprego do insertion sort
    private static final int CUTOFF = 15;

    /**
     * Ordena o array a de Strings, caractere a caractere, da esquerda para
     * a direita.
     *
     * @param a O array de Strings a ser ordenado.
     */
    public static void sort( String[] a ) {
        int n = a.length;
        String[] aux = new String[n];
        sort( a, 0, n-1, 0, aux );
    }

    // retorna o d-ésimo caractere de s, -1 (sentinela, menor que qualquer
    // caractere válido) se d for igual ao comprimento da string
    private static int charAt( String s, int d ) {

        if ( d == s.length() ) {
            return -1;
        }

        return s.charAt( d );

    }

    // ordena a[lo..hi] pelo d-ésimo caractere em diante
    private static void sort( String[] a, int lo, int hi, int d, String[] aux ) {

        // tamanho para o emprego do insertion sort
        if ( hi <= lo + CUTOFF ) {
            insertion( a, lo, hi, d );
            return;
        }

        // computando a frequência das contagens. o deslocamento de +2 (em vez
        // de +1) reserva a posição 0 para a sentinela -1, já que os índices
        // de count[] não podem ser negativos
        int[] count = new int[R+2];
        for ( int i = lo; i <= hi; i++ ) {
            int c = charAt( a[i], d );
            count[c+2]++;
        }

        // transforma as contagens em índices (posição inicial de cada
        // caractere, incluindo a sentinela)
        for ( int r = 0; r < R+1; r++ ) {
            count[r+1] += count[r];
        }

        // distribui pelas partições, na ordem original (estabilidade)
        for ( int i = lo; i <= hi; i++ ) {
            int c = charAt( a[i], d );
            aux[count[c+1]++] = a[i];
        }

        // copia de volta
        for ( int i = lo; i <= hi; i++ ) {
            a[i] = aux[i - lo];
        }

        // ordena recursivamente cada partição pelo caractere seguinte
        // (exclui a partição da sentinela -1, r = 0, que já está pronta:
        // são as Strings que terminaram exatamente em d)
        for ( int r = 0; r < R; r++ ) {
            sort( a, lo + count[r], lo + count[r+1] - 1, d+1, aux );
        }

    }


    // insertion sort
    private static void insertion( String[] a, int lo, int hi, int d ) {
        for ( int i = lo; i <= hi; i++ ) {
            for ( int j = i; j > lo && less(a[j], a[j-1], d); j-- ) {
                exchange( a, j, j-1 );
            }
        }
    }

    private static void exchange( String[] a, int i, int j ) {
        String temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    // se v é menor que w começando pelo caractere na posição d
    private static boolean less( String v, String w, int d ) {
        
        for ( int i = d; i < Math.min(v.length(), w.length()); i++ ) {
            if ( v.charAt(i) < w.charAt(i) ) {
                return true;
            }
            if ( v.charAt(i) > w.charAt(i) ) {
                return false;
            }
        }
        
        return v.length() < w.length();
        
    }
    
}
