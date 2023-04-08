package aesd.compression;

import aesd.algorithms.strings.Alphabet;
import aesd.utils.BinaryStdIn;
import aesd.utils.BinaryStdOut;

/**
 * Implementação da compressão de genomas. Fornece dois métodos estáticos para
 * comprimir e expandir uma sequência de caracteres em códigos que usam 2 bits.
 *
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 4. ed.
 * Boston: Pearson Education, 2011. 955 p.
 *
 * @author Prof. Dr. David Buzatto
 */
public class Genome {

    public static void compress() {

        Alphabet DNA = Alphabet.DNA;
        
        String s = BinaryStdIn.readString();
        int n = s.length();
        
        BinaryStdOut.write( n );

        // escreve o código de 2 bits para cada caractere
        for ( int i = 0; i < n; i++ ) {
            int d = DNA.toIndex( s.charAt( i ) );
            BinaryStdOut.write( d, 2 );
        }
        
        BinaryStdOut.close();
        
    }

    public static void expand() {
        
        Alphabet DNA = Alphabet.DNA;
        
        int n = BinaryStdIn.readInt();
        
        // lê dois bits e escreve um caractere
        for ( int i = 0; i < n; i++ ) {
            char c = BinaryStdIn.readChar( 2 );
            BinaryStdOut.write( DNA.toChar( c ), 8 );
        }
        
        BinaryStdOut.close();
        
    }

}
