package aesd.sorting.linear;

/**
 * Ordenação usando buckets (baldes) de arrays de inteiros não negativos.
 * 
 * Para a ordenação do array são repetidos dois passos:
 *   1) passagem de distribuição: os elementos do array são distribuídos
 *      em seus respectivos buckets, usando algum critério para a distribuição.
 *      nesta implementação na primeira passada usa-se a unidade do número que
 *      está sendo distribuído.
 *   2) passagem de coleta: percorre-se cada bucket recolocando os valores no
 *      array original.
 *
 * Crescimento do uso de memória em relação ao tamanho da
 * entrada: O(n*k) ou O(n+k).
 * 
 * Obs: k é a quantidade de buckets.
 *
 * In-place? Não
 *  Estável? Sim, desde que o algoritmo que ordena os buckets também seja.
 *           No exemplo implementado, a ordenação é feita por sucessivas
 *           distribuições baseadas nas posições dos números das unidades,
 *           depois dezenas, depois centenas etc, ou seja, indo do algarismo
 *           menos significativo em direção ao algarismo mais significativo.
 *
 * Complexidade:
 *       Pior caso: O(n^2)
 *      Caso médio: O(n+k)
 *     Melhor caso: O(n+k)
 *
 * Implementação baseada na obra: DEITEL, P.; DEITEL, H. Java: Como Programar.
 * 10. ed. São Paulo: Pearson, 2017. 934 p.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class BucketSort {
    
    public static void sort( int[] array ) {
        
        // tamanho do array
        int n = array.length;
        
        // 10 buckets
        final int K = 10;
        
        // aloca K buckets usando um array bidimensional.
        // cada linha é um bucket que contém n posições.
        int[][] buckets = new int[K][n];
        
        // controla os índices para inserção em cada bucket
        int[] c = new int[K];
        
        // usado para a geração das posições de distribuição
        int t1 = 10; // 10, 100, 1000, 10000...
        int t2 = 1;  //  1,  10,  100,  1000...
        
        // valor máximo, usado para identificar quando se deve parar
        int max = -1;
        
        // primeira passada
        boolean first = true;
        
        // passadas
        while ( max < 0 || max / t2 != 0 ) {

            // passagem de distribuição:
            //   colocar cada elemento do array que está sendo ordenado em seu
            //   respectivo bucket usando para isso a unidade, depois a dezena
            //   depois a centena como índice.
            for ( int i = 0; i < n; i++ ) {
                
                // extrai unidade, depois dezena, depois centena...
                int p = array[i] % t1 / t2;
                
                // distribui
                buckets[p][c[p]++] = array[i];
                
                // obtém o máximo
                if ( first ) {
                    max = max < array[i] ? array[i] : max;
                }
                
            }
            
            first = false;
            
            // passagem de coleta:
            //   percore cada bucket, recolocando os valores no array original.
            int k = 0;
            for ( int i = 0; i < K; i++ ) {
                for ( int j = 0; j < c[i]; j++ ) {
                    array[k++] = buckets[i][j];
                }
                c[i] = 0;
            }
        
            // recalcula as variáveis auxiliares para obtenção dos algarismos
            t2 = t1;
            t1 *= 10;
            
        }
    
    }
        
}
