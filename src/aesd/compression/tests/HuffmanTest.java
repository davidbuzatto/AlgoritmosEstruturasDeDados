package aesd.compression.tests;

import aesd.compression.Huffman;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintStream;

/**
 * Teste de compressão usando o algoritmo de Huffman.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class HuffmanTest {
    
    public static void main( String[] args ) throws Exception {
        
        boolean comprimir = true;
        PrintStream out = System.out;
        File arquivoComprimido = new File( "comprimido.dat" );
        
        if ( comprimir ) {
            
            try ( DataInputStream dis = new DataInputStream( 
                    new GenomeTest().getClass().getResourceAsStream(
                            "/aesd/compression/tests/data/genomeVirus.txt" ) ) ) {

                System.setIn( dis );

                System.out.println( "Comprimindo" );
                System.setOut( new PrintStream( arquivoComprimido ) );
                Huffman.compress();

                System.setOut( out );
                System.out.println( "Comprimido" );

            }
            
        } else {
        
            System.out.println( "Expandindo" );

            FileInputStream fis = new FileInputStream( arquivoComprimido );
            System.setIn( fis );
            Huffman.expand();
            
        }
        
    }

}
