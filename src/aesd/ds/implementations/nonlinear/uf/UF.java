package aesd.ds.implementations.nonlinear.uf;

/**
 * Definição da API da estrutura de dados Union-Find, também conhecida como
 * disjoint-set ou merge-find set.
 *
 * As subclasses concretas formam uma progressão didática: quick-find,
 * depois quick-union, depois união ponderada pelo tamanho e, por fim,
 * união ponderada pelo ranque com compressão de caminho. Cada uma delas
 * resolve uma limitação de complexidade deixada pela implementação
 * anterior.
 *
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 4. ed.
 * Boston: Pearson Education, 2011. 955 p.
 *
 * @author Prof. Dr. David Buzatto
 */
public abstract class UF {

    // quantidade de componentes/conjuntos
    protected int count;

    /**
     * Retorna o componente que p faz parte.
     *
     * @param p um elemento
     * @return o componente que contém p
     * @throws IllegalArgumentException se p for inválido
     */
    public abstract int find( int p ) throws IllegalArgumentException;

    /**
     * Une o conjunto que contém p com o conjunto que contém q.
     *
     * @param p um elemento
     * @param q outro elemento
     * @throws IllegalArgumentException caso p ou q sejam inválidos
     */
    public abstract void union( int p, int q ) throws IllegalArgumentException;
    
    /**
     * Verifica se dois elementos estão no mesmo componente.
     *
     * O custo desta operação depende diretamente do custo de find() em cada
     * subclasse: O(1) na quick-find, O(altura da árvore) na quick-union, e
     * quase O(1) amortizado nas versões ponderadas com compressão de
     * caminho.
     *
     * @param p um elemento
     * @param q outro elemento
     * @return verdadeiro caso p e q estejam no mesmo componente, falso caso
     * contrário
     * @throws IllegalArgumentException caso p ou q sejam inválidos
     */
    public boolean connected( int p, int q ) throws IllegalArgumentException {
        return find( p ) == find( q );
    }
    
    /**
     * Retorna a quantidade de conjuntos.
     *
     * @return o número de conjuntos.
     */
    public int count() {
        return count;
    }
    
    protected void validate( int p, int[] idOrParent ) throws IllegalArgumentException {
        int n = idOrParent.length;
        if ( p < 0 || p >= n ) {
            throw new IllegalArgumentException( "index " + p + " is not between 0 and " + ( n - 1 ) );
        }
    }

}
