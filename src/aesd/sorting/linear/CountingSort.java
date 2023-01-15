package aesd.sorting.linear;

/**
 * Ordenação por contagem de arrays de inteiros não negativos.
 * 
 * A ordenação acontece com base na contagem dos elementos do array. Após
 * a contagem, sabe-se a quantidade de cada valor, sendo assim, o array
 * de contagem codifica a ordenação. Entretanto, para que a ordenação seja
 * estável, é necessário reposicionar os elementos do array original com base
 * nas contagens.
 *
 * Crescimento do uso de memória em relação ao tamanho da
 * entrada: O(n+k).
 * 
 * Obs: k é o maior elemento do array.
 *
 * In-place? Não
 *  Estável? Sim
 *
 * Complexidade:
 *       Pior caso: O(n+k)
 *      Caso médio: O(n+k)
 *     Melhor caso: O(n+k)
 *
 * Implementação baseada na obra: MORIN, P. Open Data Structures (in Java).
 * [s.l]: [s.n], 2020. 322 p.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class CountingSort {
    
    public static void sort( int[] array, int k ) {
        
        // tamanho do array
        int n = array.length;
        
        // array de contagem e acumulação
        int[] c = new int[k+1];
        
        // array de transferência
        int[] b = new int[n];
        
        // conta a quantidade de elementos
        for ( int i = 0; i < n; i++ ) {
            c[array[i]]++;
        }
        
        // nesse ponto a ordenação já está pronta para um array de inteiros,
        // mas se existem valores associados às posições, precisamos
        // tornar a ordenação estável. sendo assim:
        
        // acumula na posição atual o valor da contagem da anterior
        for ( int i = 1; i <= k; i++ ) {
            c[i] += c[i-1];
        }
        
        // varre o array de trás para frente para reposicionar os elementos
        for ( int i = n-1; i >= 0; i-- ) {
            
            // remove um item
            c[array[i]]--;
            
            // na posição de contagem, copia o valor em array
            b[c[array[i]]] = array[i];
            
        }
        
        // copia o conteúdo de b em array (chamada nativa)
        System.arraycopy( b, 0, array, 0, n );
        
    }
    
}
