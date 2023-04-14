package aesd.sorting.generic;

import aesd.sorting.utils.SortingUtils;

/**
 * Ordenação usando um Heap (Heap Sort)
 *
 * Esse algoritmo de ordenação utiliza um heap binário máximo.
 *
 * Critério de ordenação:
 *     - Max-Heap:
 *         * Elemento pai sempre maior ou igual aos filhos
 *
 *     - Min-Heap:
 *         * Elemento pai sempre menor ou igual aos filhos
 *
 *     - Chaves armazenadas nos nós
 *
 * Utilizaremos apenas:
 *     - Árvores binárias (até dois filhos)
 *     - Completa: elementos sem filhos apenas no último nível
 *      (e anterior, quando o último nível não está completo)
 *     - Max-heap
 *
 * Árvore binária completa:
 *     - Armazenamento direto em array!
 *     - Raiz na posição 1
 *     - Último elemento na posição tamanho - 1
 *     - Manipulação dos índices:
 *          * Pai: posição do filho / 2
 *          * Filho esquerda: posição do pai * 2
 *          * Filho direita: posicao do pai * 2 + 1
 *
 *     - Para raiz na posição 0, a geração das posições seria
 *          * Pai: (posição do filho - 1) / 2
 *          * Filho esquerda: posição do pai * 2 + 1
 *          * Filho direita: posicao do pai * 2 + 2
 *
 * Elemento violando a condição heap
 *     - Valor maior que o pai
 *        * O elemento precisa "subir" na árvore
 *        * Bottom-Up heapify (swim => flutuar)
 *     - Valor menor que os filhos (um ou dois)
 *        * O elemento precisa "descer" na árvore
 *        * Top-Down heapify (sink => afundar)
 *
 * Ordenação utilizando a estrutura heap
 *
 * Duas etapas:
 *     - Construção da estrutura max-heap
 *     - Ordenação pela concatenação dos valores máximo
 *       obtidos
 *         * Iteração: removendo um elemento (maior) por vez
 *           da heap
 *
 * Abordagem 1: da esquerda para a direita,
 * adicionar um elemento por vez na heap à
 * esquerda, utilizando o bottom-up
 *
 * Abordagem 2 (mais eficiente): da direta para a esquerda,
 * construir sub-árvores e unir cada uma delas,
 * utilizando o top-down
 *
 * In-place? Sim
 *  Estável? Não
 *
 * Complexidade:
 *       Pior caso: O(n lg n)
 *      Caso médio: O(n lg n)
 *     Melhor caso: O(n lg n)
 *
 * @author Prof. Dr. David Buzatto
 */
public class HeapSort {

    public static <Type extends Comparable<Type>> void sort( Type[] array ) {
        
        // IMPORTANTE! O índice 1 é a raiz nesta implementação!

        // Abordagem 2 do Heap Sort
        // Da direta para a esquerda,
        // construir sub-árvores e unir cada uma delas,
        // utilizando o top-down (sink => afundar)

        // controla a varredura do array a partir
        // do meio, indo para o início, lendo nó a nó
        int k;

        // o espaço útil do heap é o intervalo [1; n-1]
        // pois para o cálculo dos filhos e do pai funcionar
        // como está na implementação "padrão"
        // é necessário que a primeira posição (raiz) seja 1
        int n = array.length - 1;

        // percorre o array da metade ao início, organizando
        // o heap (afundando) cada elemento. a metade após
        // n/2 são as folhas, que não podem afundar.
        for ( k = n/2; k >= 1; k-- ) {
            sink( array, k, n );
        }

        // percorre o heap da última posição
        // até a primeira
        while ( n > 1 ) {

            // troca a raiz (maior elemento) pela
            // posição atual e diminiu o tamanho
            // do heap que será processado
            SortingUtils.swap( array, 1, n-- );

            // afunda a nova raiz
            sink( array, 1, n );

        }
    
    }
    
    /*
     * Algoritmo para organização do heap.
     *
     * Flutua o nó k para a posição correta (baixo para cima)
     * se necessário (se for maior que o seu pai).
     */
    private static <Type extends Comparable<Type>> void swim( Type[] array, int k ) {

        // se o nó k não é a raiz (nó 1) e
        // seu pai (k/2) for menor que ele
        while ( k > 1 && array[k/2].compareTo( array[k] ) < 0 ) {

            // troca o pai pelo filho
            SortingUtils.swap( array, k/2, k );

            // indica que o nó que será processado
            // na próxima iteração é o pai do nó k atual
            k = k/2;

        }

    }

    /*
     * Algoritmo para organização do heap.
     *
     * Afunda o nó k para a posição correta (cima para baixo)
     * se necessário (se for menor que algum de seus filhos).
     */
    private static <Type extends Comparable<Type>> void sink( Type[] array, int k, int n ) {

        // posição do filho
        int j;

        // se o filho está numa posição válida (dentro do limite)
        while ( 2*k <= n ) {

            // filho da esquerda
            j = 2*k;

            // se j está dentro do limite
            // e o valor da posição j é menor que
            // o valor do seu irmão
            if ( j < n && array[j].compareTo( array[j+1] ) < 0 ) {

                // muda para o irmão (filho da direita)
                // pois o filho à esquerda é menor que o mai
                // e menor que o irmão (o irmão pode ir para o lugar
                // do pai se for maior que ele e o heap se mantém)
                j++;

            }

            // se o valor do pai for maior ou igual
            // ao valor do filho da direita (que faltou ser testado).
            // está ok, pois atende à regra do max-heap e termina
            // o loop
            if ( array[k].compareTo( array[j] ) >= 0 ) {
                break;
            }

            // caso contrário (se o nó k for menor que o nó j),
            // troca o pai pelo filho da direita (nó j)
            SortingUtils.swap( array, k, j );

            // indica o novo pai como o filho da direita
            // para dar continuidade ao processo de afundamento
            k = j;

        }

    }
    
}
