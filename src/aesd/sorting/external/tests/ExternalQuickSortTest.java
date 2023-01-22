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
            
            Register[] registers = new Register[]{
                new Register( 5 ),
                new Register( 3 ),
                new Register( 9 ),
                new Register( 6 ),
                new Register( 1 ),
                new Register( 7 ),
                new Register( 4 )
            };
            
            createTestFile( registers );
            
            System.out.println( "Before Sorting:" );
            showFileContent();
            
            ExternalQuickSort.sort( FILE_NAME, registers.length );
            
            System.out.println();
            System.out.println( "After Sorting:" );
            showFileContent();
            
            new File( FILE_NAME ).deleteOnExit();
            
        } catch ( Exception exc ) {
            System.out.println( exc.getMessage() );
        }
        
    }
    
    private static void createTestFile( Register[] registers ) throws IOException {
        
        try ( RandomAccessFile file = new RandomAccessFile( FILE_NAME, "rwd" ) ) {
            for ( Register r : registers ) {
                Register.write( file, r );
            }
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
