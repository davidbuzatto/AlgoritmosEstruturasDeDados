package aesd.compression.tests;

import aesd.compression.RunLength;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintStream;

/**
 * Teste de codificação de comprimento de carreira (Run Length Encoding).
 *
 * @author Prof. Dr. David Buzatto
 */
public class RunLengthTest {

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
                RunLength.compress();

                System.setOut( out );
                System.out.println( "Comprimido" );

            }
            
        } else {
        
            System.out.println( "Expandindo" );

            FileInputStream fis = new FileInputStream( arquivoComprimido );
            System.setIn( fis );
            RunLength.expand();
            
        }
        
    }

}
