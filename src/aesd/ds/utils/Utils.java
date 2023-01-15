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
    
    @SuppressWarnings( "unchecked" )
    public static void shuffle( List list ) {
        
        Random r = new Random();
        int listSize = list.getSize();
        
        for ( int i = 0; i < listSize; i++ ) {
            
            int p = r.nextInt( listSize );
            Object o1 = list.get( i );
            Object o2 = list.get( p );
            
            list.set( i, o2 );
            list.set( p, o1 );
            
        }
        
    }
    
}
