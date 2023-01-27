package aesd.algorithms.backtracking;

/**
 * Um resolvedor do problema das oito rainhas (Eight Queens Problem) que usa
 * backtracking.
 * 
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Computer Science: An
 * Interdisciplinary Approach. Boston: Pearson Education, 2016. 1146 p.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class EightQueensProblem {
    
    private int count = 0;
    
    public EightQueensProblem( int numberOfQueens ) {
        enumerate( numberOfQueens );
    }

    /**
     * Retorna verdadeiro se uma raina na posição n não conflita com nenhuma
     * outra rainha.
     * 
     * @param q Situação atual do tabuleiro.
     * @param n Rainha a ser verificada.
     * 
     * @return Verdadeiro caso não haja conflito, falso caso contrário.
     */
    private static boolean isConsistent( int[] q, int n ) {
        
        for ( int i = 0; i < n; i++ ) {
            
            // mesma coluna
            if ( q[i] == q[n] ) {
                return false;
            }
            
            // mesma diagonal maior
            if ( ( q[i] - q[n] ) == ( n - i ) ) {
                return false;
            }
            
            // mesma diagonal menor
            if ( ( q[n] - q[i] ) == ( n - i ) ) {
                return false;
            }
            
        }
        
        return true;
        
    }
    
    private void printQueens( int[] q ) {
        
        int n = q.length;
        
        for ( int i = 0; i < n; i++ ) {
            System.out.print( String.format( "%2d", n - i ) );
            for ( int j = 0; j < n; j++ ) {
                if ( q[i] == j ) {
                    System.out.print( "  Q" );
                } else {
                    System.out.print( "  *" );
                }
            }
            System.out.println();
        }
        
        System.out.print( "  " );
        
        for ( int i = 0; i < n; i++ ) {
            System.out.print( "  " + (char) ( 97 + i ) );
        }
        
        System.out.println( "\n" );
        
    }
    
    /**
     * Tenta todas as permutações para n rainhas.
     * 
     * @param n Quantidade de rainhas.
     */
    private void enumerate( int n ) {
        int[] q = new int[n];
        enumerate( q, 0 );
    }

    /**
     * Tenta todas as permutações para n rainhas usando backtracking.
     * 
     * @param q O array do posicionamento das rainhas.
     * @param k A coluna a ser testada.
     */
    private void enumerate( int[] q, int k ) {
        
        int n = q.length;
        
        if ( k == n ) {
            printQueens( q );
            count++;
        } else {
            for ( int i = 0; i < n; i++ ) {
                q[k] = i;
                if ( isConsistent( q, k ) ) {
                    enumerate( q, k + 1 );
                }
            }
        }
        
    }
    
    public int getCount() {
        return count;
    }
    
}
