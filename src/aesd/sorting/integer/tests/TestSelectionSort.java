package aesd.sorting.integer.tests;

import aesd.sorting.integer.SelectionSort;
import java.util.Arrays;

/**
 * Teste de uso do algoritmo de ordenação Selection Sort (versão int[]).
 *
 * @author Prof. Dr. David Buzatto
 */
public class TestSelectionSort {

    public static void main( String[] args ) {

        int[] array = { 5, 3, 8, 1, 9, 2, 7, 4, 6, 0 };

        System.out.println( "Antes de ordenar: " + Arrays.toString( array ) );
        SelectionSort.sort( array );
        System.out.println( "Depois de ordenar:" + Arrays.toString( array ) );

    }

}
