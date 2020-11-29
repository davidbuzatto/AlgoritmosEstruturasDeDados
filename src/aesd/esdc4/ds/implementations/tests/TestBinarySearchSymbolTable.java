/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdc4.ds.implementations.tests;

import aesd.esdc4.ds.implementations.BinarySearchSymbolTable;
import aesd.esdc4.ds.implementations.ResizingArrayList;
import aesd.esdc4.ds.interfaces.List;
import aesd.esdc4.ds.utils.Utils;
import aesd.esdc4.ds.interfaces.SymbolTable.Entry;

/**
 * Teste de uso da tabela de símbolos usando busca binária.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class TestBinarySearchSymbolTable {
    
    public static void main( String[] args ) {
        
        BinarySearchSymbolTable<Integer, String> bsST = new BinarySearchSymbolTable<>();
        
        bsST.put( 6, "João" );
        System.out.println( bsST );
        bsST.put( 8, "Maria" );
        System.out.println( bsST );
        bsST.put( 7, "David" );
        System.out.println( bsST );
        bsST.put( 4, "Fernanda" );
        System.out.println( bsST );
        bsST.put( 5, "Aurora" );
        System.out.println( bsST );
        bsST.put( 9, "Marcelo" );
        System.out.println( bsST );
        bsST.put( 3, "Ronaldinho" );
        System.out.println( bsST );
        bsST.put( 9, "Matilda" );
        System.out.println( bsST );
        bsST.put( 3, null );
        System.out.println( bsST );
        
        System.out.println( "Dados da tabela de símbolos através do iterador:" );
        for ( Entry<Integer, String> e : bsST ) {
            System.out.print( e.getKey() );
            System.out.print( " " );
        }
        
        System.out.println( "\n" );
        
        // consultas
        System.out.println( "\n----- Consultas -----" );
        List<Entry<Integer, String>> elementos = new ResizingArrayList<>();
        for ( Entry<Integer, String> e : bsST ) {
            elementos.add( e );
        }
        elementos.add( new Entry<>( 15, "Snoopy" ) );
        elementos.add( new Entry<>( 19, "Papai Noel" ) );
        elementos.add( new Entry<>( -4, "Garfield" ) );
        Utils.shuffle( elementos );
        for ( Entry<Integer, String> e : elementos ) {
            System.out.printf( "%4d está na tabela de símbolos? => %s\n", e.getKey(),
                    bsST.contains( e.getKey() ) ? "SIM" : "NÃO" );
        }
        
        System.out.println( "\n----- Remoção -----" );
        System.out.println( bsST );
        for ( Entry<Integer, String> e : elementos ) {
            System.out.printf( "Removendo o par chave/valor com chave %d...\n", e.getKey() );
            bsST.delete( e.getKey() );
            System.out.println( bsST );
        }
        
    }
    
}
