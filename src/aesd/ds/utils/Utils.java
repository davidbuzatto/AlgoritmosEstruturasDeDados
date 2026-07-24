package aesd.ds.utils;

import aesd.ds.interfaces.List;
import java.util.Random;

/**
 * Classe que contém métodos estáticos utilitários utilizados nas implementações
 * das estruturas de dados.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class Utils {
    
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
    @SuppressWarnings( "unchecked" )
    public static void shuffle( List list ) {

        Random r = new Random();
        int listSize = list.getSize();

        for ( int i = 0; i < listSize; i++ ) {

            int p = i + r.nextInt( listSize - i );
            Object o1 = list.get( i );
            Object o2 = list.get( p );

            list.set( i, o2 );
            list.set( p, o1 );

        }

    }
    
}
