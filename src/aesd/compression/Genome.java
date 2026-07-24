package aesd.compression;

import aesd.algorithms.strings.Alphabet;
import aesd.utils.BinaryStdIn;
import aesd.utils.BinaryStdOut;

/**
 * Implementação da compressão de genomas. Fornece dois métodos estáticos para
 * comprimir e expandir uma sequência de caracteres em códigos que usam 2 bits.
 *
 * Diferente de Huffman e LZW, que são de propósito geral, esta é uma
 * compressão de largura fixa especializada para o alfabeto do DNA
 * ({A, C, G, T}, apenas 4 símbolos): como 2 bits bastam para representar 4
 * símbolos distintos (2^2 = 4), cada caractere passa de 8 bits (ASCII) para
 * apenas 2, uma razão de compressão fixa de 4:1, sem qualquer análise de
 * frequência ou dicionário adaptativo.
 *
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 4. ed.
 * Boston: Pearson Education, 2011. 955 p.
 *
 * @author Prof. Dr. David Buzatto
 */
public class Genome {

    /**
     * Lê uma sequência de DNA (caracteres do alfabeto {A, C, G, T}) da
     * entrada padrão e grava sua codificação de 2 bits por caractere na
     * saída padrão.
     */
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

    /**
     * Lê a codificação de 2 bits por caractere da entrada padrão e grava a
     * sequência de DNA original decodificada na saída padrão.
     */
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
