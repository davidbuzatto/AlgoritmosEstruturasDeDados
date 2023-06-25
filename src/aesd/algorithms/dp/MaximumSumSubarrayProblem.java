package aesd.algorithms.dp;

/**
 * Implementação do algoritmo de Kadane para um resolvedor do problema do
 * subarranjo máximo de maior soma (Maximum Sum Subarray Problem) que usa
 * programação dinâmica.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class MaximumSumSubarrayProblem {
    
    // a sequência a ser analisada
    private int[] sequence;
    
    // o array das somas
    private int[] sums;
    
    // o array de resultados
    private int[] results;
    
    // a solução
    private int solution;
    
    /**
     * Cria uma instância do resolvedor do problema do subarranjo máximo de
     * maior soma e resolve o problema usando os dados passados.
     * 
     * @param sequence A sequência de valores a serem analisados.
     */
    public MaximumSumSubarrayProblem( int[] sequence ) {
        this.sequence = sequence;
        this.sums = new int[sequence.length];
        this.results = new int[sequence.length];
        solve();
    }
    
    private void solve() {
        
        int sum = 0;
        int result = 0;
        
        for ( int i = 0; i < sequence.length; i++ ) {
            
            sum += sequence[i];
            result = Math.max( result, sum );
            
            if ( sum < 0 ) {
                sum = 0;
            }
            
            sums[i] = sum;
            results[i] = result;
            
        }
        
        solution = result;
        
    }

    public int getSolution() {
        return solution;
    }

    @Override
    public String toString() {
        
        StringBuilder sb = new StringBuilder();
        int n = sequence.length;
        
        sb.append( "          " );
        
        for ( int i = 0; i < n; i++ ) {
            sb.append( String.format( "%4d ", i ) );
        }
        
        sb.append( "\nsequence: " );
        for ( int i = 0; i < n; i++ ) {
            sb.append( String.format( "%4d ", sequence[i] ) );
        }
        
        sb.append( "\n    sums: " );
        for ( int i = 0; i < n; i++ ) {
            sb.append( String.format( "%4d ", sums[i] ) );
        }
        
        sb.append( "\n results: " );
        for ( int i = 0; i < n; i++ ) {
            sb.append( String.format( "%4d ", results[i] ) );
        }
        
        return sb.toString();
        
    }
    
}
