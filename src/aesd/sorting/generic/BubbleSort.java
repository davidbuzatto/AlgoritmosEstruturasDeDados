package aesd.sorting.generic;

import aesd.sorting.utils.SortingUtils;

/**
 * Ordenação por "flutuação" ou "borbulhamento" (Bubble Sort)
 *
 * Aplicação sucessiva de comparações entre vizinhos
 * (na prática também separa a sequência em duas
 * partes: ordenada e não-ordenada).
 *
 * Iteração: percorrer toda a sequência não-ordenada
 * comparando todos os vizinhos e trocando de posição
 * quando necessário, no final, o menor elemento
 * poderá ser concatenado na sequência ordenada.
 *
 * Os valores dos dados interferem na execução do algoritmo.
 *
 * Crescimento do número de comparações em relação ao tamanho
 * de entrada:
 *     – linear (no melhor caso)
 *     – quadrático (no pior caso)
 *
 * Crescimento do número de trocas em relação ao tamanho de
 * entrada:
 *     – constante (no melhor caso)
 *     – quadrático (no pior caso)
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
public class BubbleSort {

    public static <Type extends Comparable<Type>> void sort( Type[] array ) {
        
        // tamanho do array
        int n = array.length;
        
        // indica se houve ou não pelo
        // menos uma troca
        boolean swapped;

        // índice da sequência ordenada
        // (indica o final da mesma)
        // controla a iteração
        int i = 0;

        // controla a varredura na sequência
        // não-ordenada
        int j;

        do {

            // a priori, não houve troca
            swapped = false;

            // percorre o array do seu fim
            // até o fim da sequência ordenada
            // indicado por i
            for( j = n-1; j > i; j-- ) {
                
                // se o elemento à esquerda for maior
                // que o elemento à direita, eles estão
                // trocados, sendo assim...
                if ( array[j-1].compareTo( array[j] ) > 0 ) {

                    // troca os elementos adjacentes
                    SortingUtils.swap( array, j-1, j );

                    // indica que houve uma troca
                    swapped = true;

                }

            }

            // incrementa o índice da parte ordenada
            i++;

            // tudo isso execute enquanto houver alguma troca,
            // pois pode haver mais alguma no próximo passo
            // e enquanto i (que indica a parte ordenada)
            // não chegar na posição final do array
        } while( swapped && i < n );
    
    }
    
}
