package aesd.sorting.linear.tests;

import aesd.sorting.linear.RadixSortLSD;
import java.util.Arrays;

/**
 * Teste de uso do algoritmo de ordenação Radix Sort LSD.
 *
 * @author Prof. Dr. David Buzatto
 */
public class TestRadixSortLSD {

    public static void main( String[] args ) {

        // Radix Sort LSD trabalha apenas com inteiros não negativos
        int[] array = { 170, 45, 75, 90, 802, 24, 2, 66 };

        System.out.println( "Antes de ordenar: " + Arrays.toString( array ) );
        RadixSortLSD.sort( array );
        System.out.println( "Depois de ordenar:" + Arrays.toString( array ) );

    }

}
