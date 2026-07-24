package aesd.algorithms.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Serializando e deserializando objetos em arquivos.
 *
 * Serialização converte o estado de um objeto (seus campos) diretamente em
 * uma sequência de bytes que pode ser gravada em arquivo e depois
 * reconstruída (deserializada) de volta em um objeto equivalente, sem
 * precisar escrever manualmente a lógica de conversão campo a campo — desde
 * que a classe implemente a interface marcadora Serializable (ver
 * SerializableClass).
 *
 * @author Prof. Dr. David Buzatto
 */
public class Serialization {
    
    public static void main( String[] args ) {
        
        // um arquivo
        File f = new File( "file.ser" );
        
        // serializando (escrevendo) um objeto em um arquivo
        try ( FileOutputStream fos = new FileOutputStream( f );
              ObjectOutputStream oos = new ObjectOutputStream( fos ) ) {
            
            SerializableClass obj = new SerializableClass( 10, "test" );
            oos.writeObject( obj );
            
        } catch ( IOException exc ) {
            exc.printStackTrace();
        }
        
        // deserializando (lendo) um objeto de um arquivo
        try ( FileInputStream fis = new FileInputStream( f );
              ObjectInputStream ois = new ObjectInputStream( fis ) ) {
            
            SerializableClass obj = (SerializableClass) ois.readObject();
            System.out.println( obj );
            
        } catch ( IOException | ClassNotFoundException exc ) {
            exc.printStackTrace();
        }
        
        f.deleteOnExit();
        
    }
    
}
