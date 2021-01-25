/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdc5.sorting.generic;

import aesd.esdc5.utils.SortingUtils;

/**
 * Quick Sort 3-Way.
 *
 * Neste algoritmo segue-se a mesma ideia do Quick Sort original,
 * entretanto o pivô é uma faixa de dados iguais e não somente
 * um único número. Este algoritmo tenta resolver um dos casos onde
 * o Quick Sort tradicional é ineficiente, ou seja, o caso onde
 * existem diversos valores iguais dentro do array.
 *
 * In-place? Sim
 *  Estável? Não
 *
 * Complexidade:
 *       Pior caso: O(n^2)
 *      Caso médio: O(n log n)
 *     Melhor caso: O(n)
 *
 * @author Prof. Dr. David Buzatto
 */
public class QuickSort3Way {

    public static <Tipo extends Comparable<Tipo>> void sort( Tipo[] array ) {
        quickSort3( array, 0, array.length - 1 );
    }
    
   /*
    * Algoritmo recursivo do Quick Sort 3-Way.
    *
    * Este algoritmo divide a sequencia delimitada por
    * inicio e fim e três faixas. A faixa à esquerda
    * contém elementos menores que a faixa central.
    * A faixa à direita, contém elementos maiores que
    * a faixa central. A faixa central é composta por um
    * ou mais elementos de valor igual.
    *
    * ---------------------------------------------------------
    * |   fx1: menores   |   fx2: iguais   |   fx3: maiores   |
    * ---------------------------------------------------------
    *
    */
   private static <Type extends Comparable<Type>> void quickSort3( Type[] array, int start, int end ) {
       
       // se o início for menor que o fim
       // executa o algoritmo.
       if ( start < end ) {

           // o controle dos menores inicia no início
           // e vai controlar o limite à direita
           // da faixa dos menores
           int min = start;

           // o controle dos maiores inicia no fim
           // e vai controlar o limite à esquerda
           // da faixa dos maiores
           int max = end;

           // i inicia à direita do início (do menor)
           int i = start + 1;

           // valor base (valor da faixa do meio)
           // é o valor da "faixa pivô"
           Type v = array[start];

           // faz a varredura do início ao fim, diminuindo o
           // lado esquerdo e o lado direito e posicionando os valores
           while ( i <= max ) {

               int comp = array[i].compareTo( v );
               
               // se o valor de i for menor que o valor base
               // indica que é necessário jogar o valor de i
               // para a esquerda, montando a faixa menor
               if ( comp < 0 ) {

                   // troca o valor do menor com o valor de i
                   // e desloca os dois controles para a direita
                   // este passo joga os valores menores para
                   // a esquerda
                   SortingUtils.swap( array, min++, i++ );

                   // se o valor de i for maior que o valor base
                   // indica que é necessário jogar o valor de i
                   // para a direita, montando a faixa maior
               } else if ( comp > 0 ) {

                   // troca o valor do maior com o valor de i
                   // e desloca o menor para a esquerda
                   // este passo joga os valores maiores para
                   // a direita
                   SortingUtils.swap( array, i, max-- );

                   // se i for o mesmo valor do valor base, desloca
                   // i para a direita, procurando por um valor
                   // diferente. O intervalo entre menor e i
                   // compreende a faixa do meio.
               } else {
                   i++;
               }

           }

           // neste ponto, a faixa central está formada e é necessário
           // processar as faixas esquerda e direita

           // processa a faixa esquerda que contém os elementos
           // menores que a faixa central
           quickSort3( array, start, min - 1 );

           // processa a faixa direita que contém os elementos
           // maiores que a faixa central
           quickSort3( array, max + 1, end );

       }

   }
    
}
