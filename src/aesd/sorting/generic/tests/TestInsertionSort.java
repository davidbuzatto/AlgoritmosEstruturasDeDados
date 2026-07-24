package aesd.sorting.generic.tests;

import aesd.sorting.generic.InsertionSort;
import java.util.Arrays;

/**
 * Teste de uso do algoritmo de ordenação Insertion Sort (versão genérica).
 *
 * @author Prof. Dr. David Buzatto
 */
public class TestInsertionSort {

    public static void main( String[] args ) {

        Integer[] array = { 5, 3, 8, 1, 9, 2, 7, 4, 6, 0 };

        System.out.println( "Antes de ordenar: " + Arrays.toString( array ) );
        InsertionSort.sort( array );
        System.out.println( "Depois de ordenar:" + Arrays.toString( array ) );

    }

}
