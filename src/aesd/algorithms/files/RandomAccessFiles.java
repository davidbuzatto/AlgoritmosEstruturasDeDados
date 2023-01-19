package aesd.algorithms.files;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Criando, escrevendo e lendo registros em arquivo de acesso randômico.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class RandomAccessFiles {
    
    public static void main( String[] args ) {
        
        // um arquivo
        File f = new File( "file.dat" );
        
        // um arquivo de acesso randômico aberto em modo
        // de leitura e escrita (rw)
        try ( RandomAccessFile raf = new RandomAccessFile( f, "rw" ) ){
            
            // cada registro têm 12 bytes de dados
            Register[] regs = new Register[]{
                new Register( 1, 10 ),
                new Register( 2, 20 ),
                new Register( 3, 30 ),
                new Register( 4, 40 )
            };
            
            
            // escrevendo os registros no arquivo (arquivo de 48 bytes)
            // o ponteiro do arquivo vai avançando
            for ( Register r : regs ) {
                raf.writeInt( r.getN1() );
                raf.writeDouble( r.getN2() );
            }

            
            // lendo os registros do arquivo da frente para trás
            
            // posiciona o ponteiro do arquivo no início,
            // pois nesse momento está no final.
            raf.seek( 0 );
            for ( int i = 0; i < regs.length; i++ ) {
                Register reg = new Register( raf.readInt(), raf.readDouble() );
                System.out.println( reg );
            }
            System.out.println();
            
            
            // lendo os registros do arquivo de trás para frente
            
            // tamanho do arquivo em bytes
            long length = f.length();
            for ( int i = 1; i <= regs.length; i++ ) {
                raf.seek( length - ( i * Register.SIZE ) );
                Register reg = new Register( raf.readInt(), raf.readDouble() );
                System.out.println( reg );
            }
            System.out.println();
            
            
            // lendo um registro aleatório
            
            // qual registro?
            int registerPosition = 2;
            
            // em qual posição
            int pointerPosition = registerPosition * Register.SIZE;
            
            // posiciona
            raf.seek( pointerPosition );
            
            Register reg = new Register( raf.readInt(), raf.readDouble() );
            System.out.println( reg );
        
        } catch ( IOException exc ) {
            exc.printStackTrace();
        }
        
        f.deleteOnExit();
        
    }
    
}
