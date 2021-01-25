/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdc5.sorting.integer;

import aesd.esdc5.utils.SortingUtils;

/**
 * Esta classe contém algumas implementações do algoritmo bubble sort,
 * demonstrando pequenas modificações com o objetivo de tornar sua execução
 * mais rápida.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class BubbleSortEvolution {
    
    /*
     * Ordenação por borbulhamento - versão 1.
     *
     * Complexidade:
     *     Melhor caso: O(n^2)
     *      Caso médio: O(n^2)
     *       Pior caso: O(n^2)
     */
    public static void bubbleSortV1( int[] array ) {

       int length = array.length;
       
       for ( int i = 0; i < length; i++ ) {
           for ( int j = 0; j < length - 1; j++ ) {
               if ( array[j] > array[j+1] ) {
                   SortingUtils.swap( array, j, j+1 );
               }
           }
       }

    }

    /*
     * Ordenação por borbulhamento - versão 2.
     * 
     * A última posição do ciclo anterior já está na posição correta.
     *
     * Complexidade:
     *     Melhor caso: O(n^2)
     *      Caso médio: O(n^2)
     *       Pior caso: O(n^2)
     */
    public static void bubbleSortV2( int[] array ) {

       int length = array.length;

       for ( int i = 0; i < length; i++ ) {
           for ( int j = 0; j < length - 1 - i; j++ ) {
               if ( array[j] > array[j+1] ) { 
                   SortingUtils.swap( array, j, j+1 );
               }
           }
       }

    }

    /*
     * Ordenação por borbulhamento - versão 3.
     *
     * A última posição do ciclo anterior já está na posição correta.
     * Se não houver troca em um ciclo o algoritmo para.
     *
     * Complexidade:
     *     Melhor caso: O(n)
     *      Caso médio: O(n^2)
     *       Pior caso: O(n^2)
     */
    public static void bubbleSortV3( int[] array ) {

       boolean swaped;
       int length = array.length;

       for ( int i = 0; i < length; i++ ) {

           swaped = false;

           for ( int j = 0; j < length - 1 - i; j++ ) {
               if ( array[j] > array[j+1] ) {
                   SortingUtils.swap( array, j, j+1 );
                   swaped = true;
               }
           }

           if ( !swaped ) {
               return;
           }

       }

    }

    /*
     * Ordenação por borbulhamento - versão 4.
     *
     * A última posição do ciclo anterior já está na posição correta.
     * Se não houver troca em um ciclo o algoritmo para, pois o
     * limite da busca é controlado.
     *
     * Complexidade:
     *     Melhor caso: O(n)
     *      Caso médio: O(n^2)
     *       Pior caso: O(n^2)
     */
    public static void bubbleSortV4( int[] array ) {

       int lastSwapPos;
       int limit = array.length - 1;

       while ( true ) {

           lastSwapPos = 0;

           for ( int j = 0; j < limit; j++ ) {

               if ( array[j] > array[j+1] ) {
                   SortingUtils.swap( array, j, j+1 );
                   lastSwapPos = j;
               }

           }

           if ( lastSwapPos == 0 ) {
               return;
           }

           limit = lastSwapPos;

       }

    }

}
