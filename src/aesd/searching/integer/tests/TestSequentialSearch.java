package aesd.searching.integer.tests;

import aesd.searching.integer.SequentialSearch;

/**
 * Teste de uso do algoritmo de busca sequencial (versão int[]).
 *
 * @author Prof. Dr. David Buzatto
 */
public class TestSequentialSearch {

    public static void main( String[] args ) {

        // não precisa estar ordenado, diferente da busca binária
        int[] array = { 5, 3, 8, 1, 9, 2, 7, 4, 6, 0 };

        System.out.println( "Buscando 7 (presente):     " + SequentialSearch.search( array, 7 ) );
        System.out.println( "Buscando 5 (presente):     " + SequentialSearch.search( array, 5 ) );
        System.out.println( "Buscando 0 (presente):     " + SequentialSearch.search( array, 0 ) );
        System.out.println( "Buscando 15 (ausente):     " + SequentialSearch.search( array, 15 ) );

    }

}
