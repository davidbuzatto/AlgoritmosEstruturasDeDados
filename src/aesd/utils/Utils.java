
package aesd.utils;

import aesd.ds.interfaces.List;
import java.util.Random;

/**
 * Métodos utilitários para algoritmos e estruturas de dados implementados.
 * 
 * @author Prof. Dr. David Buzatto
 */
public abstract class Utils {
    
    /**
     * Método de troca para arrays de qualquer tipo de referência.
     * 
     * @param array array com os elementos
     * @param p1 posição 1
     * @param p2 posição 2
     */
    public static void swap( Object[] array, int p1, int p2 ) {
        Object temp = array[p1];
        array[p1] = array[p2];
        array[p2] = temp;
    }
    
    /**
     * Método de troca para arrays de inteiros.
     * 
     * @param array array com os elementos
     * @param p1 posição 1
     * @param p2 posição 2
     */
    public static void swap( int[] array, int p1, int p2 ) {
        int temp = array[p1];
        array[p1] = array[p2];
        array[p2] = temp;
    }


    /**
     * Método de embaralhamento para arrays de qualquer tipo de referência,
     * usando o algoritmo de Fisher-Yates (também conhecido como shuffle de
     * Knuth): a cada posição i, troca o elemento ali com o de uma posição
     * sorteada dentro do intervalo ainda não embaralhado [i, array.length).
     * É esse intervalo que encolhe a cada iteração que garante que todas as n!
     * permutações possíveis sejam igualmente prováveis.
     *
     * @param array array a ser embaralhado
     */
    public static void shuffle( Object[] array ) {

        for ( int i = 0; i < array.length; i++ ) {
            swap( array, i, i + (int) (Math.random() * (array.length - i)) );
        }

    }

    /**
     * Método de embaralhamento para arrays de inteiros, usando o algoritmo
     * de Fisher-Yates (também conhecido como shuffle de Knuth): a cada posição i, 
     * troca o elemento ali com o de uma posição sorteada dentro do intervalo
     * ainda não embaralhado [i, array.length). É esse intervalo que encolhe a
     * cada iteração que garante que todas as n! permutações possíveis
     * sejam igualmente prováveis.
     *
     * @param array array a ser embaralhado
     */
    public static void shuffle( int[] array ) {

        for ( int i = 0; i < array.length; i++ ) {
            swap( array, i, i + (int) (Math.random() * (array.length - i)) );
        }

    }
    
    /**
     * Embaralha os elementos de uma lista usando o algoritmo de
     * Fisher-Yates (também conhecido como shuffle de Knuth): percorre a
     * lista da esquerda para a direita e, a cada posição i, troca o
     * elemento ali com o de uma posição sorteada dentro do intervalo ainda
     * não embaralhado [i, listSize). Sortear sempre dentro desse intervalo
     * que encolhe é o que garante que todas as n! permutações possíveis
     * sejam igualmente prováveis (sortear em [0, listSize) a cada iteração,
     * incluindo posições já fixadas, não teria essa garantia).
     *
     * @param list A lista cujos elementos serão embaralhados.
     */
    public static <Type> void shuffle( List<Type> list ) {

        Random r = new Random();
        int listSize = list.getSize();

        for ( int i = 0; i < listSize; i++ ) {

            int p = i + r.nextInt( listSize - i );
            Type o1 = list.get( i );
            Type o2 = list.get( p );

            list.set( i, o2 );
            list.set( p, o1 );

        }

    }
    
}
