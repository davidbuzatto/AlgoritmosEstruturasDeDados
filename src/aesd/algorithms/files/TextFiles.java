package aesd.algorithms.files;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Criando, escrevendo e lendo um arquivo de texto.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class TextFiles {
    
    public static void main( String[] args ) {
        
        // um arquivo
        File f = new File( "file.txt" );
        
        // escrevendo dados de texto em um arquivo
        try ( PrintWriter pw = new PrintWriter( f )) {
            pw.write( "test1\n" );
            pw.write( "test2\n" );
            pw.write( "test3" );
        } catch ( IOException exc ) {
            exc.printStackTrace();
        }
        
        // lendo dados de texto de um arquivo
        try ( Scanner s = new Scanner( f ) ) {
            while ( s.hasNextLine() ) {
                System.out.println( s.nextLine() );
            }
        } catch ( FileNotFoundException exc ) {
            exc.printStackTrace();
        }
        
        f.deleteOnExit();
        
    }
    
}
