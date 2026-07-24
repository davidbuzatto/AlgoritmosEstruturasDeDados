package aesd.algorithms.dp;

import aesd.ds.implementations.nonlinear.symtable.RedBlackTree;
import aesd.ds.interfaces.SymbolTable;

/**
 * Métodos estáticos para o cálculo dos termos da série de Fibonnaci com
 * recursão e programação dinâmica.
 *
 * Quatro versões, cada uma resolvendo uma limitação da anterior:
 *     - recursiveFibonacci: recursão pura, sem reaproveitamento de
 *       subproblemas. Recalcula os mesmos termos repetidas vezes, custando
 *       O(2^n).
 *     - DPBottomUpFibonacci: preenche uma tabela local do menor termo para
 *       o maior, sem recursão. Cada chamada recalcula tudo do zero, mas
 *       custa apenas O(n).
 *     - DPBottomUpFibonacciWithCache: mesma ideia bottom-up, mas guarda os
 *       termos já calculados numa tabela de símbolos persistida entre
 *       chamadas, então uma chamada só precisa calcular os termos que
 *       ainda não estavam na tabela.
 *     - DPTopDownFibonacci: essa sim é a versão top-down clássica —
 *       recursiva, mas consultando/preenchendo uma tabela de memoização a
 *       cada chamada recursiva, evitando os recálculos exponenciais da
 *       versão puramente recursiva.
 *
 * @author Prof. Dr. David Buzatto
 */
public class FibonacciProblem {

    // armazenamento persistido dos termos da série gerados por
    // DPBottomUpFibonacciWithCache
    private static SymbolTable<Integer, Long> fLookup;

    /**
     * Versão recursiva.
     *
     * @param n Termo da série de Fibonnaci.
     * @return O valor do termo da série.
     */
    public static long recursiveFibonacci( int n ) {

        if ( n < 0 ) {
            throw new IllegalArgumentException( "n must be greater than or equal to 0" );
        }

        if ( n == 0 || n == 1 ) {
            return 1;
        } else {
            return recursiveFibonacci( n - 2 ) + recursiveFibonacci( n - 1 );
        }

    }

    /**
     * Versão com programação dinâmica bottom-up.
     * Armazenamento dos resultados do subproblema localmente.
     *
     * @param n Termo da série de Fibonnaci.
     * @return O valor do termo da série.
     */
    public static long DPBottomUpFibonacci( int n ) {

        if ( n < 0 ) {
            throw new IllegalArgumentException( "n must be greater than or equal to 0" );
        }

        long[] f = new long[n+1];

        f[0] = 1;

        if ( n > 0 ) {
            f[1] = 1;
        }

        for ( int i = 2; i <= n; i++ ) {
            f[i] = f[i-2] + f[i-1];
        }

        return f[n];

    }

    /**
     * Versão com programação dinâmica bottom-up que guarda os resultados
     * dos subproblemas numa tabela de símbolos persistida entre chamadas
     * (ao contrário de DPBottomUpFibonacci, que recalcula tudo do zero a
     * cada chamada). Ainda preenche a tabela do menor termo para o maior
     * (por isso continua sendo bottom-up), só que incrementalmente: uma
     * chamada com um n menor que o maior já calculado antes é O(1); uma
     * chamada com um n maior só calcula os termos que ainda faltam.
     *
     * @param n Termo da série de Fibonnaci.
     * @return O valor do termo da série.
     */
    public static long DPBottomUpFibonacciWithCache( int n ) {

        if ( n < 0 ) {
            throw new IllegalArgumentException( "n must be greater than or equal to 0" );
        }

        if ( fLookup == null ) {
            fLookup = new RedBlackTree<>();
            fLookup.put( 0, 1L );
            fLookup.put( 1, 1L );
        }

        for ( int i = fLookup.getSize(); i <= n; i++ ) {
            fLookup.put( i, fLookup.get( i-2 ) + fLookup.get( i-1 ) );
        }

        return fLookup.get( n );

    }

    /**
     * Versão com programação dinâmica top-down: recursiva, mas consultando
     * uma tabela de memoização antes de descer mais na recursão. Se o termo
     * já foi calculado antes (dentro da mesma chamada), é devolvido
     * diretamente, sem refazer as chamadas recursivas que o gerariam —
     * o que evita o custo exponencial da versão puramente recursiva.
     *
     * @param n Termo da série de Fibonnaci.
     * @return O valor do termo da série.
     */
    public static long DPTopDownFibonacci( int n ) {

        if ( n < 0 ) {
            throw new IllegalArgumentException( "n must be greater than or equal to 0" );
        }

        return DPTopDownFibonacci( n, new long[n+1] );

    }

    // método auxiliar recursivo que consulta/preenche a tabela de
    // memoização recebida (memo[i] == 0 indica que o termo i ainda não foi
    // calculado nesta chamada, já que 0 não é um termo válido da série)
    private static long DPTopDownFibonacci( int n, long[] memo ) {

        if ( n == 0 || n == 1 ) {
            return 1;
        }

        if ( memo[n] == 0 ) {
            memo[n] = DPTopDownFibonacci( n - 2, memo ) + DPTopDownFibonacci( n - 1, memo );
        }

        return memo[n];

    }

}
