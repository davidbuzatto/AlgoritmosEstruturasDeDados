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
 * Esta implementação usa a sequência de Knuth (h = 3h + 1), para a qual o
 * pior caso conhecido é O(n^1.5) — melhor que os O(n^2) dos algoritmos
 * quadráticos simples (bubble/insertion/selection sort), mas não tão bom
 * quanto o O(n lg n) garantido por merge sort/heap sort.
 *
 * @author Prof. Dr. David Buzatto
 */
public class ShellSort {

    public static <Type extends Comparable<Type>> void sort( Type[] array ) {
        
        // tamanho do array
        int n = array.length;
        
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
        while( h < n / 3 ) {
            // sequência de Knuth: gera gaps que não são múltiplos uns dos
            // outros, ao contrário de uma sequência de potências de 2, cujos
            // gaps pares nunca comparam elementos de paridade diferente até
            // o h final = 1, degradando o desempenho
            h = 3 * h + 1; // 1, 4, 13, 40, 121...
        }

        // enquanto o espaçamento for maior ou igual
        // a 1 (o último espaçamento válido)
        while ( h >= 1 ) {

            // iniciando do espaçamento atual e andando
            // item por item até a última posição
            for( i = h; i < n; i++ ) {

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
