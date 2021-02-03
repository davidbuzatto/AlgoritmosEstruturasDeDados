/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aesd.esdi3.searching.integer;

/**
 * Algoritmo de busca binária iterativo e recursivo.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class BinarySearch {

    public static int search( int[] array, int key ) {
        
        /*
         * Algoritmo iterativo.
         */
        /*int start = 0;
        int end = array.length - 1;
        int middle;

        while ( start <= end ) {

            middle = ( start + end ) / 2;
            
            if ( key == array[middle] ) {
                return middle;
            } else if ( key < array[middle] ) {
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
    public static int searchR( 
            int[] array, int key, int start, int end ) {

        int middle = ( start + end ) / 2;
        
        if ( start <= end ) {
            if ( key == array[middle] ) {
                return middle;
            } else if ( key < array[middle] ) {
                return searchR( array, key, start, middle - 1 );
            } else {
                return searchR( array, key, middle + 1, end );
            }
        } else {
            return -1;
        }

    }
    
}
