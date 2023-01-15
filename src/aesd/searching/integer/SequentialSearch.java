
package aesd.searching.integer;

/**
 * Algoritmo de busca sequencial.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class SequentialSearch {

    public static int search( int[] array, int key ) {
        
        int n = array.length;

        for ( int i = 0; i < n; i++ ) {
            if ( array[i] == key ) {
                return i;
            }
        }

        return -1;
        
    }
    
}
