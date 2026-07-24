package aesd.algorithms.dp;

/**
 * Um resolvedor do problema do troco (Coin Change Problem) que usa programação
 * dinâmica.
 *
 * Calcula a menor quantidade de moedas (com reposição ilimitada de cada
 * denominação) necessária para totalizar exatamente a quantia v.
 * coinChange[v] é construído de baixo para cima a partir de coinChange[0] = 0,
 * tentando, para cada quantia, usar cada denominação disponível como a
 * última moeda e aproveitando o menor resultado já calculado para o
 * restante (v - moeda). Quantias sem solução ficam marcadas com
 * Integer.MAX_VALUE (nesta implementação, o "infinito"). Complexidade
 * O(v * |d|).
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
     * @param v A quantia a ser totalizada.
     * @param d As denominações de moedas disponíveis.
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
         * coinChange[v] = infinito                                se v não puder ser formado com as moedas de d
         * coinChange[v] = 1 + min(coinChange(v-d[i]))            para todo i de 0 a n-1
         */

        for ( int v = 1; v < coinChange.length; v++ ) {

            int min = Integer.MAX_VALUE;

            //System.out.println( "CoinChange(" + v + ")" );

            for ( int i = 0; i < d.length; i++ ) {

                int currValue;
                int p = v-d[i];

                // ignora valores que também não têm solução (infinito),
                // senão currValue + 1 (logo abaixo) estouraria
                if ( p >= 0 && coinChange[p] != Integer.MAX_VALUE ) {
                    currValue = coinChange[p];
                    //System.out.println( "CoinChange(" + v + "-" + d[i] + ") = " + currValue );
                    if ( currValue < min ) {
                        min = currValue;
                    }
                }

            }

            // se nenhuma moeda alcançou v, v não tem solução (infinito),
            // senão 1 + min estouraria (Integer.MAX_VALUE + 1)
            coinChange[v] = ( min == Integer.MAX_VALUE ) ? Integer.MAX_VALUE : 1 + min;

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
            if ( coinChange[i] == Integer.MAX_VALUE ) {
                sb.append( " inf " );
            } else {
                sb.append( String.format( "%4d " , coinChange[i] ) );
            }
        }
        
        return sb.toString();
        
    }
    
}
