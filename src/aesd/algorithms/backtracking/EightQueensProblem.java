package aesd.algorithms.backtracking;

/**
 * Um resolvedor do problema das oito rainhas (Eight Queens Problem) que usa
 * backtracking.
 *
 * Como duas rainhas nunca podem dividir a mesma linha (a representação já
 * garante isso: q[i] é a coluna da rainha da linha i), o problema se reduz a
 * escolher, linha a linha, uma coluna que ainda não entre em conflito de
 * coluna ou de diagonal com nenhuma rainha já posicionada (isConsistent()).
 * Ao chegar à última linha (k == n) sem conflitos, uma solução completa foi
 * encontrada; caso contrário, a recursão simplesmente retorna e a próxima
 * coluna candidata é tentada — não é necessário desfazer nada
 * explicitamente, pois q[k] é sobrescrito antes de cada nova tentativa.
 *
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Computer Science: An
 * Interdisciplinary Approach. Boston: Pearson Education, 2016. 1146 p.
 *
 * @author Prof. Dr. David Buzatto
 */
public class EightQueensProblem {

    private int count = 0;

    /**
     * Cria uma instância do resolvedor do problema das n rainhas e enumera
     * (imprimindo) todas as soluções possíveis.
     *
     * @param numberOfQueens A quantidade de rainhas (e o tamanho do
     * tabuleiro numberOfQueens x numberOfQueens).
     */
    public EightQueensProblem( int numberOfQueens ) {
        enumerate( numberOfQueens );
    }

    /**
     * Retorna verdadeiro se uma rainha na posição n não conflita com nenhuma
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

            // duas casas estão na mesma diagonal quando a diferença entre
            // suas colunas é igual à diferença entre suas linhas (em módulo);
            // os dois testes abaixo cobrem as duas diagonais (maior e menor)
            // sem precisar de Math.abs

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
