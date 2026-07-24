package aesd.sorting.integer.tests;

import aesd.sorting.integer.QuickSort;
import aesd.sorting.utils.SortingUtils;
import java.util.Arrays;

/**
 * Teste de uso do algoritmo de ordenação Quick Sort (versão int[]).
 *
 * @author Prof. Dr. David Buzatto
 */
public class TestQuickSort {

    public static void main( String[] args ) {

        int[] array = { 5, 3, 8, 1, 9, 2, 7, 4, 6, 0 };

        // embaralha antes de ordenar, já que esta implementação sempre usa
        // o primeiro elemento como pivô (ver Javadoc de QuickSort)
        SortingUtils.shuffle( array );

        System.out.println( "Antes de ordenar: " + Arrays.toString( array ) );
        QuickSort.sort( array );
        System.out.println( "Depois de ordenar:" + Arrays.toString( array ) );

    }

}
