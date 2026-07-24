package aesd.ds.implementations.nonlinear.rangequery;

/**
 * Implementação de uma Segment Tree (árvore de segmentos) para consultas de
 * soma em intervalo e atualizações pontuais eficientes sobre um array de
 * valores numéricos.
 *
 * Cada nó da árvore representa um intervalo [start, end] do array original
 * e guarda a soma de seus elementos; os dois filhos de um nó representam,
 * cada um, metade desse intervalo ([start, meio] e [meio+1, end]), até
 * chegar às folhas, que representam intervalos de um único elemento. Uma
 * consulta de soma sobre um intervalo [l, r] qualquer combina no máximo
 * O(log n) desses nós (apenas os cujo intervalo está inteiramente contido
 * em [l, r], sem precisar descer até as folhas); uma atualização pontual
 * só precisa percorrer o único caminho da raiz até a folha correspondente
 * (também O(log n)), recompondo a soma de cada nó nesse caminho.
 *
 * A árvore é armazenada em um array (mesma ideia de indexação usada em
 * MinPriorityQueue/MaxPriorityQueue): o nó 1 é a raiz, e os filhos do nó k
 * são 2k (esquerdo) e 2k+1 (direito). O tamanho 4*n é uma cota superior
 * segura para esse array, independentemente de n ser ou não uma potência
 * de 2.
 *
 * Esta implementação é especializada em soma, mas a mesma estrutura (basta
 * trocar a forma como um nó combina seus dois filhos) serve para qualquer
 * operação associativa, como mínimo, máximo ou mdc.
 *
 * Crescimento do uso de memória em relação ao tamanho da entrada: O(n).
 *
 * Complexidade:
 *   Construção: O(n)
 *   Atualização pontual (update): O(log n)
 *   Consulta de soma em intervalo (query): O(log n)
 *
 * @author Prof. Dr. David Buzatto
 */
public class SegmentTree {

    // array interno (1-based) com a soma de cada nó da árvore
    private final long[] tree;

    // quantidade de posições lógicas (0..n-1) do array original
    private final int n;

    /**
     * Constrói uma Segment Tree a partir dos valores de values.
     *
     * @param values Os valores iniciais.
     */
    public SegmentTree( long[] values ) {

        n = values.length;
        tree = new long[4 * Math.max( n, 1 )];

        if ( n > 0 ) {
            build( values, 1, 0, n - 1 );
        }

    }

    // constrói recursivamente o nó responsável pelo intervalo [start, end]
    // do array original, guardado na posição node do array interno
    private void build( long[] values, int node, int start, int end ) {

        if ( start == end ) {
            tree[node] = values[start];
            return;
        }

        int mid = start + ( end - start ) / 2;

        build( values, 2 * node, start, mid );
        build( values, 2 * node + 1, mid + 1, end );

        tree[node] = tree[2 * node] + tree[2 * node + 1];

    }

    /**
     * Define o valor absoluto da posição i (0-based), substituindo o valor
     * atual.
     *
     * @param i A posição a ser definida.
     * @param value O novo valor da posição i.
     * @throws IllegalArgumentException se i estiver fora do intervalo
     * [0, n-1].
     */
    public void update( int i, long value ) throws IllegalArgumentException {

        if ( i < 0 || i >= n ) {
            throw new IllegalArgumentException( "index " + i + " is not between 0 and " + ( n - 1 ) );
        }

        update( 1, 0, n - 1, i, value );

    }

    // atualiza o único caminho da raiz (node) até a folha que representa a
    // posição idx, recompondo a soma de cada nó no caminho de volta
    private void update( int node, int start, int end, int idx, long value ) {

        if ( start == end ) {
            tree[node] = value;
            return;
        }

        int mid = start + ( end - start ) / 2;

        if ( idx <= mid ) {
            update( 2 * node, start, mid, idx, value );
        } else {
            update( 2 * node + 1, mid + 1, end, idx, value );
        }

        tree[node] = tree[2 * node] + tree[2 * node + 1];

    }

    /**
     * Retorna a soma dos valores no intervalo [l, r] (0-based, inclusive).
     *
     * @param l O início do intervalo.
     * @param r O fim do intervalo.
     * @return A soma dos valores entre as posições l e r, inclusive.
     * @throws IllegalArgumentException se l ou r forem inválidos, ou se l
     * for maior que r.
     */
    public long query( int l, int r ) throws IllegalArgumentException {

        if ( l < 0 || r >= n || l > r ) {
            throw new IllegalArgumentException( "invalid range [" + l + ", " + r + "]" );
        }

        return query( 1, 0, n - 1, l, r );

    }

    // combina os nós cujo intervalo [start, end] está inteiramente contido
    // em [l, r] (caso base "totalmente dentro"), ignora os que não têm
    // nenhuma interseção com [l, r] (caso base "totalmente fora") e desce
    // recursivamente nos que se sobrepõem apenas parcialmente
    private long query( int node, int start, int end, int l, int r ) {

        // totalmente fora do intervalo consultado
        if ( r < start || end < l ) {
            return 0;
        }

        // totalmente dentro do intervalo consultado
        if ( l <= start && end <= r ) {
            return tree[node];
        }

        // sobreposição parcial: desce nos dois filhos e combina
        int mid = start + ( end - start ) / 2;

        return query( 2 * node, start, mid, l, r ) +
                query( 2 * node + 1, mid + 1, end, l, r );

    }

    /**
     * Retorna a quantidade de posições da Segment Tree.
     *
     * @return a quantidade de posições
     */
    public int getSize() {
        return n;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        for ( int i = 0; i < n; i++ ) {
            sb.append( query( i, i ) );
            if ( i < n - 1 ) {
                sb.append( " " );
            }
        }

        return sb.toString();

    }

}
