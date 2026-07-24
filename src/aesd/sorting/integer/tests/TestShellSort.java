package aesd.sorting.integer.tests;

import aesd.sorting.integer.ShellSort;
import java.util.Arrays;

/**
 * Teste de uso do algoritmo de ordenação Shell Sort (versão int[]).
 *
 * @author Prof. Dr. David Buzatto
 */
public class TestShellSort {

    public static void main( String[] args ) {

        int[] array = { 5, 3, 8, 1, 9, 2, 7, 4, 6, 0 };

        System.out.println( "Antes de ordenar: " + Arrays.toString( array ) );
        ShellSort.sort( array );
        System.out.println( "Depois de ordenar:" + Arrays.toString( array ) );

    }

}
