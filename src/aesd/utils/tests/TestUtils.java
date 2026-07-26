package aesd.utils.tests;

import aesd.ds.implementations.linear.ResizingArrayList;
import aesd.ds.interfaces.List;
import aesd.utils.Utils;
import java.util.Arrays;

/**
 * Teste de uso dos métodos utilitários da classe Utils.
 *
 * @author Prof. Dr. David Buzatto
 */
public class TestUtils {

    public static void main( String[] args ) {

        int[] intArray = { 10, 5, -2, 3, 7 };
        System.out.println( "int[] antes do swap:    " + Arrays.toString( intArray ) );
        Utils.swap( intArray, 0, 4 );
        System.out.println( "int[] depois do swap:   " + Arrays.toString( intArray ) );

        Integer[] objArray = { 10, 5, -2, 3, 7 };
        System.out.println( "\nObject[] antes do swap: " + Arrays.toString( objArray ) );
        Utils.swap( objArray, 0, 4 );
        System.out.println( "Object[] depois do swap:" + Arrays.toString( objArray ) );

        int[] intToShuffle = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        System.out.println( "\nint[] antes do shuffle:    " + Arrays.toString( intToShuffle ) );
        Utils.shuffle( intToShuffle );
        System.out.println( "int[] depois do shuffle:   " + Arrays.toString( intToShuffle ) );

        Integer[] objToShuffle = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        System.out.println( "\nObject[] antes do shuffle: " + Arrays.toString( objToShuffle ) );
        Utils.shuffle( objToShuffle );
        System.out.println( "Object[] depois do shuffle:" + Arrays.toString( objToShuffle ) );
        
        List<Integer> list = new ResizingArrayList<>();

        for ( int i = 1; i <= 10; i++ ) {
            list.add( i );
        }

        System.out.println( "\nLista antes do shuffle:" );
        System.out.println( list );

        Utils.shuffle( list );

        System.out.println( "Lista depois do shuffle:" );
        System.out.println( list );

    }

}
