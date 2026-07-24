package aesd.compression;

import aesd.ds.implementations.nonlinear.symtable.TernarySearchTrie;
import aesd.utils.BinaryStdIn;
import aesd.utils.BinaryStdOut;

/**
 * Implementação do algoritmo de compressão de Lempel-Ziv-Welch.
 *
 * Diferente de Huffman, que precisa conhecer as frequências dos caracteres
 * de antemão, o LZW constrói sua tabela de símbolos adaptativamente,
 * enquanto lê a entrada: começa apenas com os R caracteres individuais do
 * alfabeto e, a cada passo, codifica o maior prefixo da entrada restante já
 * presente na tabela e insere uma nova entrada (esse prefixo mais o próximo
 * caractere) para uso futuro. Cada codeword ocupa um número fixo de bits (W),
 * o que limita a tabela a L = 2^W entradas; ao esgotá-la, novas entradas
 * simplesmente deixam de ser adicionadas (a compressão continua funcionando,
 * só para de aprender). A descompressão reconstrói a mesma tabela em
 * paralelo, sem nunca precisar transmiti-la — o único caso especial é
 * quando um codeword referencia uma entrada que a descompressão ainda não
 * criou (i == codeword), resolvido reaproveitando o próprio valor anterior.
 *
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms.
 * 4. ed. Boston: Pearson Education, 2011. 955 p.
 *
 * @author Prof. Dr. David Buzatto
 */
public class LZW {

    // número de caracteres de saída
    private static final int R = 256;
    
    // número de codewords = 2^W
    private static final int L = 4096;
    
    // comprimento da codeword
    private static final int W = 12;

    /**
     * Lê a entrada padrão e grava sua codificação LZW (uma sequência de
     * codewords de W bits) na saída padrão.
     */
    public static void compress() {

        String input = BinaryStdIn.readString();
        TernarySearchTrie<Integer> st = new TernarySearchTrie<>();

        // dado que a TernarySearchTrie não é balanceada
        // seria melhor inserir em outra ordem
        for ( int i = 0; i < R; i++ ) {
            st.put( "" + (char) i, i );
        }

        // R é a codeword para EOF
        int code = R + 1;

        while ( input.length() > 0 ) {
            
            // busca pelo casamento do maior prefixo s
            String s = st.getLongestPrefixOf( input );
            
            // imprime a codificação de s
            BinaryStdOut.write( st.get( s ), W );
            
            int t = s.length();
            
            // insere s tabela de símbolos
            if ( t < input.length() && code < L ) {
                st.put( input.substring( 0, t + 1 ), code++ );
            }
            
            // escaneia a entrada após a s
            input = input.substring( t );
            
        }
        
        BinaryStdOut.write( R, W );
        BinaryStdOut.close();
        
    }
    
    /**
     * Lê a codificação LZW da entrada padrão e grava a mensagem original
     * decodificada na saída padrão.
     */
    public static void expand() {

        String[] st = new String[L];
        
        // próximo valor de codeword disponível
        int i;

        // inicializa a tabela de símbolos com todos as strings com caracacteres
        // à esquerda
        for ( i = 0; i < R; i++ ) {
            st[i] = "" + (char) i;
        }
        
        // (sem uso). lookahead para EOF
        st[i++] = "";

        int codeword = BinaryStdIn.readInt( W );
        
        // mensagem expandida é a string vazia
        if ( codeword == R ) {
            return;
        }
        
        String val = st[codeword];

        while ( true ) {
            
            BinaryStdOut.write( val );
            codeword = BinaryStdIn.readInt( W );
            
            if ( codeword == R ) {
                break;
            }
            
            String s = st[codeword];
            
            // caso especial: o codeword lido referencia uma entrada que a
            // descompressão ainda não criou (isso só ocorre quando a
            // compressão usou, na própria sequência seguinte, a entrada que
            // acabara de criar). resolve-se antecipando que essa entrada
            // seria val + o primeiro caractere de val
            if ( i == codeword ) {
                s = val + val.charAt( 0 );
            }
            
            if ( i < L ) {
                st[i++] = val + s.charAt( 0 );
            }
            
            val = s;
            
        }
        
        BinaryStdOut.close();
        
    }

}
