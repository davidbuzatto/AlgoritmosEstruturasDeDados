package aesd.compression;

import aesd.ds.implementations.nonlinear.symtable.TernarySearchTrie;
import aesd.utils.BinaryStdIn;
import aesd.utils.BinaryStdOut;

/**
 * Implementação do algoritmo de compressão de Lempel-Ziv-Welch.
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

    public static void compress() {
        
        String input = BinaryStdIn.readString();
        TernarySearchTrie<Integer> st = new TernarySearchTrie<>();

        // dado que a TernarySearchTrie não é balancaeada
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
            
            if ( i == codeword ) {
                s = val + val.charAt( 0 );   // hack para caso especial
            }
            
            if ( i < L ) {
                st[i++] = val + s.charAt( 0 );
            }
            
            val = s;
            
        }
        
        BinaryStdOut.close();
        
    }

}
