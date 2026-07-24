package aesd.ds.implementations.nonlinear.rangequery.tests;

import aesd.ds.implementations.nonlinear.rangequery.SegmentTree;

/**
 * Teste de uso da Segment Tree (árvore de segmentos).
 *
 * @author Prof. Dr. David Buzatto
 */
public class TestSegmentTree {

    public static void main( String[] args ) {

        SegmentTree st = new SegmentTree( new long[]{ 1, 2, 3, 4, 5 } );

        System.out.println( "Valores:  " + st );
        System.out.println( "query(1,3): " + st.query( 1, 3 ) ); // 2+3+4 = 9
        System.out.println( "query(0,4): " + st.query( 0, 4 ) ); // 1+2+3+4+5 = 15

        st.update( 2, 100 ); // posição 2 (valor 3) passa a valer 100
        System.out.println( "\nApós update(2, 100):" );
        System.out.println( "Valores:  " + st );
        System.out.println( "query(1,3): " + st.query( 1, 3 ) ); // 2+100+4 = 106

        //st.query( 0, 5 ); // <- intervalo fora do array!

    }

}
