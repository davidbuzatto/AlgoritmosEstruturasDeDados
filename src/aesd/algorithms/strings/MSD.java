package aesd.algorithms.strings;

/**
 * Implementação do algoritmo Most Significant Digit (MSD) para ordenação de
 * Strings 
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

    public static void sort( String[] a ) {
        int n = a.length;
        String[] aux = new String[n];
        sort( a, 0, n-1, 0, aux );
    }

    // return dth character of s, -1 if d = length of string
    private static int charAt( String s, int d ) {
        
        if ( d == s.length() ) {
            return -1;
        }
        
        return s.charAt( d );
        
    }

    private static void sort( String[] a, int lo, int hi, int d, String[] aux ) {

        // tamanho para o emprego do insertion sort
        if ( hi <= lo + CUTOFF ) {
            insertion( a, lo, hi, d );
            return;
        }

        // computando a frequência das contagens
        int[] count = new int[R+2];
        for ( int i = lo; i <= hi; i++ ) {
            int c = charAt( a[i], d );
            count[c+2]++;
        }

        // transforma as contagens em índices
        for ( int r = 0; r < R+1; r++ ) {
            count[r+1] += count[r];
        }

        // distribui
        for ( int i = lo; i <= hi; i++ ) {
            int c = charAt( a[i], d );
            aux[count[c+1]++] = a[i];
        }

        // copia de volta
        for ( int i = lo; i <= hi; i++ ) {
            a[i] = aux[i - lo];
        }

        // ordena recursivamente para os outros caracteres (exclui a sentinela -1)
        for ( int r = 0; r < R; r++ ) {
            sort(a, lo + count[r], lo + count[r+1] - 1, d+1, aux);
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
