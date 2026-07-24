
package aesd.searching.integer;

/**
 * Algoritmo de busca sequencial.
 *
 * Não exige que o array esteja ordenado (diferente da busca binária), mas
 * por isso é O(n): no pior caso, precisa examinar todos os elementos.
 * Preferível à busca binária quando o array não está ordenado ou quando
 * poucas buscas serão feitas (não compensa pagar o custo de ordenar antes).
 *
 * @author Prof. Dr. David Buzatto
 */
public class SequentialSearch {

    /**
     * Busca key em array, percorrendo-o sequencialmente.
     *
     * @param array O array onde a busca será feita.
     * @param key A chave a ser buscada.
     * @return O índice de key em array, ou -1 se não encontrada.
     */
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
