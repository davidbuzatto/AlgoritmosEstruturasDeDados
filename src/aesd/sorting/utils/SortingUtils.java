
package aesd.sorting.utils;

/**
 * Métodos utilitários para os algoritmos de ordenação.
 * 
 * @author Prof. Dr. David Buzatto
 */
public abstract class SortingUtils {
    
    /**
     * Método de troca para arrays de qualquer tipo de referência.
     * 
     * @param array array com os elementos
     * @param p1 posição 1
     * @param p2 posição 2
     */
    public static void swap( Object[] array, int p1, int p2 ) {
        Object temp = array[p1];
        array[p1] = array[p2];
        array[p2] = temp;
    }
    
    /**
     * Método de troca para arrays de inteiros.
     * 
     * @param array array com os elementos
     * @param p1 posição 1
     * @param p2 posição 2
     */
    public static void swap( int[] array, int p1, int p2 ) {
        int temp = array[p1];
        array[p1] = array[p2];
        array[p2] = temp;
    }


    /**
     * Método de embaralhamento para arrays de qualquer tipo de referência,
     * usando o algoritmo de Fisher-Yates (shuffle de Knuth): a cada posição
     * i, troca o elemento ali com o de uma posição sorteada dentro do
     * intervalo ainda não embaralhado [i, array.length). É esse intervalo
     * que encolhe a cada iteração que garante que todas as n! permutações
     * possíveis sejam igualmente prováveis.
     *
     * @param array array a ser embaralhado
     */
    public static void shuffle( Object[] array ) {

        for ( int i = 0; i < array.length; i++ ) {
            swap( array, i, i + (int) (Math.random() * (array.length - i)) );
        }

    }

    /**
     * Método de embaralhamento para arrays de inteiros, usando o algoritmo
     * de Fisher-Yates (shuffle de Knuth): a cada posição i, troca o
     * elemento ali com o de uma posição sorteada dentro do intervalo ainda
     * não embaralhado [i, array.length). É esse intervalo que encolhe a
     * cada iteração que garante que todas as n! permutações possíveis
     * sejam igualmente prováveis.
     *
     * @param array array a ser embaralhado
     */
    public static void shuffle( int[] array ) {

        for ( int i = 0; i < array.length; i++ ) {
            swap( array, i, i + (int) (Math.random() * (array.length - i)) );
        }

    }
    
}
