package aesd.searching.integer.tests;

import aesd.searching.integer.BinarySearch;

/**
 * Teste de uso do algoritmo de busca binária (versão int[]).
 *
 * @author Prof. Dr. David Buzatto
 */
public class TestBinarySearch {

    public static void main( String[] args ) {

        // já ordenado, pré-condição da busca binária
        int[] array = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };

        System.out.println( "Buscando 7 (presente):     " + BinarySearch.search( array, 7 ) );
        System.out.println( "Buscando 0 (presente):     " + BinarySearch.search( array, 0 ) );
        System.out.println( "Buscando 9 (presente):     " + BinarySearch.search( array, 9 ) );
        System.out.println( "Buscando 15 (ausente):     " + BinarySearch.search( array, 15 ) );

    }

}
