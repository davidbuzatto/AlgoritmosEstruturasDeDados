/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdi3.sorting.integer;

import aesd.esdi3.utils.SortingUtils;

/**
 * Ordenação por seleção (Selection Sort)
 *
 * Divisão dos dados em duas sequências:
 * ordenada e não-ordenada.
 *
 * Iteração: procurar pelo menor elemento da
 * sequência não-ordenada e concatená-lo na
 * sequência ordenada.
 *
 * Os valores dos dados não interferem na
 * execução do algoritmo.
 *
 * Crescimento do número de trocas em relação
 * ao tamanho de entrada: linear.
 *
 * Crescimento do número de comparações em
 * relação ao tamanho de entrada: quadrático.
 *
 * Crescimento do uso de memória em relação ao
 * tamanho da entrada: constante.
 *
 * In-place? Sim
 *  Estável? Não
 *
 * Complexidade:
 *       Pior caso: O(n^2)
 *      Caso médio: O(n^2)
 *     Melhor caso: O(n^2)
 *
 * @author Prof. Dr. David Buzatto
 */
public class SelectionSort {

    public static void sort( int[] array ) {
        
        int length = array.length;
        
        // índice da sequência ordenada
        // controla a iteração
        int i;

        // índice da sequência não ordenada
        // controla a busca pelo array mínimo
        int j;

        // índice do menor elemento da sequência não ordenada
        int min;

        // percorre todo o array
        for ( i = 0; i < length; i++ ) {

            // o mínimo é o item atual
            // é importante frisar que o lado
            // esquerdo de i é a parte ordenada do array
            min = i;

            // percorre o array na parte não-ordenada
            for ( j = i+1; j < length; j++ ) {

                // encontra o mínimo da parte não-ordenada
                if ( array[j] < array[min] ) {
                    min = j;
                }

            }

            // troca o mínimo com o item atual
            // aumentando assim a parte ordenada
            SortingUtils.swap( array, i, min );

        }
    
    }
    
}
