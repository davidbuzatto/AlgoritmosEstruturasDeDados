/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aesd.esdi3.searching.generic;

/**
 * Algoritmo de busca sequencial.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class SequentialSearch {

    public static <Type extends Comparable<Type>> int search( 
            Type[] array, Type key ) {
        
        int n = array.length;

        for ( int i = 0; i < n; i++ ) {
            if ( array[i].equals( key ) ) {
                return i;
            }
        }

        return -1;
        
    }
    
}
