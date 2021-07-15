/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.sorting.generic;

import aesd.sorting.utils.SortingUtils;

/**
 * Ordenação Shell (Shell Sort)
 *
 * Percorrer a sequência e mover os elementos
 * mais de uma posição por comparação (h-sorting)
 *
 * Decrementar o valor de h e repetir o processo.
 *
 * Os valores dos dados interferem na execução
 * do algoritmo.
 *
 * A sequência de espaçamento interferem na
 * execução do algoritmo.
 *
 * Crescimento do uso de memória em relação ao
 * tamanho da entrada: constante
 *
 * In-place? Sim
 *  Estável? Não
 *
 * Complexidade:
 *       Pior caso: ? => depende da sequência!
 *      Caso médio: ? => depende da sequência!
 *     Melhor caso: ? => depende da sequência!
 *
 * @author Prof. Dr. David Buzatto
 */
public class ShellSort {

    public static <Type extends Comparable<Type>> void sort( Type[] array ) {
        
        int length = array.length;
        
        // controla a iteração dentro de um espaçamento
        // (número de sequências de comparação)
        int i;

        // controla a iteração dentro de uma sequência
        // de comparação
        int j;

        // controla o espaçamento das sequências de
        // comparações
        int h = 1;

        // calculando o espaçamento máximo
        // relativo ao tamanho do array
        while( h < length / 3 ) {
            h = 3 * h + 1; // 1, 4, 13, 40, 121...
        }

        // enquanto o espaçamento for maior ou igual
        // a 1 (o último espaçamento válido)
        while ( h >= 1 ) {

            // iniciando do espaçamento atual e andando
            // item por item até a última posição
            for( i = h; i < length; i++ ) {

                // j marca a posição base para a comparação
                // do "arco" do algoritmo (h-sorting)
                j = i;

                // se o j indicar um valor maior que o espaçamento,
                // ou seja, ele pode "andar" para trás sem
                // estourar a busca antes do início
                // e o item à esquerda de j (distância de 1 espaçamento)
                // for maior que o item em j, eles estão em posições
                // erradas, sendo assim...
                while( j >= h && array[j-h].compareTo( array[j] ) > 0 ) {

                    // troca os elementos
                    SortingUtils.swap( array, j-h, j );

                    // prepara j para comparar outro arco
                    // dando continuidade ao h-sorting
                    j = j - h;

                }

            }

            // faz o processo inverso da geração
            // do espaçamento, ou seja, obtém
            // os espaçamento anterior da série
            h = h / 3;

        }
    
    }
    
}
