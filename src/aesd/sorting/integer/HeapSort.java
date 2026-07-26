package aesd.sorting.integer;

import aesd.utils.Utils;

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
 *     - Para raiz na posição 0, a geração das posições seria:
 *          * Pai: (posição do filho - 1) / 2
 *          * Filho esquerda: posição do pai * 2 + 1
 *          * Filho direita: posicao do pai * 2 + 2
 *     - Nessa implementação usaremos uma posição lógica e um ajuste para
 *       a posição física.
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

    public static void sort( int[] array ) {

        // IMPORTANTE! O índice 1 é a raiz nesta implementação! Como o array
        // do Java é 0-based, todo acesso físico ao array é feito na posição
        // (índice lógico - 1). A matemática de índice do heap (k/2, 2*k,
        // 2*k+1) continua toda em base 1, igual ao material.

        // Abordagem 2 do Heap Sort
        // Da direita para a esquerda,
        // construir sub-árvores e unir cada uma delas,
        // utilizando o top-down (sink => afundar)

        // controla a varredura do array a partir
        // do meio, indo para o início, lendo nó a nó
        int k;

        // n é a quantidade de elementos do heap, que ocupa as posições
        // lógicas [1; n] (fisicamente, [0; n-1])
        int n = array.length;

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
            Utils.swap( array, 0, n-1 );
            n--;

            // afunda a nova raiz
            sink( array, 1, n );

        }

    }

    /*
     * Algoritmo para organização do heap.
     *
     * Flutua o nó k para a posição correta (baixo para cima)
     * se necessário (se for maior que o seu pai).
     *
     * Não é usado por sort() (que constrói o heap todo via sink(), a
     * abordagem 2 descrita acima), mas fica disponível como referência da
     * abordagem 1 e para reuso em estruturas como filas de prioridade, que
     * inserem um elemento por vez e precisam flutuá-lo.
     */
    private static void swim( int[] array, int k ) {

        // se o nó k não é a raiz (nó 1) e
        // seu pai (k/2) for menor que ele
        // (acesso físico ao array sempre com -1, pois k e k/2 são índices lógicos em base 1)
        while ( k > 1 && array[k/2 - 1] < array[k-1] ) {

            // troca o pai pelo filho
            Utils.swap( array, k/2 - 1, k-1 );

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
    private static void sink( int[] array, int k, int n ) {

        // posição do filho
        int j;

        // se o filho está numa posição válida (dentro do limite)
        while ( 2*k <= n ) {

            // filho da esquerda
            j = 2*k;

            // se j está dentro do limite
            // e o valor da posição j é menor que
            // o valor do seu irmão
            // (acesso físico ao array sempre com -1, pois j e k são índices lógicos em base 1)
            if ( j < n && array[j-1] < array[j] ) {

                // muda para o irmão (filho da direita)
                // pois o filho à esquerda é menor que o maior filho (o da direita),
                // e menor que o irmão (o irmão pode ir para o lugar
                // do pai se for maior que ele e o heap se mantém)
                j++;

            }

            // se o valor do pai for maior ou igual
            // ao valor do filho da direita (que faltou ser testado).
            // está ok, pois atende à regra do max-heap e termina
            // o loop
            if ( array[k-1] >= array[j-1] ) {
                break;
            }

            // caso contrário (se o nó k for menor que o nó j),
            // troca o pai pelo filho da direita (nó j)
            Utils.swap( array, k-1, j-1 );

            // indica o novo pai como o filho da direita
            // para dar continuidade ao processo de afundamento
            k = j;

        }

    }
    
}
