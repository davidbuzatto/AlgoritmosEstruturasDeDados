package aesd.ds.implementations.nonlinear.rangequery;

/**
 * Implementação de uma Fenwick Tree (também chamada de Binary Indexed Tree
 * ou BIT), uma estrutura para consultas de soma de prefixo e atualizações
 * pontuais eficientes sobre um array de valores numéricos.
 *
 * A ideia central é que cada posição i do array interno tree[] não guarda o
 * valor bruto de uma única posição, mas sim a soma de um intervalo cujo
 * tamanho é a menor potência de 2 que divide i (o chamado "bit menos
 * significativo ativo" de i, calculado por i & -i em complemento de dois).
 * Isso faz cada atualização ou consulta tocar apenas O(log n) desses
 * intervalos: subir na árvore somando i += i & -i propaga uma atualização
 * pelos intervalos que contêm i; descer somando i -= i & -i acumula uma
 * soma de prefixo a partir dos intervalos que compõem [1, i].
 *
 * É didaticamente interessante por resolver, com uma estrutura simples (um
 * único array), o mesmo problema de consulta e atualização eficientes que
 * SegmentTree resolve com uma árvore explícita — a um custo de código bem
 * menor, porém restrito a operações invertíveis (como soma), diferente de
 * SegmentTree, que também serve para operações não invertíveis (como
 * mínimo/máximo).
 *
 * A indexação é 1-based (a posição 0 é usada apenas como sentinela e nunca
 * armazena dado real), pois i & -i não faz sentido para i = 0.
 *
 * Crescimento do uso de memória em relação ao tamanho da entrada: O(n).
 *
 * Complexidade:
 *   Construção a partir de um array existente: O(n)
 *   Atualização pontual (update/set): O(log n)
 *   Consulta de soma de prefixo/intervalo (prefixSum/rangeSum): O(log n)
 *
 * @author Prof. Dr. David Buzatto
 */
public class FenwickTree {

    // array interno (1-based); tree[i] guarda a soma de um intervalo de
    // tamanho (i & -i) terminando em i
    private final long[] tree;

    // quantidade de posições lógicas (1..n)
    private final int n;

    /**
     * Constrói uma Fenwick Tree vazia (todos os valores iniciam em zero)
     * para n posições.
     *
     * @param n A quantidade de posições.
     * @throws IllegalArgumentException se n for negativo.
     */
    public FenwickTree( int n ) throws IllegalArgumentException {

        if ( n < 0 ) {
            throw new IllegalArgumentException( "n must be nonnegative" );
        }

        this.n = n;
        tree = new long[n + 1];

    }

    /**
     * Constrói uma Fenwick Tree a partir dos valores de values, que passam
     * a ocupar as posições 1..values.length.
     *
     * @param values Os valores iniciais (values[0] ocupa a posição 1,
     * values[1] a posição 2, e assim por diante).
     */
    public FenwickTree( long[] values ) {

        n = values.length;
        tree = new long[n + 1];

        // constrói em O(n): soma cada valor apenas na posição i e deixa
        // que cada i "empurre" sua soma acumulada para o próximo ancestral
        // (i + (i & -i)) numa única passada, ao invés de chamar update()
        // n vezes (o que custaria O(n log n))
        for ( int i = 1; i <= n; i++ ) {

            tree[i] += values[i - 1];

            int parent = i + ( i & -i );
            if ( parent <= n ) {
                tree[parent] += tree[i];
            }

        }

    }

    /**
     * Soma delta ao valor atual da posição i (1-based).
     *
     * @param i A posição a ser atualizada.
     * @param delta O valor a ser somado ao valor atual da posição i.
     * @throws IllegalArgumentException se i estiver fora do intervalo
     * [1, n].
     */
    public void update( int i, long delta ) throws IllegalArgumentException {

        validate( i );

        // sobe na árvore, somando delta em cada intervalo que cobre i,
        // avançando sempre para o próximo ancestral via i += i & -i
        for ( ; i <= n; i += i & -i ) {
            tree[i] += delta;
        }

    }

    /**
     * Define o valor absoluto da posição i (1-based), substituindo o valor
     * atual.
     *
     * @param i A posição a ser definida.
     * @param value O novo valor da posição i.
     * @throws IllegalArgumentException se i estiver fora do intervalo
     * [1, n].
     */
    public void set( int i, long value ) throws IllegalArgumentException {
        update( i, value - pointQuery( i ) );
    }

    /**
     * Retorna a soma dos valores no intervalo [1, i] (soma de prefixo).
     *
     * @param i O fim do intervalo (1-based).
     * @return A soma dos valores entre as posições 1 e i, inclusive.
     * @throws IllegalArgumentException se i estiver fora do intervalo
     * [0, n].
     */
    public long prefixSum( int i ) throws IllegalArgumentException {

        if ( i < 0 || i > n ) {
            throw new IllegalArgumentException( "index " + i + " is not between 0 and " + n );
        }

        long sum = 0;

        // desce na árvore, acumulando os intervalos que compõem [1, i],
        // avançando sempre para o intervalo anterior via i -= i & -i
        for ( ; i > 0; i -= i & -i ) {
            sum += tree[i];
        }

        return sum;

    }

    /**
     * Retorna a soma dos valores no intervalo [lo, hi] (1-based, inclusive).
     *
     * @param lo O início do intervalo.
     * @param hi O fim do intervalo.
     * @return A soma dos valores entre as posições lo e hi, inclusive.
     * @throws IllegalArgumentException se lo ou hi forem inválidos, ou se
     * lo for maior que hi.
     */
    public long rangeSum( int lo, int hi ) throws IllegalArgumentException {

        validate( lo );
        validate( hi );

        if ( lo > hi ) {
            throw new IllegalArgumentException( "lo must be less than or equal to hi" );
        }

        return prefixSum( hi ) - prefixSum( lo - 1 );

    }

    /**
     * Retorna o valor atual da posição i (1-based).
     *
     * @param i A posição consultada.
     * @return O valor atual da posição i.
     * @throws IllegalArgumentException se i estiver fora do intervalo
     * [1, n].
     */
    public long pointQuery( int i ) throws IllegalArgumentException {
        return rangeSum( i, i );
    }

    /**
     * Retorna a quantidade de posições da Fenwick Tree.
     *
     * @return a quantidade de posições
     */
    public int getSize() {
        return n;
    }

    private void validate( int i ) throws IllegalArgumentException {
        if ( i < 1 || i > n ) {
            throw new IllegalArgumentException( "index " + i + " is not between 1 and " + n );
        }
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        for ( int i = 1; i <= n; i++ ) {
            sb.append( pointQuery( i ) );
            if ( i < n ) {
                sb.append( " " );
            }
        }

        return sb.toString();

    }

}
