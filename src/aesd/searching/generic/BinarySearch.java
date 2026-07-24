
package aesd.searching.generic;

/**
 * Algoritmo de busca binária iterativo e recursivo.
 *
 * Pré-condição: o array precisa estar ordenado. A cada comparação, o
 * algoritmo descarta metade do intervalo restante, o que garante
 * complexidade O(log n) — bem mais rápido que a busca sequencial O(n) à
 * custa de exigir um array ordenado. As duas versões (iterativa, comentada
 * abaixo, e recursiva, efetivamente usada) são mantidas lado a lado para
 * fins de comparação didática.
 *
 * @author Prof. Dr. David Buzatto
 */
public class BinarySearch {

    /**
     * Busca key em array, que precisa estar ordenado.
     *
     * @param array O array ordenado onde a busca será feita.
     * @param key A chave a ser buscada.
     * @return O índice de key em array, ou -1 se não encontrada.
     */
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
    
    /**
     * Algoritmo recursivo da busca binária.
     *
     * @param array O array ordenado onde a busca será feita.
     * @param key A chave a ser buscada.
     * @param start Início do intervalo de busca.
     * @param end Fim do intervalo de busca.
     * @return O índice de key em array, ou -1 se não encontrada.
     */
    public static <Type extends Comparable<Type>> int searchR(
            Type[] array, Type key, int start, int end ) {

        if ( start <= end ) {

            int middle = ( start + end ) / 2;
            int comp = key.compareTo( array[middle] );

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
