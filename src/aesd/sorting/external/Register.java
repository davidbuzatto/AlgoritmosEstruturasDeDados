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

    /**
     * Lê um registro a partir da posição atual do arquivo. O formato em
     * disco é apenas a chave inteira, em 4 bytes (ver getSize()); os outros
     * campos do registro, se existissem, seriam lidos logo em seguida,
     * nesta mesma ordem em que write() os grava.
     *
     * @param file O arquivo de onde o registro será lido.
     * @return O registro lido.
     * @throws IOException em caso de erro de leitura.
     */
    public static Register read( RandomAccessFile file ) throws IOException {
        int key = file.readInt();
        // aqui processaria os outros campos do registro para criar o objeto
        return new Register( key );
    }

    /**
     * Escreve um registro na posição atual do arquivo, no mesmo formato de
     * 4 bytes (apenas a chave) lido por read().
     *
     * @param file O arquivo onde o registro será escrito.
     * @param register O registro a ser escrito.
     * @throws IOException em caso de erro de escrita.
     */
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
