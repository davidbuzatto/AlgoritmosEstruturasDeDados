package aesd.sorting.external.tests;

import aesd.sorting.external.ExternalQuickSort;
import aesd.sorting.external.Register;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Teste do QuickSort externo.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class ExternalQuickSortTest {

    private static final String FILE_NAME = "qe.dat";
    
    public static void main( String[] args ) {
        
        try {
            
            createTestFile();
            
            System.out.println( "Before Sorting:" );
            showFileContent();
            
            ExternalQuickSort.sort( FILE_NAME, 7 );
            
            System.out.println();
            System.out.println( "After Sorting:" );
            showFileContent();
            
            new File( FILE_NAME ).deleteOnExit();
            
        } catch ( Exception exc ) {
            System.out.println( exc.getMessage() );
        }
        
    }
    
    private static void createTestFile() throws IOException {
        
        try ( RandomAccessFile file = new RandomAccessFile( FILE_NAME, "rwd" ) ) {
            Register.write( file, new Register( 5 ) );
            Register.write( file, new Register( 3 ) );
            Register.write( file, new Register( 9 ) );
            Register.write( file, new Register( 6 ) );
            Register.write( file, new Register( 1 ) );
            Register.write( file, new Register( 7 ) );
            Register.write( file, new Register( 4 ) );
        }
            
    }
    
    private static void showFileContent() throws IOException {
        
        try ( RandomAccessFile arq = new RandomAccessFile( FILE_NAME, "r" ) ) {
            
            Register reg = Register.read( arq );
            
            while ( arq.getFilePointer() < arq.length() ) {
                System.out.println( "Register = " + reg );
                reg = Register.read( arq );
            }
            
            System.out.println( "Register = " + reg );
            
        }
        
    }
    
}
