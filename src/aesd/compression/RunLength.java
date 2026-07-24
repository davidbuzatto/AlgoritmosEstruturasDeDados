package aesd.compression;

import aesd.utils.BinaryStdIn;
import aesd.utils.BinaryStdOut;

/**
 * Implementação da codificação de comprimento de carreira (Run Length Encoding).
 *
 * A entrada é lida bit a bit e reduzida a uma sequência de contagens: em vez
 * de gravar cada bit individualmente, grava-se o comprimento de cada
 * "carreira" (run) de bits repetidos consecutivos, alternando implicitamente
 * entre carreiras de 0 e de 1 (a primeira carreira é sempre considerada de
 * bit 0, mesmo que tenha comprimento 0). Cada contagem ocupa LG_R bits fixos
 * (aqui, 8), o que limita o comprimento máximo de uma carreira a R - 1 = 255;
 * carreiras mais longas são fatiadas em uma carreira de 255 seguida de uma
 * carreira de comprimento 0 do bit oposto, preservando a alternância. É mais
 * eficiente quanto mais longas e repetitivas forem as carreiras de bits
 * iguais na entrada (ex.: imagens binárias simples), e pode até aumentar o
 * tamanho de entradas com muita alternância bit a bit.
 *
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms.
 * 4. ed. Boston: Pearson Education, 2011. 955 p.
 *
 * @author Prof. Dr. David Buzatto
 */
public class RunLength {

    private static final int R = 256;
    private static final int LG_R = 8;

    /**
     * Lê os bits da entrada padrão e grava sua codificação RLE na saída
     * padrão.
     */
    public static void compress() {

        char run = 0;
        boolean old = false;

        while ( !BinaryStdIn.isEmpty() ) {

            boolean b = BinaryStdIn.readBoolean();

            if ( b != old ) {
                BinaryStdOut.write( run, LG_R );
                run = 1;
                old = !old;
            } else {
                if ( run == R - 1 ) {

                    // carreira atingiu o comprimento máximo representável em
                    // LG_R bits: fecha essa carreira e abre uma carreira de
                    // comprimento 0 do bit oposto, apenas para manter a
                    // alternância implícita entre carreiras de 0 e de 1
                    BinaryStdOut.write( run, LG_R );
                    run = 0;
                    BinaryStdOut.write( run, LG_R );
                }
                run++;
            }

        }

        BinaryStdOut.write( run, LG_R );
        BinaryStdOut.close();

    }

    /**
     * Lê a codificação RLE da entrada padrão e grava os bits originais na
     * saída padrão.
     */
    public static void expand() {
        
        boolean b = false;
        
        while ( !BinaryStdIn.isEmpty() ) {
            
            int run = BinaryStdIn.readInt( LG_R );
            
            for ( int i = 0; i < run; i++ ) {
                BinaryStdOut.write( b );
            }
            
            b = !b;
            
        }
        
        BinaryStdOut.close();
        
    }

}
