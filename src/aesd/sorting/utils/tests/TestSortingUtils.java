package aesd.sorting.utils.tests;

import aesd.sorting.utils.SortingUtils;
import java.util.Arrays;

/**
 * Teste de uso dos métodos utilitários da classe SortingUtils.
 *
 * @author Prof. Dr. David Buzatto
 */
public class TestSortingUtils {

    public static void main( String[] args ) {

        int[] intArray = { 10, 5, -2, 3, 7 };
        System.out.println( "int[] antes do swap:    " + Arrays.toString( intArray ) );
        SortingUtils.swap( intArray, 0, 4 );
        System.out.println( "int[] depois do swap:   " + Arrays.toString( intArray ) );

        Integer[] objArray = { 10, 5, -2, 3, 7 };
        System.out.println( "\nObject[] antes do swap: " + Arrays.toString( objArray ) );
        SortingUtils.swap( objArray, 0, 4 );
        System.out.println( "Object[] depois do swap:" + Arrays.toString( objArray ) );

        int[] intToShuffle = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        System.out.println( "\nint[] antes do shuffle:    " + Arrays.toString( intToShuffle ) );
        SortingUtils.shuffle( intToShuffle );
        System.out.println( "int[] depois do shuffle:   " + Arrays.toString( intToShuffle ) );

        Integer[] objToShuffle = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        System.out.println( "\nObject[] antes do shuffle: " + Arrays.toString( objToShuffle ) );
        SortingUtils.shuffle( objToShuffle );
        System.out.println( "Object[] depois do shuffle:" + Arrays.toString( objToShuffle ) );

    }

}
