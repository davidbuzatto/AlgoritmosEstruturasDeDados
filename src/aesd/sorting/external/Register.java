package aesd.sorting.external;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Um registro com um campo inteiro.
 * 
 * Implementação baseada na obra: ZIVIANI, N. Projeto de Algoritmos com
 * Implementações em Java e C++. São Paulo: Cengage, 2006. 644 p.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class Register implements Comparable<Register> {

    private int key;
    // outros atributos do registro viriam aqui...
    
    public Register( int key ) {
        this.key = key;
    }

    @Override
    public int compareTo( Register o ) {
        if ( this.key < o.key ) {
            return -1;
        } else if ( this.key > o.key ) {
            return 1;
        }
        return 0;
    }

    public static Register read( RandomAccessFile file ) throws IOException {
        int key = file.readInt();
        // aqui processaria os outros campos do registro para criar o objeto
        return new Register( key );
    }
    
    public static void write( RandomAccessFile file, Register register ) throws IOException {
        int key = register.getKey();
        // aqui processaria os outros campos do registro para armazenar no arquivo
        file.writeInt( key );
    }
    
    /**
     * Tamanho do registro em bytes.
     * Como só possui uma chave inteira, o tamanho é quatro bytes.
     * 
     * @return O tamanho do registro no arquivo.
     */
    public static int getSize() {
        return 4;
    }

    public int getKey() {
        return key;
    }

    public void setKey( int key ) {
        this.key = key;
    }

    @Override
    public String toString() {
        return String.valueOf( key );
    }
    
}
