package aesd.algorithms.dp;

/**
 * Um resolvedor do Problema da Mochila (Knapsack Problem) que usa programação
 * dinâmica.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class KnapsackProblem {
    
    // tabela pd
    private int z[][];
    
    // capacidade em unidade de peso
    private int c;
    
    // quantidade de itens
    private int n;
    
    // pesos dos itens
    private int w[];
    
    // valores dos itens
    private int v[];

    // solução
    private int solution;
    
    /**
     * Cria uma instância do resolvedor do problema da mochila e resolve
     * o problema para os dados passados.
     * 
     * @param c A capacidade máxima da mochila, em unidades de peso.
     * @param w Um array de pesos de n elementos.
     * @param v Um array de valores de n elementos.
     */
    public KnapsackProblem( int c, int[] w, int[] v ) {
        
        this.c = c;
        
        this.w = w;
        this.v = v;
        
        n = w.length;
        
        // w e v precisam ter o mesmo comprimento.
        if ( w.length != v.length ) {
            throw new IllegalArgumentException( "weights and nalues must be the same size." );
        }
        
        solve();
        
    }
    
    private void solve() {
        
        /*
         * z[k][d] = 0,                                           se k = 0 ou d = 0
         * z[k][d] = z[k-1][d],                                   se w[k-1] > d
         * z[k][d] = max( z[k-1][d], z[k-1][d-w[k-1]] + v[k-1]],  se w[k-1] < d
         */
        
        z = new int[n+1][c+1];
        
        for ( int k = 1; k < z.length; k++ ) {
            for ( int d = 1; d < z[k].length; d++ ) {
                if ( w[k-1] > d ) {
                    z[k][d] = z[k-1][d];
                } else {
                    z[k][d] = Math.max( z[k-1][d], z[k-1][d-w[k-1]] + v[k-1] );
                }
            }
        }
        
        solution = z[n][c];
        
    }

    public int getSolution() {
        return solution;
    }

    @Override
    public String toString() {
        
        StringBuilder sb = new StringBuilder();
        
        for ( int i = 0; i < z.length; i++ ) {
            if ( i != 0 ) {
                sb.append( "\n" );
            }
            for ( int j = 0; j < z[i].length; j++ ) {
                sb.append( String.format( "%4d", z[i][j] ) ).append( " " );
            }
        }
        
        return sb.toString();
        
    }
    
}
