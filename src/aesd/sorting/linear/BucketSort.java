package aesd.sorting.linear;

/**
 * Ordenação usando buckets (baldes) de arrays de inteiros não negativos.
 *
 * Diferente do Radix Sort LSD (ver RadixSortLSD), que usa buckets como
 * mecanismo de distribuição por dígito em várias passadas, aqui a
 * distribuição é feita numa única passada, por faixa de valor: o intervalo
 * [0, maior valor] é dividido em K buckets de largura igual, e cada
 * elemento vai para o bucket correspondente à sua faixa de valor. Como
 * cada bucket pode acabar com mais de um elemento, seu conteúdo ainda
 * precisa ser ordenado — aqui, com insertion sort, eficiente para as
 * poucas dezenas de elementos esperadas por bucket quando os dados estão
 * uniformemente distribuídos.
 *
 * Este algoritmo é eficiente quando os valores de entrada estão
 * uniformemente distribuídos ao longo do intervalo [0, maior valor]: nesse
 * cenário, cada bucket recebe, em média, poucos elementos, e o custo total
 * de ordenar todos os buckets fica próximo de O(n). Com dados muito
 * concentrados numa faixa estreita de valores, porém, um único bucket pode
 * acumular quase todos os elementos, degradando o desempenho para o de um
 * insertion sort comum.
 *
 * Para a ordenação do array são executados três passos:
 *   1) passagem de contagem: conta-se quantos elementos cairão em cada
 *      bucket, permitindo alocar cada bucket já com o tamanho exato
 *      necessário (sem desperdiçar memória nem precisar de listas que
 *      crescem dinamicamente);
 *   2) passagem de distribuição: cada elemento do array é copiado para o
 *      seu bucket, de acordo com a faixa de valor em que se encaixa;
 *   3) passagem de ordenação e coleta: cada bucket é ordenado
 *      individualmente (insertion sort) e recolocado, em sequência, de
 *      volta no array original.
 *
 * Crescimento do uso de memória em relação ao tamanho da
 * entrada: O(n+k).
 *
 * Obs: k é a quantidade de buckets.
 *
 * In-place? Não
 *  Estável? Sim, desde que o algoritmo usado para ordenar cada bucket
 *           também seja (insertion sort, usado aqui, é estável).
 *
 * Complexidade:
 *       Pior caso: O(n^2), quando todos os elementos caem no mesmo bucket
 *      Caso médio: O(n+k), com os dados uniformemente distribuídos
 *     Melhor caso: O(n+k)
 *
 * Implementação baseada na obra: CORMEN, T. H. et al. Introduction to
 * Algorithms. 3. ed. Cambridge: MIT Press, 2009. 1292 p.
 *
 * @author Prof. Dr. David Buzatto
 */
public class BucketSort {

    public static void sort( int[] array ) {

        // tamanho do array
        int n = array.length;

        // array vazio ou com um único elemento já está ordenado
        if ( n < 2 ) {
            return;
        }

        // maior valor do array, usado para calcular a largura de cada bucket
        int max = array[0];
        for ( int i = 1; i < n; i++ ) {
            if ( array[i] > max ) {
                max = array[i];
            }
        }

        // quantidade de buckets: um para cada elemento do array tende a
        // manter, em média, poucos elementos por bucket quando os dados
        // são uniformemente distribuídos
        int k = n;

        // passagem de contagem: em qual bucket cada elemento cairá, e
        // quantos elementos cada bucket vai receber ao final
        int[] bucketOf = new int[n];
        int[] count = new int[k];

        for ( int i = 0; i < n; i++ ) {

            // mapeia o valor do elemento para um bucket em [0, k-1];
            // (max + 1) no denominador garante que o próprio maior valor
            // do array não estoure para um bucket k, que não existe
            int bucket = (int) ( (long) array[i] * k / ( (long) max + 1 ) );

            bucketOf[i] = bucket;
            count[bucket]++;

        }

        // aloca cada bucket já com o tamanho exato que vai precisar
        int[][] buckets = new int[k][];
        for ( int i = 0; i < k; i++ ) {
            buckets[i] = new int[count[i]];
        }

        // passagem de distribuição
        int[] filled = new int[k];
        for ( int i = 0; i < n; i++ ) {
            int bucket = bucketOf[i];
            buckets[bucket][filled[bucket]++] = array[i];
        }

        // passagem de ordenação (cada bucket, individualmente) e coleta
        // (recolocação em sequência de volta no array original)
        int pos = 0;
        for ( int i = 0; i < k; i++ ) {

            insertionSort( buckets[i] );

            for ( int j = 0; j < buckets[i].length; j++ ) {
                array[pos++] = buckets[i][j];
            }

        }

    }

    // insertion sort simples, usado para ordenar o conteúdo de cada bucket;
    // é estável (só desloca elementos estritamente maiores que o atual) e
    // eficiente para os poucos elementos esperados em cada bucket
    private static void insertionSort( int[] bucket ) {

        for ( int i = 1; i < bucket.length; i++ ) {

            int current = bucket[i];
            int j = i - 1;

            while ( j >= 0 && bucket[j] > current ) {
                bucket[j+1] = bucket[j];
                j--;
            }

            bucket[j+1] = current;

        }

    }

}
