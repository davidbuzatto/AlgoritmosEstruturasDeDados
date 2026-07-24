package aesd.algorithms.dp;

/**
 * Implementação do algoritmo de Kadane para um resolvedor do problema do
 * subarranjo máximo de maior soma (Maximum Sum Subarray Problem) que usa
 * programação dinâmica.
 *
 * A recorrência é: a maior soma de um subarranjo terminando em i é
 * sequence[i] mais a maior soma terminando em i-1, mas só se essa soma
 * anterior for positiva — caso contrário, é melhor recomeçar em i (arrastar
 * uma soma negativa só piora o resultado). Percorrendo o array uma única
 * vez e guardando o melhor valor encontrado, evita-se testar todos os
 * O(n^2) subarranjos possíveis. Complexidade O(n), tempo ótimo para o
 * problema.
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

        // começa em MIN_VALUE (e não 0) para que sequências totalmente
        // negativas resultem no maior elemento (menos negativo), e não em 0
        // (que corresponderia a um subarranjo vazio, não permitido aqui)
        int result = Integer.MIN_VALUE;
        
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
