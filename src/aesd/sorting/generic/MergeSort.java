package aesd.sorting.generic;

/**
 * Ordenação por intercalação (Merge Sort)
 *
 * Dividir para conquistar.
 *
 * Divisão da sequência em partes menores para
 * facilitar a ordenação.
 *
 * União de sequências menores já ordenadas,
 * gerando sequências maiores ordenadas.
 *
 * Os valores dos dados não-interferem na execução do
 * algoritmo.
 *
 * Crescimento do número de comparações em relação ao
 * tamanho de entrada:
 *     – linear-logarítmico
 *
 * Crescimento do número de trocas em relação ao
 * tamanho de entrada:
 *     – linear-logarítmico
 *
 * Crescimento do uso de memória em relação ao
 * tamanho da entrada: linear
 *
 * In-place? Não
 *  Estável? Sim
 *
 * Complexidade:
 *       Pior caso: O(n log n)
 *      Caso médio: O(n log n)
 *     Melhor caso: O(n log n)
 *
 * @author Prof. Dr. David Buzatto
 */
public class MergeSort {
    
    @SuppressWarnings( "unchecked" )
    public static <Type extends Comparable<Type>> void sort( Type[] array ) {
        
        // tamanho do array
        int n = array.length;
        
        // aloca o espaço auxiliar para
        // armazenar os valores que serão ordenados
        Type[] tempMS = (Type[]) new Object[n];

        // chama o Merge Sort (neste caso, a versão Top-Down (recursiva))
        topDown( array, 0, n - 1, tempMS );
        
        // ou 
        //bottomUp( array, 0, length - 1, tempMS );
    
    }
    
    /*
     * Merge Sort Top-Down.
     *
     * É um algoritmo recursivo que gera uma árvore
     * (árvore merge) e intercala os nós das subárvores
     * esquerda e direita de um determinado nó, deixando-o
     * ordenado.
     */
    private static <Type extends Comparable<Type>> void topDown( 
            Type[] array, int start, int end, Type[] tempMS ) {

       // indica o meio do array
       // usado para particionar o problema
       int middle;

       // se inicio for menor que fim indica
       // que ainda existem intervalos com
       // dois ou mais itens, sendo assim...
       if ( start < end ) {

           // calcula o meio do intervalo
           middle = ( start + end ) / 2;

           // invoca o algoritmo no intervalo da esquerda
           topDown( array, start, middle, tempMS );

           // invoca o algoritmo no intervalo da direita
           topDown( array, middle + 1, end, tempMS );

           // intercalação (merge) das sub-árvores
           // do nó atual da árvore merge
           merge( array, start, middle, end, tempMS );

       }

    }

    /*
     * Merge Sort Bottom-Up.
     *
     * Algoritmo iterativo para a ordenação por merge.
     * O algoritmo inicia pelas folhas da árvore, executando
     * a intercalação.
     */
    private static <Type extends Comparable<Type>> void bottomUp( 
            Type[] array, int start, int end, Type[] tempMS ) {

       // m controla o espaçamento que é proporcional ao nível
       // atual da árvore, ou seja, 1 para o último nível,
       // 2 para o penúltimo, 4 para o antepenúltimo e assim
       // por diante.
       int m;

       // controla o início do intervalo que será ordenado
       int i;

       // enquanto m for menor ou igual à posição final,
       // seu valor é dobrado a cada iteração
       for ( m = 1; m <= end; m *= 2 ) {

           // a partir do início do array e enquanto
           // i for menor que o final menos o valor de m
           // (que controla o espaçamento do nível atual)
           // incrementa i em duas vezes o espaçamento
           // este for gera as posições iniciais e finais
           // de cada nó
           for ( i = start; i <= end - m; i += 2*m ) {

               // faz o merge do intervalo atual
               // sendo o início igual a i, o meio igual
               // a i mais o espaçamento menos 1 e o final
               // o menor valor entre o final do intervalo
               // baseado em i (i+2*m-1) e o final. este mínimo
               // evita o estouro do intervalo para além
               // dos limites válidos do array
               merge( array, i, i+m-1, Math.min( i+2*m-1, end ), tempMS );

           }
       }
    }

    /*
     * Intercalação
     */
    private static <Type extends Comparable<Type>> void merge( 
            Type[] array, int start, int middle, int end, Type[] tempMS ) {

       // i marca o início do intervalo que será ordenado
       int i = start;

       // j marca o limite do intervalo que será ordenado
       int j = middle + 1;

       // k é usado para iterar entre o início e o fim
       int k;

       // iterando pelo intervalo [inicio; fim]
       // fazendo a cópia dos dados do array
       // original para o temporário
       for ( k = start; k <= end; k++ ) {
           tempMS[k] = array[k];
       }

       // iterando pelo intervalo [inicio; fim]
       for ( k = start; k <= end; k++ ) {

           // se i passou do meio, quer dizer
           // que a parte controlada por ele
           // já foi ordenada, então copia-se
           // o valor da posição j e anda para a
           // direita na mesma ou seja
           // este trecho faz a cópia do
           // restante da parte controlada por j
           // neste ponto a parte j também já está
           // ordenada
           if ( i > middle ) {
               array[k] = tempMS[j++];

               // se j passou do fim, quer dizer
               // que a parte controlada por ele
               // já foi ordenada, então copia-se
               // o valor da posição i e anda para a
               // direita na mesma ou seja
               // este trecho faz a cópia do
               // restante da parte controlada por i
               // neste ponto a parte i também já está
               // ordenada
           } else if( j > end ) {
               array[k] = tempMS[i++];

               // se o valor da posição j é menor
               // que o valor da posição i, quer dizer
               // que os valores estão trocados, sendo assim
               // copia-se o valor da posição j para o array original
               // e avança j
           } else if( tempMS[j].compareTo( tempMS[i] ) < 0 ) {
               array[k] = tempMS[j++];

               // se o valor da posição i é menor ou igual
               // que o valor da posição j, quer dizer
               // que os valores estão ordenados, sendo assim
               // copia-se o valor da posição i para o array original
               // e avança i
           } else {
               array[k] = tempMS[i++];
           }

       }

    }
    
}
