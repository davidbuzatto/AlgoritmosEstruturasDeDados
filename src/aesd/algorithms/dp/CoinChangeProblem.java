package aesd.algorithms.dp;

/**
 * Um resolvedor do problema do troco (Coin Change Problem).
 * 
 * @author Prof. Dr. David Buzatto
 */
public class CoinChangeProblem {
    
    // tabela de programação dinâmica
    private int coinChange[];
    
    // quantia
    private int v;
    
    // denominação das moedas
    private int d[];

    // solução
    private int solution;
    
    /**
     * Cria uma instância do resolvedor do problema do troco e resolve
     * o problema para os dados passados.
     * 
     * @param v
     * @param d 
     */
    public CoinChangeProblem( int v, int[] d ) {
        this.v = v;
        this.d = d;
        solve();
    }
    
    
    private void solve() {
        
        coinChange = new int[v+1];
        
        /*
         * coinChange[v] = 0,                                     se v = 0
         * coinChange[v] = Integer.MIN_VALUE (menos infinito)     se v < 0
         * coinChange[v] = 1 + min(coinChange(v-d[i]))            para todo i de 0 a n-1
         */
        
        for ( int v = 1; v < coinChange.length; v++ ) {
            
            int min = Integer.MAX_VALUE;
            
            //System.out.println( "CoinChange(" + v + ")" );
            
            for ( int i = 0; i < d.length; i++ ) {
                
                int currValue;
                int p = v-d[i];
                
                if ( p >= 0 ) {
                    currValue = coinChange[p];
                    //System.out.println( "CoinChange(" + v + "-" + d[i] + ") = " + currValue );
                    if ( currValue < min ) {
                        min = currValue;
                    }
                }
                
            }
            
            coinChange[v] = 1 + min;
            
        }
        
        solution = coinChange[v];
        
    }
    
    
    
    public int getSolution() {
        return solution;
    }

    @Override
    public String toString() {
        
        StringBuilder sb = new StringBuilder();
        
        sb.append( "  <0" );
        
        for ( int i = 0; i < coinChange.length; i++ ) {
            sb.append( String.format( "%4d " , i ) );
        }
        sb.append( "\n-inf" );
        for ( int i = 0; i < coinChange.length; i++ ) {
            sb.append( String.format( "%4d " , coinChange[i] ) );
        }
        
        return sb.toString();
        
    }
    
}
