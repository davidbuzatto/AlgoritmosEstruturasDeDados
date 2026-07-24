package aesd.algorithms.strings;

/**
 * Implementação do algoritmo Least Significant Digit (LSD) para ordenação de
 * Strings
 *
 * Ordena da direita para a esquerda: primeiro pelo último caractere (menos
 * significativo), depois pelo penúltimo, e assim sucessivamente até o
 * primeiro. Cada passada usa counting sort (estável) sobre um único
 * caractere, e é justamente a estabilidade de cada passada que garante que a
 * ordem relativa obtida pelas passadas anteriores (caracteres menos
 * significativos, já corretos) seja preservada. Complexidade O(wn), sendo n
 * o número de Strings e w o comprimento fixo de cada uma.
 *
 * Pré-condição: todas as Strings do array devem possuir o mesmo comprimento,
 * igual a w.
 *
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms.
 * 4. ed. Boston: Pearson Education, 2011. 955 p.
 *
 * @author Prof. Dr. David Buzatto
 */
public class LSD {

    /**
     * Ordena o array a de Strings de comprimento fixo w, caractere a
     * caractere, da direita para a esquerda.
     *
     * @param a O array de Strings a ser ordenado.
     * @param w O comprimento (fixo) de cada String em a.
     */
    public static void sort( String[] a, int w ) {

        int n = a.length;
        int R = 256;       // tamanho do alfabeto ASCII estendido
        String[] aux = new String[n];

        for ( int d = w - 1; d >= 0; d-- ) {

            // ordenando pelo d-ésimo caractere (counting sort estável)

            // computando a frequência das contagens
            int[] count = new int[R + 1];
            for ( int i = 0; i < n; i++ ) {
                count[a[i].charAt( d ) + 1]++;
            }

            // computando a acumulação (posição inicial de cada caractere)
            for ( int r = 0; r < R; r++ ) {
                count[r + 1] += count[r];
            }

            // movendo os dados para a posição ordenada, na ordem original
            // (garante estabilidade)
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
