package aesd.ds.implementations.nonlinear.symtable.tests;

import aesd.ds.implementations.nonlinear.symtable.LinearProbingHashTable;
import aesd.ds.implementations.linear.ResizingArrayList;
import aesd.ds.interfaces.List;
import aesd.ds.utils.Utils;
import aesd.ds.interfaces.SymbolTable.Entry;

/**
 * Teste de uso da tabela de dispersão com sondagem linear.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class TestLinearProbingHashTable {
    
    public static void main( String[] args ) {
        
        LinearProbingHashTable<Integer, String> lph = new LinearProbingHashTable<>();
        
        lph.put( 6, "João" );
        System.out.println( lph );
        lph.put( 8, "Maria" );
        System.out.println( lph );
        lph.put( 7, "David" );
        System.out.println( lph );
        lph.put( 4, "Fernanda" );
        System.out.println( lph );
        lph.put( 5, "Aurora" );
        System.out.println( lph );
        lph.put( 9, "Marcelo" );
        System.out.println( lph );
        lph.put( 3, "Ronaldinho" );
        System.out.println( lph );
        lph.put( 9, "Matilda" );
        System.out.println( lph );
        lph.put( 3, null );
        System.out.println( lph );
        
        System.out.println( "Dados da tabela de dispersão através do iterador:" );
        for ( Entry<Integer, String> e : lph ) {
            System.out.print( e.getKey() );
            System.out.print( " " );
        }
        
        System.out.println( "\n" );
        
        // consultas
        System.out.println( "\n----- Consultas -----" );
        List<Entry<Integer, String>> elementos = new ResizingArrayList<>();
        for ( Entry<Integer, String> e : lph ) {
            elementos.add( e );
        }
        elementos.add( new Entry<>( 15, "Snoopy" ) );
        elementos.add( new Entry<>( 19, "Papai Noel" ) );
        elementos.add( new Entry<>( -4, "Garfield" ) );
        Utils.shuffle( elementos );
        for ( Entry<Integer, String> e : elementos ) {
            System.out.printf( "%4d está na tabela de dispersão? => %s\n", e.getKey(),
                    lph.contains( e.getKey() ) ? "SIM" : "NÃO" );
        }
        
        System.out.println( "\n----- Remoção -----" );
        System.out.println( lph );
        for ( Entry<Integer, String> e : elementos ) {
            System.out.printf( "Removendo o par chave/valor com chave %d...\n", e.getKey() );
            lph.delete( e.getKey() );
            System.out.println( lph );
        }
        
    }
    
}
