package aesd.ds.implementations.nonlinear.uf.tests;

import aesd.ds.implementations.nonlinear.uf.QuickUnionUF;
import aesd.ds.implementations.nonlinear.uf.UF;

/**
 * Teste de uso da estrutura union-find com união rápida (QuickUnionUF).
 *
 * @author Prof. Dr. David Buzatto
 */
public class TestQuickUnionUF {

    public static void main( String[] args ) {

        UF uf = new QuickUnionUF( 10 );

        System.out.println( "Componentes iniciais: " + uf.count() );

        uf.union( 4, 3 );
        uf.union( 3, 8 );
        uf.union( 6, 5 );
        uf.union( 9, 4 );
        uf.union( 2, 1 );

        System.out.println( "Componentes após as uniões: " + uf.count() );

        System.out.println( "4 e 8 conectados? " + uf.connected( 4, 8 ) );  // true
        System.out.println( "5 e 0 conectados? " + uf.connected( 5, 0 ) );  // false
        System.out.println( "find(9) == find(3)? " + ( uf.find( 9 ) == uf.find( 3 ) ) ); // true

        uf.union( 5, 0 );
        uf.union( 7, 2 );
        uf.union( 6, 1 );

        System.out.println( "\nComponentes após mais uniões: " + uf.count() );
        System.out.println( "5 e 0 conectados? " + uf.connected( 5, 0 ) );  // true

    }

}
