package aesd.sorting.integer;

import aesd.sorting.utils.SortingUtils;

/**
 * Ordenação por inserção (Insertion Sort)
 *
 * Divisão dos dados em duas sequências:
 * ordenada e não-ordenada.
 *
 * Iteração: inserir o primeiro elemento da
 * sequência não-ordenada na sequência
 * ordenada.
 *
 * Os valores dos dados interferem na execução do algoritmo.
 *
 * Crescimento do número de comparações em relação ao tamanho
 * de entrada:
 *     - linear (no melhor caso)
 *     - quadrático (no pior caso)
 *
 * Crescimento do número de trocas em relação ao tamanho de
 * entrada:
 *     - constante (no melhor caso)
 *     - quadrático (no pior caso)
 *
 * Crescimento do uso de memória em relação ao tamanho da
 * entrada: constante.
 *
 * In-place? Sim
 *  Estável? Sim
 *
 * Complexidade:
 *       Pior caso: O(n^2)
 *      Caso médio: O(n^2)
 *     Melhor caso: O(n)
 *
 * @author Prof. Dr. David Buzatto
 */
public class InsertionSort {

    public static void sort( int[] array ) {
        
        // tamanho do array
        int n = array.length;
        
        // índice da sequência ordenada
        // controla a iteração
        int i;

        // controla a inserção do elemento na sequência
        // não-ordenada na sequência ordenada.
        int j;

        // percorre o array partindo da segunda posição
        // isso indica que a posição 0 já faz parte da sequência
        // ordenada
        for ( i = 1; i < n; i++ ) {

            // j indica o início da parte não-ordenada
            j = i;

            // se j ainda puder percorrer a parte não-ordenada
            // e a posição à esquerda do j for maior que a posição
            // à direita, indica que os valores estão trocados,
            // sendo assim...
            while ( j > 0 && array[j-1] > array[j] ) {

                // troca os elementos adjacentes
                SortingUtils.swap( array, j-1, j );

                // e posiciona o j para a esquerda
                j--;

            }

        }
    
    }
    
}
