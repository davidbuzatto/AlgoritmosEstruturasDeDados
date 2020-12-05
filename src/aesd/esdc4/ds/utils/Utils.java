/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdc4.ds.utils;

import aesd.esdc4.ds.interfaces.List;
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
