/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdc5.sorting.integer;

import aesd.esdc5.utils.SortingUtils;

/**
 * Ordenação "Rápida" (Quick Sort)
 *
 * Escolha de um elemento pivô.
 *
 * Separação da sequência em duas partes:
 *     - Elementos menores que o pivô
 *     – Elementos maiores que o pivô
 *
 * Pivô não precisa mais ser movido!
 *
 * Quantas comparações são executadas?
 *     – Melhor caso: array sempre dividido ao meio
 *         * Comparações: linear-logarítmico
 *     – Pior caso: pivô como menor ou maior valor
 *         * Comparações: quadrático
 *
 * Quantidade de memória necessária?
 *     - Pior caso: linear
 *     – Melhor caso: logarítmico
 *
 * Pivô:
 *     – Embaralhar o array antes de ordenar!
 *     – Mediana de uma amostra
 *     – Posição randômica
 *
 * In-place? Sim
 *  Estável? Não
 *
 * Complexidade:
 *       Pior caso: O(n^2)
 *      Caso médio: O(n log n)
 *     Melhor caso: O(n log n)
 *
 * @author Prof. Dr. David Buzatto
 */
public class QuickSort {

    public static void sort( int[] array ) {
        quickSort( array, 0, array.length - 1 );
    }
    
   /*
    * Método recursivo do Quick Sort.
    *
    * É um algoritmo recursivo que particiona um intervalo
    * com base em um pivô escolhido e gera uma árvore
    * (árvore quick) onde as subárvores esquerda e direita
    * de um nó são formadas respectivamente pelas
    * sequências com valores menores que o pivo
    * e maiores que o pivô.
    */
   private static void quickSort( int[] array, int start, int end ) {

       // meio é a posição do pivô escolhido
       int middle;

       // se inicio for menor que fim indica
       // que ainda existem intervalos com
       // dois ou mais itens, sendo assim...
       if ( start < end ) {

           // obtém o meio (posição do pivô) após realizar
           // o particionamento. após a execução desta função
           // os elementos à esquerda do pivô são menores que ele
           // e os elementos a direita são maiores que ele.
           middle = partition( array, start, end );

           // cria a subárvore esquerda (com elementos menores que o pivô)
           quickSort( array, start, middle-1 );

           // cria a subárvore direita (com elementos maiores que o pivô).
           quickSort( array, middle+1, end );

       }

   }
   
   /*
    * Algoritmo de particionamento.
    *
    * Escolhe o pivô inicialmente (o primeiro elemento)
    * e reorganiza o array de modo que os elementos
    * à esquerda do pivô sejam menores que o mesmo no
    * subconjunto em questão e os elementos a sua direita
    * sejam maiores. Devolve a posição em que o pivô se encontra.
    */
   private static int partition( int[] array, int start, int end ) {

       // marca o início do intervalo
       // o valor da posição inicial é o pivô
       int i = start;

       // marca a posição após o fim do intervalo
       int j = end + 1;

       // reposiciona os elementos com base
       // no valor do pivô.
       while( true ) {

           // se o valor da posição do lado direito
           // de i for menor que o valor da posição
           // de início (pivô), continua posicionado i
           // para a direita, ou seja, faz com que i
           // chegue ao limite + 1 que existe posições
           // menores que o pivô.
           while ( array[++i] < array[start] ) {

               // se chegou no fim, pára
               if( i == end ) {
                   break;
               }

           }

           // se o valor da posição do lado esquerdo
           // de j for maior que o valor da posição
           // de início (pivô), continua posicionado j
           // para a esquerda, ou seja, faz com que j
           // chegue ao limite - 1 que existe posições
           // maiores que o pivô.
           while ( array[--j] > array[start] ) {

               // se chegou ao início, pára
               if ( j == start ) {
                   break;
               }

           }

           // se i for maior ou igual a j, pára o algoritmo,
           // pois tanto o limite da parte menor (i) quanto o
           // da parte maior (j) foram encontrados, sendo assim
           // a posição do pivô já foi encontrada
           if ( i >= j ) {
               break;
           }

           // troca i por j, posicionando o valor menor
           // que está em j na posição de i e vice versa
           SortingUtils.swap( array, i, j );

       }

       // neste ponto, todos os valores à esquerda de i
       // (com exercão do pivô e de i) são menores que o pivô
       // enquanto todos os valores à direita de j (com exceção de j)
       // são maiores que o pivô


       // troca o valor da posição inicial (valor do pivô)
       // com o valor de j (que é menor que o pivô), fazendo
       // com que o o pivô seja posicionado corretamente
       // e que o início receba o valor menor que o pivô
       SortingUtils.swap( array, start, j );

       // j agora contém o valor do pivô, que divide
       // a sequência menor e a sequencia maior e por consequência
       // j é a posição do pivô
       return j;

   }
    
}
