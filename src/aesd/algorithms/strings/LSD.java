package aesd.algorithms.strings;

/**
 * Implementação do algoritmo Least Significant Digit (LSD) para ordenação de
 * Strings 
 * 
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 
 * 4. ed. Boston: Pearson Education, 2011. 955 p.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class LSD {

    public static void sort( String[] a, int w ) {
        
        int n = a.length;
        int R = 256;       // tamanho do alfabeto ASCII estendido
        String[] aux = new String[n];

        for ( int d = w - 1; d >= 0; d-- ) {
            
            // ordenando pelo d-ésimo caractere

            // computando a frequência das contagens
            int[] count = new int[R + 1];
            for ( int i = 0; i < n; i++ ) {
                count[a[i].charAt( d ) + 1]++;
            }

            // computando a acumulação
            for ( int r = 0; r < R; r++ ) {
                count[r + 1] += count[r];
            }

            // movendo os dados
            for ( int i = 0; i < n; i++ ) {
                aux[count[a[i].charAt( d )]++] = a[i];
            }

            // copiando de volta
            for ( int i = 0; i < n; i++ ) {
                a[i] = aux[i];
            }
            
        }
        
    }
    
}
