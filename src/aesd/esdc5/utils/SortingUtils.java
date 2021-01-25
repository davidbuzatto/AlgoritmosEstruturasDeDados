/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aesd.esdc5.utils;

/**
 * Métodos utilitários para os algoritmos de ordenação.
 * 
 * @author Prof. Dr. David Buzatto
 */
public abstract class SortingUtils {
    
    /**
     * Método de troca para arrays de qualquer tipo de referência.
     * 
     * @param array array com os elementos
     * @param p1 posição 1
     * @param p2 posição 2
     */
    public static void swap( Object[] array, int p1, int p2 ) {
        Object temp = array[p1];
        array[p1] = array[p2];
        array[p2] = temp;
    }
    
    /**
     * Método de troca para arrays de inteiros.
     * 
     * @param array array com os elementos
     * @param p1 posição 1
     * @param p2 posição 2
     */
    public static void swap( int[] array, int p1, int p2 ) {
        int temp = array[p1];
        array[p1] = array[p2];
        array[p2] = temp;
    }


    /**
     * Método de embaralhamento para arrays de qualquer tipo de referência.
     * 
     * @param array array a ser embaralhado
     */
    public static void shuffle( Object[] array ) {
                
        for ( int i = 0; i < array.length; i++ ) {
            swap( array, i, (int) (Math.random() * array.length) );
        }

    }
    
    /**
     * Método de embaralhamento para arrays de inteiros.
     * 
     * @param array array a ser embaralhado
     */
    public static void shuffle( int[] array ) {
                
        for ( int i = 0; i < array.length; i++ ) {
            swap( array, i, (int) (Math.random() * array.length) );
        }

    }
    
}
