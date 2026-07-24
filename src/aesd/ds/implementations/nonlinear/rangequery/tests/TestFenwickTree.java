package aesd.ds.implementations.nonlinear.rangequery.tests;

import aesd.ds.implementations.nonlinear.rangequery.FenwickTree;

/**
 * Teste de uso da Fenwick Tree (Binary Indexed Tree).
 *
 * @author Prof. Dr. David Buzatto
 */
public class TestFenwickTree {

    public static void main( String[] args ) {

        FenwickTree ft = new FenwickTree( new long[]{ 1, 2, 3, 4, 5 } );

        System.out.println( "Valores:      " + ft );
        System.out.println( "prefixSum(3): " + ft.prefixSum( 3 ) );   // 1+2+3 = 6
        System.out.println( "rangeSum(2,4):" + ft.rangeSum( 2, 4 ) ); // 2+3+4 = 9

        ft.update( 2, 10 ); // posição 2 (valor 2) passa a valer 12
        System.out.println( "\nApós update(2, 10):" );
        System.out.println( "Valores:      " + ft );
        System.out.println( "prefixSum(3): " + ft.prefixSum( 3 ) );   // 1+12+3 = 16

        ft.set( 5, 100 ); // posição 5 passa a valer exatamente 100
        System.out.println( "\nApós set(5, 100):" );
        System.out.println( "Valores:      " + ft );
        System.out.println( "rangeSum(4,5):" + ft.rangeSum( 4, 5 ) ); // 4+100 = 104

        //ft.update( 6, 1 ); // <- posição fora do intervalo [1, 5]!

    }

}
