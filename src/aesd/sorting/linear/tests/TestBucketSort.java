package aesd.sorting.linear.tests;

import aesd.sorting.linear.BucketSort;
import java.util.Arrays;

/**
 * Teste de uso do algoritmo de ordenação Bucket Sort.
 *
 * @author Prof. Dr. David Buzatto
 */
public class TestBucketSort {

    public static void main( String[] args ) {

        // Bucket Sort trabalha apenas com inteiros não negativos
        int[] array = { 5, 3, 8, 1, 9, 2, 7, 4, 6, 0 };

        System.out.println( "Antes de ordenar: " + Arrays.toString( array ) );
        BucketSort.sort( array );
        System.out.println( "Depois de ordenar:" + Arrays.toString( array ) );

    }

}
