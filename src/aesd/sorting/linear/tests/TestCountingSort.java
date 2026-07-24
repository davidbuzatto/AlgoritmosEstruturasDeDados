package aesd.sorting.linear.tests;

import aesd.sorting.linear.CountingSort;
import java.util.Arrays;

/**
 * Teste de uso do algoritmo de ordenação Counting Sort.
 *
 * @author Prof. Dr. David Buzatto
 */
public class TestCountingSort {

    public static void main( String[] args ) {

        // Counting Sort trabalha apenas com inteiros não negativos
        int[] array = { 5, 3, 8, 1, 9, 2, 7, 4, 6, 0 };

        // maior valor do array, usado para dimensionar o array de contagem
        int k = 9;

        System.out.println( "Antes de ordenar: " + Arrays.toString( array ) );
        CountingSort.sort( array, k );
        System.out.println( "Depois de ordenar:" + Arrays.toString( array ) );

    }

}
