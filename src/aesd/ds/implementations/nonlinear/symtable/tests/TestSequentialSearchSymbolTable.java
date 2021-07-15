/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.ds.implementations.nonlinear.symtable.tests;

import aesd.ds.implementations.linear.ResizingArrayList;
import aesd.ds.implementations.nonlinear.symtable.SequentialSearchSymbolTable;
import aesd.ds.interfaces.List;
import aesd.ds.utils.Utils;
import aesd.ds.interfaces.SymbolTable.Entry;

/**
 * Teste de uso da tabela de símbolos usando busca sequencial.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class TestSequentialSearchSymbolTable {
    
    public static void main( String[] args ) {
        
        SequentialSearchSymbolTable<Integer, String> ssST = new SequentialSearchSymbolTable<>();
        
        ssST.put( 6, "João" );
        System.out.println( ssST );
        ssST.put( 8, "Maria" );
        System.out.println( ssST );
        ssST.put( 7, "David" );
        System.out.println( ssST );
        ssST.put( 4, "Fernanda" );
        System.out.println( ssST );
        ssST.put( 5, "Aurora" );
        System.out.println( ssST );
        ssST.put( 9, "Marcelo" );
        System.out.println( ssST );
        ssST.put( 3, "Ronaldinho" );
        System.out.println( ssST );
        ssST.put( 9, "Matilda" );
        System.out.println( ssST );
        ssST.put( 3, null );
        System.out.println( ssST );
        
        System.out.println( "Dados da tabela de símbolos através do iterador:" );
        for ( Entry<Integer, String> e : ssST ) {
            System.out.print( e.getKey() );
            System.out.print( " " );
        }
        
        System.out.println( "\n" );
        
        // consultas
        System.out.println( "\n----- Consultas -----" );
        List<Entry<Integer, String>> elementos = new ResizingArrayList<>();
        for ( Entry<Integer, String> e : ssST ) {
            elementos.add( e );
        }
        elementos.add( new Entry<>( 15, "Snoopy" ) );
        elementos.add( new Entry<>( 19, "Papai Noel" ) );
        elementos.add( new Entry<>( -4, "Garfield" ) );
        Utils.shuffle( elementos );
        for ( Entry<Integer, String> e : elementos ) {
            System.out.printf( "%4d está na tabela de símbolos? => %s\n", e.getKey(),
                    ssST.contains( e.getKey() ) ? "SIM" : "NÃO" );
        }
        
        System.out.println( "\n----- Remoção -----" );
        System.out.println( ssST );
        for ( Entry<Integer, String> e : elementos ) {
            System.out.printf( "Removendo o par chave/valor com chave %d...\n", e.getKey() );
            ssST.delete( e.getKey() );
            System.out.println( ssST );
        }
        
    }
    
}
