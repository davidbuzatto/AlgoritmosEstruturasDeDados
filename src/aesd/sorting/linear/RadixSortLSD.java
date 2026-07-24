package aesd.sorting.linear;

/**
 * Ordenação por distribuição de dígitos (Radix Sort LSD - least
 * significant digit) de arrays de inteiros não negativos.
 *
 * Diferente do Bucket Sort "clássico" (ver BucketSort), que distribui os
 * elementos em buckets por faixa de valor numa única passada e depois
 * ordena o conteúdo de cada bucket, aqui os buckets são reaproveitados
 * como mecanismo de distribuição por dígito, em múltiplas passadas
 * (unidade, dezena, centena etc.), sem nenhuma ordenação por comparação
 * dentro deles — o algarismo usado como índice de distribuição muda a
 * cada passada, do menos significativo para o mais significativo.
 *
 * Para a ordenação do array são repetidos dois passos, uma vez para cada
 * algarismo do maior valor do array:
 *   1) passagem de distribuição: os elementos do array são distribuídos
 *      em seus respectivos buckets, usando o algarismo da posição atual
 *      (unidade na primeira passada, dezena na segunda etc.) como índice.
 *   2) passagem de coleta: percorre-se cada bucket recolocando os valores no
 *      array original.
 *
 * Crescimento do uso de memória em relação ao tamanho da
 * entrada: O(n*k) ou O(n+k).
 *
 * Obs: k é a quantidade de buckets (aqui, sempre 10 — um por algarismo).
 *
 * In-place? Não
 *  Estável? Sim, pois a distribuição e a coleta preservam a ordem relativa
 *           dos elementos que caem no mesmo bucket em cada passada — e é
 *           exatamente essa estabilidade, mantida passada após passada, que
 *           garante a corretude do algoritmo: se não fosse estável, ordenar
 *           por um algarismo mais significativo poderia desfazer a
 *           ordenação já obtida pelos algarismos menos significativos.
 *
 * Complexidade:
 *       Pior caso: O(d*(n+k)), sendo d a quantidade de algarismos do maior valor
 *      Caso médio: O(n+k)
 *     Melhor caso: O(n+k)
 *
 * Implementação baseada na obra: DEITEL, P.; DEITEL, H. Java: Como Programar.
 * 10. ed. São Paulo: Pearson, 2017. 934 p.
 *
 * @author Prof. Dr. David Buzatto
 */
public class RadixSortLSD {

    public static void sort( int[] array ) {

        // tamanho do array
        int n = array.length;

        // array vazio já está ordenado. sem essa parada, o laço mais abaixo,
        // que depende do maior valor encontrado no array, nunca teria esse
        // valor atualizado (não há elementos para percorrer) e ficaria
        // repetindo para sempre.
        if ( n == 0 ) {
            return;
        }

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
