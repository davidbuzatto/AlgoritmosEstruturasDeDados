package aesd.ds.implementations.nonlinear.symtable.tests;

import aesd.ds.implementations.linear.ResizingArrayList;
import aesd.ds.implementations.nonlinear.symtable.SeparateChainingHashTable;
import aesd.ds.interfaces.List;
import aesd.ds.utils.Utils;
import aesd.ds.interfaces.SymbolTable.Entry;

/**
 * Teste de uso da tabela de dispersão com encadeamento.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class TestSeparateChainingHashTable {
    
    public static void main( String[] args ) {
        
        SeparateChainingHashTable<Integer, String> sch = new SeparateChainingHashTable<>();
        
        sch.put( 6, "João" );
        System.out.println( sch );
        sch.put( 8, "Maria" );
        System.out.println( sch );
        sch.put( 7, "David" );
        System.out.println( sch );
        sch.put( 4, "Fernanda" );
        System.out.println( sch );
        sch.put( 5, "Aurora" );
        System.out.println( sch );
        sch.put( 9, "Marcelo" );
        System.out.println( sch );
        sch.put( 3, "Ronaldinho" );
        System.out.println( sch );
        sch.put( 9, "Matilda" );
        System.out.println( sch );
        sch.put( 3, null );
        System.out.println( sch );
        
        System.out.println( "Dados da tabela de dispersão através do iterador:" );
        for ( Entry<Integer, String> e : sch ) {
            System.out.print( e.getKey() );
            System.out.print( " " );
        }
        
        System.out.println( "\n" );
        
        // consultas
        System.out.println( "\n----- Consultas -----" );
        List<Entry<Integer, String>> elementos = new ResizingArrayList<>();
        for ( Entry<Integer, String> e : sch ) {
            elementos.add( e );
        }
        elementos.add( new Entry<>( 15, "Snoopy" ) );
        elementos.add( new Entry<>( 19, "Papai Noel" ) );
        elementos.add( new Entry<>( -4, "Garfield" ) );
        Utils.shuffle( elementos );
        for ( Entry<Integer, String> e : elementos ) {
            System.out.printf( "%4d está na tabela de dispersão? => %s\n", e.getKey(),
                    sch.contains( e.getKey() ) ? "SIM" : "NÃO" );
        }
        
        System.out.println( "\n----- Remoção -----" );
        System.out.println( sch );
        for ( Entry<Integer, String> e : elementos ) {
            System.out.printf( "Removendo o par chave/valor com chave %d...\n", e.getKey() );
            sch.delete( e.getKey() );
            System.out.println( sch );
        }
        
    }
    
}
