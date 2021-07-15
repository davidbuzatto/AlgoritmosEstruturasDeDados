/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.ds.implementations.nonlinear.uf;

/**
 * Implementação do tipo de dados union-find (disjoint-sets) com busca rápida.
 * 
 * Ordem de crescimento das operações:
 *     Union -> O(n).
 *     Find -> O(1).
 * 
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 4. ed.
 * Boston: Pearson Education, 2011. 955 p.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class QuickFindUF {

    // identificadores dos componentes
    // id[i] = identificador do componente i
    private int[] id;
    
    // quantidade de componentes/conjuntos
    private int count;

    /**
     * Cria uma estrutura union-find com n elementos, sendo que, inicialmente, 
     * cada elemento faz parte de seu próprio conjunto.
     *
     * @param n quantidade de elementos
     * @throws IllegalArgumentException se n for menor que zero
     */
    public QuickFindUF( int n ) throws IllegalArgumentException {
        
        if ( n < 0 ) {
            throw new IllegalArgumentException( "Number of elements must be nonnegative" );
        }
        
        id = new int[n];
        count = n;
        
        for ( int i = 0; i < n; i++ ) {
            id[i] = i;
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
        return id[p];
    }

    /**
     * Une o conjunto que contém p com o conjunto que contém q
     *
     * @param p um elemento
     * @param q outro elemento
     * @throws IllegalArgumentException caso p ou q sejam inválidos
     */
    public void union( int p, int q ) throws IllegalArgumentException {
        
        // em qual componente?
        int pID = find( p );
        int qID = find( q );
        
        // mesmo componente? não faz nada
        if ( pID == qID ) {
            return;
        }
        
        // componentes distintos
        // insere componente p no componente q
        for ( int i = 0; i < id.length; i++ ) {
            if ( id[i] == pID ) {
                id[i] = qID;
            }
        }
        
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
        int n = id.length;
        if ( p < 0 || p >= n ) {
            throw new IllegalArgumentException( "index " + p + " is not between 0 and " + ( n - 1 ) );
        }
    }
    
}
