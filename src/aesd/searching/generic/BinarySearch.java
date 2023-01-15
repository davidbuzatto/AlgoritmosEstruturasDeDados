
package aesd.searching.generic;

/**
 * Algoritmo de busca binária iterativo e recursivo.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class BinarySearch {

    public static <Type extends Comparable<Type>> int search( 
            Type[] array, Type key ) {
        
        /*
         * Algoritmo iterativo.
         */
        /*int start = 0;
        int end = array.length - 1;
        int middle;
        int comp;

        while ( start <= end ) {

            middle = ( start + end ) / 2;
            comp = key.compareTo( array[middle] );
            
            if ( comp == 0 ) {
                return middle;
            } else if ( comp < 0 ) {
                end = middle - 1;
            } else {
                start = middle + 1;
            }

        }

        return -1;*/


        /*
         * Algoritmo recursivo.
         */
        return searchR( array, key, 0, array.length - 1 );
        
    }
    
    /*
     * Algoritmo recursivo da busca binária.
     */
    public static <Type extends Comparable<Type>> int searchR( 
            Type[] array, Type key, int start, int end ) {

        int middle = ( start + end ) / 2;
        int comp = key.compareTo( array[middle] );
        
        if ( start <= end ) {
            if ( comp == 0 ) {
                return middle;
            } else if ( comp < 0 ) {
                return searchR( array, key, start, middle - 1 );
            } else {
                return searchR( array, key, middle + 1, end );
            }
        } else {
            return -1;
        }

    }
    
}
