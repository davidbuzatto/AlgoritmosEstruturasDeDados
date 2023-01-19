package aesd.algorithms.files;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Criando, escrevendo e lendo um arquivo binário.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class BinaryFiles {
    
    public static void main( String[] args ) {
        
        // um arquivo
        File f = new File( "file.dat" );
        
        // escrevendo dados binários em um arquivo
        try ( FileOutputStream fos = new FileOutputStream( f );
              BufferedOutputStream bos = new BufferedOutputStream( fos );
              DataOutputStream dos = new DataOutputStream( bos ) ) {
            
            dos.write( 2 );
            dos.write( 4 );
            dos.write( 6 );
            dos.write( 8 );
            
        } catch ( IOException exc ) {
            exc.printStackTrace();
        }
        
        // lendo dados binários de um arquivo
        try ( FileInputStream fis = new FileInputStream( f );
              BufferedInputStream bis = new BufferedInputStream( fis );
              DataInputStream dis = new DataInputStream( bis ) ) {
            
            int d;
            
            while ( ( d = dis.read() ) != -1 ) {
                System.out.println( d );
            }
            
        } catch ( IOException exc ) {
            exc.printStackTrace();
        }
        
        f.deleteOnExit();
        
    }
    
}
