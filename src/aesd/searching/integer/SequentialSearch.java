/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
