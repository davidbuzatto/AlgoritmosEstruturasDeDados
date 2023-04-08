package aesd.compression;

import aesd.ds.implementations.nonlinear.pq.MinPriorityQueue;
import aesd.utils.BinaryStdIn;
import aesd.utils.BinaryStdOut;

/**
 * Implementação do algoritmo de compressão de Huffman.
 * 
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 
 * 4. ed. Boston: Pearson Education, 2011. 955 p.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class Huffman {

    // tamanho do alfabeto estendido da tabela ASCII
    private static final int R = 256;

    // nó da Trie de Huffman
    private static class Node implements Comparable<Node> {

        private final char ch;
        private final int freq;
        private final Node left, right;

        Node( char ch, int freq, Node left, Node right ) {
            this.ch = ch;
            this.freq = freq;
            this.left = left;
            this.right = right;
        }

        // verifica se um nó é folha
        private boolean isLeaf() {
            return ( left == null ) && ( right == null );
        }

        // compara baseado na frequência
        public int compareTo( Node that ) {
            return this.freq - that.freq;
        }
        
    }

    public static void compress() {
        
        // lê a entrada
        String s = BinaryStdIn.readString();
        char[] input = s.toCharArray();

        // tabula as contagens de frequências
        int[] freq = new int[R];
        for ( int i = 0; i < input.length; i++ ) {
            freq[input[i]]++;
        }

        // constroi a Trie de Huffman
        Node root = buildTrie( freq );

        // constroi a tabela de códigos
        String[] st = new String[R];
        buildCode( st, root, "" );

        // imprime a Trie para o decodificador
        writeTrie( root );

        // imprime o número de byres na mensagem original sem compressão
        BinaryStdOut.write( input.length );

        // usa o código de Huffman para codificar a entrada
        for ( int i = 0; i < input.length; i++ ) {
            
            String code = st[input[i]];
            
            for ( int j = 0; j < code.length(); j++ ) {
                
                if ( code.charAt( j ) == '0' ) {
                    BinaryStdOut.write( false );
                } else if ( code.charAt( j ) == '1' ) {
                    BinaryStdOut.write( true );
                } else {
                    throw new IllegalStateException( "Illegal state" );
                }
            }
            
        }

        // fecha o fluxo de saída
        BinaryStdOut.close();
        
    }

    // constroi a Trie de Huffman dadas as frequências
    private static Node buildTrie( int[] freq ) {

        // inicializa a fila de prioridades com as árvores únicas
        MinPriorityQueue<Node> pq = new MinPriorityQueue<>();
        for ( char c = 0; c < R; c++ ) {
            if ( freq[c] > 0 ) {
                pq.insert( new Node( c, freq[c], null, null ) );
            }
        }

        // funde as menores árvores
        while ( pq.getSize() > 1 ) {
            Node left = pq.delete();
            Node right = pq.delete();
            Node parent = new Node( '\0', left.freq + right.freq, left, right );
            pq.insert( parent );
        }
        
        return pq.delete();
        
    }

    // escreve a Trie codificada em bits na saída padrão
    private static void writeTrie( Node x ) {
        
        if ( x.isLeaf() ) {
            BinaryStdOut.write( true );
            BinaryStdOut.write( x.ch, 8 );
            return;
        }
        
        BinaryStdOut.write( false );
        writeTrie( x.left );
        writeTrie( x.right );
        
    }

    // constroi a tabela de pesquisa usando os símbolos e suas codificações
    private static void buildCode( String[] st, Node x, String s ) {
        
        if ( !x.isLeaf() ) {
            buildCode( st, x.left, s + '0' );
            buildCode( st, x.right, s + '1' );
        } else {
            st[x.ch] = s;
        }
        
    }
    
    public static void expand() {

        // lê a trie de Huffman do fluxo de entrada
        Node root = readTrie();

        // número de bytes a escrever
        int length = BinaryStdIn.readInt();

        // decodifica usando a Trie de Huffman
        for ( int i = 0; i < length; i++ ) {
            
            Node x = root;
            
            while ( !x.isLeaf() ) {
                boolean bit = BinaryStdIn.readBoolean();
                if ( bit ) {
                    x = x.right;
                } else {
                    x = x.left;
                }
            }
            
            BinaryStdOut.write( x.ch, 8 );
            
        }
        
        BinaryStdOut.close();
        
    }

    private static Node readTrie() {
        
        boolean isLeaf = BinaryStdIn.readBoolean();
        
        if ( isLeaf ) {
            return new Node( BinaryStdIn.readChar(), -1, null, null );
        } else {
            return new Node( '\0', -1, readTrie(), readTrie() );
        }
        
    }

}
