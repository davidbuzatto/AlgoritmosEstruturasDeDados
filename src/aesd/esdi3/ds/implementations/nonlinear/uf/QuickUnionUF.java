/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdi3.ds.implementations.nonlinear.uf;

/**
 * Implementação do tipo de dados union-find (disjoint-sets) com união rápida,
 * representando a estrutura como uma árvore.
 * 
 * Ordem de crescimento das operações:
 *     Union -> O(h).
 *     Find -> O(h).
 *     Obs: h é a altura da árvore.
 * 
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 4. ed.
 * Boston: Pearson Education, 2011. 955 p.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class QuickUnionUF {

    // pais dos elementos
    // parent[i] = pai de i
    private int[] parent;
    
    // quantidade de componentes/conjuntos
    private int count;

    /**
     * Cria uma estrutura union-find com n elementos, sendo que, inicialmente, 
     * cada elemento faz parte de seu próprio conjunto.
     *
     * @param n quantidade de elementos
     * @throws IllegalArgumentException se n for menor que zero
     */
    public QuickUnionUF( int n ) {
        
        if ( n < 0 ) {
            throw new IllegalArgumentException( "Number of elements must be nonnegative" );
        }
        
        parent = new int[n];
        count = n;
        
        for ( int i = 0; i < n; i++ ) {
            parent[i] = i;
        }
        
    }

    /**
     * Retorna o componente que p faz parte.
     *
     * @param p um elemento
     * @return o componente que contém p
     * @throws IllegalArgumentException se p for inválido
     */
    public int find( int p ) throws IllegalArgumentException {
        
        validate( p );
        
        // caminha até a raiz
        while ( p != parent[p] ) {
            p = parent[p];
        }
        
        return p;
        
    }
    
    /**
     * Une o conjunto que contém p com o conjunto que contém q
     *
     * @param p um elemento
     * @param q outro elemento
     * @throws IllegalArgumentException caso p ou q sejam inválidos
     */
    public void union( int p, int q ) {
        
        // mesma raiz para p e q
        int rootP = find( p );
        int rootQ = find( q );
        
        if ( rootP == rootQ ) {
            return;
        }
        
        parent[rootP] = rootQ;
        count--;
        
    }
    
    /**
     * Verifica se dois elementos estão no mesmo componente.
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
    
    private void validate( int p ) throws IllegalArgumentException {
        int n = parent.length;
        if ( p < 0 || p >= n ) {
            throw new IllegalArgumentException( "index " + p + " is not between 0 and " + ( n - 1 ) );
        }
    }

}
