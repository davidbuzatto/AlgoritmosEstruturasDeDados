package aesd.ds.utils.tests;

import aesd.ds.implementations.linear.ResizingArrayList;
import aesd.ds.interfaces.List;
import aesd.ds.utils.Utils;

/**
 * Teste de uso dos métodos utilitários da classe Utils.
 *
 * @author Prof. Dr. David Buzatto
 */
public class TestUtils {

    public static void main( String[] args ) {

        List<Integer> list = new ResizingArrayList<>();

        for ( int i = 1; i <= 10; i++ ) {
            list.add( i );
        }

        System.out.println( "Antes do embaralhamento:" );
        System.out.println( list );

        Utils.shuffle( list );

        System.out.println( "\nDepois do embaralhamento:" );
        System.out.println( list );

    }

}
